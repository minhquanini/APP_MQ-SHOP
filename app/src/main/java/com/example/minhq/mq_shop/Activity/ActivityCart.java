package com.example.minhq.mq_shop.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhq.mq_shop.Adapter.AdapterCart;
import com.example.minhq.mq_shop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ActivityCart extends AppCompatActivity {
    ListView listViewcart;
    TextView textViewempty;
    static TextView textViewtotal;
    Button buttonpay, buttoncontinue;
    Toolbar toolbarcart;
    AdapterCart adapterCart;
    public static int quantity=0;
    public static int productid=0;
    public static ArrayList<Integer> soluongmaxmoisanpham;
    int userid=0;

    int userId =0;
    boolean checkuserid=false;
    String checkmenuitem="Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        ActionToolbar();
        CheckData();
        EventUtils();
        quantity=getIntent().getIntExtra("quantity",-1);
        productid=getIntent().getIntExtra("productID",-1);
        CatchItemListview();
        EventButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SharedPreferences sharedPreferences=getSharedPreferences(ActivityLogin.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        userid =sharedPreferences.getInt("userid",0);
        if(userid !=0){
            checkuserid=true;
        }
        if(checkuserid==true){
            menu.findItem(R.id.menu_login).setTitle("Log out");
            checkmenuitem="Log out";
        }
        else
        {
            menu.findItem(R.id.menu_login).setTitle("Login");
            //checkmenuitem="Log out";
        }
        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_login:
                if(checkmenuitem=="Log out"){
                    SharedPreferences sharedPreferences=getSharedPreferences(ActivityLogin.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("userid");
                    //editor.clear();
                    editor.commit();
                    checkuserid=false;
                    invalidateOptionsMenu();
                    checkmenuitem="Login";
                    Intent intent2=new Intent(ActivityCart.this,MainActivity.class);
                    startActivity(intent2);
                    MainActivity.arrOrderDetail.clear();
                    //Toast.makeText(this, userid+"", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    SharedPreferences sharedPreferences=getSharedPreferences(ActivityLogin.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                    userid=sharedPreferences.getInt("userid",0);
                    Toast.makeText(this, userid+"", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(ActivityCart.this,ActivityLogin.class);
                    startActivity(intent1);
                }


        }
        return true;
    }

    private void EventButton() {
        buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityCart.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buttonpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences(ActivityLogin.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                userId =sharedPreferences.getInt("userid",0);
                if(MainActivity.arrOrderDetail.size()>0){
                    if(userId ==0){
                        Toast.makeText(ActivityCart.this, "Xin mời đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(ActivityCart.this, ActivityCustomer.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(ActivityCart.this, "Giỏ hàng chưa có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CatchItemListview() {
        listViewcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ActivityCart.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Xóa sản phẩm này khỏi giỏ hàng?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.arrOrderDetail.size()<=0){
                            textViewempty.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            MainActivity.arrOrderDetail.remove(position);
                            adapterCart.notifyDataSetChanged();
                            EventUtils();
                            if(MainActivity.arrOrderDetail.size()<=0){
                                textViewempty.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                textViewempty.setVisibility(View.INVISIBLE);
                                adapterCart.notifyDataSetChanged();
                                EventUtils();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCart.notifyDataSetChanged();
                        EventUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static Double EventUtils() {
        Double totalmoney=0.0;
        for (int i=0;i<MainActivity.arrOrderDetail.size();i++){
            totalmoney+=MainActivity.arrOrderDetail.get(i).getPricepd();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        textViewtotal.setText(decimalFormat.format(totalmoney)+" Đ");
        return totalmoney;
    }

    private void CheckData() {
        if(MainActivity.arrOrderDetail.size()<=0){
            adapterCart.notifyDataSetChanged();
            textViewempty.setVisibility(View.VISIBLE);
            listViewcart.setVisibility(View.INVISIBLE);
        }
        else
        {
            adapterCart.notifyDataSetChanged();
            textViewempty.setVisibility(View.INVISIBLE);
            listViewcart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        textViewtotal=(TextView) findViewById(R.id.tv_total);
        textViewempty=(TextView) findViewById(R.id.tv_emptycart);
        buttonpay=(Button) findViewById(R.id.btn_paycart);
        toolbarcart=(Toolbar) findViewById(R.id.toolbar_cart);
        buttoncontinue=(Button) findViewById(R.id.btn_buycontinue);
        listViewcart=(ListView) findViewById(R.id.lv_cart);
        adapterCart=new AdapterCart(ActivityCart.this,R.layout.row_cart,MainActivity.arrOrderDetail);
        listViewcart.setAdapter(adapterCart);
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
