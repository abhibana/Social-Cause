package com.practice.compass.app;

import android.app.Activity;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;
import com.practice.compass.network.CommonSpiceService;

public class BaseActivity extends Activity{

    private SpiceManager loginManager = new SpiceManager(CommonSpiceService.class);

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginManager.shouldStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        loginManager.cancelAllRequests();
        loginManager.removeAllDataFromCache();
    }

    protected SpiceManager getCommonSpiceManager(){ return  loginManager; }
}
