/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

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
        
    }
    
}
