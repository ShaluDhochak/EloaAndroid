package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.user.eloaandroid.Beans.LoginBean;
import com.example.user.eloaandroid.Beans.RegisterBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.Utils.ApiLink;
import com.example.user.eloaandroid.Utils.Connectivity;
import com.example.user.eloaandroid.Utils.GSONRequest;
import com.example.user.eloaandroid.Utils.Utilities;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView signIn_btn, signUpHeading_tv, signInHeading_tv;
    RelativeLayout signUpHeader_rl, signInHeader_rl;

    EditText emailAddress_et,password_et;

    //shared Preference
    String EmailId, current_password, Login_type, userId;
    public static LoginBean loginBean;

    //SignUp Form
    EditText nameAddressSignUp_et,emailAddressSignUp_et,addressSignUp_et,phoneSignUp_et,lawFirmNameSignUp_et,passwordSignUp_et,confirmPasswordasswordSignUp_et;
    TextView sighUp_btn;

    RelativeLayout loginFb_rl;

    //facebook integration code
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    LoginButton loginBtnFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //Fb Intergration code
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken currentToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        //Login via Fb
       // loginFb_rl = (RelativeLayout) findViewById(R.id.loginFb_rl);
        loginBtnFB = (LoginButton)findViewById(R.id.loginBtnFB);

        //loginFb_rl.setOnClickListener(this);

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        };
        loginBtnFB.setReadPermissions("user_friends");
        loginBtnFB.registerCallback(callbackManager, callback);


        //singInHeading
        signInHeading_tv = (TextView) findViewById(R.id.signInHeading_tv);
        signInHeading_tv.setOnClickListener(this);

        signInHeader_rl = (RelativeLayout) findViewById(R.id.signInHeader_rl);
        signInHeader_rl.setVisibility(View.VISIBLE);

        //signUpHeading
        signUpHeading_tv = (TextView) findViewById(R.id.signUpHeading_tv);
        signUpHeading_tv.setOnClickListener(this);

        signUpHeader_rl=(RelativeLayout) findViewById(R.id.signUpHeader_rl);
        signUpHeader_rl.setVisibility(View.GONE);

        sighUp_btn = (TextView) findViewById(R.id.sighUp_btn);
        sighUp_btn.setOnClickListener(this);

        //SignUpForm
        nameAddressSignUp_et = (EditText) findViewById(R.id.nameAddressSignUp_et);
        emailAddressSignUp_et = (EditText) findViewById(R.id.emailAddressSignUp_et);
        addressSignUp_et = (EditText) findViewById(R.id.addressSignUp_et);
        phoneSignUp_et = (EditText) findViewById(R.id.phoneSignUp_et);
        lawFirmNameSignUp_et = (EditText) findViewById(R.id.lawFirmNameSignUp_et);
        passwordSignUp_et = (EditText) findViewById(R.id.passwordSignUp_et);
        confirmPasswordasswordSignUp_et = (EditText) findViewById(R.id.confirmPasswordasswordSignUp_et);

        //SignIn Form
        emailAddress_et = (EditText) findViewById(R.id.emailAddress_et);
        password_et =(EditText) findViewById(R.id.password_et);

        signIn_btn = (TextView) findViewById(R.id.signIn_btn);
        signIn_btn.setOnClickListener(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected  void onActivityResult(int requestCode, int responseCode, Intent intent){
        super.onActivityResult(requestCode, responseCode, intent);

        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

   private void nextActivity(Profile profile){
        if(profile!= null){
            Intent profileIntent = new Intent(LoginActivity.this, FacebookProfileActivity.class);
            profileIntent.putExtra("name", profile.getFirstName());
            profileIntent.putExtra("surname", profile.getLastName());
            profileIntent.putExtra("imageUrl", profile.getProfilePictureUri(200, 200));
        }
   }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signIn_btn:
               // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
               // startActivity(intent);
                getLoginFunction();
                break;

            /*
            case R.id.loginFb_rl:
                getLoginFbFunction();
                break;

                */

            case R.id.signUpHeading_tv:
                signUpHeader_rl.setVisibility(View.VISIBLE);
                signInHeader_rl.setVisibility(View.GONE);
                break;

            case R.id.signInHeading_tv:
                signInHeader_rl.setVisibility(View.VISIBLE);
                signUpHeader_rl.setVisibility(View.GONE);
                break;

            case R.id.sighUp_btn:

                if (nameAddressSignUp_et.getText().toString().trim().length()>0){
                    if (emailAddressSignUp_et.getText().toString().trim().length()>0 ) {
                        if (isEmailValid(emailAddressSignUp_et.getText().toString().trim())) {
                            if (addressSignUp_et.getText().toString().trim().length()>0){
                                if ((phoneSignUp_et.getText().toString().trim().length()>0) && (phoneSignUp_et.getText().toString().length()==10) ){
                                    if (lawFirmNameSignUp_et.getText().toString().trim().length()>0){
                                        if (passwordSignUp_et.getText().toString().trim().length()>0){
                                            if (confirmPasswordasswordSignUp_et.getText().toString().trim().length()>0){
                                                if ((passwordSignUp_et.getText().toString()).equals(confirmPasswordasswordSignUp_et.getText().toString())){
                                                    getSignUpFunction();
                                                }else{
                                                    Toast.makeText(this, "Password must be Same as Confirm Password.", Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(this, "Please enter Law Firm Name.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(this, "Please enter contact no of 10 digit.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(this, "Please enter Address.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Email Address must contain @ and .", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Please enter Email Address.", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(this, "Please enter Name.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void sharedPreferenceData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("email_id", EmailId);
        edit.putString("password", current_password);
        edit.putString("login_type", Login_type);
        edit.putString("user_id", userId);
        edit.commit();
    }

    private void getSignUpFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            String name = nameAddressSignUp_et.getText().toString().trim();
            String email =emailAddressSignUp_et.getText().toString().trim() ;
            String address = addressSignUp_et.getText().toString().trim();
            String law_firm_name = lawFirmNameSignUp_et.getText().toString().trim();
            String phone = phoneSignUp_et.getText().toString().trim();
           // String area_of_low_id = emailAddress_et.getText().toString().trim();
            String password = passwordSignUp_et.getText().toString().trim();
         //   String social_login_type;
          //          String socil_type;


            String Url = ApiLink.ROOT_URL + ApiLink.REGISTER_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            map.put("address", address);
            map.put("law_firm_name", law_firm_name);
            map.put("phone", phone);
            map.put("password", password);
            map.put("area_of_low_id","");
            map.put("social_login_type","");
            map.put("social_api","");

            GSONRequest<RegisterBean> loginGsonRequest = new GSONRequest<RegisterBean>(
                    Request.Method.POST,
                    Url,
                    RegisterBean.class, map,
                    new com.android.volley.Response.Listener<RegisterBean>() {
                        @Override
                        public void onResponse(RegisterBean res) {
                            if (res.isStatus())
                            {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                Toast.makeText(LoginActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this,"Something went wrong!! Not Registered",Toast.LENGTH_SHORT).show();
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
            final String login_type ="1";

            String Url = ApiLink.ROOT_URL + ApiLink.LOGIN_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("email_id", name);
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
                                //Saving data to Shared Preference
                                loginBean = res;
                                EmailId = loginBean.getData().get(0).getEmail();
                                current_password = password_et.getText().toString();
                                Login_type = login_type;
                                userId = res.getData().get(0).getId();

                                sharedPreferenceData();

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
