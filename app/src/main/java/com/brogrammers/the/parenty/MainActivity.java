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

import java.util.Calendar;

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



            }
        });


        final DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


            }
        };


        final TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {


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

                new TimePickerDialog(MainActivity.this,t,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show();

            }
        });

    }
}
