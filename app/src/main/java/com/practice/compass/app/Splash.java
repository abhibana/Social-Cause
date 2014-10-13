package com.practice.compass.app;


import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.practice.compass.login.R;

public class Splash extends Activity {

    private static final String PREFS_NAME = "Credentials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread t = new Thread(){
            public void run(){
                try{
                    Thread.sleep(1000);
                    checkConnection(Splash.this);
                    Looper.prepare();
                }catch(InterruptedException e){}
            }
        };
        t.start();
    }

    private void checkConnection(Activity activity){
        if(hasConnection()){
            if(getSharedPreferences(PREFS_NAME,MODE_PRIVATE).getBoolean("Remember",false)){
                startActivity(new Intent(Splash.this,Home.class));
            }else {
                startActivity(new Intent(Splash.this, Login.class));
            }
            finish();
        }

        else{
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getWindow().getContext(),"Network Connection Unavailable",Toast.LENGTH_LONG).show();
                }
            });

            finish();
        }
    }

    private boolean hasConnection(){

        ConnectivityManager connectionManager = (ConnectivityManager) Splash.this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiInfo != null && wifiInfo.isConnected()) { return true;}

        NetworkInfo mobileNetworkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) { return true;}

        NetworkInfo otherNetworkInfo = connectionManager.getActiveNetworkInfo();
        if(otherNetworkInfo != null && otherNetworkInfo.isConnected()) { return true;}

        return false;
    }
}
