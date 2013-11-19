/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import java.io.IOException;
import java.io.InputStream;
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
        try(InputStream is = getClass().getResourceAsStream("/map/3A.xml")){
           int Byte;
           while((Byte = is.read()) != -1){
               System.out.println((char)Byte);
           }
        }catch(IOException e){}
    }
    
}
