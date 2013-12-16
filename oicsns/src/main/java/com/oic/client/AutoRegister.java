/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.client;

import com.oic.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author b2020
 */
public class AutoRegister {
    private String accessToken;
    private String accessTokenSecret;
    private AutoRegister() {
    }
    
    public AutoRegister(String accessToken, String accessTokenSecret){
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }
    
    public void register() throws SQLException{
        Connection con;
        PreparedStatement ps;
        String sql = "INSERT INTO user (accesstoken, accesstokensecret) VALUES (?, ?)";
        con = DatabaseConnection.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, accessToken);
        ps.setString(2, accessTokenSecret);
        if(!ps.execute()){
            throw new SQLException();
        }
    }
}
