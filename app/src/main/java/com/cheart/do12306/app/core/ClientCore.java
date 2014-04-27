package com.cheart.do12306.app.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.Header;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import com.cheart.do12306.app.util.StringHelper;

public class ClientCore {
    private static final String TAG = "ClientCore";
    public static String JSESSIONID = "";
    public static String BIGipServerotn = "";
    private static X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    public ClientCore() {
    }

    public ClientCore(String JSESSIONID, String BIGipServerotn) {
        this.JSESSIONID = JSESSIONID;
        this.BIGipServerotn = BIGipServerotn;
    }

    public static HttpClient getHttpClient(Context context) throws KeyManagementException,
            NoSuchAlgorithmException {
      /*  SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
        ClientConnectionManager ccm = new DefaultHttpClient()
                .getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.connection.timeout", Integer.valueOf(8000));
        params.setParameter("http.socket.timeout", Integer.valueOf(8000));
        HttpClient httpclient = new DefaultHttpClient(ccm, params);
        return httpclient;*/
        //#2

        HttpClient httpClient = null;

        AssetManager am = context.getAssets();
        try {
            Log.v(TAG, "IN RUN");
            InputStream in = am.open("srca.cer");
            //读取证书
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");  //问1
            Certificate cer = cerFactory.generateCertificate(in);
            //创建一个证书库，并将证书导入证书库
            KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");   //问2
            keyStore.load(null, null);
            keyStore.setCertificateEntry("trust", cer);
            //把咱的证书库作为信任证书库
            SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore);
            Scheme sch = new Scheme("https", socketFactory, 443);
//完工
            HttpClient mHttpClient = new DefaultHttpClient();
            mHttpClient.getConnectionManager().getSchemeRegistry().register(sch);
            httpClient = mHttpClient;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {

        }
        return httpClient;

    }

	/*
     * set header for http get method
	 */

    public void setHeader(Map<String, String> headerMap, HttpGet get) {
        if (headerMap != null && get != null) {
            for (Map.Entry<String, String> e : headerMap.entrySet()) {
                get.setHeader(e.getKey(), e.getValue());
            }
        }
    }

	/*
     * set header for http post method
	 */

    public void setHeader(Map<String, String> headerMap, HttpPost post) {
        if (headerMap != null && post != null) {
            for (Map.Entry<String, String> e : headerMap.entrySet()) {
                post.setHeader(e.getKey(), e.getValue());
            }
        }
    }

    public HttpGet getHttpGet(String url, Map<String, String> cookieMap) {
        HttpGet httpGet = new HttpGet(url);
        if (cookieMap != null) {

            String cookie_str = "JSESSIONID=" + this.JSESSIONID
                    + "; BIGipServerotn=" + this.BIGipServerotn;
            cookie_str += getCookieStringFormMap(cookieMap);
            httpGet.addHeader("Cookie", cookie_str);
        } else {
            httpGet.addHeader("Cookie", "JSESSIONID=" + this.JSESSIONID
                    + ";BIGipServerotn=" + this.BIGipServerotn);
        }
        return httpGet;
    }

    public HttpPost getHttpPost(String url, Map<String, String> cookieMap) {
        HttpPost httpPost = new HttpPost(url);
        if (!StringHelper.isEmptyString(this.JSESSIONID)
                && !StringHelper.isEmptyString(this.BIGipServerotn)) {
            if (cookieMap != null) {
                String cookieStr = "JSESSIONID=" + this.JSESSIONID
                        + "; BIGipServerotn=" + this.BIGipServerotn;
                for (Map.Entry<String, String> e : cookieMap.entrySet()) {
                    cookieStr += "; " + e.getKey() + "=" + e.getValue();

                }
                httpPost.addHeader("Cookie", cookieStr);
            } else {
                httpPost.addHeader("Cookie", "JSESSIONID=" + this.JSESSIONID
                        + "; BIGipServerotn=" + this.BIGipServerotn);

            }
        }
        return httpPost;

    }

    public void getCookie(Context context, String url, Map<String, String> headerMap,
                          Map<String, String> cookieMap) {

        HttpGet httpGet = getHttpGet(url, cookieMap);
        setHeader(headerMap, httpGet);

        try {
            HttpClient httpClient = getHttpClient(context);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].getName().equals("Set-Cookie")) {
                    String cookie = headers[i].getValue();
                    String cookieName = cookie.split("=")[0];
                    String cookieValue = cookie.split("=")[1].split(";")[0];
                    if (cookieName.equals("JSESSIONID")) {
                        this.JSESSIONID = cookieValue;
                    }
                    if (cookieName.equals("BIGipServerotn")) {
                        this.BIGipServerotn = cookieValue;
                    }
                }
            }

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCookieStringFormMap(Map<String, String> cookieMap) {

        String result = "";
        for (Map.Entry<String, String> e : cookieMap.entrySet()) {
            result += "; " + e.getKey() + "=" + e.getValue();
        }
        return result;
    }

    public List<NameValuePair> setParams(Map<String, String> paramsMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> e : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
        }
        return params;

    }

    public String getRequest(Context context, String url, Map<String, String> paramsMap,
                             Map<String, String> headerMap, Map<String, String> cookieMap,
                             boolean isGzip) {
        HttpClient httpClient = null;
        String responseBody = "";
        List params = null;
        String params_str = null;

        if (paramsMap != null) {
            params = setParams(paramsMap);
            params_str = URLEncodedUtils.format(params, "utf-8");
        }

        try {
            httpClient = getHttpClient(context);
        } catch (Exception e) {

            e.printStackTrace();
        }
        HttpGet httpGet = getHttpGet(url
                        + (params_str == null || params_str == "" ? "" : params_str),
                cookieMap
        );
        if (headerMap != null) {
            setHeader(headerMap, httpGet);
        }
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (isGzip) {
                responseBody = zipInputStream(httpResponse.getEntity()
                        .getContent());
            } else {
                responseBody = readInputStream(httpResponse.getEntity()
                        .getContent());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return responseBody;
    }

    public String postRequest(Context context, String url, Map<String, String> paramsMap,
                              Map<String, String> headerMap, Map<String, String> cookieMap,
                              boolean isGzip, boolean showResponseHeader) {
        System.out.println("post mether");
        System.out.println(cookieMap == null);
        HttpClient httpClient = null;
        String responseBody = "";
        List params = null;
        String params_str = null;

        if (paramsMap != null) {
            params = setParams(paramsMap);
            params_str = URLEncodedUtils.format(params, "utf-8");
        }

        try {
            httpClient = getHttpClient(context);
        } catch (Exception e) {

            e.printStackTrace();
        }
        HttpPost httpPost = getHttpPost(url, cookieMap);
        if (headerMap != null) {
            setHeader(headerMap, httpPost);
        }
        try {
            UrlEncodedFormEntity uef = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(uef);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (showResponseHeader) {
                Header[] headers = httpResponse.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    System.out.println(headers[i].getName() + "-->"
                            + headers[i].getValue());

                }
            }
            if (isGzip) {
                responseBody = zipInputStream(httpResponse.getEntity()
                        .getContent());
            } else {

                responseBody = readInputStream(httpResponse.getEntity()
                        .getContent());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return responseBody;
    }

    public InputStream getPicInputStream(Context context, HttpGet httpGet) {

        InputStream resultInputStream = null;
        HttpClient httpClient;
        FileOutputStream out = null;

        HttpResponse httpResponse;
        try {
            httpClient = getHttpClient(context);
            httpResponse = httpClient.execute(httpGet);
            InputStream in = httpResponse.getEntity().getContent();
            resultInputStream = in;
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultInputStream;
    }

    public void getPic(Context context, HttpGet httpGet, String fileDir) {
        System.out.println(fileDir);
        HttpClient httpClient;
        FileOutputStream out = null;

        HttpResponse httpResponse;
        try {
            httpClient = getHttpClient(context);
            File f = new File(fileDir);
            out = new FileOutputStream(f);
            httpResponse = httpClient.execute(httpGet);
            InputStream in = httpResponse.getEntity().getContent();
            byte[] data = new byte[1];
            int end = 0;
            end = in.read(data);
            while (end != -1) {

                out.write(data);
                end = in.read(data);

            }

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String readInputStream(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line + "\n");
        is.close();
        return buffer.toString();
    }

    private String zipInputStream(InputStream is) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(is);
        BufferedReader in = new BufferedReader(new InputStreamReader(gzip,
                "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line + "\n");
        is.close();
        return buffer.toString();
    }

    public boolean isNullCookies(ClientCore core) {
        boolean result = false;
        if (core.BIGipServerotn.equals("") || core.JSESSIONID.equals("")){
            result = true;
        } else{
            result = false;
        }
        return result;
    }

}
