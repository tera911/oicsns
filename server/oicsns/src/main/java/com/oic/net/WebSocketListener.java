/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import java.util.logging.Logger;
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
    
    private Session session;
    
    @OnWebSocketConnect
    public void onConnect(Session session){
        this.session = session;
        System.out.println("connect is " + session.getRemoteAddress());
    }
    @OnWebSocketMessage
    public void onText(String message){
        System.out.println(session.getRemoteAddress() + ": " + message);
    }
    
    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        
    }
    
    public Session getSession(){
        return this.session;
    }
}
