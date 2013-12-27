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
 * @author t
 */
public class logoutHandler implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "logout");
        responseJSON.put("status", 0);
        webSocket.sendJson(responseJSON);
        webSocket.userLogout();
    }
    
}
