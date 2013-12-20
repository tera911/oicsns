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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class RegisterProfile implements ActionEventImpl{
    private static final Logger LOG = Logger.getLogger(SetProfile.class.getName());

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "setprofile");
        if(!validation(json,webSocket)){
            System.out.println("invalid json.");
            return;
        }
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try{
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);
            String sql = "INSERT INTO user SET accesstoken = ?, accesstokensecret = ?, studentnumber = ?, name = ?, avatarid = ?, grade = ?, sex = ?, birth = ?, comment = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, json.get("accesstoken").toString());
            ps.setString(2, json.get("accesstokensecret").toString());
            ps.setString(3, json.get("studentid").toString());
            ps.setString(4, json.get("username").toString());
            ps.setInt(5, Integer.parseInt(json.get("avatarid").toString()));
            ps.setInt(6, Integer.parseInt(json.get("grade").toString()));
            ps.setInt(7, Integer.parseInt(json.get("gender").toString()));
            ps.setDate(8, toDate(json.get("birthday").toString()));
            ps.setString(9, json.get("comment").toString());
            ps.executeUpdate();
            ps.close();
            
            sql = "SELECT last_insert_id() AS last";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                throw new SQLException();
            }
            long userid = rs.getLong("last");
            rs.close();
            ps.close();
            
            sql = "INSERT INTO setting SET userid = ?, privategrade = ?, privatesex = ?, privatebirth =?";
            ps = con.prepareStatement(sql);
            ps.setLong(1, userid);
            ps.setInt(2, Integer.parseInt(json.get("vgrade").toString()));
            ps.setInt(3, Integer.parseInt(json.get("vgender").toString()));
            ps.setInt(4, Integer.parseInt(json.get("vbirthday").toString()));
            ps.executeUpdate();
            ps.close();
            con.commit();
            
            responseJSON.put("status",0);
        }catch(Exception e){
            try{
                con.rollback();
            }catch(SQLException sq){
                LOG.warning("[setProfile]Error Rolling back.");
            }
            e.printStackTrace();
            responseJSON.put("status",1);
        }finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SetProfile.class.getName()).log(Level.WARNING,"Error going back to AutoCommit mode", ex);
            }
        }
        
        webSocket.sendJson(responseJSON);
    }
    
    private boolean validation(JSONObject json, WebSocketListener webSocket){
        Validators v = new Validators(json);
        v.add("accesstoken", v.required());
        v.add("accesstokensecret", v.required());
        v.add("studentid", v.studentId());
        v.add("username", v.required());
        v.add("avatarid", v.integerType());
        v.add("grade" , v.integerType());
        v.add("gender", v.integerType());
        v.add("birthday", v.birthday());
        v.add("comment", v.required());
        v.add("vgrade", v.required());
        v.add("vgender", v.required());
        v.add("vbirthday", v.required());
       if(v.validate()){
           return true;
       }else{
           webSocket.sendJson(v.getError());
           return false;
       }
    }
    
    private Date toDate(String date) throws ParseException{
        Date data;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = format.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        data = new Date(cal.getTimeInMillis());
        return data;
    }
    
}
