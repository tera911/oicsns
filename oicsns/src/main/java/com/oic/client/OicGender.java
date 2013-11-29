/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.client;

/**
 * 性別の列挙型
 * @author morimoto
 */
public enum OicGender {
    MAN("man"),
    WOMAN("woman"),
    OTOKONOKO("otokonoko"),
    MIKUN("mikun");
    
    private String gender;
    private OicGender(String s){
        gender = s;
    }
    public String getValue(){
        return gender;
    }
    
    public static OicGender valueOf(int i){
        return OicGender.values()[i];
    }
   
}
