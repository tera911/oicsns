/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

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
        
        switch(cmd){
            case "getUserName":
                JSONObject json1 = new JSONObject();
                json1.put("name", webSocket.getCharacter().getName());
                webSocket.sendJson(json1);
                break;
        }
    }
    
}
