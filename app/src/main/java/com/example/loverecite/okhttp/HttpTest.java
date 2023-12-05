package com.example.loverecite.okhttp;

import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpTest {


   private HttpTest(){}
    private static HttpTest instance = new HttpTest();
    public static HttpTest getInstance(){
        return instance;
    }
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();


    public Looper looper = Looper.myLooper();

    public void getRequest(String url,Callback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //翻译api的get请求
    public void requestGet(String query,String from, String to,Callback callback){
        TransApi transApi = new TransApi("20220705001265156","w47xFqKotWVf6UXeTbyw");
        String url = transApi.buildString(query, from, to);
        getRequest(url,callback);
    }

    public void loginWithOkhttp(String account, String password, Callback callback){
        //0表登录，1表注册
        String url = new UserInfoCheck().outputString(account,password,0);
        getRequest(url,callback);
    }

    //注册
    public void registerWithOkHttp(String account, String password,Callback callback){
        String url = new UserInfoCheck().outputString(account,password,1);
        getRequest(url,callback);
    }

    //注册后拿到用户单词信息
    public void checkWithOkHttp(String account,String password,Callback callback){
        String url = new UserInfoCheck().outputString(account,password,2);
        getRequest(url,callback);
    }

    //提交修改信息
    public void sendChangedInfo(String username,String password,String name,String sex,String age,String phone,Callback callback){
        String url = new UserInfoCheck().outputString(username,password,name,age,sex,phone);
        getRequest(url,callback);
    }

    //提交选词范围
    public void sendListMassage(String username,String bookname,String state,Callback callback){
        String url = new UserInfoCheck().outputString(username,bookname,state);
        getRequest(url,callback);
    }

    //提交单词信息
    public  void sendWordMassage(String username,String bookname,String wordid,String state,Callback callback){
        String url = new UserInfoCheck().outputString(username,bookname,wordid,state);
        getRequest(url,callback);
    }

    //查看cet4与cet6背单词数
    public void sendCheckRequest(String username,int caseid,Callback callback){
        String url = new UserInfoCheck().outputString(username,caseid);
        getRequest(url,callback);
    }
}
