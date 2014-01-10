/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.connection;

import com.oic.client.OicCharacter;
import com.oic.net.WebSocketListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 * サーバーに接続されているクライアントを管理する
 *
 * @author Morimoto
 */
public class Connections {

    private static final List<WebSocketListener> userConnections = Collections.synchronizedList(new ArrayList<WebSocketListener>());
    private static final Logger LOG = Logger.getLogger(Connections.class.getName());

    /**
     * クライアントを追加する
     *
     * @param websocketListener
     */
    public static void addConnection(WebSocketListener websocketListener) {
        synchronized (userConnections) {
            userConnections.add(websocketListener);
        }
    }

    public synchronized static void removeConnection(WebSocketListener webSocketListener) {
        synchronized (userConnections) {
            try {
                webSocketListener.getSession().close();
            } catch (IOException ex) {
                Logger.getLogger(Connections.class.getName()).log(Level.WARNING, null, ex);
            }
            userConnections.remove(webSocketListener);
        }
    }

    /**
     * ユーザーの正当性を確認する 定期的に実行される
     */
    public static synchronized void checkLive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    JSONObject json = new JSONObject();
                    json.put("method", "live");
                    json.put("status", 0);
                    broadCastMessage(json);
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();

    }

    /**
     * 接続されているユーザー全体にJSONを送信する
     *
     * @param json
     */
    public static void broadCastMessage(JSONObject json) {
        synchronized (userConnections) {
            Session session;
            try {
                for(int i = 0; i < userConnections.size(); i++){
                    WebSocketListener websocket = userConnections.get(i);
                    session = websocket.getSession();
                    if (session.isOpen()) {
                        session.getRemote().sendString(json.toJSONString());
                    } else {
                        session.close();
                        userConnections.remove(i);
                    }
                }
            } catch (IOException e) {
                LOG.log(Level.WARNING, "error {0}", e.toString());
            }
        }
    }
    
    public static void mapBroadCastMessage(JSONObject json, int mapid){
        synchronized(userConnections){
            try{
                for(int i = 0; i < userConnections.size(); i++){
                    
                    WebSocketListener webSocket = userConnections.get(i);
                    OicCharacter c = webSocket.getCharacter();
                    Session session = webSocket.getSession();
                    try{
                        if(c.getMap().getMapId() == mapid){
                            if (session.isOpen()) {
                                session.getRemote().sendString(json.toJSONString());
                            } else {
                                session.close();
                                userConnections.remove(webSocket);
                            }
                        }
                    }catch(NullPointerException e){
                        session.close();
                        userConnections.remove(webSocket);
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
