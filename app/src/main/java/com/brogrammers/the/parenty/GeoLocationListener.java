package com.brogrammers.the.parenty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Sandesh on 27-08-2017.
 */

public class GeoLocationListener extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        HashMap<String,String> params=new HashMap<String,String>();
        params.put("latitude",location.getLatitude()+"");
        params.put("longitude",location.getLongitude()+"");
        Calendar cal=Calendar.getInstance();
        try {
            params.put("timestamp", URLEncoder.encode(""+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)
                    +" "+String.format("%02d",cal.get(Calendar.HOUR_OF_DAY))+":"+
                    String.format("%02d",cal.get(Calendar.MINUTE))+":"+String.format("%02d",cal.get(Calendar.SECOND)),"UTF-8"));


            Toast.makeText(context,"Cords recieved "+params.get("timestamp")+" "+String.format("%02d",cal.get(Calendar.HOUR_OF_DAY))+":"+
                    String.format("%02d",cal.get(Calendar.MINUTE))+":"+String.format("%02d",cal.get(Calendar.SECOND)),Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("success");
                    if(result.equals("true"))
                        Toast.makeText(context,"Data sent",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context,"Sending failed",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        DbAPICall sendLocation =new DbAPICall(params,"sendLocation.php",responseListener);
        RequestQueue Queue=Volley.newRequestQueue(context);
        Queue.add(sendLocation);

    }
}
