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
 *
 * @author morimoto
 */
public class GetUserInfo implements ActionEventImpl {

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "getuserinfo");
        OicCharacter c = webSocket.getCharacter();
        long userid = c.getUserId();
        int mapid = c.getMapid();
        if (validation(json)) {
            userid = Long.parseLong(json.get("userid").toString());
            mapid = Integer.parseInt(json.get("mapid").toString());
        }
        System.out.println("mapid : "+ mapid);
        System.out.println("userid : "+ userid);
        getUserinfo(responseJSON, userid, mapid);
        webSocket.sendJson(responseJSON);
    }

    private boolean validation(JSONObject json) {
        Validators v = new Validators(json);
        v.add("mapid", v.required(), v.integerType());
        v.add("userid", v.required(), v.longType());
        return v.validate();
    }

    private JSONObject getUserinfo(JSONObject json, long userid, int mapid) {
        MapFactory factory = MapFactory.getInstance();
        OicMap map = factory.getMap(mapid);
        OicCharacter c = map.getUser(userid);
        json.put("userid", c.getUserId());
        json.put("username", c.getName());
        JSONObject posJSON = new JSONObject();
        Position pos = c.getPos();
        posJSON.put("x", pos.getX());
        posJSON.put("y", pos.getX());
        posJSON.put("width", pos.getWidth());
        posJSON.put("height", pos.getHeight());
        json.put("pos", posJSON);
        json.put("mapid", c.getMapid());
        json.put("avatarid", c.getAvatarId());
        return json;
    }
}
