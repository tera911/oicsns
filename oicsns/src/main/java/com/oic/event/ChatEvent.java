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
        OicCharacter c = webSocket.getCharacter();
        //validation
        if(json.get("text") == null || json.get("key") == null){
            LOG.warning("invalid Message!");
            return;
        }
        
        //認証
        LOG.log(Level.INFO, "ping key : {0}", json.get("key"));
        
        JSONObject sendJson = (JSONObject)json.clone(); //jsonコピー
        sendJson.put("userId",c.getUserId());           //送信者idを付加
        sendJson.remove("key");                         //認証用keyを削除
        Connections.BroadCastMessage(sendJson);         //接続しているユーザーに送信
        LOG.log(Level.INFO, "chat event : {0}", session.getRemoteAddress());
    }
    
}
