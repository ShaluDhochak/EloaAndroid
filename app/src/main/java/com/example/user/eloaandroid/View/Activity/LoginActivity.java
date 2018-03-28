package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.user.eloaandroid.Beans.LoginBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.Utils.ApiLink;
import com.example.user.eloaandroid.Utils.Connectivity;
import com.example.user.eloaandroid.Utils.GSONRequest;
import com.example.user.eloaandroid.Utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView signIn_btn;
    EditText emailAddress_et,password_et;

    RelativeLayout loginFb_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress_et = (EditText) findViewById(R.id.emailAddress_et);
        password_et =(EditText) findViewById(R.id.password_et);

        signIn_btn = (TextView) findViewById(R.id.signIn_btn);
        signIn_btn.setOnClickListener(this);

        //Login via Fb
        loginFb_rl = (RelativeLayout) findViewById(R.id.loginFb_rl);
        loginFb_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signIn_btn:
               // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
               // startActivity(intent);
                getLoginFunction();
                break;

            case R.id.loginFb_rl:
                getLoginFbFunction();
                break;
        }
    }

    private void getLoginFbFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            String name = emailAddress_et.getText().toString().trim();
            String password =password_et.getText().toString().trim() ;

            String Url = ApiLink.ROOT_URL + ApiLink.LOGIN_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("email_id", name);
          //  map.put("password", password);
            map.put("login_type", "2");
            map.put("social_login_type", "facebook");
            map.put("social_api", "1123");

            GSONRequest<LoginBean> loginGsonRequest = new GSONRequest<LoginBean>(
                    Request.Method.POST,
                    Url,
                    LoginBean.class, map,
                    new com.android.volley.Response.Listener<LoginBean>() {
                        @Override
                        public void onResponse(LoginBean res) {
                            if (res.isStatus())
                            {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } else {
                                Toast.makeText(LoginActivity.this,"Enter valid credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Utilities.serverError(LoginActivity.this);
                        }
                    });
            loginGsonRequest.setShouldCache(false);
            Utilities.getRequestQueue(LoginActivity.this).add(loginGsonRequest);
        }
        else
            Utilities.internetConnectionError(LoginActivity.this);

    }

    private void getLoginFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            String name = emailAddress_et.getText().toString().trim();
            String password =password_et.getText().toString().trim() ;

            String Url = ApiLink.ROOT_URL + ApiLink.LOGIN_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("email_id", name);
            map.put("password", password);
            map.put("login_type", "1");

            GSONRequest<LoginBean> loginGsonRequest = new GSONRequest<LoginBean>(
                    Request.Method.POST,
                    Url,
                    LoginBean.class, map,
                    new com.android.volley.Response.Listener<LoginBean>() {
                        @Override
                        public void onResponse(LoginBean res) {
                            if (res.isStatus())
                            {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                                Toast.makeText(LoginActivity.this,"Enter valid credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Utilities.serverError(LoginActivity.this);
                        }
                    });
            loginGsonRequest.setShouldCache(false);
            Utilities.getRequestQueue(LoginActivity.this).add(loginGsonRequest);
        }
        else
            Utilities.internetConnectionError(LoginActivity.this);

    }
}
