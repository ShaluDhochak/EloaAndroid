package com.example.user.eloaandroid.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.eloaandroid.Beans.VideoDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoFeaturesDescriptionBean;
import com.example.user.eloaandroid.Beans.VideoTitleBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.Utils.PrefUtil;
import com.example.user.eloaandroid.View.Adapter.VideoDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoFeaturesDescriptionAdapter;
import com.example.user.eloaandroid.View.Adapter.VideoTitleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView videoDescription_rv, keywordsVideoInfo_rv, VideoTitleInfo_rv;
    VideoDescriptionAdapter videoDescriptionAdapter;
    VideoTitleAdapter videoTitleAdapter;

    VideoView VideoDetail_videoView;
    ImageView playVideoDetail_iv;

    //Showing Video Details
    RelativeLayout previewVideoInfo_rl;
    String location, addAdditional, ownTitle, ownDesc;
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
        VideoDetail_videoView.setVideoURI(videoUri);
        VideoDetail_videoView.seekTo(100);
        playVideoDetail_iv = (ImageView) findViewById(R.id.playVideoDetail_iv);
        playVideoDetail_iv.setOnClickListener(this);

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
              location = locationEditText_et.getText().toString().trim();
              addAdditional = addAdditionalKeywords_et.getText().toString().trim();
              ownTitle = addYourOwnTitle_et.getText().toString().trim();
              ownDesc = addYourOwnDesc_et.getText().toString().trim();

              if(location.length() > 0 && addAdditional.length() > 0 && ownTitle.length() > 0 && ownDesc.length() > 0) {
                  if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                      boolean hasPermission = (ContextCompat.checkSelfPermission(VideoDetailActivity.this,
                              Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                      if (!hasPermission) {
                          ActivityCompat.requestPermissions(VideoDetailActivity.this,
                                  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                  100);
                      } else
                          callUploadVideo();
                  } else
                      callUploadVideo();
              }else
                  Toast.makeText(VideoDetailActivity.this,"Please enter all fields",Toast.LENGTH_SHORT).show();


              ///do api code here for saving video - videopath ???

              break;


          case R.id.playVideoDetail_iv:
              VideoDetail_videoView.setVideoURI(videoUri);
              playVideoDetail_iv.setVisibility(View.GONE);
              MediaController mediaController = new MediaController(this);
              mediaController.setMediaPlayer(VideoDetail_videoView);
              VideoDetail_videoView.setMediaController(mediaController);
              VideoDetail_videoView.requestFocus();
              VideoDetail_videoView.start();
              VideoDetail_videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer mp) {
                      playVideoDetail_iv.setVisibility(View.VISIBLE);
                  }
             });

              break;
      }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callUploadVideo();
                } else
                {
                    Toast.makeText(VideoDetailActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void callUploadVideo()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    uploadVideo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = this.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void uploadVideo()
    {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VideoDetailActivity.this,("Video upload started."),Toast.LENGTH_SHORT).show();

                }
            });

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://hunaniinfotech.com/public/mehul/index.php/Api/update_video");

            FileBody filebodyVideo = new FileBody(new File(getRealPathFromURI(videoUri)));
            StringBody userIdStringBody = new StringBody(PrefUtil.getUserId(VideoDetailActivity.this));
            StringBody keywordStringBody = new StringBody(addAdditional);
            StringBody titleStringBody = new StringBody(ownTitle);
            StringBody descriptionStringBody = new StringBody(ownDesc);
            StringBody locationStringBody = new StringBody(location);

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("video", filebodyVideo);
            reqEntity.addPart("user_id", userIdStringBody);
            reqEntity.addPart("keyword", keywordStringBody);
            reqEntity.addPart("title",titleStringBody);
            reqEntity.addPart("description",descriptionStringBody);
            reqEntity.addPart("location",descriptionStringBody);
            httppost.setEntity(reqEntity);

            // DEBUG
            Log.i("http executing request " , httppost.getRequestLine()+"");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            Log.i( "http Status Line : ", response.getStatusLine( ) +"");
            if (resEntity != null) {
                Log.i("http Res Entity : ", EntityUtils.toString( resEntity ) );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VideoDetailActivity.this,("Video uploaded."),Toast.LENGTH_SHORT).show();
                    }
                });
                } // end if

            if (resEntity != null) {
                resEntity.consumeContent( );
            } // end if

            httpclient.getConnectionManager( ).shutdown( );
            startActivity(new Intent(VideoDetailActivity.this, HomeActivity.class));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
