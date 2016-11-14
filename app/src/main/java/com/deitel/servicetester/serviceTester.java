package com.deitel.servicetester;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by BigHome on 13/11/2016.
 */

/*
* This is an application object. As you can see, serviceTester class extends Application, not
* Activity as usual.
* */

public class serviceTester extends Application {
    public static final String TAG = "serviceTesterDebug";


    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"serviceTester App started.");
        Intent serviceIntent = new Intent(this,serviceHandler.class);
        startService(serviceIntent);
    }
}
