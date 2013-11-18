/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.event;

import com.oic.client.OicCharacter;
import com.oic.net.WebSocketListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 *
 * @author kxhtj529
 */
public class ChatEvent implements ActionEventImpl{
    private static final Logger LOG = Logger.getLogger(ChatEvent.class.getName());
    
    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        Session session = webSocket.getSession();
        OicCharacter c = webSocket.getCharacter();
        
        String name = c.getName();
        LOG.log(Level.INFO, "chat event : {0}", session.getRemoteAddress());
    }
    
}
