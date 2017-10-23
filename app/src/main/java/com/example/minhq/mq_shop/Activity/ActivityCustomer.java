package com.example.minhq.mq_shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityCustomer extends AppCompatActivity {
    EditText editTextname,editTextemail,editTextphone,editTextaddress;
    RadioButton radioButtonpaydirect, radioButtonpayonline;
    Button buttonAccept, buttonCancel;
    String namecustomer="";
    String emailcustomer="";
    String phonecustomer="";
    String addresscustomer="";
    String paymentmethod="";
    //Date currentTime = Calendar.getInstance().getTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        AnhXa();
        EventButton();
    }

    private void EventButton() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityCustomer.this,ActivityCart.class);
                startActivity(intent);
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namecustomer=editTextname.getText().toString();
                emailcustomer=editTextemail.getText().toString();
                phonecustomer=editTextphone.getText().toString();
                addresscustomer=editTextaddress.getText().toString();
                if(namecustomer.equals("")&&emailcustomer.equals("")&&phonecustomer.equals("")&&addresscustomer.equals("")){
                    Toast.makeText(ActivityCustomer.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(radioButtonpaydirect.isChecked()==true) {
                        paymentmethod=radioButtonpaydirect.getText().toString();
                        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCustomer.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathpostcustomer,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String orderID) {
                                        //Trả về orderID
                                        Log.d("orderID",orderID);
                                        if(Integer.parseInt(orderID)>0){
                                            RequestQueue queue=Volley.newRequestQueue(ActivityCustomer.this);
                                            StringRequest request=new StringRequest(Request.Method.POST, Server.pathpostorderdetail,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            if(response.equals("1")){
                                                                MainActivity.arrOrderDetail.clear();
                                                                Toast.makeText(ActivityCustomer.this, "Bạn đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(ActivityCustomer.this,MainActivity.class);
                                                                startActivity(intent);
                                                                Toast.makeText(ActivityCustomer.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(ActivityCustomer.this, "Không thể thực hiện đặt hàng", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(ActivityCustomer.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    JSONArray jsonArray=new JSONArray();
                                                    for(int i=0;i<MainActivity.arrOrderDetail.size();i++){
                                                        JSONObject jsonObject=new JSONObject();
                                                        try {
                                                            jsonObject.put("orderID",orderID);
                                                            jsonObject.put("productID",MainActivity.arrOrderDetail.get(i).getIdpd());
                                                            jsonObject.put("quantity",MainActivity.arrOrderDetail.get(i).getQuantitypd());
                                                            jsonObject.put("price",MainActivity.arrOrderDetail.get(i).getPricepd());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                            jsonArray.put(jsonObject);
                                                    }
                                                    HashMap<String,String> hashMap=new HashMap<String, String>();
                                                    hashMap.put("orderdetail",jsonArray.toString());
                                                    return hashMap;
                                                }
                                            };
                                            queue.add(request);
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> param=new HashMap<String, String>();
                                param.put("customername",namecustomer);
                                param.put("customeremail",emailcustomer);
                                param.put("customerphone",phonecustomer);
                                param.put("customeraddress",addresscustomer);
                                //param.put("createddate",currentTime+"");
                                param.put("total",ActivityCart.EventUtils()+"");
                                param.put("paymentmethod",paymentmethod);
                                //param.put("orderstatus","Chưa duyệt");
                                //Làm thêm về user
                                //param.put("userID","4");
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else if(radioButtonpayonline.isChecked()==true)
                    {
                        /*
                        Intent intent=new Intent(ActivityCustomer.this,ActivityPayOnline.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("name",namecustomer);
                        bundle.putString("email",emailcustomer);
                        bundle.putString("phone",phonecustomer);
                        bundle.putString("address",addresscustomer);
                        bundle.putString("payonline",radioButtonpayonline.getText().toString());
                        intent.putExtra("BUNDLE",bundle);
                        startActivity(intent);
                        */
                        paymentmethod=radioButtonpayonline.getText().toString();
                        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCustomer.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathpostcustomer,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String orderid) {
                                        //Trả về orderID
                                        Log.d("orderID",orderid);
                                        if(Integer.parseInt(orderid)>0){
                                            RequestQueue queue1=Volley.newRequestQueue(ActivityCustomer.this);
                                            StringRequest request1=new StringRequest(Request.Method.POST, Server.pathpostorderdetail,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            if(response.equals("1")){
                                                                //MainActivity.arrOrderDetail.clear();
                                                                Toast.makeText(ActivityCustomer.this, "Bạn đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(ActivityCustomer.this,ActivityPayOnline.class);
                                                                startActivity(intent);
                                                                Toast.makeText(ActivityCustomer.this, "Nhập thông tin thanh toán", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(ActivityCustomer.this, "Không thể thực hiện đặt hàng", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(ActivityCustomer.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    JSONArray jsonArray=new JSONArray();
                                                    for(int i=0;i<MainActivity.arrOrderDetail.size();i++){
                                                        JSONObject jsonObject=new JSONObject();
                                                        try {
                                                            jsonObject.put("orderID",orderid);
                                                            jsonObject.put("productID",MainActivity.arrOrderDetail.get(i).getIdpd());
                                                            jsonObject.put("quantity",MainActivity.arrOrderDetail.get(i).getQuantitypd());
                                                            jsonObject.put("price",MainActivity.arrOrderDetail.get(i).getPricepd());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        jsonArray.put(jsonObject);
                                                    }
                                                    HashMap<String,String> hashMap=new HashMap<String, String>();
                                                    hashMap.put("orderdetail",jsonArray.toString());
                                                    return hashMap;
                                                }
                                            };
                                            queue1.add(request1);
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> param=new HashMap<String, String>();
                                param.put("customername",namecustomer);
                                param.put("customeremail",emailcustomer);
                                param.put("customerphone",phonecustomer);
                                param.put("customeraddress",addresscustomer);
                                //param.put("createddate",currentTime+"");
                                param.put("total",ActivityCart.EventUtils()+"");
                                param.put("paymentmethod",paymentmethod);
                                //param.put("orderstatus","Chưa duyệt");
                                //Làm thêm về user
                                //param.put("userID","4");
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }

            }
        });
    }

    private void AnhXa() {
        editTextname= findViewById(R.id.edt_namecustomer);
        editTextemail= findViewById(R.id.edt_emailcustomer);
        editTextphone= findViewById(R.id.edt_phonecustomer);
        editTextaddress= findViewById(R.id.edt_addresscustomer);
        radioButtonpaydirect= findViewById(R.id.rdb_directly);
        radioButtonpayonline= findViewById(R.id.rdb_online);
        buttonAccept= findViewById(R.id.btn_accept);
        buttonCancel= findViewById(R.id.btn_cancel);

    }
}
