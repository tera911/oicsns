/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.net.WebSocketListener;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class ChatlogEvent implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        if(validation(json)){
            return;
        }
        String mapId = json.get("mapid").toString();
        
        
    }
    
    private boolean validation(JSONObject json){
       if(json.get("mapid") == null){
           return false;
       }
       return true;
    }
}
