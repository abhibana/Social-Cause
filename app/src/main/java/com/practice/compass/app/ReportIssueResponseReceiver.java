package com.practice.compass.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReportIssueResponseReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getBooleanExtra("Success",false)){
            Toast.makeText(context,"Your report has been submitted successfully",Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(context.getApplicationContext(), "Report submission failed. Try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
