/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author B2020
 */
public class WebSocketServletImpl extends WebSocketServlet{

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(WebSocketListener.class);
    }
    
}
