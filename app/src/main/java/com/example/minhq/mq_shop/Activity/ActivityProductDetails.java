package com.example.minhq.mq_shop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhq.mq_shop.Model.OrderDetail;
import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class ActivityProductDetails extends AppCompatActivity {
    Toolbar toolbarproductdetail;
    ImageView imageViewproduct;
    TextView textViewnameproduct, textViewpriceproduct, textViewcontentproduct, textViewclickattribute;
    Spinner spinner;
    Button buttonAddcart;
    int quantity=0;
    int idpd=0;
    String namepd="";
    String imagepd="";
    Double pricepd= 0.0;
    Double promotionpricepd=0.0;

    String descriptionpd="";
    String contentpd="";

    int userid=0;
    boolean checkuserid=false;
    String checkmenuitem="Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        AnhXa();
        ActionToolbar();
        GetInfo();
        CatchEvenSpinner();
        textViewclickattribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityProductDetails.this,ActivityAttributeProduct.class);
                intent.putExtra("productid",idpd);
                startActivity(intent);
            }
        });

        EventButton();


    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SharedPreferences sharedPreferences=getSharedPreferences(ActivityLogin.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        userid=sharedPreferences.getInt("userid",0);
        if(userid!=0){
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
            case R.id.menu_cart:
                Intent intent=new Intent(ActivityProductDetails.this,ActivityCart.class);
                //intent.putExtra("quantity",quantity);
                startActivity(intent);
                break;
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
                    Intent intent2=new Intent(ActivityProductDetails.this,MainActivity.class);
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
                    Intent intent1=new Intent(ActivityProductDetails.this,ActivityLogin.class);
                    startActivity(intent1);
                }

        }
        return true;
    }

    private void EventButton() {
        buttonAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 0) {
                    Toast.makeText(ActivityProductDetails.this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                } else {
                    if (MainActivity.arrOrderDetail.size() > 0) {
                        int slsp = Integer.parseInt(spinner.getSelectedItem().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.arrOrderDetail.size(); i++) {
                            if (MainActivity.arrOrderDetail.get(i).getIdpd() == idpd) {
                                MainActivity.arrOrderDetail.get(i).setQuantitypd(MainActivity.arrOrderDetail.get(i).getQuantitypd() + slsp);
                                if (MainActivity.arrOrderDetail.get(i).getQuantitypd() > quantity) {
                                    MainActivity.arrOrderDetail.get(i).setQuantitypd(quantity);
                                }
                                MainActivity.arrOrderDetail.get(i).setPricepd(pricepd * MainActivity.arrOrderDetail.get(i).getQuantitypd());
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            int soluongsp = Integer.parseInt(spinner.getSelectedItem().toString());
                            Double newprice = soluongsp * pricepd;
                            MainActivity.arrOrderDetail.add(new OrderDetail(idpd, namepd, newprice, imagepd, soluongsp,quantity));
                        }
                    } else {
                        int soluongsp = Integer.parseInt(spinner.getSelectedItem().toString());
                        Double newprice = soluongsp * pricepd;
                        MainActivity.arrOrderDetail.add(new OrderDetail(idpd, namepd, newprice, imagepd, soluongsp,quantity));
                    }
                    Intent intent = new Intent(ActivityProductDetails.this, ActivityCart.class);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("productID", idpd);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchEvenSpinner() {
        //Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        if(quantity==0){
            Toast.makeText(this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
        }
        else {
            List<Integer> soluong = new ArrayList<Integer>();
            for (int i = 1; i <= quantity; i++) {
                soluong.add(i);
            }
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
            spinner.setAdapter(arrayAdapter);
        }
    }

    private void GetInfo() {
        //int idpd=0;

        Product product= (Product) getIntent().getSerializableExtra("productdetails");
        idpd=product.getProductID();
        namepd=product.getNameproduct();
        imagepd=product.getImageproduct();
        pricepd=product.getPrice();
        promotionpricepd=product.getPromotionprice();
        quantity=product.getQuantity();
        descriptionpd=product.getDescriptionproduct();
        contentpd=product.getContentproduct();
        

        textViewnameproduct.setText(namepd);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        textViewpriceproduct.setText("Giá bán: "+ decimalFormat.format(pricepd)+" Đ");
        textViewcontentproduct.setText(contentpd);
        Picasso.with(getApplicationContext()).load(imagepd)
                .placeholder(R.drawable.no_image)
                .into(imageViewproduct);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarproductdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarproductdetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarproductdetail=(Toolbar) findViewById(R.id.toolbar_productdetail);
        imageViewproduct=(ImageView) findViewById(R.id.img_productdetail);
        textViewnameproduct=(TextView) findViewById(R.id.tv_nameproductdetail);
        textViewpriceproduct=(TextView) findViewById(R.id.tv_priceproductdetail);
        textViewcontentproduct=(TextView) findViewById(R.id.tv_contentproduct);
        textViewclickattribute=(TextView) findViewById(R.id.tv_linktoattribute);

        spinner=(Spinner) findViewById(R.id.spinner);
        buttonAddcart=(Button) findViewById(R.id.btn_addcart);
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
