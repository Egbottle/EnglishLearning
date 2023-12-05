package com.example.loverecite.fragment.dictionaryfragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loverecite.Gson.BaiduEntoChBean;
import com.example.loverecite.R;
import com.example.loverecite.okhttp.HttpTest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DictionaryFragment extends Fragment {

    private DictionaryViewModel mViewModel;

    public static DictionaryFragment newInstance() {
        return new DictionaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        // 通过id找到btn与edittext
        Button btn = getView().findViewById(R.id.btn0);
        EditText eidtText = getView().findViewById(R.id.blank);
        TextView textView = getView().findViewById(R.id.result);
         TextView t1 = getView().findViewById(R.id.Ch);
        TextView t2 = getView().findViewById(R.id.En);
        ImageButton ibtn = getView().findViewById(R.id.change);
        //初始为中翻英
        final String[] origin = {"zh"};
        final String[] trans = {"en"};
        //设置handler，对传出消息进行处理
        Handler mHaandler = new Handler(HttpTest.getInstance().looper) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //what传过来信息标号，相当于id
                if (msg.what == 0) {
                    //将信息的obj类型转为string
                    String data = (String) msg.obj;
                    Gson gson = new Gson();
                    BaiduEntoChBean bean = gson.fromJson(data, BaiduEntoChBean.class);
                    textView.setText(bean.getTrans_result().get(0).getDst());

                }
                if (msg.what == 1) {
                    Looper.prepare();
                    try {
                        Snackbar.make(getView(), "请求失败", Snackbar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }
        };

        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t1.getText()=="中文"){
                    t1.setText("英文");
                    t2.setText("中文");
                    origin[0] = "en";
                    trans[0] = "zh";
                }
                else{
                    t1.setText("中文");
                    t2.setText("英文");
                    origin[0] = "zh";
                    trans[0] = "en";
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resource = eidtText.getText().toString();
                if (resource.equals("")) {
                    Snackbar.make(view, "输入框内容不可为空", Snackbar.LENGTH_SHORT).show();
                } else {
                    String source = resource.replace("\n"," ");

                    HttpTest.getInstance().requestGet(source, origin[0], trans[0], new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Message message = new Message();
                            //用于区分谁发送的消息
                            message.what = 1;
                            mHaandler.sendMessage(message);

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            final String result = response.body().string();
                            Message message = new Message();
                            //用于区分谁发送的消息
                            message.what = 0;
                            //将想发送的字符串放入
                            message.obj = result;
                            //发送消息至mHandler
                            try {
                                mHaandler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }

}