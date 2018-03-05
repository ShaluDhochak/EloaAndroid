package com.example.user.eloaandroid.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.View.Adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllVideosActivity extends AppCompatActivity {

    RecyclerView videoList_RecyclerView;
    VideoListAdapter faqsAdapter;
    private List<VideoListBean> movieList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);
        initialiseUI();
    }

    private void initialiseUI() {
        videoList_RecyclerView = (RecyclerView) findViewById(R.id.videoList_RecyclerView);

        faqsAdapter = new VideoListAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        videoList_RecyclerView.setLayoutManager(mLayoutManager);
        videoList_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        videoList_RecyclerView.setAdapter(faqsAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        VideoListBean movie = new VideoListBean("Video1", "11:00", "Monday", "Lorem Ipsum I ssimply dummy", "lorem ipsum is simply dummy text of the printing an dtypesetting industry.", " Lorem 1, LOREM 2");
        movieList.add(movie);

        movie = new VideoListBean("Video2", "13:00", "Tuesday", "Lorem Ipsum 2 ssimply dummy", "lorem ipsum is simply dummy text of the printing an dtypesetting industry.", " Lorem 3, LOREM 4");
        movieList.add(movie);
    }
}
