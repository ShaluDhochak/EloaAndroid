package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.user.eloaandroid.Beans.LoginBean;
import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.Utils.ApiLink;
import com.example.user.eloaandroid.Utils.GSONRequest;
import com.example.user.eloaandroid.Utils.Utilities;
import com.example.user.eloaandroid.View.Adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllVideosActivity extends AppCompatActivity {

    RecyclerView videoList_RecyclerView;
    VideoListAdapter faqsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);
        initialiseUI();
    }

    private void initialiseUI() {
        videoList_RecyclerView = (RecyclerView) findViewById(R.id.videoList_RecyclerView);
        getVideosList();
    }

    private List<VideoListBean> getVideosList() {
        List<VideoListBean> videoList = new ArrayList<>();

        String Url = ApiLink.ROOT_URL + ApiLink.VIDEO_LIST;
        //   String URL = "http://vistacars.in/lms/api/login_user";
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "37");

        GSONRequest<VideoListBean> videoListGsonRequest = new GSONRequest<VideoListBean>(
                Request.Method.POST,
                Url,
                VideoListBean.class, map,
                new com.android.volley.Response.Listener<VideoListBean>() {
                    @Override
                    public void onResponse(VideoListBean res) {
                        if (res.isStatus()) {
                            Log.i("Respose : ",res.toString());
                            faqsAdapter = new VideoListAdapter(AllVideosActivity.this,res.getData());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            videoList_RecyclerView.setLayoutManager(mLayoutManager);
                            videoList_RecyclerView.setItemAnimator(new DefaultItemAnimator());
                            videoList_RecyclerView.setAdapter(faqsAdapter);
                        } else {
                            Toast.makeText(AllVideosActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Utilities.serverError(AllVideosActivity.this);
                    }
                });
        videoListGsonRequest.setShouldCache(false);
        Utilities.getRequestQueue(AllVideosActivity.this).add(videoListGsonRequest);


        return videoList;
    }

    private String getUserId() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AllVideosActivity.this);
        String userId = pref.getString("user_id", "1");

        return userId;

    }
}
