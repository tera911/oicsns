/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.event;

import com.oic.net.WebSocketListener;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 * イベントのインターフェース
 * @author Morimoto
 */
public interface ActionEventImpl {
    public void ActionEvent(JSONObject json, WebSocketListener webSocket);
}
