package com.example.loverecite.fragment.dictionaryfragment;

public class ChorEn {
    public static String CheckLanguage(String resource){
        if(true == resource.matches("^[a-zA-Z]*")){
            return "en";
        }else if(true == resource.matches("[\u4e00-\u9fa5]+")){
            return "zh";
        }else{
            return "None";
        }

    }
}
