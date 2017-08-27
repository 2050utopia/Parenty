package com.brogrammers.the.parenty;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Sandesh on 27-08-2017.
 */

public class GeoLocationService extends Service {

    private Context appContext;
    Location location;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        appContext=getBaseContext();//Get the context here
        LocationManager locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        showToast();

        return Service.START_NOT_STICKY;
    }

    void showToast(){
        if(null !=appContext){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run()
                {
                    Toast.makeText(appContext, location.getLongitude()+location.getLatitude()+"", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public void onCreate()
    {
            super.onCreate();
    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }



}
