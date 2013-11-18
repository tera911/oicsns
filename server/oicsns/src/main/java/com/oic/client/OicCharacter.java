/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.client;

/**
 *
 * @author morimoto
 */
public class OicCharacter {
    private long userId;
    private String name;
    
    public static OicCharacter loadCharFromDB(long userId){
        OicCharacter ret = new OicCharacter();
        //SQLからデータをロードする
        ret.userId = 10000;
        ret.name = "まにゅ";
        return ret;
    }
    
    public String getName(){
        return this.name;
    }
    public long getUserId(){
        return this.userId;
    }
}
