package com.example.minhq.mq_shop.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minhq.mq_shop.Adapter.AdapterAttribute;
import com.example.minhq.mq_shop.Model.Attribute;
import com.example.minhq.mq_shop.R;
import com.example.minhq.mq_shop.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityAttributeProduct extends AppCompatActivity {
    Toolbar toolbarAttribute;
    ListView listViewAttribute;
    AdapterAttribute adapterAttribute;
    ArrayList<Attribute> arrAttribute;
    int idproduct=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_product);
        AnhXa();
        GetProductID();
        GetData();
        ActionToolbar();
    }

    private void GetProductID() {
        idproduct=getIntent().getIntExtra("productid",-1);
        //Toast.makeText(this, idproduct+"", Toast.LENGTH_SHORT).show();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarAttribute);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAttribute.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarAttribute=(Toolbar) findViewById(R.id.toolbar_attributeproduct);
        listViewAttribute=(ListView) findViewById(R.id.lv_attributeproduct);
        arrAttribute=new ArrayList<>();
        adapterAttribute=new AdapterAttribute(this,R.layout.row_attribute,arrAttribute);
        listViewAttribute.setAdapter(adapterAttribute);
     }

    private void GetData() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathpostattribute,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response!=null){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    int idatt=jsonObject.getInt("attributeID");
                                    String nameatt=jsonObject.getString("nameattribute");
                                    int idpd=jsonObject.getInt("productID");
                                    String valueatt=jsonObject.getString("value");
                                    arrAttribute.add(new Attribute(idatt,nameatt,idpd,valueatt));
                                    adapterAttribute.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityAttributeProduct.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("productID", String.valueOf(idproduct));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
