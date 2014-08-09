package com.cheart.do12306.app.view;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.TabHost;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;

import junit.framework.Test;

public class Main2Activity extends TabActivity {


    private TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        setFragment();
        changeLayout();
    }

    private void changeLayout(){
        tabHost.setCurrentTab(0);
    }

    private void setFragment(){
        tabHost = getTabHost();
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("MAIN").setContent(new Intent(this,
                MainActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("query").setIndicator("QUERY").setContent(new Intent(this,
                QueryActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("doTicket").setIndicator("DO_TICKET").setContent(new Intent(this,
                DoTicketSetBase.class)));
        tabHost.addTab(tabHost.newTabSpec("testquery").setIndicator("TEST_QUERY").setContent(new Intent(this,
                TestDoQuery.class)));

        tabHost.addTab(tabHost.newTabSpec("showdo").setIndicator("SHOW_DO").setContent(new Intent(this,
                DoTicketShowDo.class)));

        tabHost.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

}
