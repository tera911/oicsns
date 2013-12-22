/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.client.OicGender;
import com.oic.net.WebSocketListener;
import com.oic.utils.DatabaseConnection;
import com.oic.utils.Tools;
import com.oic.utils.Validators;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class GetProfile implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        if(!validation(json)){
            responseJSON.put("status", "1");
            webSocket.sendJson(responseJSON);
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT user.userid, name, avatarid, grade, sex, birth, comment, privategrade, privatesex, privatebirth FROM user JOIN setting ON (user.userid = setting.userid) WHERE user.userid = ?;";
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(json.get("userid").toString()));
            rs = ps.executeQuery();
            
            if(rs.next()){
                responseJSON.put("userid", rs.getLong("userid"));
                responseJSON.put("username", rs.getString("name"));
                responseJSON.put("avatarid", rs.getInt("avatarid"));
                if(rs.getString("privategrade").equals("public")){
                    responseJSON.put("grade", rs.getInt("grade"));
                }
                if(rs.getString("privatesex").equals("public")){
                    responseJSON.put("gender", OicGender.getGender(rs.getString("sex")).toString());
                }
                if(rs.getString("privatebirth").equals("public")){
                    responseJSON.put("birthday", Tools.convertData(rs.getDate("birth")));
                }
                responseJSON.put("comment", rs.getString("comment"));
                responseJSON.put("status", 0);
            }else{
                responseJSON.put("status", 1);
            }
        }catch(SQLException se){
            responseJSON.put("status", 1);
            se.printStackTrace();
        }finally{
             try{    rs.close();    }catch(Exception e1){}
             try{    ps.close();    }catch(Exception e1){}
        }
        webSocket.sendJson(responseJSON);
    }
    
    private boolean validation(JSONObject json){
        Validators v = new Validators(json);
        v.add("userid", v.required());
        return v.validate();
    }
}
