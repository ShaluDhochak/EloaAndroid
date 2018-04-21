package com.example.user.eloaandroid.View.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
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
import com.example.user.eloaandroid.Utils.PrefUtil;
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
    ProgressDialog progressDialog ;

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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        getVideosList();
    }

    private List<VideoListBean> getVideosList() {
        List<VideoListBean> videoList = new ArrayList<>();

        String Url = ApiLink.ROOT_URL + ApiLink.VIDEO_LIST;
        //   String URL = "http://vistacars.in/lms/api/login_user";
        Map<String, String> map = new HashMap<>();
        map.put("user_id", PrefUtil.getUserId(getActivity()));
        progressDialog.show();

        GSONRequest<VideoListBean> videoListGsonRequest = new GSONRequest<VideoListBean>(
                Request.Method.POST,
                Url,
                VideoListBean.class, map,
                new com.android.volley.Response.Listener<VideoListBean>() {
                    @Override
                    public void onResponse(VideoListBean res) {
                        if (res.isStatus()) {
                            Log.i("Respose : ", res.toString());
                            List<VideoListBean.Data> videoList = res.getData();
                            for (VideoListBean.Data data : videoList) {
                                try {
                                    data.setBitmap(retriveVideoFrameFromVideo(data.getVideo()));
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }

                            progressDialog.dismiss();
                            faqsAdapter = new VideoListAdapter(getActivity(), videoList);
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

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

}
