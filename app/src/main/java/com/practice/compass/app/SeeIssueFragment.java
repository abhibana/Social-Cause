package com.practice.compass.app;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.practice.compass.login.R;
import com.practice.compass.network.request.SeeIssueRequest.SeeIssueRequest;
import com.practice.compass.network.request.SeeIssueRequest.SeeIssueResponse;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SeeIssueFragment extends Fragment implements ListView.OnItemClickListener{

    ListView listView;
    Activity activity;
    private LocationManager locationManager;
    private Location currentLocation;
    private String latitude,longitude;
    private Home home;
    private ArrayList<SeeIssueResponse> issues;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        home = (Home) activity;
        loadIssues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.issue_list,container,false);
        listView = (ListView) v.findViewById(R.id.issue_list);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            home.showIssueDetails(issues.get(position));
    }

    private void loadIssues(){
        locationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        try {
            latitude = String.valueOf(currentLocation.getLatitude());
            longitude = String.valueOf(currentLocation.getLatitude());
        }catch (NullPointerException ne){
            Crouton.makeText(activity, "Please Enable the Location Service", Style.INFO).show();
            return;
        }

        SeeIssueRequest seeIssueRequest = new SeeIssueRequest(latitude, longitude);

        home.getCommonSpiceManager().execute(seeIssueRequest,new SeeIssueRequestListener());

    }

    private class SeeIssueRequestListener implements RequestListener<SeeIssueResponse.AsList> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Crouton.makeText(activity,"Something Went Wrong. Please try again.",Style.ALERT).show();
        }

        @Override
        public void onRequestSuccess(SeeIssueResponse.AsList seeIssueResponse) {
            issues = seeIssueResponse;
            listView.setAdapter(new SetUpList(activity,issues));
            Log.v("Size", String.valueOf(issues.size()));
        }
    }
}
