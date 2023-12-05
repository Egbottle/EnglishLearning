package com.example.loverecite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.okhttp.HttpTest;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChangeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        EditText ed1 = findViewById(R.id.password);
        EditText ed2 = findViewById(R.id.name);
        EditText ed3 = findViewById(R.id.age);
        EditText ed4 = findViewById(R.id.sex);
        EditText ed5 = findViewById(R.id.phone);
        Button btn_apply = findViewById(R.id.btn_apply);

        //先获取值
//        String password = mybundle.getString("password");.
//        String name = mybundle.getString("name");
//        String age = mybundle.getString("age");
//        String sex = mybundle.getString("sex");
//        String phone = mybundle.getString("phone");
        String username = TransportInfo.getUsername();
        String name = TransportInfo.getName();
        String password = TransportInfo.getPassword();
        String sex = TransportInfo.getSex();
        String age = TransportInfo.getAge();
        String phone = TransportInfo.getPhone();

        //传入edittext中
        ed1.setText(password);
        ed2.setText(name);
        ed3.setText(age);
        ed4.setText(sex);
        ed5.setText(phone);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password_ = ed1.getText().toString();
                String name_ = ed2.getText().toString();
                String age_ = ed3.getText().toString();
                String sex_ = ed4.getText().toString();
                String phone_ = ed5.getText().toString();
                //若信息未修改就提交，发出提醒
                if(!RegisterActivity.isLetterDigit(password_)){
                    Snackbar.make(view,"密码仅能由大小写字母和数字组成！",Snackbar.LENGTH_SHORT).show();
                }else if(password_.equals("")){
                    Snackbar.make(view,"密码不可为空！",Snackbar.LENGTH_SHORT).show();
                }else if(password_.length()<6) {
                    Snackbar.make(view, "密码过短，6-20字符！", Snackbar.LENGTH_SHORT).show();
                }else if(!sex_.equals("男")&&!sex_.equals("女")&&!sex_.equals("")){
                    Snackbar.make(view, "性别只能输入男或女！", Snackbar.LENGTH_SHORT).show();
                }else if(phone_.length()!=11&&phone_.length()!=7&&phone_.length()!=0){
                    Snackbar.make(view, "电话号码长度错误！", Snackbar.LENGTH_SHORT).show();
                }else if(password.equals(password_)&&name.equals(name_)&&age.equals(age_)&&sex.equals(sex_)&&phone.equals(phone_)){
                    Snackbar.make(view, "信息未修改！", Snackbar.LENGTH_SHORT).show();
                }else if(password.equals("")&&name.equals("")&&age.equals("")&&sex.equals("")&&phone.equals("")){
                    Snackbar.make(view, "信息不可全空！", Snackbar.LENGTH_SHORT).show();
                }else{
                    HttpTest.getInstance().sendChangedInfo(username, password_, name_, sex_, age_, phone_, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeInfoActivity.this,"网络连接失败，请重新提交！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(result.equals("200")) {
                                        TransportInfo transportInfo = new TransportInfo(username,password_,name_,sex_,age_,phone_);
                                        Toast.makeText(ChangeInfoActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ChangeInfoActivity.this,"修改时出现问题,请检查格式！",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                    });
                }

            }
        });
    }
}