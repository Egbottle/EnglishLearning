package com.example.loverecite.datautils;

public class WordInformation {
    private static String word;
    private static String wordId;
    private static String trans;
    private static String phonetic;

    public WordInformation(String word, String wordId, String trans, String phonetic){
        this.word = word;
        this.wordId = wordId;
        this.trans = trans;
        this.phonetic= phonetic;
    }

    public static String getPhonetic() {
        return phonetic;
    }

    public static String getTrans() {
        return trans;
    }

    public static String getWord() {
        return word;
    }

    public static String getWordId() {
        return wordId;
    }
}
