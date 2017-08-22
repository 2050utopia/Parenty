package com.brogrammers.the.parenty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_string=(EditText)findViewById(R.id.et_string);
    EditText et_int=(EditText)findViewById(R.id.et_int);
    EditText et_longint=(EditText)findViewById(R.id.et_longint);
    EditText et_date=(EditText)findViewById(R.id.et_date);
    EditText et_time=(EditText)findViewById(R.id.et_time);
    Button btn_send=(Button)findViewById(R.id.btn_send);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        


    }
}
