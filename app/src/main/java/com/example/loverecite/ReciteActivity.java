package com.example.loverecite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loverecite.Gson.WordMassageBean;
import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.datautils.WordInformation;
import com.example.loverecite.okhttp.HttpTest;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ReciteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //获取传过来的信息
        String state = getIntent().getStringExtra("state");
        String book = getIntent().getStringExtra("book");
        final String[] idword = new String[1];
        //标签找到控件
        TextView word_t = findViewById(R.id.word);
        TextView trans_t = findViewById(R.id.tran);
        TextView phonetic_t = findViewById(R.id.phonetic);
        Button button1 = findViewById(R.id.btnr1);
        Button button2 = findViewById(R.id.btnr2);

        //发送获取单词请求
        HttpTest.getInstance().sendListMassage(TransportInfo.getUsername(), book, state, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReciteActivity.this,"网络连接失败，请重试！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();

                idword[0] = WordInformation.getWordId();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("109")) {
                                Toast.makeText(ReciteActivity.this, "所有单词已标记认识或已背完！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                startActivity(intent);
                                ReciteActivity.this.finish();
                            }
                            else if(result.equals("105")||result.equals("108")||result.equals("101")||result.equals("102")) {
                                Toast.makeText(ReciteActivity.this, "服务器端获取信息失败！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                startActivity(intent);
                                ReciteActivity.this.finish();
                            }
                            else {
                                //解析data数据
                                Gson gson = new Gson();
                                WordMassageBean bean = gson.fromJson(result,WordMassageBean.class);
                                //从bean中获取信息
                                String wordId = bean.getWordId();
                                String word = bean.getWord();
                                String trans = bean.getWordTranslation();
                                String pronunce = bean.getWordPhonetic();
                                //存入wordiformation
                                WordInformation wi = new WordInformation(word,wordId,trans,pronunce);

                                word_t.setText(word);
                                trans_t.setText(trans);
                                phonetic_t.setText(pronunce);
                            }
                        }
                    });

            }
        });




//设置监听器类
        class ClickListener implements View.OnClickListener{
            private String wordstate;
            ClickListener(String wordstate){
                this.wordstate = wordstate;
            }
            public void onClick(View view) {
                HttpTest.getInstance().sendWordMassage(TransportInfo.getUsername(), book, WordInformation.getWordId(), wordstate, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReciteActivity.this,"网络连接失败，请重试！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();
                        if(result.equals("200")){
                            //发送获取单词请求
                            HttpTest.getInstance().sendListMassage(TransportInfo.getUsername(), book, state, new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ReciteActivity.this,"网络连接失败，请重试！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    String result = response.body().string();

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result.equals("109")) {
                                                    Toast.makeText(ReciteActivity.this, "所有单词已标记认识或已背完！", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                                    startActivity(intent);
                                                    ReciteActivity.this.finish();
                                                }
                                                else if(result.equals("105")||result.equals("108")||result.equals("101")||result.equals("102")) {
                                                    Toast.makeText(ReciteActivity.this, "服务器端获取信息失败！", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                                    startActivity(intent);
                                                    ReciteActivity.this.finish();
                                                }
                                                else {
                                                    //解析data数据
                                                    Gson gson = new Gson();
                                                    WordMassageBean bean = gson.fromJson(result,WordMassageBean.class);
                                                    //从bean中获取信息
                                                    String wordId = bean.getWordId();
                                                    String word = bean.getWord();
                                                    String trans = bean.getWordTranslation();
                                                    String pronunce = bean.getWordPhonetic();
                                                    //存入wordiformation
                                                    WordInformation wi = new WordInformation(word,wordId,trans,pronunce);
                                                    word_t.setText(word);
                                                    trans_t.setText(trans);
                                                    phonetic_t.setText(pronunce);
                                                }
                                            }
                                        });

                                }
                            });
                        }else if(result.equals("109")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReciteActivity.this,"所选单词已背完",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                    startActivity(intent);
                                    ReciteActivity.this.finish();
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReciteActivity.this,"服务器端信息上传失败",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ReciteActivity.this, FragmentActivity.class);
                                    startActivity(intent);
                                    ReciteActivity.this.finish();
                                }
                            });
                        }
                    }
                });
            }
        }


        ClickListener cl1 = new ClickListener("2");
        ClickListener cl2 = new ClickListener("3");

        button1.setOnClickListener(cl1);
        button2.setOnClickListener(cl2);
    }

}
