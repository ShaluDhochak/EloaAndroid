package com.example.user.eloaandroid.Utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;

/*
  Created by Shalu Dhochak on 3/28/2018.
*/

public class Utilities {

    public static void serverError(Context context) {
        Toast.makeText(context, "Server Error.Try again later.", Toast.LENGTH_SHORT).show();
    }

    public static void internetConnectionError(Context context) {
        Toast.makeText(context, "Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static RequestQueue getRequestQueue(Context context) {
        return VolleySingleton.getInstance(context).getRequestQueue();
    }

}
