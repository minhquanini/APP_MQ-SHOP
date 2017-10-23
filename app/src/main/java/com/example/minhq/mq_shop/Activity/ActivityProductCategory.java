package com.example.minhq.mq_shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minhq.mq_shop.Adapter.AdapterProduct;
import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.R;
import com.example.minhq.mq_shop.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityProductCategory extends AppCompatActivity {
    Toolbar toolbarProductCategory;
    ListView listViewProductCategory;
    AdapterProduct adapterProduct;
    ArrayList<Product> arrProduct;
    int idcat=0;
    int quantitypd=0;
    //public static int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        AnhXa();
        GetIDcategory();
        ActionToolbar();
        GetData();
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent=new Intent(ActivityProductCategory.this,ActivityCart.class);
                //intent.putExtra("quantity",arrProduct.get());
                startActivity(intent);
                break;
            case R.id.menu_login:
                Intent intent1=new Intent(ActivityProductCategory.this,ActivityLogin.class);
                startActivity(intent1);

        }
        return true;
    }


    private void GetData() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathpostidcat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response!=null){
                            //Toast.makeText(ActivityProductCategory.this, response+"", Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    int pdID=jsonObject.getInt("productID");
                                    String pdname=jsonObject.getString("nameproduct");
                                    String pdimage=jsonObject.getString("imageproduct");
                                    String anhproduct="http://10.1.18.114:8080/MQ-SHOP/"+pdimage;
                                    Double pdprice=jsonObject.getDouble("price");
                                    Double pdproprice=jsonObject.getDouble("promotionprice");
                                    quantitypd=jsonObject.getInt("quantity");
                                    String pddescript=jsonObject.getString("descriptionproduct");
                                    String pdcontent=jsonObject.getString("contentproduct");
                                    arrProduct.add(new Product(pdID,pdname,anhproduct,pdprice,pdproprice,quantitypd,pddescript,pdcontent));
                                    //a=arrProduct.get(i).getQuantity();
                                    adapterProduct.notifyDataSetChanged();
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
                        Toast.makeText(ActivityProductCategory.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("categoryID",String.valueOf(idcat));
                //param.put("categoryID",idcat);
                return param;
            }

            /*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<String,String>();
                headers.put("Content-Type","application/json; charset=UTF-8");
                headers.put("categoryID",String.valueOf(idcat));
                return headers;
            }
            */
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarProductCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProductCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIDcategory() {
        idcat=getIntent().getIntExtra("idcategory",-1);
        Log.d("Giá trị categoryID: ",idcat+"");
    }

    private void AnhXa() {
        toolbarProductCategory=(Toolbar) findViewById(R.id.toolbar_productcategory);
        listViewProductCategory=(ListView) findViewById(R.id.lv_productcategory);
        arrProduct=new ArrayList<>();
        adapterProduct=new AdapterProduct(this,R.layout.row_product,arrProduct);
        listViewProductCategory.setAdapter(adapterProduct);
        listViewProductCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ActivityProductCategory.this,ActivityProductDetails.class);
                intent.putExtra("productdetails",arrProduct.get(position));
                startActivity(intent);
            }
        });
    }
}
