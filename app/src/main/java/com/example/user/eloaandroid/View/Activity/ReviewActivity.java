package com.example.user.eloaandroid.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.eloaandroid.R;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener{

    //Header Layout
    ImageView headerBack_iv,headerRight_iv;
    TextView headingText_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //Header Layout
        headerBack_iv = (ImageView) findViewById(R.id.headerBack_iv);
        headerRight_iv= (ImageView) findViewById(R.id.headerRight_iv);
        headingText_tv= (TextView) findViewById(R.id.headingText_tv);

        headerRight_iv.setVisibility(View.GONE);
        headerBack_iv.setOnClickListener(this);
        headingText_tv.setText("Review Video");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.headerBack_iv:
                onBackPressed();
                break;

        }
    }
}
