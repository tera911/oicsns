/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event.map;

import com.oic.event.ActionEventImpl;
import com.oic.net.WebSocketListener;
import com.oic.utils.Validators;
import org.json.simple.JSONObject;

/**
 *
 * @author kxhtj529
 */
public class TransferMap implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "transfermap");
        if(!validation(json)){
        responseJSON.put("status", "1");
            webSocket.sendJson(responseJSON);
        }
    }
    
    private boolean validation(JSONObject json){
        Validators v = new Validators(json);
        v.add("mapid", v.integerType());
        return v.validate();
    }
    
}
