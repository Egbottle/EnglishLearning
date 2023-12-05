package com.example.loverecite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.loverecite.Gson.Cet4Bean;
import com.example.loverecite.Gson.Cet6Bean;
import com.google.gson.Gson;

public class CetInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView tv1 = findViewById(R.id.username);
        TextView tv2 = findViewById(R.id.non_choose);
        TextView tv3 = findViewById(R.id.known);
        TextView tv4 = findViewById(R.id.unknown);
        //获取传过来的信息
        String data = getIntent().getStringExtra("data");
        int id = getIntent().getIntExtra("id",0);
        //解析数据
        if(id==0) {
            Gson gson = new Gson();
            Cet4Bean c4 = gson.fromJson(data, Cet4Bean.class);
            tv1.setText(c4.getUsername());
            tv2.setText(c4.getCET4NonChooseNum());
            tv3.setText(c4.getCET4KonwnNum());
            tv4.setText(c4.getCET4UnkonwnNum());
        }else{
            Gson gson = new Gson();
            Cet6Bean c6 = gson.fromJson(data,Cet6Bean.class);
            tv1.setText(c6.getUsername());
            tv2.setText(c6.getCET6NonChooseNum());
            tv3.setText(c6.getCET6KonwnNum());
            tv4.setText(c6.getCET6UnkonwnNum());
        }


    }
}