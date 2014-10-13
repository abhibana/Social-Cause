package com.practice.compass.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.practice.compass.login.R;
import com.practice.compass.network.request.ReportIssueRequest;
import com.practice.compass.network.request.ReportIssueResponse;

import java.io.File;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ReportIssueFragment extends Fragment implements View.OnClickListener{

    private int cameraResult = 0;
    ImageView showIssueImage;
    EditText issueDesc;
    Button takeIssuePicButton,reportIssueButton;
    private LocationManager locationManager;
    private Location currentLocation;
    private Uri imageUri;
    private String issueDescription,email,latitude,longitude;
    private static final String PREFS_NAME = "Credentials";
    private Home home;
    InputMethodManager inputMethoManager;
    Activity activity;
    ViewPager pager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        home = (Home) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.report_issue,container,false);
        showIssueImage = (ImageView) view.findViewById(R.id.show_issue_image);
        issueDesc = (EditText) view.findViewById(R.id.issue_desc);
        takeIssuePicButton = (Button) view.findViewById(R.id.take_issue_pic);
        reportIssueButton = (Button) view.findViewById(R.id.report_issue);
        pager = (ViewPager) activity.findViewById(R.id.switcher);
        takeIssuePicButton.setOnClickListener(this);
        reportIssueButton.setOnClickListener(this);
        inputMethoManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethoManager.hideSoftInputFromWindow(issueDesc.getWindowToken(),0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_issue_pic:    takePic();
                                         break;

            case R.id.report_issue:      reportIssue();
                                         inputMethoManager.hideSoftInputFromWindow(issueDesc.getWindowToken(),0);
                                         break;
        }
    }

    private void takePic(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Issue Image");
            if(!file.isDirectory()) {
                file.mkdir();
            }
            File imageFile = new File(file.getAbsolutePath()+"/report.jpg");
            imageUri = Uri.fromFile(imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        }catch(Exception e){}

        startActivityForResult(cameraIntent,cameraResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == activity.RESULT_OK) {
            showIssueImage.setImageURI(imageUri);
            showIssueImage.setVisibility(View.VISIBLE);
        }
    }


    private void reportIssue(){

        issueDescription = issueDesc.getText().toString();
        SharedPreferences preferences = activity.getSharedPreferences(PREFS_NAME,activity.MODE_PRIVATE);
        email = preferences.getString("email","");
        locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        try {
            latitude = String.valueOf(currentLocation.getLatitude());
            longitude = String.valueOf(currentLocation.getLatitude());
        }catch(NullPointerException ne){
            Crouton.makeText(activity, "Please turn on the Location service", Style.INFO).show();
            return;
        }

        File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Issue Image/report.jpg");

        if(showIssueImage.isShown() && issueDescription.length()!=0){

            ReportIssueRequest reportIssueRequest = new ReportIssueRequest(imageFile,issueDescription,email,latitude,longitude);
            home.getCommonSpiceManager().execute(reportIssueRequest,new ReportIssueRequestListener());
            pager.setCurrentItem(1,true);

            Toast.makeText(activity.getApplicationContext(),"Done",Toast.LENGTH_SHORT);
        }

        else{
            if(!showIssueImage.isShown()){
                Crouton.makeText(activity,"Please take an image for the issue",Style.ALERT).show();
            }
            else if(issueDescription.length() == 0) {
                Crouton.makeText(activity, "Description can not be left blank", Style.ALERT).show();
            }
        }
    }

    class ReportIssueRequestListener implements RequestListener<ReportIssueResponse> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Intent intent = new Intent();
            intent.setAction("com.login.reportIssueResponse");
            intent.putExtra("Success",false);
            activity.sendBroadcast(intent);
        }

        @Override
        public void onRequestSuccess(ReportIssueResponse reportIssueResponse) {
            Intent intent = new Intent();
            intent.setAction("com.login.reportIssueResponse");
            intent.putExtra("Success", true);
            activity.sendBroadcast(intent);
        }
    }
}
