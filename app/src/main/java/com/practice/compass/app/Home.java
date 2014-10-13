package com.practice.compass.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.octo.android.robospice.SpiceManager;
import com.practice.compass.login.R;
import com.practice.compass.network.CommonSpiceService;
import com.practice.compass.network.request.SeeIssueRequest.Images;
import com.practice.compass.network.request.SeeIssueRequest.SeeIssueResponse;
import com.practice.compass.network.request.SeeIssueRequest.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Home extends FragmentActivity implements ActionBar.TabListener,ViewPager.OnPageChangeListener{

    ViewPager pager;
    ActionBar actionbar;
    private User user;
    private ArrayList<Images> images;
    Picasso imageLoader;
    @InjectView(R.id.issue_image) ImageView issueImage;
    @InjectView(R.id.reported_by) TextView reportedBy;
    @InjectView(R.id.issue_desc) TextView issueDesc;
    @InjectView(R.id.issue_detail) LinearLayout issueDetail;
    private SpiceManager loginManager = new SpiceManager(CommonSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        pager = (ViewPager) findViewById(R.id.switcher);
        actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        setUpTabs();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        imageLoader = new Picasso.Builder(this).executor(Executors.newSingleThreadExecutor()).build();
        ButterKnife.inject(this);
        pager.setOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {

        if(issueDetail.isShown()){
            issueDetail.setVisibility(View.GONE);
            actionbar.show();
        }else{
            super.onBackPressed();
        }
    }

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
         actionbar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    class MyPagerAdapter extends FragmentStatePagerAdapter{

        Fragment fragment = null;
        FragmentManager manager;

        MyPagerAdapter(FragmentManager manager){
            super(manager);
            this.manager = manager;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if(position == 0){
                fragment = new ReportIssueFragment();
            }

            else if(position == 1){
                fragment = new SeeIssueFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void setUpTabs(){
        ActionBar.Tab reportIssueTab = actionbar.newTab();
        reportIssueTab.setText("Report Issue");
        reportIssueTab.setTabListener(this);
        ActionBar.Tab seeIssueTab = actionbar.newTab();
        seeIssueTab.setText("See Issues");
        seeIssueTab.setTabListener(this);
        actionbar.addTab(reportIssueTab);
        actionbar.addTab(seeIssueTab);
    }

    public void showIssueDetails(SeeIssueResponse response){
        user = response.getUser();
        images = response.getImages();

        try {
            if (!images.get(0).getThumbnailUrl().equals("")) {
                imageLoader.with(this).load(Uri.parse(images.get(0).getThumbnailUrl())).resize(200, 200).centerCrop().into(issueImage);
            }
        } catch (Exception e) {
            imageLoader.with(this).load(R.drawable.issue).resize(200, 200).into(issueImage);
        }

        try {
            if (!user.getEmail().equals("")) {
                reportedBy.setText(user.getEmail().trim());
            }
        } catch (NullPointerException ne) {
            reportedBy.setText("No User Name available");
        }

        try {
            if (!response.getPostDesc().equals("")) {
                issueDesc.setText(response.getPostDesc().trim());
            }
        } catch (NullPointerException ne) {
            issueDesc.setText("No Issue Description available");
        }
        issueDetail.setVisibility(View.VISIBLE);
        actionbar.hide();
    }
}
