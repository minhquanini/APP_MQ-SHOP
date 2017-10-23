package com.example.minhq.mq_shop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.minhq.mq_shop.BuildConfig;
import com.example.minhq.mq_shop.Utils.Server;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;

import com.example.minhq.mq_shop.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityPayOnline extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    int i=0;
    //Button buttoncompletepayonline,buttoncancelpayonline;
    Button submit;
    //CardInputWidget mCardInputWidget;
    Double money=0.0;
    EditText cardnumber, month,year,cvc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_online);
        //GetData();
        //mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        //buttoncancelpayonline=(Button) findViewById(R.id.btn_cancelpayonline);
        //buttoncompletepayonline=(Button) findViewById(R.id.btn_completepayonline);
        cardnumber=findViewById(R.id.cardNumber);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        cvc=findViewById(R.id.cvc);
        money=ActivityCart.EventUtils()/22680.0;
        Double d = new Double(money);
        i = d.intValue();


        final Card card=new Card(cardnumber.getText().toString(),
                Integer.parseInt(month.getText().toString()),
                Integer.parseInt(year.getText().toString()),
                cvc.getText().toString());

        submit= findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stripe stripe=new Stripe(ActivityPayOnline.this,"pk_test_zweiNdynhBQNk96SuMNZCq9P");
                stripe.createToken(card,
                        new TokenCallback() {
                            @Override
                            public void onError(Exception error) {
                                Toast.makeText(ActivityPayOnline.this, "Lỗi tạo token", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(final Token token) {
                                Toast.makeText(ActivityPayOnline.this, "Token: "+token.getId(), Toast.LENGTH_SHORT).show();

                                RequestQueue requestQueue= Volley.newRequestQueue(ActivityPayOnline.this);
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathposttoken,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(response.equals("1")){
                                                    Toast.makeText(ActivityPayOnline.this, "Thanh toán thành công", Toast.LENGTH_LONG).show();
                                                    MainActivity.arrOrderDetail.clear();
                                                    Intent intent=new Intent(ActivityPayOnline.this,MainActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(ActivityPayOnline.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(ActivityPayOnline.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(ActivityPayOnline.this, "Không thể gửi token lên web server", Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String,String> hashMap=new HashMap<String, String>();
                                        hashMap.put("token",token.getId());
                                        hashMap.put("total",i+"");
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(stringRequest);

                            }
                        });
            }
        });






        /*
        buttoncompletepayonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card card=mCardInputWidget.getCard();
                if (card == null) {
                    Toast.makeText(ActivityPayOnline.this, "Mã thẻ thanh toán không tồn tại", Toast.LENGTH_SHORT).show();
                }

                Stripe stripe=new Stripe(ActivityPayOnline.this,"pk_test_zweiNdynhBQNk96SuMNZCq9P");
                stripe.createToken(card,
                        new TokenCallback() {
                            @Override
                            public void onError(Exception error) {
                                Toast.makeText(ActivityPayOnline.this, "Lỗi tạo token", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(final Token token) {
                                //Gửi token lên web server
                                Toast.makeText(ActivityPayOnline.this, "Token: "+token.getId(), Toast.LENGTH_SHORT).show();

                                RequestQueue requestQueue= Volley.newRequestQueue(ActivityPayOnline.this);
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.pathposttoken,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(ActivityPayOnline.this, "Token: "+response, Toast.LENGTH_LONG).show();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(ActivityPayOnline.this, "Không thể gửi token lên web server", Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String,String> hashMap=new HashMap<String, String>();
                                        hashMap.put("token",token.getId());
                                        hashMap.put("total",(ActivityCart.EventUtils()/22680.00)+"");
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(stringRequest);

                            }
                        });

            }
        });
        */

    }

    private void GetData() {
        intent=getIntent();
        bundle=intent.getBundleExtra("BUNDLE");
        Toast.makeText(this, bundle.getString("name"), Toast.LENGTH_SHORT).show();
    }
}
