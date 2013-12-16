/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.login;

import com.oic.connection.Connections;
import com.oic.event.ActionEventImpl;
import com.oic.net.WebSocketListener;
import com.oic.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 * テストログイン用<br>
 * json{method:testLogin, userId:test, password:terara}
 * @author morimoto
 * 
 */
public class TestLoginHandler implements ActionEventImpl{
    private static final Logger LOG = Logger.getLogger(TestLoginHandler.class.getName());
    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        String accesstoken;
        String accesstokensecret;
        
        //validation
        if(json.get("userid") == null || json.get("password") == null){
            LOG.warning("invalid Message!");
            return;
        }
        
        accesstoken = json.get("userid").toString(); //JSONからデータ取り出し
        accesstokensecret = json.get("password").toString();
        
        long userId = -1;
        try{
            userId = login(accesstoken, accesstokensecret);
        }catch(SQLException e){
            LOG.log(Level.WARNING, "SQL Exception : {0}",e);
        }
        
        JSONObject loginStatus = new JSONObject();
        if(userId > 0){//ログイン成功
            webSocket.userLogin(userId);
            loginStatus.put("status", "1");
            LOG.log(Level.INFO, "Login Success.");
        }else{  //ログイン失敗
            loginStatus.put("status", "0");
            LOG.log(Level.INFO, "Login Faild.");  
        }
        webSocket.sendJson(loginStatus);
    }
    
    public long login(String accesstoken, String accesstokensecret) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnection.getConnection();
            Long userId;
            ps = con.prepareStatement("SELECT userid FROM user WHERE accesstoken = ? AND accesstokensecret = ?");
            ps.setString(1, accesstoken);
            ps.setString(2, accesstokensecret);
            rs = ps.executeQuery();
            if(!rs.next()){
                return -1;
            }
            userId = rs.getLong("userid");
            return userId;
        }catch(SQLException e){
            try{    rs.close();    }catch(Exception e1){}
            try{    ps.close();    }catch(Exception e1){}
            return -1;
        }       
    }
    
}
