package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by minhq on 10/13/2017.
 */

public class AdapterProduct extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<Product> listProduct;

    public AdapterProduct(Context context, int resource, ArrayList<Product> listProduct) {
        this.context = context;
        this.resource = resource;
        this.listProduct = listProduct;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_product,null);
            viewHolder.txtname=(TextView) convertView.findViewById(R.id.tv_nameproduct);
            viewHolder.txtprice=(TextView) convertView.findViewById(R.id.tv_priceproduct);
            viewHolder.txtdescript=(TextView) convertView.findViewById(R.id.tv_descriptionproduct);
            viewHolder.imgpd=(ImageView) convertView.findViewById(R.id.img_product);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Product product= (Product) getItem(position);
        viewHolder.txtname.setText(product.getNameproduct());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtprice.setText("Giá bán: "+decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtdescript.setMaxLines(2);
        viewHolder.txtdescript.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtdescript.setText(product.getDescriptionproduct());
        Picasso.with(context).load(product.getImageproduct())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imgpd);
        return convertView;
    }

    public class ViewHolder{
        TextView txtname, txtprice, txtdescript;
        ImageView imgpd;
    }
}
