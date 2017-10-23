package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

public class AdapterLVHome extends ArrayAdapter<Menu> {
    private Context context;
    private int resource;
    private ArrayList<Menu> listMenu;

    public AdapterLVHome(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Menu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listMenu = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_menu_home,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageViewHome=(ImageView) convertView.findViewById(R.id.imgview_home);
            viewHolder.textViewHome=(TextView) convertView.findViewById(R.id.tv_home);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Menu menu=listMenu.get(position);
        //Menu menu= (Menu) getItem(position);
        viewHolder.textViewHome.setText(menu.getName());
        Picasso.with(context).load(menu.getImage())
                .placeholder(R.drawable.error)
                .into(viewHolder.imageViewHome);
        return convertView;
    }

    public class ViewHolder{
        TextView textViewHome;
        ImageView imageViewHome;
    }
}
