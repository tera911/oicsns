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
public class Character {
    private long userId;
    private String name;
    
    public static Character loadCharFromDB(){
        Character ret = new Character();
        //SQLからデータをロードする
        ret.userId = 10000;
        ret.name = "まにゅ";
        return ret;
    }
}
