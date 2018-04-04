package com.example.user.eloaandroid.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    String EmailId, current_password, Login_type, userId, social_login_type_sp, social_api_sp;
    public static LoginBean loginBean;

    //SignUp Form
    EditText nameAddressSignUp_et,emailAddressSignUp_et,addressSignUp_et,phoneSignUp_et,lawFirmNameSignUp_et,passwordSignUp_et,confirmPasswordasswordSignUp_et;
    EditText areaOfLawIdSignUp_et;
    TextView sighUp_btn, signUp_tv, sighUpFb_btn;

    RelativeLayout loginFb_rl, fbSignUp_rl;

    //facebook integration code
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    LoginButton loginBtnFB;

    //SignUp Fb login
    ImageView fb_iv;
    TextView loginFb_tv;

    //Shared Preference
    SharedPreferences pref;
    String emailId_sharedPref, password_sharedPref, login_type_sharedPref, social_login_tyoe_sharedpref, social_api_sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //Fb Intergration code
        fbSignUp_rl = (RelativeLayout) findViewById(R.id.fbSignUp_rl);
        fbSignUp_rl.setOnClickListener(this);

        sighUpFb_btn = (TextView) findViewById(R.id.sighUpFb_btn);
        sighUpFb_btn.setOnClickListener(this);

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

        fb_iv = (ImageView) findViewById(R.id.fb_iv);
        loginFb_tv = (TextView) findViewById(R.id.loginFb_tv);

     /*   if (emailId_sharedPref.equals("")){
            loginBtnFB.setVisibility(View.VISIBLE);
            fb_iv.setVisibility(View.GONE);
            loginFb_tv.setVisibility(View.GONE);
        }else{
            fb_iv.setVisibility(View.VISIBLE);
            loginFb_tv.setVisibility(View.VISIBLE);
            loginBtnFB.setVisibility(View.GONE);

        }
*/
        loginFb_tv.setOnClickListener(this);
        fb_iv.setOnClickListener(this);

         //loginFb_rl.setOnClickListener(this);

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
              //  Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
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

        signUp_tv = (TextView) findViewById(R.id.signUp_tv);
        signUp_tv.setOnClickListener(this);

        signUpHeader_rl=(RelativeLayout) findViewById(R.id.signUpHeader_rl);
        signUpHeader_rl.setVisibility(View.GONE);

        sighUp_btn = (TextView) findViewById(R.id.sighUp_btn);
        sighUp_btn.setOnClickListener(this);

        //SignUpForm
        nameAddressSignUp_et = (EditText) findViewById(R.id.nameSignUp_et);
        emailAddressSignUp_et = (EditText) findViewById(R.id.emailAddressSignUp_et);
        addressSignUp_et = (EditText) findViewById(R.id.addressSignUp_et);
        phoneSignUp_et = (EditText) findViewById(R.id.phoneSignUp_et);
        lawFirmNameSignUp_et = (EditText) findViewById(R.id.lawFirmNameSignUp_et);
        areaOfLawIdSignUp_et= (EditText) findViewById(R.id.areaOfLawIdSignUp_et);
        passwordSignUp_et = (EditText) findViewById(R.id.passwordSignUp_et);
        confirmPasswordasswordSignUp_et = (EditText) findViewById(R.id.confirmPasswordasswordSignUp_et);
        clearText();

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
           /* Intent profileIntent = new Intent(LoginActivity.this, FacebookProfileActivity.class);
            profileIntent.putExtra("name", profile.getFirstName());
            profileIntent.putExtra("surname", profile.getLastName());
            profileIntent.putExtra("imageUrl", profile.getProfilePictureUri(200, 200));
            startActivity(profileIntent);
            */
            signUpHeader_rl.setVisibility(View.VISIBLE);
            signInHeader_rl.setVisibility(View.GONE);
            sighUpFb_btn.setVisibility(View.VISIBLE);
            sighUp_btn.setVisibility(View.GONE);
            nameAddressSignUp_et.setText(profile.getFirstName() + " "+ profile.getLastName());
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

            case R.id.loginFb_tv:
                getLoginFbFunction();
                break;

            case R.id.fb_iv:
                getLoginFbFunction();
                break;

            case R.id.signUp_tv:
                signUpHeader_rl.setVisibility(View.VISIBLE);
                signInHeader_rl.setVisibility(View.GONE);
                break;

            case R.id.sighUpFb_btn:
                if (nameAddressSignUp_et.getText().toString().trim().length()>0){
                    if (emailAddressSignUp_et.getText().toString().trim().length()>0 ) {
                        if (isEmailValid(emailAddressSignUp_et.getText().toString().trim())) {
                            if (addressSignUp_et.getText().toString().trim().length()>0){
                                if ((phoneSignUp_et.getText().toString().trim().length()>0) && (phoneSignUp_et.getText().toString().length()==10) ){
                                    if (lawFirmNameSignUp_et.getText().toString().trim().length()>0){
                                        if (areaOfLawIdSignUp_et.getText().toString().trim().length()>0) {
                                            if (passwordSignUp_et.getText().toString().trim().length() > 0) {
                                                if (confirmPasswordasswordSignUp_et.getText().toString().trim().length() > 0) {
                                                    if ((passwordSignUp_et.getText().toString()).equals(confirmPasswordasswordSignUp_et.getText().toString())) {
                                                        getFbSignUpFunction();
                                                    } else {
                                                        Toast.makeText(this, "Password must be Same as Confirm Password.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(this, "Please enter Area of Low Id", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "First login from Facebook..", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fbSignUp_rl:

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

                if ((nameAddressSignUp_et.getText().toString().trim().length()>0)&& !(nameAddressSignUp_et.getText().toString()).equals("Name")){
                    if((emailAddressSignUp_et.getText().toString().trim().length()>0)  && !(emailAddressSignUp_et.getText().toString().equals("Email Address")) ) {
                        if (isEmailValid(emailAddressSignUp_et.getText().toString().trim())) {
                            if ((addressSignUp_et.getText().toString().trim().length()>0) && !(addressSignUp_et.getText().toString().equals("Address"))){
                                if ((phoneSignUp_et.getText().toString().trim().length()>0) && (phoneSignUp_et.getText().toString().length()==10) && !(phoneSignUp_et.getText().toString().equals("Phone"))){
                                    if ((lawFirmNameSignUp_et.getText().toString().trim().length()>0 ) && !(lawFirmNameSignUp_et.getText().toString().equals("Law Firm Name")) ){
                                        if ((areaOfLawIdSignUp_et.getText().toString().trim().length()>0) && !(areaOfLawIdSignUp_et.getText().toString().equals("Area of Low Id"))) {
                                            if ((passwordSignUp_et.getText().toString().trim().length()>0) && !(passwordSignUp_et.getText().toString().equals("Password"))){
                                            if ((confirmPasswordasswordSignUp_et.getText().toString().trim().length()>0 ) && !(confirmPasswordasswordSignUp_et.getText().toString().equals("Confirm Password"))){
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
                                            Toast.makeText(this, "Please enter Area of Low Id", Toast.LENGTH_SHORT).show();
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
        edit.putString("social_login_type_sp", social_login_type_sp);
        edit.putString("social_api_sp", social_api_sp);
        edit.commit();
    }

    private void getSignUpFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            String name = nameAddressSignUp_et.getText().toString().trim();
            String email =emailAddressSignUp_et.getText().toString().trim() ;
            String address = addressSignUp_et.getText().toString().trim();
            String law_firm_name = lawFirmNameSignUp_et.getText().toString().trim();
            String phone = phoneSignUp_et.getText().toString().trim();
            String area_of_low_id = areaOfLawIdSignUp_et.getText().toString().trim();
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
            map.put("area_of_low_id",area_of_low_id);
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
                                clearText();

                            } else {
                                Toast.makeText(LoginActivity.this,"Something went wrong!! Not Registered",Toast.LENGTH_SHORT).show();
                                clearText();
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

    private void getFbSignUpFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            String name = nameAddressSignUp_et.getText().toString().trim();
            String email =emailAddressSignUp_et.getText().toString().trim() ;
            String address = addressSignUp_et.getText().toString().trim();
            String law_firm_name = lawFirmNameSignUp_et.getText().toString().trim();
            String phone = phoneSignUp_et.getText().toString().trim();
            String area_of_low_id = areaOfLawIdSignUp_et.getText().toString().trim();
            String password = passwordSignUp_et.getText().toString().trim();
             String social_login_type = "fb";
            String social_type = "11";


            String Url = ApiLink.ROOT_URL + ApiLink.REGISTER_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();

            map.put("name", name);
            map.put("email", email);
            map.put("address", address);
            map.put("law_firm_name", law_firm_name);
            map.put("phone", phone);
            map.put("password", password);
            map.put("area_of_low_id",area_of_low_id);
            map.put("social_login_type", social_login_type);
            map.put("social_api",social_type);


      /*      map.put("name", "Shalu Dhochak");
            map.put("email", "shalu5@gmail.com");
            map.put("address", "Pune");
            map.put("law_firm_name", "Headth & fitness");
            map.put("phone", "7799889988");
            map.put("password", "123");
            map.put("area_of_low_id","12");
            map.put("social_login_type","fb");
            map.put("social_api","11");
*/

            GSONRequest<RegisterBean> registerGsonRequest = new GSONRequest<RegisterBean>(
                    Request.Method.POST,
                    Url,
                    RegisterBean.class, map,
                    new com.android.volley.Response.Listener<RegisterBean>() {
                        @Override
                        public void onResponse(RegisterBean res) {
                            if (res.isStatus())
                            {
                                EmailId = res.getData().getEmail().toString();
                                current_password = res.getData().getPassword().toString();
                                //current_password = password_et.getText().toString();
                                Login_type = "2";
                                userId = res.getData().getUser_id().toString();
                                social_login_type_sp =res.getData().getSocial_login_type().toString();
                                social_api_sp = res.getData().getSocial_api().toString();

                                sharedPreferenceData();
                                Toast.makeText(LoginActivity.this, "Registration successfull via Facebook.", Toast.LENGTH_SHORT).show();
                                clearText();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                                Toast.makeText(LoginActivity.this,"Something went wrong!! Not Registered",Toast.LENGTH_SHORT).show();
                                clearText();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Utilities.serverError(LoginActivity.this);
                        }
                    });
            registerGsonRequest.setShouldCache(false);
            Utilities.getRequestQueue(LoginActivity.this).add(registerGsonRequest);
        }
        else
            Utilities.internetConnectionError(LoginActivity.this);
    }

    private void clearText(){
        nameAddressSignUp_et.setText("Name");
        emailAddressSignUp_et.setText("Email Address");
        addressSignUp_et.setText("Address");
        phoneSignUp_et.setText("Phone");
        lawFirmNameSignUp_et.setText("Law Firm Name");
        areaOfLawIdSignUp_et.setText("Area of Low Id");
        passwordSignUp_et.setText("Password");
        confirmPasswordasswordSignUp_et.setText("Confirm Password");

    }

    private void getLoginFbFunction(){
        if (Connectivity.isConnected(LoginActivity.this)) {

            pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

            emailId_sharedPref =pref.getString("email_id", "");
            password_sharedPref =pref.getString("password", "");
            login_type_sharedPref = pref.getString("login_type", "");
            social_login_tyoe_sharedpref = pref.getString("social_login_type_sp", "");
            social_api_sharedPref =pref.getString("social_api_sp", "");

            String Url = ApiLink.ROOT_URL + ApiLink.LOGIN_URL;
            //   String URL = "http://vistacars.in/lms/api/login_user";
            Map<String, String> map = new HashMap<>();
            map.put("email_id", emailId_sharedPref);
          //  map.put("password", password);
            map.put("login_type", login_type_sharedPref);
            map.put("social_login_type", social_login_tyoe_sharedpref);
            map.put("social_api", social_api_sharedPref);

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
