/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.net;

import com.oic.client.OicCharacter;
import com.oic.connection.Connections;
import com.oic.event.CheckDuplication;
import com.oic.event.PosUpdate;
import com.oic.event.RegisterProfile;
import com.oic.login.LoginHandler;
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
    
    /**
     * NOLOGIN  何もしていない
     * LOGIN    ログイン済み
     * REGISTER 登録すべきユーザ
     */
    public enum LoginStatus{
        /**
         * 未ログイン状態
         */
        NOLOGIN, 
        /**
         * ログインしている
         */
        LOGIN, 
        /**
         * 登録する
         */
        REGISTER,
    }
    private LoginStatus login = LoginStatus.NOLOGIN;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        login = LoginStatus.NOLOGIN;
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
            //LOG.log(Level.INFO, "status : {0}", login);
            method = json.get("method").toString();
        } catch (ParseException e) {
            LOG.log(Level.WARNING, "{0}", e);
        }

        /* メッセージ振り分け  */
        selectMessage(method, json, this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        userLogout();
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
        }else if(method.equals("cmd")){
         //   new CmdEvent().ActionEvent(json, webSocketListener);
        }else if ((login == LoginStatus.NOLOGIN || login == LoginStatus.REGISTER) && method.equals("duplication")) {
            /* 重複確認　通ったらStatusがREGISTERになる */
            new CheckDuplication().ActionEvent(json, webSocketListener);
        }else if (login == LoginStatus.NOLOGIN && method.equals("login")) {
            /* ログイン */
            new LoginHandler().ActionEvent(json, webSocketListener);
            //new LoginHandler().ActionEvent(json, this);//ログイン処理
        }else if (login == LoginStatus.REGISTER && method.equals("setprofile")) {
            /* 重複確認したら新規登録 */
            new RegisterProfile().ActionEvent(json, webSocketListener);
        }else if(login == LoginStatus.LOGIN){
           LoginEvent.execEvent(method, json, webSocketListener);
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

    /**
     * ユーザがログイン
     * @param userId 
     */
    public void userLogin(long userId) {
        login = LoginStatus.LOGIN;
        c = OicCharacter.loadCharFromDB(userId);
        if (c == null) {//未登録
            login = LoginStatus.REGISTER;
        }
        c.loginMap(31); //ログイン時のマップは3A教室固定
        
        //全体にログインを通知
        new PosUpdate().ActionEvent(null, this);
    }

    /**
     * ユーザの状態をログインしてない状態にする
     */
    public void userNoLogin(){
        login = LoginStatus.NOLOGIN;
    }
    /**
     * ユーザをログアウトする
     */
    public void userLogout() {
        login = LoginStatus.NOLOGIN;
        try{
            c.getMap().removeCharacter(c.getUserId());
        }catch(NullPointerException e){
        }
        Connections.removeConnection(this);
    }
    
    /**
     * ユーザ登録開始
     */
    public void userRegister(){
        login = LoginStatus.REGISTER;
    }
    
    /**
     * ログインしているか確認
     * @return 
     */
    public boolean isLogin(){
        return (login == LoginStatus.LOGIN);
    }
    
    /**
     * 登録状態か確認
     * @return 
     */
    public boolean isRegister(){
        return (login == LoginStatus.REGISTER);
    }
}
