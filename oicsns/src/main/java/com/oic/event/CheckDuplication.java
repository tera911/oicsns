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
 * 重複確認
 * @author morimoto
 */
public class CheckDuplication implements ActionEventImpl{

    @Override
    public void ActionEvent(JSONObject json, WebSocketListener webSocket) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "duplication");
        Validators v = new Validators(json);
        v.add("username", v.required(), v.maxLength(24));
        if(!v.validate()){
            responseJSON.put("result", 2);
        }else{
            //重複確認
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
                String sql = "SELECT name FROM user WHERE name = ?";
                con = DatabaseConnection.getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, json.get("username").toString());
                rs = ps.executeQuery();
                if(rs.next()){//重複
                    responseJSON.put("result", 1);
                }else{//未登録
                    webSocket.userRegister();//登録開始
                    responseJSON.put("result", 0);
                }
            }catch(Exception e){
                responseJSON.put("result", 2);
                e.printStackTrace();
            }finally{
                try{    rs.close();    }catch(Exception e1){}
                try{    ps.close();    }catch(Exception e1){}
            }
        }
        webSocket.sendJson(responseJSON);
    }
    
    
}
