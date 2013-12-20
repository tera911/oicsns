/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.oic.event.ActionEventImpl;
import java.util.ArrayList;

/**
 *
 * @author b2020
 */
public class EventProcesser {
    private ArrayList<String> events;
    public EventProcesser() {
       
    }
    
    /**
     * イベントを設定する
     */
    private void reset(){
        events = new ArrayList<>();
        
    }    
}
