package com.deitel.servicetester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "serviceTesterDebug";
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
        t = (TextView) findViewById(R.id.timerTextView);
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            /*String timeElapsed = intent.getStringExtra("timeElapsed");
            Log.d(TAG, "Got message: " + timeElapsed);
            t.setText(timeElapsed);*/
            String lat = intent.getStringExtra("latitude");
            String lng = intent.getStringExtra("longitude");
            Log.d(TAG, "Got message: " + lat + " " + lng);
            t.setText(lat + " " + lng);
        }
    };

}
