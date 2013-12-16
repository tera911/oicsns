/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.net.WebSocketListener;
import com.oic.utils.DatabaseConnection;
import com.oic.utils.Validators;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class SetProfile implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "setprofile");
        if(!validation(json,webSocket)){
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        try{
            String sql = "UPDATE user SET studentnumber = ?, name = ?, avatarid = ?, grade = ?, sex = ?, birth = ?, comment = ? "
                    + "WHERE userid = ?";
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, json.get("studentid").toString());
            ps.setString(2, json.get("username").toString());
            ps.setInt(3, Integer.parseInt(json.get("avatarid").toString()));
            ps.setInt(4, Integer.parseInt(json.get("grade").toString()));
            ps.setInt(5, Integer.parseInt(json.get("gender").toString()));
            //TODO 項目追加
            responseJSON.put("status",0);
        }catch(Exception e){
            e.printStackTrace();
            responseJSON.put("status",1);
        }
        
        webSocket.sendJson(responseJSON);
    }
    
    private boolean validation(JSONObject json, WebSocketListener webSocket){
        Validators v = new Validators(json);
       v.add("userid", v.integerType());
       v.add("studentid", v.studentId());
       v.add("username", v.required());
       v.add("avatarid", v.integerType());
       v.add("grade" , v.integerType());
       v.add("gender", v.integerType());
       v.add("birthday", v.birthday());
       v.add("comment", v.required());
       if(v.validate()){
           return true;
       }else{
           webSocket.sendJson(v.getError());
           return false;
       }
    }
    
}
