package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhq.mq_shop.Model.Brand;
import com.example.minhq.mq_shop.Model.Category;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by minhq on 10/12/2017.
 */

public class AdapterBrand extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<Brand> listbrand;

    public AdapterBrand(Context context, int resource, ArrayList<Brand> listbrand) {
        this.context = context;
        this.resource = resource;
        this.listbrand = listbrand;
    }

    @Override
    public int getCount() {
        return listbrand.size();
    }

    @Override
    public Object getItem(int position) {
        return listbrand.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new AdapterBrand.ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_brand,null);
            //convertView=layoutInflater.inflate(R.layout.listview_category,null);
            viewHolder.txtView=(TextView) convertView.findViewById(R.id.tv_brand);
            viewHolder.imgview=(ImageView) convertView.findViewById(R.id.imgview_brand);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (AdapterBrand.ViewHolder) convertView.getTag();

        }
        Brand brand=(Brand) getItem(position);
        viewHolder.txtView.setText(brand.getNamebrand());
        Picasso.with(context).load(brand.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imgview);
        return convertView;
    }

    private class ViewHolder{
        ImageView imgview;
        TextView txtView;
    }
}
