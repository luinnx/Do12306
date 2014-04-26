package com.cheart.do12306.app.domain;

/**
 * Created by cheart on 4/26/2014.
 */
public class BaseData {

    BaseQueryLeft queryLeftNewDTO;
    String secretStr;
    String buttonTextInfo;
    public BaseQueryLeft getQueryLeftNewDTO() {
        return queryLeftNewDTO;
    }
    public void setQueryLeftNewDTO(BaseQueryLeft queryLeftNewDTO) {
        this.queryLeftNewDTO = queryLeftNewDTO;
    }
    public String getSecretStr() {
        return secretStr;
    }
    public void setSecretStr(String secretStr) {
        this.secretStr = secretStr;
    }
    public String getButtonTextInfo() {
        return buttonTextInfo;
    }
    public void setButtonTextInfo(String buttonTextInfo) {
        this.buttonTextInfo = buttonTextInfo;
    }


}