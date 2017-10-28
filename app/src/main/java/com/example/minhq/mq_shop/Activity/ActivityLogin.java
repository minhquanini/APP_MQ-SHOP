package com.example.minhq.mq_shop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minhq.mq_shop.R;
import com.example.minhq.mq_shop.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonCancel, buttonSignup;
    Toolbar toolbarLogin;
    int userId;
    public static final String SHARED_PREFERENCES_NAME="data_user";
    //String userName;
    //int[] datauser=null;
    int error=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        ActionToolbar();
        ActionButton();

    }

    private void ActionButton() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityLogin.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=editTextUsername.getText().toString();
                final String password=editTextPassword.getText().toString();
                if(username.equals("")||password.equals("")){
                    Toast.makeText(ActivityLogin.this, "Bạn chưa nhập username hoặc password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(ActivityLogin.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathlogin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response!=null){
                                        try {
                                            //JSONArray jsonArray=new JSONArray(response);
                                            //JSONObject jsonObject=jsonArray.getJSONObject(1);
                                            JSONObject jsonObject=new JSONObject(response);
                                            userId=jsonObject.getInt("userID");
                                            SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences.edit();
                                            editor.putInt("userid",userId);
                                            editor.commit();
                                            Log.d("a","ko lỗi");

                                            Toast.makeText(ActivityLogin.this, response, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(ActivityLogin.this,MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(ActivityLogin.this, "UserID: "+userId, Toast.LENGTH_SHORT).show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            error=1;
                                            Toast.makeText(ActivityLogin.this, "Sai username hoặc password hehe", Toast.LENGTH_SHORT).show();
                                        }
                                        /*
                                        if(error==1){
                                            Toast.makeText(ActivityLogin.this, "Sai username hoặc password hehe", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(error==0) {

                                            Toast.makeText(ActivityLogin.this, response, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(ActivityLogin.this,MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(ActivityLogin.this, "UserID: "+userId, Toast.LENGTH_SHORT).show();
                                        }
                                        */
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityLogin.this, "Sai username hoặc password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ActivityLogin.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> param=new HashMap<String, String>();
                            param.put("username",username);
                            param.put("password",password);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void AnhXa() {
        editTextUsername=findViewById(R.id.edt_username);
        editTextPassword=findViewById(R.id.edt_password);
        buttonLogin=findViewById(R.id.btn_login);
        buttonCancel=findViewById(R.id.btn_cancel);
        buttonSignup=findViewById(R.id.btn_signup);
        toolbarLogin= findViewById(R.id.toolbar_login);
    }
}
