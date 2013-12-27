/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import com.oic.map.Position;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author morimoto
 */
public class Tools {
    public static String convertData(Date date){
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        return smdf.format(date);
    }
    
    public static JSONObject convertPosToJSON(Position pos){
        JSONObject json = new JSONObject();
        json.put("x", pos.getX());
        json.put("y", pos.getY());
        json.put("width", pos.getWidth());
        json.put("height", pos.getHeight());
        return json;
    }
}
