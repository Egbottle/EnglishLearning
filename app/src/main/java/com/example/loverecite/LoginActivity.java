package com.example.loverecite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.loverecite.Gson.UserInfoBean;
import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.okhttp.HttpTest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


//返回值含义
/*200 成功
        101 加载驱动失败
        102 数据库连接失败
        101.5 加载驱动失败或数据库连接失败
        103 用户登录问题（账号密码错误）
        105 获取用户信息失败*/
public class LoginActivity extends AppCompatActivity {
    private EditText edt_user;
    private EditText edt_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences share = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        if(share!=null&&share.getBoolean("LoginBool",true)&&share.getString("username","").length()>=2){
            Toast.makeText(LoginActivity.this,"验证成功！",Toast.LENGTH_SHORT).show();
            String account = share.getString("username","");
            HttpTest.getInstance().checkWithOkHttp(account, "", new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "用户信息获取失败，请检查网络！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String result = response.body().string();
                    //解析收到的json数据
                    Gson gson = new Gson();
                    UserInfoBean bean = gson.fromJson(result, UserInfoBean.class);
                    //用静态成员转移信息
                    TransportInfo transportInfo = new TransportInfo(bean.getUsername(),bean.getUserpassword(),bean.getName(),bean.getSex(),bean.getAge(),bean.getPhone());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(LoginActivity.this, FragmentActivity.class);
                            startActivity(intent1);
                            LoginActivity.this.finish();
                        }
                    });
                }
            });
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Handler mHandler = new Handler(HttpTest.getInstance().looper) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //what传过来信息标号，相当于id
                if (msg.what == 0) {
                    //将信息的obj类型转为string
                    String data = (String) msg.obj;
                    if (msg.obj.equals("200")) {
                        Toast.makeText(LoginActivity.this, "验证成功！", Toast.LENGTH_SHORT).show();
                        editor.putString("username",edt_user.getText().toString());
                        editor.putBoolean("LoginBool",true);
                        editor.apply();
                        //登录成功后拿到用户信息
                        HttpTest.getInstance().checkWithOkHttp(edt_user.getText().toString(), "", new okhttp3.Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "用户信息获取失败，请检查网络！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                final String result = response.body().string();
                                //解析收到的json数据
                                Gson gson = new Gson();
                                UserInfoBean bean = gson.fromJson(result, UserInfoBean.class);
                                //用静态成员转移信息
                                TransportInfo transportInfo = new TransportInfo(bean.getUsername(),bean.getUserpassword(),bean.getName(),bean.getSex(),bean.getAge(),bean.getPhone());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent1 = new Intent(LoginActivity.this, FragmentActivity.class);
                                        startActivity(intent1);
                                        LoginActivity.this.finish();
                                    }
                                });
                            }
                        });


                    }
                    //103 账号密码错误
                    else if (msg.obj.equals("103")) {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误，请重试！", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "请求已发送，但未响应！！", Toast.LENGTH_SHORT).show();
                    }
                }
                if (msg.what == 1) {
                    try {
                        Toast.makeText(LoginActivity.this, "请求失败，请检查网络！！", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };



        //通过id找到输入框
        edt_user = findViewById(R.id.user_input);
        edt_pwd = findViewById(R.id.pwd_input);
        Button btn_login = findViewById(R.id.btn_login);
        //给按钮绑定点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = edt_user.getText().toString();
                String password = edt_pwd.getText().toString();
                if(account.equals("")|password.equals("")){
                    Snackbar.make(view,"输入内容为空，请重试！",Snackbar.LENGTH_SHORT).show();
                }else{
                    HttpTest.getInstance().loginWithOkhttp(account, password, new Callback() {
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
        });
    }
    public void register(View v){
        Intent intent1=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent1);
    }
}