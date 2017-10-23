package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhq.mq_shop.Activity.ActivityProductDetails;
import com.example.minhq.mq_shop.Activity.MainActivity;
import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.Model.ProductNew;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by minhq on 10/12/2017.
 */

public class AdapterProuductNew extends RecyclerView.Adapter<AdapterProuductNew.ItemHolder> {
    private Context context;
    private int resource;
    private ArrayList<Product> listProduct;


    //private final PublishSubject<String> onClickSubject = PublishSubject.create();

    public AdapterProuductNew(Context context, int resource, ArrayList<Product> listProduct) {
        this.context = context;
        this.resource = resource;
        this.listProduct = listProduct;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_new,null);
        ItemHolder itemHolder=new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Product product=listProduct.get(position);
        holder.txtnameproductnew.setText(product.getNameproduct());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtpriceproductnew.setText("Giá: "+ decimalFormat.format(product.getPrice())+" Đ");
        Picasso.with(context).load(product.getImageproduct())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgproductnew);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgproductnew;
        public TextView txtnameproductnew, txtpriceproductnew;

        public ItemHolder(View itemView) {
            super(itemView);
            imgproductnew= (ImageView) itemView.findViewById(R.id.imgview_productnew);
            txtnameproductnew=(TextView) itemView.findViewById(R.id.tv_nameproductnew);
            txtpriceproductnew=(TextView) itemView.findViewById(R.id.tv_priceproductnew);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ActivityProductDetails.class);
                    int position=getAdapterPosition();
                    intent.putExtra("productdetails",listProduct.get(position));
                    //intent.putExtra("quantity", MainActivity.a);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(context, listProduct.get(position).getNameproduct(), Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });
        }

    }


}
