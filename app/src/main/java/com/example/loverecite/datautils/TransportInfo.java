package com.example.loverecite.datautils;

public class TransportInfo {
    static private String username;
    static private String name;
    static private String password;
    static  private String sex;
    static private String age;
    static private String phone;

   public TransportInfo(String username,String password,String name,String sex,String age,String phone){
        this.name = name;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

    public static String getAge() {
        return age;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getSex() {
        return sex;
    }

    public static String getUsername() {
        return username;
    }
}
