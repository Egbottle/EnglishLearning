package com.example.loverecite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loverecite.okhttp.HttpTest;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/*     返回值含义
        200 成功
        101 加载驱动失败
        102 数据库连接失败
        101.5 加载驱动失败或数据库连接失败
        106 用户名重复*/

public class RegisterActivity extends AppCompatActivity {
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }


    Handler mHandler = new Handler(HttpTest.getInstance().looper) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //what传过来信息标号，相当于id
            if (msg.what == 0) {
                //将信息的obj类型转为string
                String data = (String) msg.obj;
                if (msg.obj.equals("200")){
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                    }
                else if (msg.obj.equals("106"))
                    Toast.makeText(RegisterActivity.this, "已有此用户名，请更换！", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(RegisterActivity.this, "请求已发送，但未响应！！", Toast.LENGTH_SHORT).show();
                }
            }
            if (msg.what == 1) {
                try {
                    Toast.makeText(RegisterActivity.this, "请求失败，请检查网络！！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //通过id找到button
        Button btn1= findViewById(R.id.btn1);
        //设置点击事件
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //通过id找到控件
                EditText phoneEdt = findViewById(R.id.phone);
                EditText pdsEdt = findViewById(R.id.pds);
                EditText chk_pdsEdt = findViewById(R.id.check_pds);
                //将Editable对象转换成字符串
                String name = phoneEdt.getText().toString();
                String pds = pdsEdt.getText().toString();
                String chk = chk_pdsEdt.getText().toString();

                if (name.equals("")||pds.equals("")||chk.equals("")){
                    //输出无焦点提示
                    Snackbar.make(v,"输入框内容不可为空",Snackbar.LENGTH_SHORT).show();
                }else if(!(pds.equals(chk))){
                    //判断两次输入的密码是否相同
                    Snackbar.make(v,"两次输入的密码不相同",Snackbar.LENGTH_SHORT).show();
                }else if(!isLetterDigit(pds)){
                    Snackbar.make(v,"密码仅能由大小写字母和数字组成！",Snackbar.LENGTH_SHORT).show();
                }else if(name.length()<2){
                    Snackbar.make(v,"用户名过短，2-11个字符！",Snackbar.LENGTH_SHORT).show();
                }else if(pds.length()<6) {
                    Snackbar.make(v, "密码过短，6-20字符！", Snackbar.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"注册中，请稍等...",Toast.LENGTH_SHORT).show();
                    HttpTest.getInstance().registerWithOkHttp(name, pds, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Message message = new Message();
                            //用于区分谁发送的消息
                            message.what = 1;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            final String result = response.body().string();
                            Message message = new Message();
                            //用于区分谁发送的消息
                            message.what = 0;
                            message.obj = result;
                            mHandler.sendMessage(message);
                        }
                    });

                }
            }
        });   //为按钮注册点击事件监听器
    }
    }