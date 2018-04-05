package com.example.user.eloaandroid.View.Activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.user.eloaandroid.Beans.VideoDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoFeaturesDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoTitleBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.View.Adapter.VideoDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoFeaturesDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoTitleAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView videoDescription_rv, keywordsVideoInfo_rv, VideoTitleInfo_rv;
    VideoDescriptionAdapter videoDescriptionAdapter;
    VideoTitleAdapter videoTitleAdapter;

    VideoView VideoDetail_videoView;
    ImageView playVideoDetail_iv;

    //Showing Video Details
    RelativeLayout previewVideoInfo_rl;
    String location, addAdditional, ownTitle, ownDesc, videopath;
    Uri videoUri;


    //ListOf Edit Information
    EditText locationEditText_et, addAdditionalKeywords_et, addYourOwnTitle_et, addYourOwnDesc_et;


 VideoFeaturesDescriptionAdapter videoFeaturesDescriptionAdapter;
    private List<VideoDescriptionBean> videoList = new ArrayList<>();
    private List<VideoFeaturesDescriptionBean> keywordsList = new ArrayList<>();
    private List<VideoTitleBean> videoTitleList = new ArrayList<>();


    //Header Layout
    ImageView headerBack_iv,headerRight_iv;
    TextView headingText_tv, saveHeader_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        initialiseUI();
    }

    private void initialiseUI(){

        videoUri = Uri.parse(getIntent().getExtras().getString("video_uri"));

        //showing Relative Layout
        previewVideoInfo_rl =(RelativeLayout) findViewById(R.id.previewVideoInfo_rl);
        previewVideoInfo_rl.setVisibility(View.GONE);

        //Edit Text for Video Details
        locationEditText_et = (EditText) findViewById(R.id.locationEditText_et);
        addAdditionalKeywords_et = (EditText) findViewById(R.id.addAdditionalKeywords_et);
        addYourOwnTitle_et = (EditText) findViewById(R.id.addYourOwnTitle_et);
        addYourOwnDesc_et = (EditText) findViewById(R.id.addYourOwnDesc_et);

        VideoDetail_videoView = (VideoView) findViewById(R.id.VideoDetail_videoView);
        playVideoDetail_iv = (ImageView) findViewById(R.id.playVideoDetail_iv);
        playVideoDetail_iv.setOnClickListener(this);

        /*
        videoView = findViewById(R.id.videoView);

         */
        videoDescription_rv = (RecyclerView) findViewById(R.id.videoDescription_rv);

        //Header Layout
        headerBack_iv = (ImageView) findViewById(R.id.headerBack_iv);
        headerRight_iv= (ImageView) findViewById(R.id.headerRight_iv);
        headingText_tv= (TextView) findViewById(R.id.headingText_tv);
        saveHeader_tv = (TextView) findViewById(R.id.saveHeader_tv);
        saveHeader_tv.setOnClickListener(this);

        headerRight_iv.setVisibility(View.GONE);
        headerBack_iv.setOnClickListener(this);
        headingText_tv.setText("Video Details");

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

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.headerBack_iv :
              onBackPressed();
              break;

          case R.id.saveHeader_tv:
              location = locationEditText_et.getText().toString();
              addAdditional = addAdditionalKeywords_et.getText().toString();
              ownTitle = addYourOwnTitle_et.getText().toString();
              ownDesc = addYourOwnDesc_et.getText().toString();
              ///do api code here for saving video - videopath ???

              break;


          case R.id.playVideoDetail_iv:
              VideoDetail_videoView.setVideoURI(videoUri);

              MediaController mediaController = new MediaController(this);
              mediaController.setMediaPlayer(VideoDetail_videoView);
              VideoDetail_videoView.setMediaController(mediaController);
              VideoDetail_videoView.requestFocus();
              VideoDetail_videoView.start();
              VideoDetail_videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer mp) {
                      finish();
                  }
              });

              break;
      }
    }
}
