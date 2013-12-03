/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.login;

import com.oic.event.ActionEventImpl;
import com.oic.net.WebSocketListener;
import org.json.simple.JSONObject;

/**
 *
 * @author kxhtj529
 */
public class LoginHandler implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        long userId = Long.parseLong(json.get("userid").toString());
        String password = json.get("password").toString();
        
        //login true
        webSocket.setCharacter(com.oic.client.OicCharacter.loadCharFromDB(userId));
    }
    
}
