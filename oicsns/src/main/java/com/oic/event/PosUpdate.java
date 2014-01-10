/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.net.WebSocketListener;
import com.oic.utils.Validators;
import org.json.simple.JSONObject;

/**
 * ユーザの座標を更新する
 * @author morimoto
 */
public class PosUpdate implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "posupdate");
        responseJSON.put("status", 0);
        if(validate(json)){
            MapFactory mapFactory = MapFactory.getInstance();
            OicMap map = mapFactory.getMap(Integer.parseInt(json.get("mapid").toString()));
            map.BroadCastMap(responseJSON);
        }else{
            OicCharacter c = webSocket.getCharacter();
            c.getMap().BroadCastMap(responseJSON);
        }
    }
    
    private boolean validate(JSONObject json){
        Validators v = new Validators(json);
        v.add("mapid", v.required());
        return v.validate();
    }
}
