/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author B2020
 */
public class Connection {
    private List<Session> sessions = new ArrayList<Session>();
    private HashMap<Integer, Session> connects = new HashMap<Integer, Session>();
    private HashMap<Integer, String> authKey = new HashMap<Integer, String>();
    
    public void addSession(Session session){
        this.sessions.add(session);
        for(Session s : this.sessions){
            System.out.println(s.getRemoteAddress());
        }
    }
}
