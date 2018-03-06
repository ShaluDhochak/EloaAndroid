package com.example.user.eloaandroid.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.eloaandroid.Beans.VideoDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoFeaturesDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoTitleBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.View.Adapter.VideoDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoFeaturesDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoTitleAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity {

    RecyclerView videoDescription_rv, keywordsVideoInfo_rv, VideoTitleInfo_rv;
    VideoDescriptionAdapter videoDescriptionAdapter;
    VideoTitleAdapter videoTitleAdapter;

VideoFeaturesDescriptionAdapter videoFeaturesDescriptionAdapter;
    private List<VideoDescriptionBean> videoList = new ArrayList<>();
    private List<VideoFeaturesDescriptionBean> keywordsList = new ArrayList<>();
    private List<VideoTitleBean> videoTitleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        initialiseUI();
    }

    private void initialiseUI(){
        videoDescription_rv = (RecyclerView) findViewById(R.id.videoDescription_rv);


        videoDescriptionAdapter = new VideoDescriptionAdapter(videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        videoDescription_rv.setLayoutManager(mLayoutManager);
        videoDescription_rv.setItemAnimator(new DefaultItemAnimator());
        videoDescription_rv.setAdapter(videoDescriptionAdapter);

        prepareVideoDescriptionData();

        keywordsVideoInfo_rv = (RecyclerView) findViewById(R.id.keywordsVideoInfo_rv);
        videoFeaturesDescriptionAdapter = new VideoFeaturesDescriptionAdapter(keywordsList);
        RecyclerView.LayoutManager keywordsLayoutManager = new LinearLayoutManager(getApplicationContext());
        keywordsVideoInfo_rv.setLayoutManager(keywordsLayoutManager);
        keywordsVideoInfo_rv.setItemAnimator(new DefaultItemAnimator());
        keywordsVideoInfo_rv.setAdapter(videoFeaturesDescriptionAdapter);

        prepareFeaturesDescription();


        VideoTitleInfo_rv = (RecyclerView) findViewById(R.id.VideoTitleInfo_rv);
        videoTitleAdapter = new VideoTitleAdapter(videoTitleList);
        RecyclerView.LayoutManager vLayoutManager = new LinearLayoutManager(getApplicationContext());
        VideoTitleInfo_rv.setLayoutManager(vLayoutManager);
        VideoTitleInfo_rv.setItemAnimator(new DefaultItemAnimator());
        VideoTitleInfo_rv.setAdapter(videoTitleAdapter);

        prepareVideoTitle();

    }

    private void prepareVideoTitle() {
        VideoTitleBean videoTitle = new VideoTitleBean("1", "Personal Inquiry");
        videoTitleList.add(videoTitle);

        videoTitle = new VideoTitleBean("2", "Car Accidents");
        videoTitleList.add(videoTitle);

        videoTitle = new VideoTitleBean("3", "Inquiry Firm");
        videoTitleList.add(videoTitle);
    }

    private void prepareFeaturesDescription() {
        VideoFeaturesDescriptionBean keywords = new VideoFeaturesDescriptionBean("1", "Personal Inquiry");
        keywordsList.add(keywords);

        keywords = new VideoFeaturesDescriptionBean("2", "Personal Inquiry");
        keywordsList.add(keywords);
    }


        private void prepareVideoDescriptionData() {
        VideoDescriptionBean video = new VideoDescriptionBean("1", "Injuried in an accident due to the negligenceof another?, Call ABC Law Fiem at (555)555 555 today!");
        videoList.add(video);

        video = new VideoDescriptionBean("2", "Injuried in an accident due to the negligenceof another?, Call ABC Law Fiem at (555)555 555 today!");
        videoList.add(video);


    }
}
