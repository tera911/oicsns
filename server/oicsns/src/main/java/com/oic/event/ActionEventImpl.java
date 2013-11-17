/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import net.arnx.jsonic.JSON;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author B2020
 */
public interface ActionEventImpl {
    public void ActionEvent(JSON json, Session session);
}
