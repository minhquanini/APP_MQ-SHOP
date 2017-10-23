package com.example.minhq.mq_shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.minhq.mq_shop.R;

public class ActivityContact extends AppCompatActivity {
    TextView textView_Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        textView_Phone=(TextView) findViewById(R.id.tv_phone);
    }
}
