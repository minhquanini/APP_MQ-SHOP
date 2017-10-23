package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhq.mq_shop.Model.Attribute;
import com.example.minhq.mq_shop.Model.Category;
import com.example.minhq.mq_shop.R;

import java.util.ArrayList;

/**
 * Created by minhq on 10/15/2017.
 */

public class AdapterAttribute extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<Attribute> listattribute;

    public AdapterAttribute(Context context, int resource, ArrayList<Attribute> listattribute) {
        this.context = context;
        this.resource = resource;
        this.listattribute = listattribute;
    }

    @Override
    public int getCount() {
        return listattribute.size();
    }

    @Override
    public Object getItem(int position) {
        return listattribute.get(position);
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
            convertView=inflater.inflate(R.layout.row_attribute,null);
            viewHolder.txtnameattribute=(TextView) convertView.findViewById(R.id.tv_nameattribute);
            viewHolder.txtvalueattribute=(TextView) convertView.findViewById(R.id.tv_attributevalue);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Attribute attribute= (Attribute) getItem(position);
        viewHolder.txtnameattribute.setText(attribute.getNameattribute());
        viewHolder.txtvalueattribute.setText(attribute.getValue());
        return convertView;
    }

    public class ViewHolder{
        TextView txtnameattribute,txtvalueattribute;
    }
}
