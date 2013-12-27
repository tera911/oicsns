/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.event.map.GetMapInfo;
import com.oic.event.map.GetMapList;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.map.Position;
import com.oic.net.LoginEvent;
import com.oic.net.WebSocketListener;
import com.oic.utils.Tools;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * debug用コマンド
 * @author morimoto
 */
public class CmdEvent implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        String cmd = json.get("cmd").toString();
        System.out.println("cmd : "+cmd);
        OicCharacter c = webSocket.getCharacter();
        JSONObject json1 = new JSONObject();    
        switch(cmd){
            case "userinfo":
                json1.put("id", c.getUserId());
                json1.put("name", c.getName());
                json1.put("studentId",c.getStudentNumber().toString());
                json1.put("avaterId", c.getAvatarId());
                json1.put("birthday", c.getBirthday().toString());
                json1.put("mapid", c.getMapid());
                break;
            case "username":
                json1.put("name", webSocket.getCharacter().getName());
                break;
            case "getchar" :
                json1.put("method", "getchar");
                json1.put("userid", 1);
                json1.put("username", "みーくん");
                json1.put("mapid", 31);
                Position pos = new Position(300, 200, 100, 230);
                json1.put("pos", Tools.convertPosToJSON(pos));
                json1.put("avatar", 1);
                webSocket.sendJson(json1);
                
                json1.put("method", "getchar");
                json1.put("userid", 2);
                json1.put("username", "みかん");
                json1.put("mapid", 31);
                pos = new Position(150, 250, 100, 230);
                json1.put("pos", Tools.convertPosToJSON(pos));
                json1.put("avatar", 2);
                webSocket.sendJson(json1);
                
                json1.put("method", "getchar");
                json1.put("userid", 3);
                json1.put("username", "りんごちゃん");
                json1.put("mapid", 31);
                pos = new Position(400, 200, 100, 230);
                json1.put("pos", Tools.convertPosToJSON(pos));
                json1.put("avatar", 3);
                
            break;
            case "getmaplist":
                json1.put("method", "getmaplist");
                new GetMapList().ActionEvent(json1, webSocket);
            break;
            case "getmapinfo":
                json1.put("method", "getmapinfo");
                json1.put("mapid", json.get("mapid"));
                new GetMapInfo().ActionEvent(json1, webSocket);
            break;
            case "online":
               // JSONObject maps = new JSONObject();
                MapFactory factory = MapFactory.getInstance();
                for(OicMap map : factory.getMapList()){
                    String mapName = map.getMapName();
                    int count = map.getUserCont();
                    json1.put(mapName,  count);
                }
               // json1.put("user", maps);
            break;
        }
        webSocket.sendJson(json1);
    }
    
}
