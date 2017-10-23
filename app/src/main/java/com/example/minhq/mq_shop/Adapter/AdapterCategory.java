package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhq.mq_shop.Model.Category;
import com.example.minhq.mq_shop.Model.Menu;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhq on 10/10/2017.
 */

public class AdapterCategory extends BaseAdapter {
    private Context context;
    private int resource;
    //private LayoutInflater layoutInflater;
    private ArrayList<Category> listcategory;

    public AdapterCategory(Context context, int resource, ArrayList<Category> listcategory) {
        this.context = context;
        //layoutInflater = LayoutInflater.from(context);
        this.resource=resource;
        this.listcategory = listcategory;
    }

    @Override
    public int getCount() {
        return listcategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listcategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_category,null);
            //convertView=layoutInflater.inflate(R.layout.listview_category,null);
           viewHolder.txtView=(TextView) convertView.findViewById(R.id.tv_category);
           // viewHolder.imgview=(ImageView) convertView.findViewById(R.id.imgview_category);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();

        }
        Category category= (Category) getItem(position);
        viewHolder.txtView.setText(category.getNamecategory());
       // Picasso.with(context).load(category.getImage())
       //         .into(viewHolder.imgview);
        return convertView;
    }


    private class ViewHolder{
        //ImageView imgview;
        TextView txtView;
    }
}
