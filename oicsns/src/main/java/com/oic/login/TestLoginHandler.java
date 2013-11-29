/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.login;

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
        long userId;
        String password;
        
        //validation
        if(json.get("userid") == null || json.get("password") == null){
            LOG.warning("invalid Message!");
            return;
        }
        
        userId = Long.parseLong(json.get("userid").toString());
        password = json.get("password").toString();
        
        int status = -1;
        try{
            status = login(userId, password);
        }catch(SQLException e){
            LOG.log(Level.WARNING, "SQL Exception : {0}",e);
        }
        if(status == 1){
            LOG.log(Level.INFO, "Login Success.");
        }else{
            LOG.log(Level.INFO, "Login Faild.");
        }
    }
    
    public int login(long userid, String password) throws SQLException{
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE userid = ? AND accesstokensecret = ?");
        ps.setLong(1, userid);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()){
            return -1;
        }
        return 1;
    }
    
}
