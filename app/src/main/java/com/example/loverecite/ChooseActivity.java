package com.example.loverecite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.okhttp.HttpTest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseActivity extends AppCompatActivity {



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        //获得从BookFragment传来的信息
        String book = getIntent().getStringExtra("book");

        class ClickListener implements View.OnClickListener{
            private String state;
            ClickListener(String state){
                this.state = state;
            }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this,ReciteActivity.class);
                intent.putExtra("state",state);
                intent.putExtra("book",book);
                startActivity(intent);
                ChooseActivity.this.finish();
            }
        }

        //点击第一个按钮
        ClickListener mcl1 = new ClickListener("1");
        btn_1.setOnClickListener(mcl1);
        //点击第二个按钮
        ClickListener mcl2 = new ClickListener("2");
        btn_2.setOnClickListener(mcl2);
        //点击第三个按钮
        ClickListener mcl3 = new ClickListener("3");
        btn_3.setOnClickListener(mcl3);
    }
}

