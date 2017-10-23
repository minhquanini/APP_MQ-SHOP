package com.example.minhq.mq_shop.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhq.mq_shop.Adapter.AdapterCart;
import com.example.minhq.mq_shop.Model.OrderDetail;
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
                if(MainActivity.arrOrderDetail.size()>0){
                    Intent intent=new Intent(ActivityCart.this,ActivityCustomer.class);
                    startActivity(intent);
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
}
