/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.net;

import com.oic.client.OicCharacter;
import com.oic.connection.Connections;
import com.oic.event.ChatEvent;
import com.oic.event.ChatlogEvent;
import com.oic.event.CheckDuplication;
import com.oic.event.CmdEvent;
import com.oic.login.TestLoginHandler;
import java.io.IOException;
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
    
    public enum Status{
        NOLOGIN, LOGIN, REGISTER, NOTHING
    }
    public enum Register{
        NOREGISTER,REGISTERED
    }
    /**
     * loing =<br>
     * -1 // not login<br>
     * 0 //logout (keep cache) 1 //login (no register) 2 //login
     */
    private Status login = Status.NOLOGIN;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        login = Status.NOTHING;
        this.session = session;
        Connections.addConnection(this);//コネクションリストに追加

        LOG.log(Level.INFO, "connection :{0}", session.getRemoteAddress());//Logging 
    }

    @OnWebSocketMessage
    public void onText(String message) {
        String method = "";
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) (new JSONParser().parse(message));
            LOG.log(Level.INFO, "method : {0}", json.get("method"));
            method = json.get("method").toString();
        } catch (ParseException e) {
            LOG.log(Level.WARNING, "{0}", e);
        }

        /* メッセージ振り分け  */
        selectMessage(method, json, this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOG.log(Level.INFO, "close statusCode = {0} reason = {1}", new Object[]{statusCode, reason});
    }

    /**
     * クライアントから送信されるメッセージを振り分け
     *
     * @param method
     * @param json
     * @param webSocketListener
     */
    public void selectMessage(String method, JSONObject json, WebSocketListener webSocketListener) {
        if (method == null) { //ぬるぽ回避のため
            LOG.log(Level.WARNING, "Message Method is NULL : {0}", json.toString());
            return;
        } else if (login == Status.NOTHING && method.equals("login")) {
            new TestLoginHandler().ActionEvent(json, this);
            //new LoginHandler().ActionEvent(json, this);//ログイン処理
        } else if (login == Status.NOTHING) {
            //ユーザー登録処理
            return;
        }
        switch (method) {//ログイン後の振り分け
            case "allchat":
                new ChatEvent().ActionEvent(json, this);
                break;
            case "getchatlog":
                new ChatlogEvent().ActionEvent(json, webSocketListener);
                break;

            /* マップ */
            case "getmapid":
                break;
            case "transfermap":
                break;
            case "getmapall":
                break;
            case "getmapfloor":
                break;
            case "getmapinfo":
                break;
                
            /* マップでの情報 */
            case "posupdate":
                break;
            case "getuserinfo":
                break;

            /* 登録系*/
            case "duplication":
                new CheckDuplication().ActionEvent(json, webSocketListener);
                break;
            case "getprofile":
                
                break;
            case "setprofile":
                break;

            /* utils */
            case "cmd":
                new CmdEvent().ActionEvent(json, webSocketListener);
                break;
        }
    }

    public void sendJson(JSONObject json) {
        try {
            session.getRemote().sendString(json.toJSONString());
        } catch (IOException e) {
            LOG.log(Level.WARNING, "send Json error{0}", e);
        }
    }

    public OicCharacter getCharacter() {
        return c;
    }

    public void setCharacter(OicCharacter character) {
        this.c = character;
    }

    public Session getSession() {
        return this.session;
    }

    public void userLogin(long userId) {
        login = Status.LOGIN;
        c = OicCharacter.loadCharFromDB(userId);
        if (c == null) {//未登録
            login = Status.REGISTER;
        }
        c.setMapid(31); //ログイン時のマップは3A教室固定
    }

    public void userLogout() {
        login = Status.NOTHING;
        Connections.removeConnection(this);
    }
}
