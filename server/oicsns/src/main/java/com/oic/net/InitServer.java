/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * サーバー起動時に実行されるクラス
 * @author Morimoto
 */
public class InitServer extends HttpServlet{
    @Override
    public void init() throws ServletException {
        super.init();
        System.err.println("Init.");
        try(InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream("/map/3A.xml"))){
          
        }catch(IOException e){}
    }
    
}
