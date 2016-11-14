package com.deitel.servicetester;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BigHome on 13/11/2016.
 */

public class serviceHandler extends Service {

    public static final String TAG = "serviceTesterDebug";
    private Timer timer;
    public double timeElapsed = 0;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean gpsOn = false;
    private double lat;
    private double lng;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //return null because it's an unbound service
        return null;
    }

    public void onCreate(){
        Log.d(TAG,"Service created");
        //startTimer();
        if(locationListener == null){
            Log.d(TAG, "locationListener is null, setting up locationManager & locationListener");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Define a listener that responds to location updates
            locationListener = new LocationListener() {

                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    updateLocation(location);
                    sendMessage();
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
    }

    public void onDestroy(){

    }

    public int onStartCommand(Intent intent,int flags,int startId){
        return START_STICKY;
    }

    private void startTimer(){
        //create task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Log.d(TAG,"Timer task executed.");
                timeElapsed += 5;
                sendMessage();
            }
        };
        timer = new Timer(true);
        int delay = 1000;
        int interval = 250;
        timer.schedule(task,delay,interval);
    }

    private void stopTimer(){
        if(timer != null){
            timer.cancel();
        }
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
    // be received by the ReceiverActivity.
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        //intent.putExtra("timeElapsed", String.valueOf(timeElapsed/1000));
        intent.putExtra("latitude",String.valueOf(lat));
        intent.putExtra("longitude",String.valueOf(lng));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void updateLocation(Location l){
        lat = l.getLatitude();
        lng = l.getLongitude();
    }

}
