/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.net.WebSocketListener;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
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
                webSocket.sendJson(json1);
                break;
            case "username":
                json1.put("name", webSocket.getCharacter().getName());
                webSocket.sendJson(json1);
                break;
        }
    }
    
}
