package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.eloaandroid.R;

public class StartRecordingActivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_VIDEO_CAPTURE = 0;
    TextView startRecordingImage_tv, continueStep_tv;
    VideoView videoViewRecording_vv;
    ImageView playIcon_iv;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_recording);

        startRecordingImage_tv = (TextView) findViewById(R.id.startRecordingImage_tv);
        startRecordingImage_tv.setOnClickListener(this);

        videoViewRecording_vv = (VideoView) findViewById(R.id.videoViewRecording_vv);

        continueStep_tv = (TextView) findViewById(R.id.continueStep_tv);
        continueStep_tv.setOnClickListener(this);
        playIcon_iv = (ImageView) findViewById(R.id.playIcon_iv);
        playIcon_iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startRecordingImage_tv:
                dispatchTakeVideoIntent();
                break;

            case R.id.playIcon_iv:
                startRecordingImage_tv.setVisibility(View.GONE);
                videoViewRecording_vv.start();
                videoViewRecording_vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        startRecordingImage_tv.setVisibility(View.VISIBLE);
                    }
                });
                break;

            case R.id.continueStep_tv:
                if(videoUri != null) {
                    Intent intent = new Intent(StartRecordingActivity.this, VideoDetailActivity.class);
                    intent.putExtra("video_uri", videoUri.toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(StartRecordingActivity.this,"Record the video first",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
      //  if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
      //  }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            videoViewRecording_vv.setVideoURI(videoUri);
            videoViewRecording_vv.seekTo(100);
        }
    }


}
