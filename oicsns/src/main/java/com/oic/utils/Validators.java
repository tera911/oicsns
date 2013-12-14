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
    private final JSONObject json;
    private final Map<String, validationType> validationRule;
    private int maxLength;
    private int minLength;
    public enum validationType {REQUIRED, STUDENTID, MAXLENGTH, MINLENGTH};

    public Validators(JSONObject json) {
        validationRule = new HashMap<>();
        this.json = json;
    }
    
    /**
     * Validationルールを追加
     * @param key
     * @param types
     */
    public void add(String key, validationType... types){
        for(validationType type : types){
            validationRule.put(key, type);
        }
    }
    
    public boolean validate(){
        try{
            for(Map.Entry<String, validationType> e : validationRule.entrySet()){
                String data = json.get(e.getKey()).toString();
                switch(e.getValue()){
                    //空白チェック
                    case REQUIRED:
                        if(data.equals("")){
                            throw new NullPointerException();
                        }
                    break;
                    case STUDENTID:
                        Pattern pattern = Pattern.compile("^[a-zA-Z][0-9]{4}$");
                        Matcher matcher = pattern.matcher(data);
                        if(!matcher.find()){
                            throw new NullPointerException();
                        }
                    break;
                    case MAXLENGTH:
                        if(data.getBytes().length > maxLength){
                            throw new NullPointerException();
                        }
                    break;
                    case MINLENGTH:
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
    public validationType required(){
        return validationType.REQUIRED;
    }
    
    /**
     * 学籍番号
     * @return 
     */
    public validationType studentId(){
        return validationType.STUDENTID;
    }
    
    /**
     * 最大文字数を決める
     * 全角は2文字として認識される
     * @param maxLength
     * @return 
     */
    public validationType maxLength(int maxLength){
        this.maxLength = maxLength;
        return validationType.MAXLENGTH;
    }
    
    /**
     * 最低文字数を決める
     * 全角は2文字として認識される
     * @param minLength
     * @return 
     */
    public validationType minLength(int minLength){
        this.minLength = minLength;
        return validationType.MINLENGTH;
    }
}
