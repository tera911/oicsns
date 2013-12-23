/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.oic.event.ActionEventImpl;
import com.oic.event.ChatEvent;
import com.oic.event.CmdEvent;
import com.oic.event.GetProfile;
import com.oic.event.SetProfile;
import com.oic.event.GetUserInfo;
import com.oic.event.map.GetMapInfo;
import com.oic.event.map.GetMapList;
import com.oic.event.map.TransferMap;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
 public enum LoginEvent{
        /**
         * 全体チャット
         */
        ALLCHAT("allchat", new ChatEvent()), 
        /**
         *  チャットログを取得 
         */
        GETCHATLOG("getchatlog", null),
        /**
         * マップデータを取得
         */
        GETMAPID("getmaplist", new GetMapList()),
        /**
         * 
         */
        TRANSFERMAP("transfermap", new TransferMap()),
        GETMAPINFO("getmapinfo", new GetMapInfo()),
        POSUPDATE("posupdate", null),
        GETUSERINFO("getuserinfo", new GetUserInfo()),
        /**
         * プロフィール設定
         */
        SETPROFILE("setprofile", new SetProfile()),
        /**
         * プロフィール取得
         */
        GETPROFILE("getprofile", new GetProfile()),
        /**
         * デバッグ用コマンド
         */
        CMD("cmd", new CmdEvent());
        
    private String eventName;
    private ActionEventImpl eventInstance;

    private LoginEvent(String s, ActionEventImpl event) {
        this.eventName = s;
        this.eventInstance = event;
    }
        
    private String getEventName(){
        return eventName;
    }
    
    private ActionEventImpl getEventInstance(){
        return eventInstance;
    }
    
    public static void execEvent(String eventName, JSONObject json, WebSocketListener webSocket){
        for(LoginEvent e : LoginEvent.values()){
            if(e.getEventName().equals(eventName)){
                System.out.println("Exec : "+eventName);
                e.getEventInstance().ActionEvent(json, webSocket);
            }
        }
    }
        
    }
