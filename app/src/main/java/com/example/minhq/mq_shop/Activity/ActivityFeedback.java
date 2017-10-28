package com.example.minhq.mq_shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class ActivityFeedback extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextContent;
    Button buttonSend, buttonCancel;
    Toolbar toolbarFeedback;
    private String namefeedback="", emailfeedback="", contentfeedback="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        AnhXa();
        ActionToolbar();
        ActionButton();
    }

    private void ActionButton() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityFeedback.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namefeedback=editTextName.getText().toString();
                emailfeedback=editTextEmail.getText().toString();
                contentfeedback=editTextContent.getText().toString();
                if(namefeedback.equals("")||emailfeedback.equals("")||contentfeedback.equals("")){
                    Toast.makeText(ActivityFeedback.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(ActivityFeedback.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathpostfeedback,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(Integer.parseInt(response)==1){
                                        Toast.makeText(ActivityFeedback.this, "Gửi phản hồi thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ActivityFeedback.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityFeedback.this, "Gửi phản hồi thất bại", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ActivityFeedback.this, "Lỗi khi gửi phản hồi", Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> param=new HashMap<String, String>();
                            param.put("namefb",namefeedback);
                            param.put("emailfb",emailfeedback);
                            param.put("contentfb",contentfeedback);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarFeedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarFeedback.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        editTextName=findViewById(R.id.edt_namefb);
        editTextEmail=findViewById(R.id.edt_emailfb);
        editTextContent=findViewById(R.id.edt_contentfb);
        buttonSend=findViewById(R.id.btn_sendfb);
        buttonCancel=findViewById(R.id.btn_cancelfb);
        toolbarFeedback=findViewById(R.id.toolbar_feedback);
    }
}
