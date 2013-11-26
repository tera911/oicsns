/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.oic.client.OicCharacter;
import com.oic.connection.Connections;
import com.oic.event.ChatEvent;
import com.oic.login.LoginHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author morimoto
 */
@WebSocket
public class WebSocketListener {
    private static final Logger LOG = Logger.getLogger(WebSocketListener.class.getName());
    private Session session;
    private OicCharacter c = null;//キャラクターインスタンス,最初は未登録の可能性もあるからNULL
   
    
    
    @OnWebSocketConnect
    public void onConnect(Session session){
        LOG.log(Level.INFO, "connection :{0}", session.getRemoteAddress());
        this.session = session;
        Connections.addConnection(this);
        c = OicCharacter.loadCharFromDB(111);
    }
    @OnWebSocketMessage
    public void onText(String message){
        String method = "";
        JSONObject json = new JSONObject();
        try{
        json = (JSONObject)(new JSONParser().parse(message));
        LOG.log(Level.INFO, "method : {0}", json.get("method"));
        method = json.get("method").toString();
        }catch(ParseException e){   
        }
        //メッセージ振り分け
        if(method.equals("login")){
            new LoginHandler().ActionEvent(json, this);
        }else if(method.equals("chat")){
            new ChatEvent().ActionEvent(json, this);
        }else{
            
        }
    }
    
    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        LOG.log(Level.INFO, "close statusCode = {0} reason = {1}", new Object[]{statusCode, reason});
    }
    
    public OicCharacter getCharacter(){
        return c;
    }
    public void setCharacter(OicCharacter character){
        this.c = character;
    }
    
    public Session getSession(){
        return this.session;
    }
}
