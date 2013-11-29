/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import com.oic.Config;
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
    private static ThreadLocal<Connection> con = new ThreadLocalConnection();
    
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
           try{
                Class.forName("com.mysql.jdbc.Driver").newInstance(); //jdbc.mysqlドライバーのインスタンスを生成
            }catch(ClassNotFoundException ce){
                LOG.warning("SQL Connector not found.");//見つからなかった場合
            }catch(Exception e){
                LOG.log(Level.WARNING, "SQL initialize faild.\n{0}",e); //なんらかのエラー
            }
            String url = Config.DB_HOST;//接続先
            String user = Config.DB_USER;//接続ユーザー
            String pass = Config.DB_PASSWORD;//接続パスワード
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
