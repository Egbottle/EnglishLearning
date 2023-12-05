package com.example.loverecite.okhttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserInfoCheck {
    //接入端口
    //登录
    private String url_login = "http://123.56.25.87:8080/Maven/Login?";
    //注册
    private String url_register = "http://123.56.25.87:8080/Maven/Register?";
    //登录成功后返回信息
    private String url_userinfo = "http://123.56.25.87:8080/Maven/Condition?";
    //提交修改信息请求
    private  String url_changeinfo = "http://123.56.25.87:8080/Maven/Alter?";
    //选择背的单词范围
    private String url_chooseword = "http://123.56.25.87:8080/Maven/Transfer?";
    //选择
    private String url_sendword = "http://123.56.25.87:8080/Maven/Recite?";
    //查看已背的CET4的单词数
    private String url_cet4 = "http://123.56.25.87:8080/Maven/CET4?";
    //查看已背的CET6的单词数
    private String url_cet6 = "http://123.56.25.87:8080/Maven/CET6?";
    //登录，注册，修改信息，选择背词范围
    public String outputString(String account, String password,int caseId){
        switch(caseId) {
            case 0:
                return url_login + "username=" + account + "&userpassword=" + password;
            case 1:
                return url_register + "username=" + account + "&userpassword=" + password;
            default:
                return url_userinfo + "username="+account;
        }
    }
    //修改信息
    public String outputString(String username,String password,String name,String age,String sex,String phone){
        return url_changeinfo + "username="+username+"&userpassword="+password+"&name="+name+"&age="+age+"&sex="+sex+"&phone="+phone;
    }
    //选词范围
    public String outputString(String username,String bookname,String state) {
        return url_chooseword + "username=" + username + "&bookname=" + bookname + "&recitestate=" + state;
    }
    //上传标记单词信息
    public  String outputString(String username,String bookname,String wordld,String state){
        return url_sendword + "username=" + username + "&bookname="+ bookname+"&wordId="+wordld+"&wordstate="+state;
    }
    public String outputString(String username,int caseid){
        switch (caseid){
            case 0:
                return url_cet4 + "username=" + username;
            default:
                return url_cet6 + "username=" + username;
        }
    }
    }



