package com.example.user.eloaandroid.View.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.Utils.ApiLink;
import com.example.user.eloaandroid.Utils.GSONRequest;
import com.example.user.eloaandroid.Utils.Utilities;
import com.example.user.eloaandroid.View.Activity.AllVideosActivity;
import com.example.user.eloaandroid.View.Adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DashboardFragment extends Fragment {
    RecyclerView videoList_RecyclerView;
    VideoListAdapter faqsAdapter;
    private List<VideoListBean> movieList = new ArrayList<>();

    View view;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialiseUI();
        return view;

    }

    private void initialiseUI() {
        videoList_RecyclerView = (RecyclerView) view.findViewById(R.id.videoList_RecyclerView);
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
                            faqsAdapter = new VideoListAdapter(getActivity(),res.getData());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            videoList_RecyclerView.setLayoutManager(mLayoutManager);
                            videoList_RecyclerView.setItemAnimator(new DefaultItemAnimator());
                            videoList_RecyclerView.setAdapter(faqsAdapter);
                        } else {
                            Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Utilities.serverError(getActivity());
                    }
                });
        videoListGsonRequest.setShouldCache(false);
        Utilities.getRequestQueue(getActivity()).add(videoListGsonRequest);


        return videoList;
    }

    private String getUserId() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userId = pref.getString("user_id", "1");

        return userId;

    }


}
