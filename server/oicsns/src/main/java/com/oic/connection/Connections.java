/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.connection;

import com.oic.net.WebSocketListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 *
 * @author B2020
 */
public class Connections {
    private static List<WebSocketListener> userConnections = new ArrayList<>();
    private static final Logger LOG = Logger.getLogger(Connections.class.getName());
    
    public static void addConnection(WebSocketListener conn){
        userConnections.add(conn);
    }
    
    public static synchronized void checkLive(){
        //ping!
    }
    
    public void BroadCastMessage(JSONObject json){
        Session session;
        try{
        for(WebSocketListener websocket : userConnections){
            session = websocket.getSession();
            session.getRemote().sendString(json.toJSONString());
        }
        }catch(IOException e){
           LOG.log(Level.WARNING ,"error {0}",e.toString());
        }
    }
  
}
