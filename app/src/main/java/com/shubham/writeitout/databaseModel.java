package com.shubham.writeitout;

import java.util.ArrayList;

/**
 * Created by hp on 03-May-17.
 */

public class databaseModel {
  private  ArrayList<String> newWord = new ArrayList<>();

    public ArrayList<String> getNewWord() {
        return newWord;
    }

    public void setNewWord(ArrayList<String> newWord) {
        this.newWord = newWord;
    }

    public ArrayList<Integer> getNum() {
        return num;
    }

    public void setNum(ArrayList<Integer> num) {
        this.num = num;
    }

    private ArrayList<Integer>num=new ArrayList<>();

}
