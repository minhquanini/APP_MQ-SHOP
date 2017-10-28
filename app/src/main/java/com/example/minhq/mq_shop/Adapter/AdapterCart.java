package com.example.minhq.mq_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhq.mq_shop.Activity.ActivityCart;
import com.example.minhq.mq_shop.Activity.ActivityProductDetails;
import com.example.minhq.mq_shop.Activity.MainActivity;
import com.example.minhq.mq_shop.Model.OrderDetail;
import com.example.minhq.mq_shop.Model.Product;
import com.example.minhq.mq_shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by minhq on 10/16/2017.
 */

public class AdapterCart extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<OrderDetail> listCart=null;


    public AdapterCart(Context context, int resource, ArrayList<OrderDetail> listCart) {
        this.context = context;
        this.resource = resource;
        this.listCart = listCart;
    }

    @Override
    public int getCount() {
        return listCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_cart,null);
            viewHolder.txtnamepdcart=(TextView) convertView.findViewById(R.id.tv_namepdcart);
            viewHolder.txtpricepdcart=(TextView) convertView.findViewById(R.id.tv_pricepdcart);
            viewHolder.buttonminus=(Button) convertView.findViewById(R.id.btn_minus);
            viewHolder.buttonplus=(Button) convertView.findViewById(R.id.btn_plus);
            viewHolder.buttonvalue=(Button) convertView.findViewById(R.id.btn_value);
            viewHolder.imageViewpdcart=(ImageView) convertView.findViewById(R.id.img_cart);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        OrderDetail orderDetail= (OrderDetail) getItem(position);
        viewHolder.txtnamepdcart.setText(orderDetail.getNamepd());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtpricepdcart.setText("Giá: "+decimalFormat.format(orderDetail.getPricepd())+" Đ");
        Picasso.with(context).load(orderDetail.getImagepd())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imageViewpdcart);
        viewHolder.buttonvalue.setText(orderDetail.getQuantitypd()+"");
        int soluong= Integer.parseInt(viewHolder.buttonvalue.getText().toString());


        if(soluong>= MainActivity.arrOrderDetail.get(position).quantitymax){  //sai dòng này!!!!!!!!!!!!!!!!!!!!!!!!
        //if(soluong> 10){


            viewHolder.buttonplus.setVisibility(View.INVISIBLE);
            viewHolder.buttonminus.setVisibility(View.VISIBLE);
            //Mới thêm
            //viewHolder.buttonvalue.setText(ActivityCart.quantity+"");
        }
        else if(soluong==1)
        {
            viewHolder.buttonminus.setVisibility(View.INVISIBLE);
        }
        else if(soluong>1){
            viewHolder.buttonminus.setVisibility(View.VISIBLE);
            viewHolder.buttonplus.setVisibility(View.VISIBLE);
        }
        //final ViewHolder finalViewHolder = viewHolder;
        viewHolder.buttonplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitynew=Integer.parseInt(viewHolder.buttonvalue.getText().toString())+1;
                int quantitypressent= MainActivity.arrOrderDetail.get(position).getQuantitypd();
                Double pricepressent=MainActivity.arrOrderDetail.get(position).getPricepd();
                MainActivity.arrOrderDetail.get(position).setQuantitypd(quantitynew);
                Double giamoinhat=(pricepressent*quantitynew)/quantitypressent;
                MainActivity.arrOrderDetail.get(position).setPricepd(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                viewHolder.txtpricepdcart.setText("Giá: "+decimalFormat.format(giamoinhat)+" Đ");
                ActivityCart.EventUtils();
                //viewHolder.buttonvalue.setText(String.valueOf(quantitynew));
                if(quantitynew>MainActivity.arrOrderDetail.get(position).quantitymax-1){
                    viewHolder.buttonplus.setVisibility(View.INVISIBLE);
                    viewHolder.buttonminus.setVisibility(View.VISIBLE);
                    viewHolder.buttonvalue.setText(String.valueOf(quantitynew));
                }
                else
                {
                    viewHolder.buttonplus.setVisibility(View.VISIBLE);
                    viewHolder.buttonminus.setVisibility(View.VISIBLE);
                    viewHolder.buttonvalue.setText(String.valueOf(quantitynew));
                }
            }
        });
        viewHolder.buttonminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitynew=Integer.parseInt(viewHolder.buttonvalue.getText().toString())-1;
                int quantitypressent= MainActivity.arrOrderDetail.get(position).getQuantitypd();
                Double pricepressent=MainActivity.arrOrderDetail.get(position).getPricepd();
                MainActivity.arrOrderDetail.get(position).setQuantitypd(quantitynew);
                Double giamoinhat=(pricepressent*quantitynew)/quantitypressent;
                MainActivity.arrOrderDetail.get(position).setPricepd(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                viewHolder.txtpricepdcart.setText("Giá: "+decimalFormat.format(giamoinhat)+" Đ");
                ActivityCart.EventUtils();
                //viewHolder.buttonvalue.setText(String.valueOf(quantitynew));
                if(quantitynew<2){
                    viewHolder.buttonplus.setVisibility(View.VISIBLE);
                    viewHolder.buttonminus.setVisibility(View.INVISIBLE);
                    viewHolder.buttonvalue.setText(quantitynew+"");
                }
                else
                {
                    viewHolder.buttonplus.setVisibility(View.VISIBLE);
                    viewHolder.buttonminus.setVisibility(View.VISIBLE);
                    viewHolder.buttonvalue.setText(String.valueOf(quantitynew));
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{
        public TextView txtnamepdcart, txtpricepdcart;
        public ImageView imageViewpdcart;
        public Button buttonminus, buttonplus, buttonvalue;
    }
}
