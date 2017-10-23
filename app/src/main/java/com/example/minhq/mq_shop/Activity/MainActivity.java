package com.example.minhq.mq_shop.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.minhq.mq_shop.Adapter.AdapterBrand;
import com.example.minhq.mq_shop.Adapter.AdapterCategory;
import com.example.minhq.mq_shop.Adapter.AdapterLVHome;
import com.example.minhq.mq_shop.Adapter.AdapterProuductNew;
import com.example.minhq.mq_shop.Model.Brand;
import com.example.minhq.mq_shop.Model.Category;
import com.example.minhq.mq_shop.Model.Menu;
import com.example.minhq.mq_shop.Model.OrderDetail;
import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.Model.ProductNew;
import com.example.minhq.mq_shop.R;

import com.example.minhq.mq_shop.Utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<OrderDetail> arrOrderDetail;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ListView listView, listviewCategory;
    AdapterLVHome adapter=null;
    AdapterCategory adaptercategory=null;
    AdapterBrand adapterbrand=null;
    AdapterProuductNew adapterProuductNew=null;
    ArrayList<Menu> arrMenu;
    ArrayList<Category> arrCategory;
    ArrayList<Brand> arrBrand;
    public static ArrayList<Product> arrProduct;

    int id=0;
    String namecat="";

    int idbrand=0;
    String namebrd;
    String imgbrd="";
   // public static int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ListMenuHome();
        ActionBar();
        ActionViewFlipper();
        GetDataMenu();
        GetDataProduct();



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
                Intent intent=new Intent(MainActivity.this,ActivityCart.class);
                //intent.putExtra("quantity",a);
                startActivity(intent);
                break;
            case R.id.menu_login:
                Intent intent1=new Intent(MainActivity.this,ActivityLogin.class);
                startActivity(intent1);

        }
        return true;
    }

    private void ActionViewFlipper() {
        ArrayList<String>  quangcao=new ArrayList<>();
        quangcao.add("http://genknews.genkcdn.vn/thumb_w/660/2017/canonfullframemirrorlessfeat-1507267852181.jpg");
        quangcao.add("http://image.nghenhinvietnam.vn/w1024/uploaded/vietduc/2016_06_10/70/nghenhinvietnam_vn_pentax_k_70_1_skfh.jpg");
        quangcao.add("https://tinhte.cdnforo.com/store/2015/06/3060460_3060402_3048062_tinhte.vn_X-T10_hands_on_8.jpg");
        quangcao.add("http://xuansoncamera.com/image/data/gioi-thieu-may-anh-canon-nikon-hinh2.jpg");
        for(int i=0;i<quangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar       =(Toolbar) findViewById(R.id.toolbarHome);
        viewFlipper   =(ViewFlipper) findViewById(R.id.viewlipper);
        recyclerView  =(RecyclerView) findViewById(R.id.recycleview);
        navigationView=(NavigationView) findViewById(R.id.navigationview);
        drawerLayout  =(DrawerLayout) findViewById(R.id.drawerlayout);
        listView      =(ListView) findViewById(R.id.listviewHome);
        arrProduct = new ArrayList<>();
        adapterProuductNew=new AdapterProuductNew(this,R.layout.row_product_new,arrProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapterProuductNew);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"Đang chọn vị trí thứ "+ position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        Toast.makeText(MainActivity.this, "Bạn trở về trang chủ", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //Toast.makeText(MainActivity.this, "Bạn chọn danh mục", Toast.LENGTH_SHORT).show();
                        Dialogcategory();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        //Toast.makeText(MainActivity.this, "Bạn chọn nhãn hiệu", Toast.LENGTH_SHORT).show();
                        Dialogbrand();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Bạn muốn liên hệ", Toast.LENGTH_SHORT).show();
                        Intent intent_contact=new Intent(MainActivity.this,ActivityContact.class);
                        startActivity(intent_contact);
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "Bạn phản hồi", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        if(arrOrderDetail!=null){

        }
        else
        {
            arrOrderDetail=new ArrayList<>();
        }
    }

    private void getDatacategory(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, Server.pathcat,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    id=jsonObject.getInt("categoryID");
                                    namecat=jsonObject.getString("namecategory");
                                    arrCategory.add(new Category(id,namecat));
                                    //arrCategory.add(namecat);
                                    adaptercategory.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi hiển thị!", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private ArrayList<Menu> GetDataMenu(){
        arrMenu.add(new Menu("Trang Chủ",R.drawable.home));
        arrMenu.add(new Menu("Danh Mục",R.drawable.category));
        arrMenu.add(new Menu("Nhãn Hiệu",R.drawable.camera));
        arrMenu.add(new Menu("Liên Hệ",R.drawable.contact));
        arrMenu.add(new Menu("Phản Hồi",R.drawable.feedback));
        return arrMenu;
    }

    private void ListMenuHome(){
        arrMenu   =new ArrayList<>();
        adapter=new AdapterLVHome(this,R.layout.listview_menu_home,arrMenu);
        listView.setAdapter(adapter);
    }

    private void Dialogcategory() {
        arrCategory=new ArrayList<>();
        getDatacategory();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.listview_dialog_category, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Chọn danh mục");
        ListView lv = (ListView) convertView.findViewById(R.id.lv_category);
        adaptercategory = new AdapterCategory(this,R.layout.listview_category,arrCategory);
        lv.setAdapter(adaptercategory);
        final Dialog dialog = alertDialog.create();
        dialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Bạn chọn danh mục có ID: "+arrCategory.get(position).getCategoryID(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ActivityProductCategory.class);
                intent.putExtra("idcategory",arrCategory.get(position).getCategoryID());
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //alertDialog.show();

    }

    private void Dialogbrand(){
        arrBrand=new ArrayList<>();
        getDatabrand();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view =(View) inflater.inflate(R.layout.listview_dialog_brand,null);
        alertDialog.setView(view);
        alertDialog.setTitle("Chọn nhãn hiệu");
        ListView lvbrd=(ListView) view.findViewById(R.id.lv_brand);
        adapterbrand=new AdapterBrand(this,R.layout.listview_brand,arrBrand);
        lvbrd.setAdapter(adapterbrand);
        final Dialog dialog = alertDialog.create();
        dialog.show();
        lvbrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Bạn chọn nhãn hiệu "+arrBrand.get(position).getBrandID(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ActivityProductBrand.class);
                intent.putExtra("idbrand",arrBrand.get(position).getBrandID());
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //alertDialog.show();
    }

    private void getDatabrand(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, Server.pathbrd,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    idbrand=jsonObject.getInt("brandID");
                                    namebrd=jsonObject.getString("namebrand");
                                    imgbrd=jsonObject.getString("image");
                                    String anh="http://10.1.18.114:8080/MQ-SHOP/"+imgbrd;
                                    arrBrand.add(new Brand(idbrand,namebrd,anh));
                                    //arrCategory.add(namecat);
                                    adapterbrand.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi hiển thị!", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataProduct(){
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, Server.pathpdnew, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    int idpdnew=jsonObject.getInt("productID");
                                    String namepd=jsonObject.getString("nameproduct");
                                    Double pricepd=jsonObject.getDouble("price");
                                    Double propricepd=jsonObject.getDouble("promotionprice");
                                    int quantitypd=jsonObject.getInt("quantity");
                                    String imgpd=jsonObject.getString("imageproduct");
                                    String loadimage="http://10.1.18.114:8080/MQ-SHOP/"+imgpd;
                                    String descriptpd=jsonObject.getString("descriptionproduct");
                                    String contpd=jsonObject.getString("contentproduct");
                                    arrProduct.add(new Product(idpdnew,namepd,loadimage,pricepd,propricepd,quantitypd,descriptpd,contpd));
                                    //a=arrProduct.get(i).getQuantity();
                                    adapterProuductNew.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
