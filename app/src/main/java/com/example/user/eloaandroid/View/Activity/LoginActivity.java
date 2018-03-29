package com.example.user.eloaandroid.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView  signUp_btn, signInHeading_tv, signUpHeading_tv;
    RelativeLayout signInButton_ll;
    LinearLayout signUpButton_ll;
    EditText name_et, emailid_et_SignUp, address_et, phone_et, lawfirm_et, passwordSignUp_et, confirmPasswordSignUp_et;
    TextView signIn_btn;
    EditText emailAddress_et,password_et;

    RelativeLayout loginFb_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress_et = (EditText) findViewById(R.id.emailAddress_et);
        password_et =(EditText) findViewById(R.id.password_et);

        //Login via Fb
        loginFb_rl = (RelativeLayout) findViewById(R.id.loginFb_rl);
        loginFb_rl.setOnClickListener(this);

        signIn_btn = (TextView) findViewById(R.id.signIn_btn);
        signIn_btn.setOnClickListener(this);
        signInButton_ll = findViewById(R.id.signInButton_ll);
        signInHeading_tv = findViewById(R.id.signInHeading_tv);
        signInHeading_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton_ll.setVisibility(View.VISIBLE);
                signUpButton_ll.setVisibility(View.GONE);
            }
        });
        signUpButton_ll = findViewById(R.id.signUpButton_ll);

        signUpHeading_tv = findViewById(R.id.signUpHeading_tv);
        signUpHeading_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton_ll.setVisibility(View.GONE);
                signUpButton_ll.setVisibility(View.VISIBLE);
            }
        });

        name_et = findViewById(R.id.name_et);
        emailid_et_SignUp = findViewById(R.id.emailid_et);
        address_et = findViewById(R.id.address_et);
        phone_et = findViewById(R.id.phone_et);
        lawfirm_et = findViewById(R.id.lawfirm_et);
        passwordSignUp_et = findViewById(R.id.passwordSignUp_et);
        confirmPasswordSignUp_et = findViewById(R.id.confirmPasswordSignUp_et);
        signUp_btn = findViewById(R.id.signUp_btn);
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public void signUp() {

        try {

            String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name", name_et.getText().toString());
//            jsonObject.put("law_firm_name",lawfirm_et.getText().toString());
//            jsonObject.put("email",emailid_et_SignUp.getText().toString());
//            jsonObject.put("password",passwordSignUp_et.getText().toString());
//            jsonObject.put("phone",phone_et.getText().toString());
//            jsonObject.put("DeviceToken","bvbhffg");
//            jsonObject.put("DeviceId",deviceId);
//            jsonObject.put("AppVersion","1");
//            jsonObject.put("Type","1");
//            jsonObject.put("OsVersion","2");
//            jsonObject.put("DeviceName",android.os.Build.MODEL);
//            jsonObject.put("address",address_et.getText().toString());

            JSONArray jsonArray = new JSONArray();
            JSONObject emailJsonObject = new JSONObject();
            emailJsonObject.put("key","email");
            emailJsonObject.put("value",emailid_et_SignUp.getText().toString());
            emailJsonObject.put("description","");
            jsonArray.put(emailJsonObject);

            JSONObject nameJsonObject = new JSONObject();
            nameJsonObject.put("key","name");
            nameJsonObject.put("value",name_et.getText().toString());
            nameJsonObject.put("description","");
            jsonArray.put(nameJsonObject);

            JSONObject addressJsonObject = new JSONObject();
            addressJsonObject.put("key","address");
            addressJsonObject.put("value",address_et.getText().toString());
            addressJsonObject.put("description","");
            jsonArray.put(addressJsonObject);

            JSONObject lawFirmJsonObject = new JSONObject();
            lawFirmJsonObject.put("key","law_firm_name");
            lawFirmJsonObject.put("value",lawfirm_et.getText().toString());
            lawFirmJsonObject.put("description","");
            jsonArray.put(lawFirmJsonObject);

            JSONObject phoneJsonObject = new JSONObject();
            phoneJsonObject.put("key","phone");
            phoneJsonObject.put("value",phone_et.getText().toString());
            phoneJsonObject.put("description","");
            jsonArray.put(phoneJsonObject);

            JSONObject areaOfLowIdJsonObject = new JSONObject();
            areaOfLowIdJsonObject.put("key","area_of_low_id");
            areaOfLowIdJsonObject.put("value","");
            areaOfLowIdJsonObject.put("description","");
            jsonArray.put(areaOfLowIdJsonObject);

            JSONObject passwordJsonObject = new JSONObject();
            passwordJsonObject.put("key","password");
            passwordJsonObject.put("value",passwordSignUp_et.getText().toString());
            passwordJsonObject.put("description","");
            jsonArray.put(passwordJsonObject);

            JSONObject socialLoginJsonObject = new JSONObject();
            socialLoginJsonObject.put("key","social_login_type");
            socialLoginJsonObject.put("value","");
            socialLoginJsonObject.put("description","");
            jsonArray.put(socialLoginJsonObject);


            JSONObject socialAPIJsonObject = new JSONObject();
            socialAPIJsonObject.put("key","social_api");
            socialAPIJsonObject.put("value","");
            socialAPIJsonObject.put("description","");
            jsonArray.put(socialAPIJsonObject);
//
//            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, "http://hunaniinfotech.com/public/mehul/index.php/Api/register", jsonArray, new Response.Listener<JSONArray>() {
//
//                public void onResponse(JSONArray response) {
//
//                    Log.i("Response : ", response.toString());
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error : ", "");
//                }
//            });
           // Volley.newRequestQueue(this).add(jsonObjectRequest);
        }catch (Exception e)
        {

        }
    }

    public void login()
    {
        JSONObject jsonObject = new JSONObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://hunaniinfotech.com/eloa/api/agent/login", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("Response : ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error : ", "");
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
