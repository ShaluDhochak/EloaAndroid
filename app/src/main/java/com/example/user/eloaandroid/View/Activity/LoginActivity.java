package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.user.eloaandroid.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView signIn_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn_btn = (TextView) findViewById(R.id.signIn_btn);
        signIn_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signIn_btn:
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                break;

        }
    }
}
