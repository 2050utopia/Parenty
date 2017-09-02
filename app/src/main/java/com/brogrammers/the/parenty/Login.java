package com.brogrammers.the.parenty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView tv_createAccount;
    EditText et_mobileno,et_password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences=getSharedPreferences("parentdetails", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString("username","").isEmpty())
        {
            Intent intent_dash=new Intent(Login.this,DashBoard.class);
            startActivity(intent_dash);
        }

        btn_login=(Button)findViewById(R.id.btn_login);
        tv_createAccount=(TextView)findViewById(R.id.tv_createAccount);
        et_password=(EditText)findViewById(R.id.et_password);
        et_mobileno=(EditText)findViewById(R.id.et_mobileno);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String phpapi = ((Parenty) Login.this.getApplication()).getDb_url()+"verifyLogin.php";
                //Toast.makeText(Login.this,phpapi,Toast.LENGTH_SHORT).show();

                StringRequest verifyLogin=new StringRequest(Request.Method.POST,phpapi,
                        new Response.Listener<String>() {
                            @Override
                             public void onResponse(String response) {

                                try {

                                    JSONObject jsonObject=new JSONObject(response);
                                    Boolean success=new Boolean(jsonObject.getString("success"));

                                    if(success)
                                    {
                                        Boolean validmobile=new Boolean(jsonObject.getString("validmobile"));
                                        if(validmobile)
                                        {
                                            Boolean validpassword=new Boolean(jsonObject.getString("validpassword"));
                                            if(validpassword)
                                            {
                                                String username=jsonObject.getString("username");
                                                sharedPreferences=getSharedPreferences("parentdetails", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                                editor.putString("username",username);
                                                editor.commit();
                                                Intent intent_dash=new Intent(Login.this,DashBoard.class);
                                                startActivity(intent_dash);
                                            }
                                            else
                                            {
                                                AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                                                alertDialog.setTitle("Sorry");
                                                alertDialog.setMessage("Invalid Password");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                        }
                                        else
                                        {
                                            AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                                            alertDialog.setTitle("Sorry");
                                            alertDialog.setMessage("Invalid MobileNo");
                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            alertDialog.show();
                                        }

                                    }

                                } catch (JSONException e) {


                                    AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                                    alertDialog.setTitle("Oops");
                                    alertDialog.setMessage("Something occured.Try again.");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 if (error instanceof TimeoutError ) {
                                     Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();

                                 }
                                 else if(error instanceof NoConnectionError){
                                     AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                                     alertDialog.setTitle("Oops");
                                     alertDialog.setMessage("No Internet Connection");
                                     alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                             new DialogInterface.OnClickListener() {
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     dialog.dismiss();
                                                 }
                                             });
                                     alertDialog.show();
                                 }
                                     else if (error instanceof AuthFailureError) {
                                     Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                                 } else if (error instanceof ServerError) {
                                     Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                                 } else if (error instanceof NetworkError) {
                                     Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                                 } else if (error instanceof ParseError) {
                                     Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                                 }
                            }
                        })
                {
                    @Override
                            protected Map<String,String> getParams() {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("mobileno", et_mobileno.getText().toString());
                        params.put("password", et_password.getText().toString());
                        return params;
                    }
                };

                RequestQueue queue= Volley.newRequestQueue(Login.this);
                queue.add(verifyLogin);


            }
        });

        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_signup=new Intent(Login.this,Registration.class);
                Login.this.startActivity(intent_signup);
            }
        });



    }

}
