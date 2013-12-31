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
        OicCharacter c = webSocket.getCharacter();
        //validation
        if(json.get("text") == null){
            LOG.warning("invalid Message!");
            return;
        }

        json.put("userid",c.getUserId());           //送信者idを付加
        //Connections.broadCastMessage(json);         //接続しているユーザーに送信
        Connections.mapBroadCastMessage(json, c.getMapid());//マップにいる人全員に送信
    }
    
}
