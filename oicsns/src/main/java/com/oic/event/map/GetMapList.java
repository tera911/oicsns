/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event.map;

import com.oic.event.ActionEventImpl;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.net.WebSocketListener;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author kxhtj529
 */
public class GetMapList implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "getmaplist");
        MapFactory mapFactory = MapFactory.getInstance();
        Map<Integer,String> maps = new HashMap<>();
        for(OicMap map : mapFactory.getMapList()){
            maps.put(map.getMapId(), map.getMapName());
        }
        responseJSON.put("maplist", maps);
        webSocket.sendJson(responseJSON);
    }
}
