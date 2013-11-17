/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.oic.connection.Connections;
import java.lang.String;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 *
 * @author morimoto
 */
@WebSocket
public class WebSocketListener {
    private static final Logger LOG = Logger.getLogger(WebSocketListener.class.getName());
    private Character c = null;//キャラクターインスタンス,最初は未登録の可能性もあるからNULL
    @OnWebSocketConnect
    public void onConnect(Session session){
        LOG.info("connection :" + session.getRemoteAddress());
        Connections.addConnection(this);
    }
    @OnWebSocketMessage
    public void onText(String message){
        
        Method method = null;
        try{
        method = JSON.decode(message, Method.class);
        }catch(JSONException je){
            je.printStackTrace();
        }
        LOG.info("method" + method.getMethod());
    }
    
    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        LOG.info("close statusCode = " + statusCode + " reason = "+ reason);
    }
    
    public Character getCharacter(){
        return c;
    }
    public class Method{
        String method;
        public String getMethod(){
            return method;
        }
    }
}
