/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import com.oic.connection.Connections;
import com.oic.map.MapFactory;
import com.oic.map.OicMap;
import com.oic.xml.XmlParser;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.ArrayList;

/**
 * サーバー起動時に実行されるクラス
 * @author Morimoto
 * @author b2280@otani
 */
public class InitServer extends HttpServlet{
    @Override
    public void init() throws ServletException {
        super.init();
        mapInitialize();
        Connections.checkLive();
    }
    
    private static void mapInitialize(){
        List<OicMap> oicmap = new ArrayList<>();//格納用変数
        MapFactory mapFactory = MapFactory.getInstance();
        XmlParser parser = new XmlParser();
        oicmap = parser.loadOicMap();//mapデータのリストを読み込む
        mapFactory.addMap(oicmap);   //mapデータを格納
        
    }
}
