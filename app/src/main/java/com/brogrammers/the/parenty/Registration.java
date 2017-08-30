package com.brogrammers.the.parenty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Registration extends AppCompatActivity {

    EditText et_retypeppwd,et_ppwd,et_pmobno,et_pemail,et_pname;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_pname=(EditText)findViewById(R.id.et_pname);
        et_pemail=(EditText)findViewById(R.id.et_pemail);
        et_pmobno=(EditText)findViewById(R.id.et_pmobno);
        et_ppwd=(EditText)findViewById(R.id.et_ppwd);
        et_retypeppwd=(EditText)findViewById(R.id.et_retypeppwd);

        btn_signup=(Button)findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Registration.this,"onclick",Toast.LENGTH_SHORT).show();
                String phpapi=((Parenty)Registration.this.getApplication()).getDb_url()+"registerUser.php";
                StringRequest signup=new StringRequest(Request.Method.POST, phpapi, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Registration.this,"onresponse",Toast.LENGTH_SHORT).show();
                        try {


                            JSONObject jsonObject=new JSONObject(response);
                            Boolean success=new Boolean(jsonObject.getString("success"));
                            Toast.makeText(Registration.this,"jsonconverted"+success,Toast.LENGTH_SHORT).show();
                            if(success)
                            {
                                Toast.makeText(Registration.this,"success",Toast.LENGTH_SHORT).show();
                                if(jsonObject.getString("userexist").equals("true"))
                                {
                                    Toast.makeText(Registration.this,"userexist",Toast.LENGTH_SHORT).show();
                                    AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
                                    alertDialog.setTitle("Oops");
                                    alertDialog.setMessage("Phone number already exist.Please Login.");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent login=new Intent(Registration.this,Login.class);
                                                    Registration.this.startActivity(login);
                                                }
                                            });
                                    alertDialog.show();



                                }
                                else
                                {
                                    Toast.makeText(Registration.this,"usernew",Toast.LENGTH_SHORT).show();
                                    AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
                                    alertDialog.setTitle("Thankyou");
                                    alertDialog.setMessage("Welcome to Parenty.");
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
                                Toast.makeText(Registration.this,"nosuccess",Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
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

                        if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();

                        }
                        else if(error instanceof NoConnectionError){
                            AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
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
                    protected Map<String,String> getParams()
                    {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("username",et_pname.getText().toString());
                        params.put("useremail",et_pemail.getText().toString());
                        params.put("userpassword",et_ppwd.getText().toString());
                        params.put("usermobileno",et_pmobno.getText().toString());
                        return params;
                    }

                };
                RequestQueue queue= Volley.newRequestQueue(Registration.this);
                queue.add(signup);


            }
        });

    }
}
