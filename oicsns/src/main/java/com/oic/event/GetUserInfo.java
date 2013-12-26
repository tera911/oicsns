/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.map.Position;
import com.oic.net.WebSocketListener;
import com.oic.utils.Validators;
import org.json.simple.JSONObject;

/**
 * ユーザのマップ上の描画に関する情報を処理する
 * @author morimoto
 */
public class GetUserInfo implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method","getuserinfo");
        if(!validation(json)){
            responseJSON.put("status", 1);
            webSocket.sendJson(responseJSON);
            return;
        }
        long userid = Long.parseLong(json.get("userid").toString());
        int mapid = Integer.parseInt(json.get("mapid").toString());
        MapFactory factory = MapFactory.getInstance();
        OicMap map = factory.getMap(mapid);
        OicCharacter c = map.getUser(userid);
        responseJSON.put("userid", c.getUserId());
        responseJSON.put("username", c.getName());
        JSONObject posJSON = new JSONObject();
        Position pos = map.getPos();
        posJSON.put("x", pos.getX());
        posJSON.put("y", pos.getX());
        posJSON.put("width", pos.getWidth());
        posJSON.put("height", pos.getHeight());
        responseJSON.put("pos", posJSON);
        responseJSON.put("mapid", c.getMapid());
        responseJSON.put("avatarid", c.getAvatarId());
        webSocket.sendJson(responseJSON);
    }
    
    private boolean validation(JSONObject json){    
        Validators v = new Validators(json);
        v.add("userid", v.required());
        v.add("mapid", v.integerType());
        return v.validate();
    }
}
