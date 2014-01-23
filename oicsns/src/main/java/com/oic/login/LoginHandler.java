/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.login;

import com.oic.event.ActionEventImpl;
import com.oic.net.WebSocketListener;
import com.oic.utils.DatabaseConnection;
import com.oic.utils.Validators;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author kxhtj529
 */
public class LoginHandler implements ActionEventImpl{
private static final Logger LOG = Logger.getLogger(LoginHandler.class.getName());
    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "login");
        //validation
        if(!validation(json)){
            responseJSON.put("status", "1");
            webSocket.sendJson(responseJSON);
            return;
        }
        String accesstoken;
        String accesstokensecret;
        accesstoken = json.get("userid").toString(); //JSONからデータ取り出し
        accesstokensecret = json.get("password").toString(); //JSONからデータ取り出し
        
        long userId = -1;
        userId = login(accesstoken, accesstokensecret);//ログイン出来た場合はuseridが入る
        
        if(userId  > 0){//ログイン成功
            webSocket.userLogin(userId);
            responseJSON.put("status", "0");
            LOG.log(Level.INFO, "Login Success.");
        }else{  //ログイン失敗
            responseJSON.put("status", "1");
            LOG.log(Level.INFO, "Login Faild.");  
        }
        webSocket.sendJson(responseJSON);
    }
    
    /**
     * ログインに成功したらユーザIDが返ってくる<br>
     * 新規IDの場合は自動で登録
     * @param accessToken
     * @param accessTokenSecret
     * @return 
     */
    public long login(String accessToken, String accessTokenSecret){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        try{
            con = DatabaseConnection.getConnection();
            sql = "SELECT userid FROM user WHERE studentnumber = ? AND secretkey = ?";
            Long userId;
            ps = con.prepareStatement(sql);
            ps.setString(1, accessToken);
            ps.setString(2, accessTokenSecret);
            rs = ps.executeQuery();
            if(!rs.next()){
                return -1;               
            }
            userId = rs.getLong("userid");
            return userId;
        }catch(SQLException e){
            try{    rs.close();    }catch(Exception e1){}
            try{    ps.close();    }catch(Exception e1){}
            e.printStackTrace();
            return -1;
        }       
    }
    
    public boolean validation(JSONObject json){
        Validators v = new Validators(json);
        v.add("userid" , v.required());
        v.add("password", v.required());
        return v.validate();
    }
    
}
