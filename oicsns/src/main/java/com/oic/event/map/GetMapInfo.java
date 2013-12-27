/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event.map;

import com.oic.event.ActionEventImpl;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.map.Position;
import com.oic.net.WebSocketListener;
import com.oic.utils.Validators;
import org.json.simple.JSONObject;

/**
 *
 * @author kxhtj529
 */
public class GetMapInfo implements ActionEventImpl{
 
    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "getmapinfo");
        if(!validation(json)){
            responseJSON.put("status", "1");
            webSocket.sendJson(responseJSON);
            return;
        }
        
        MapFactory factory = MapFactory.getInstance();
        int mapid = Integer.parseInt(json.get("mapid").toString());
        OicMap map = factory.getMap(mapid);
        
        responseJSON.put("mapid", map.getMapId());
        responseJSON.put("imgpath", map.getPath());
        JSONObject posJSON = new JSONObject();
        Position pos = map.getPos();
        posJSON.put("x", pos.getX());
        posJSON.put("y", pos.getX());
        posJSON.put("width", pos.getWidth());
        posJSON.put("height", pos.getHeight());
        responseJSON.put("pos", posJSON);
        webSocket.sendJson(responseJSON);
    }
    
    public boolean validation(JSONObject json){
        Validators v = new Validators(json);
        v.add("mapid", v.integerType());
        return v.validate();
    }

}
