/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.connection;

import com.oic.net.WebSocketListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author B2020
 */
public class Connections {
    private static List<WebSocketListener> userConnections = new ArrayList<WebSocketListener>();
    
    public static void addConnection(WebSocketListener conn){
        userConnections.add(conn);
    }
    
    public static synchronized void checkLive(){
        //ping!
    }
  
}
