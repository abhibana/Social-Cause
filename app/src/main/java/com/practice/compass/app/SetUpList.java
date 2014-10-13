package com.practice.compass.app;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.compass.login.R;
import com.practice.compass.network.request.SeeIssueRequest.Images;
import com.practice.compass.network.request.SeeIssueRequest.SeeIssueResponse;
import com.practice.compass.network.request.SeeIssueRequest.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SetUpList extends BaseAdapter{

    Context context;
    Activity activity;
    private int listSize;
    private ArrayList<SeeIssueResponse> issues;
    private User user;
    private ArrayList<Images> images;
    private SeeIssueResponse response;

    SetUpList(Activity activity,ArrayList<SeeIssueResponse> issues) {
        this.activity = activity;
        context = activity.getApplicationContext();
        this.issues = issues;
        listSize = issues.size();
    }

    @Override
    public int getCount() {
        return listSize;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        response = issues.get(position);
        user = response.getUser();
        images = response.getImages();
        IssueListHolder holder = null;

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.issue_list_row, parent, false);
            holder = new IssueListHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (IssueListHolder) convertView.getTag();
        }

        try {
            if (!images.get(0).getThumbnailUrl().equals("")) {
                Picasso.with(context).load(Uri.parse(images.get(0).getThumbnailUrl())).resize(120, 120).centerInside().into(holder.issueImage);
            }
        }catch(Exception e){ Picasso.with(context).load(R.drawable.issue).resize(120,120).centerInside().into(holder.issueImage); }

        try {
            if (!user.getEmail().equals("")) {
                holder.email.setText(user.getEmail());
            }
        }catch (NullPointerException ne){ holder.email.setText("No User Name available"); }

         try{
             if (!response.getPostDesc().equals("")) {
                holder.issueDesc.setText(response.getPostDesc().substring(0,5).trim() + ".....");
            }
        }catch (NullPointerException ne){ holder.issueDesc.setText("No Issue Description available".substring(0,5)+"....."); }

        return convertView;
    }

    class IssueListHolder{

        ImageView issueImage;
        TextView email;
        TextView issueDesc;

        IssueListHolder(View v){
            issueImage = (ImageView) v.findViewById(R.id.list_issue_image);
            email = (TextView) v.findViewById(R.id.user_name);
            issueDesc = (TextView) v.findViewById(R.id.issue_desc);
        }
    }
}
