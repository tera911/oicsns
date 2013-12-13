/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class Validators {
    private JSONObject json;
    private Map<String, Integer> validationRule;
    private int maxLength;
    private int minLength;

    public Validators(JSONObject json) {
        validationRule = new HashMap<>();
        this.json = json;
    }
    
    /**
     * Validationルールを追加
     * @param key
     * @param type 
     */
    public void add(String key, int... type){
        for(Integer i : type){
            validationRule.put(key, i);
        }
    }
    
    public boolean validate(){
        try{
            for(Map.Entry<String, Integer> e : validationRule.entrySet()){
                String data = json.get(e.getKey()).toString();
                switch(e.getValue()){
                    //空白チェック
                    case 1:
                        if(data.equals("")){
                            throw new NullPointerException();
                        }
                    break;
                    case 2:
                        Pattern pattern = Pattern.compile("^[a-zA-Z][0-9]{4}$");
                        Matcher matcher = pattern.matcher(data);
                        if(!matcher.find()){
                            throw new NullPointerException();
                        }
                    break;
                    case 3:
                        if(data.getBytes().length > maxLength){
                            throw new NullPointerException();
                        }
                    break;
                    case 4:
                        if(data.getBytes().length < minLength){
                            throw new NullPointerException();
                        }
                    break;
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    
    /* validation type */
    
    /**
     * 空白禁止
     * @return 
     */
    public int required(){
        return 1;
    }
    
    /**
     * 学籍番号
     * @return 
     */
    public int studentId(){
        return 2;
    }
    
    /**
     * 最大文字数を決める
     * 全角は2文字として認識される
     * @param maxLength
     * @return 
     */
    public int maxLength(int maxLength){
        this.maxLength = maxLength;
        return 3;
    }
    
    /**
     * 最低文字数を決める
     * 全角は2文字として認識される
     * @param minLength
     * @return 
     */
    public int minLength(int minLength){
        this.minLength = minLength;
        return 4;
    }
}
