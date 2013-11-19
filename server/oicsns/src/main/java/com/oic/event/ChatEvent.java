/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.connection.Connections;
import com.oic.net.WebSocketListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 *  method:chat でのチャット処理
 * @author Morimoto
 */
public class ChatEvent implements ActionEventImpl{
    private static final Logger LOG = Logger.getLogger(ChatEvent.class.getName());
    
    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        Session session = webSocket.getSession();
     //   OicCharacter c = webSocket.getCharacter();
        
       // String name = c.getName();
        //recive  {method:"chat",text:"aaaa"}
        //send {method:"chat", text:"aaaa",userId:"0001"}
        JSONObject sendJson = (JSONObject)json.clone();
        sendJson.put("userId","0001");
        
        Connections.BroadCastMessage(sendJson);
        LOG.log(Level.INFO, "chat event : {0}", session.getRemoteAddress());
    }
    
}
