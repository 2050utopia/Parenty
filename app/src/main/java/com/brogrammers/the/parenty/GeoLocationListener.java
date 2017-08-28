package com.brogrammers.the.parenty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Sandesh on 27-08-2017.
 */

public class GeoLocationListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);



        Toast.makeText(context, location.getLongitude()+" and "+location.getLatitude()+"", Toast.LENGTH_SHORT).show();

    }
}
