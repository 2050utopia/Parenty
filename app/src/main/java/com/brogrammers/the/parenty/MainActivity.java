package com.brogrammers.the.parenty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_string,et_int,et_longint,et_date,et_time;
    Button btn_send;
    Calendar cal=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_string=(EditText)findViewById(R.id.et_string);
        et_int=(EditText)findViewById(R.id.et_int);
        et_longint=(EditText)findViewById(R.id.et_longint);
        et_date=(EditText)findViewById(R.id.et_date);
        et_time=(EditText)findViewById(R.id.et_time);
        Button btn_send=(Button)findViewById(R.id.btn_send);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Response.Listener<String> responseListener=new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                       try{

                           JSONObject jsonObject =new JSONObject(response);
                           String result=jsonObject.getString("success");

                           if(result.equals("true"))
                               Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                           else
                               Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();


                       }
                       catch(JSONException je)
                       {
                            je.printStackTrace();
                       }


                   }
               };

             String phpapi="demo.php";
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",et_string.getText().toString());
                params.put("age",et_int.getText().toString());
                params.put("mobile",et_longint.getText().toString());
                try {
                    params.put("dob", URLEncoder.encode(et_date.getText().toString(),"UTF-8"));
                    params.put("tob", URLEncoder.encode(et_time.getText().toString(),"UTF-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                DbAPICall trail =new DbAPICall(params,phpapi,responseListener);

                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                queue.add(trail);

            }
        });


        final DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                cal.set(Calendar.YEAR,year);
                cal.set(Calendar.MONTH,month+1);
                cal.set(Calendar.DAY_OF_MONTH,day);
                et_date.setText(cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR));


            }
        };


        final TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {


                cal.set(Calendar.HOUR_OF_DAY,hour);
                cal.set(Calendar.MINUTE,minute);

                //calculation of am pm
                String ampm=cal.get(Calendar.HOUR_OF_DAY)>11?"PM":"AM";
                int timein12hour=cal.get(Calendar.HOUR_OF_DAY);

                //midnight shows 0:45 so to make it 12 :45 this logic is used
                if(timein12hour==0)
                    timein12hour=12;
                else if(timein12hour>12)
                    timein12hour-=12;

                //seconds and minutes should occupy two digits always
                et_time.setText(timein12hour+":"+String.format("%02d",cal.get(Calendar.MINUTE))+" "+ampm);


            }
        };

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(MainActivity.this,d,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new TimePickerDialog(MainActivity.this,t,cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),false).show();

            }
        });

    }
}
