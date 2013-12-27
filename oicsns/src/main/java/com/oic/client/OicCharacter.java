/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.client;

import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.map.Position;
import com.oic.utils.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author morimoto
 */
public class OicCharacter {
    private Position pos;
    private long userId;
    private String studentNumber;
    private long avatarId;
    private String name;
    private int grade;
    private OicGender gender;
    private Date birthday;
    private String comment;
    private int mapid;
    private OicMap map;
    
    private static final int CHAR_WIDTH = 100;
    private static final int CHAR_HEIGHT = 230;
    
    public static OicCharacter loadCharFromDB(long userId){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            OicCharacter ret = new OicCharacter();
            String sql = "SELECT * FROM user WHERE userid = ? ";
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if(!rs.next()){
                throw new RuntimeException("Loading the Information Filed.");
            }
            ret.userId = rs.getLong("userid");
            ret.studentNumber = rs.getString("studentnumber");
            ret.avatarId = rs.getLong("avatarid");
            ret.name = rs.getString("name");
            ret.grade = rs.getInt("grade");
            ret.gender = OicGender.getGender(rs.getString("sex"));
            ret.birthday = rs.getDate("birth");
            ret.comment = rs.getString("comment");
            
            Position pos = new Position(0, 0, CHAR_WIDTH, CHAR_HEIGHT);
            ret.setPos(pos);
            
            rs.close();
            ps.close();
            return ret;
        }catch(Exception e){
            try{    rs.close();    }catch(Exception e1){}
            try{    ps.close();    }catch(Exception e1){}
 
            e.printStackTrace();
            return null;
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(long avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public OicGender getGender() {
        return gender;
    }

    public void setGender(OicGender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
   
    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public int getMapid() {
        return mapid;
    }
    
    public OicMap getMap(){
        return map;
    }

    //TODO 整合性チェック
    public void changeMap(int mapid) {
        map.removeCharacter(userId);
        enterMap(mapid);
    }
    public void loginMap(int mapid){
        enterMap(mapid);
    }
    private void enterMap(int mapid){
        MapFactory factory = MapFactory.getInstance();
        map = factory.getMap(mapid);
        this.mapid = map.getMapId();
        map.setCharacter(this);
   }
}