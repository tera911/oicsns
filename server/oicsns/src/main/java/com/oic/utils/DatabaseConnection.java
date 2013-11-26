/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morimoto
 * このクラスは一意なDBコネクションスレッドを返す
 */
public class DatabaseConnection {
    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());
    private static ThreadLocal<Connection> con;
    
    /**
     * SQLConnectionを取得する
     * @return SQL connection 
     */
    public static Connection getConnection(){
        return con.get();
    }
    
    /**
     * すべての接続を閉じる
     * @throws SQLException 
     */
    public static void closeAll() throws SQLException{
        for(Connection con : ThreadLocalConnection.allConnections){
            con.close();
        }
    }
    
    private static class ThreadLocalConnection extends ThreadLocal<Connection>{
    public static Collection<Connection> allConnections = new LinkedList<>();

        @Override
        protected Connection initialValue() {
            String driver = "";
            String url = "";
            String user = "";
            String pass = "";
            try{
                Class.forName(driver);
                
            }catch(ClassNotFoundException e){
                LOG.log(Level.WARNING, "Database Driver is not found");
            }
            try{
                Connection con = DriverManager.getConnection(url, user, pass);
                allConnections.add(con);
                return con;
            }catch(SQLException e){
                LOG.log(Level.WARNING, "SQL Connection error");
                return null;
            }
        }
        
        
    }
}
