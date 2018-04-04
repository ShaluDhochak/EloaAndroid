package com.example.user.eloaandroid.View.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener{


    //Header Layout
    ImageView headerBack_iv,headerRight_iv;
    TextView headingText_tv;

    //Shared Preference
    SharedPreferences pref;
    String emailId, password, login_type;

    //Profile String Text
    TextView nameString_tv,emailString_tv,addressString_tv,lawFirmNameString_tv,phoneString_tv,areaOfLawString_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //Header Layout
        headerBack_iv = (ImageView) findViewById(R.id.headerBack_iv);
        headerRight_iv= (ImageView) findViewById(R.id.headerRight_iv);
        headingText_tv= (TextView) findViewById(R.id.headingText_tv);

        //Profile String
        nameString_tv =(TextView) findViewById(R.id.nameString_tv);
        emailString_tv =(TextView) findViewById(R.id.emailString_tv);
        addressString_tv =(TextView) findViewById(R.id.addressString_tv);
        lawFirmNameString_tv =(TextView) findViewById(R.id.lawFirmNameString_tv);
        phoneString_tv =(TextView) findViewById(R.id.phoneString_tv);
        areaOfLawString_tv =(TextView) findViewById(R.id.areaOfLawString_tv);

       // setProfileData();
        headerRight_iv.setVisibility(View.GONE);
        headerBack_iv.setOnClickListener(this);
        headingText_tv.setText("Profile");
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileData();
    }


    private void setProfileData(){
        if (Connectivity.isConnected(MyProfileActivity.this)) {
            //Shared Preference
            pref = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
            emailId = pref.getString("email_id", "");
            password = pref.getString("password", "");
            login_type = pref.getString("login_type", "");


            String Url = ApiLink.ROOT_URL + ApiLink.LOGIN_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("email_id", emailId);
            map.put("password", password);
            map.put("login_type", login_type);

            GSONRequest<LoginBean> loginGsonRequest = new GSONRequest<LoginBean>(
                    Request.Method.POST,
                    Url,
                    LoginBean.class, map,
                    new com.android.volley.Response.Listener<LoginBean>() {
                        @Override
                        public void onResponse(LoginBean res) {
                            if (res.isStatus())
                            {
                                nameString_tv.setText(res.getData().get(0).getName());
                                emailString_tv.setText(res.getData().get(0).getEmail());
                                addressString_tv.setText(res.getData().get(0).getAddress());
                                lawFirmNameString_tv.setText(res.getData().get(0).getLaw_firm_name());
                                phoneString_tv.setText(res.getData().get(0).getPhone());
                                areaOfLawString_tv.setText(res.getData().get(0).getArea_of_low_id());
                                //startActivity(new Intent(MyProfileActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                               // Toast.makeText(MyProfileActivity.this,"Enter valid credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Utilities.serverError(MyProfileActivity.this);
                        }
                    });
            loginGsonRequest.setShouldCache(false);
            Utilities.getRequestQueue(MyProfileActivity.this).add(loginGsonRequest);
        }
        else
            Utilities.internetConnectionError(MyProfileActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.headerBack_iv:
                onBackPressed();
                break;
        }
    }
}
