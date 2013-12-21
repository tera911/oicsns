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
public enum OicGender{
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
    
    /**
     * 文字列から性別の列挙型を返す
     * @param gender
     * @return 
     */
    public static OicGender getGender(String gender){
        for(OicGender gen : OicGender.values()){
            if(gen.getValue().equals(gender)){
                return gen;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString(); 
    }   
}
