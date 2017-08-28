package com.brogrammers.the.parenty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Login);
    }
    public void addbuttonListener()
    {
        final Context context = this;

        Button login_btn = (Button) findViewById(R.id.btn_login);

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Passcode.class);
                startActivity(intent);

            }

        });

    }

}
