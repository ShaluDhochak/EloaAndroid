package com.example.user.eloaandroid.View.Activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.eloaandroid.R;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class FacebookProfileActivity extends AppCompatActivity {

    ImageView userProfile;

    private ShareDialog sharedDialog;
    TextView nameUserFb_tv,emailIdFb_tv,contactNoFb_tv,LocationFb_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_facebook_profile);

        ShareLinkContent content = new ShareLinkContent.Builder().build();

        sharedDialog.show(content);
        initialiseUI();

    }

    private void initialiseUI(){

       // userProfile = (ImageView) findViewById(R.id.userProfile);
        nameUserFb_tv =(TextView) findViewById(R.id.nameUserFb_tv);
        emailIdFb_tv =(TextView) findViewById(R.id.emailIdFb_tv);
        contactNoFb_tv =(TextView) findViewById(R.id.contactNoFb_tv);
        LocationFb_tv =(TextView) findViewById(R.id.LocationFb_tv);


        Bundle inBundle = getIntent().getExtras();
        String name  =inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        new FacebookProfileActivity.DownloadImage((ImageView)findViewById(R.id.userProfile)).execute(imageUrl);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        public void execute(String imageUrl) {
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIconll = null;
            try{

            }catch(Exception e){
                Log.e("Error")
            }

            return null;
        }
    }
}
