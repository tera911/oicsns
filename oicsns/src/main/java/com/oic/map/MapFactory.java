/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.map;

import java.util.ArrayList;
import java.util.List;

/**
 * ゲーム内のすべてのMapを管理するクラス
 * @author Morimoto
 */
public class MapFactory {
    private List<OicMap> mapContainer = new ArrayList<>();
    private static MapFactory instance;
    
    public MapFactory getInstance(){
        if(instance == null){
            instance = new MapFactory();
        }
        return instance;
    }
    private MapFactory(){}
    
    /**
     * MapIDからマップデータを取得する
     * @param mapId
     * @return 
     */
    public OicMap getMap(Integer mapId){
        OicMap ret = null;
        for(OicMap map : mapContainer){
            if(map.getMapId() == mapId){
                ret = map;
            }
        }
        return ret;
    }
    
    /**
     * マップを登録
     * @param map 
     */
    public void addMap(OicMap map){
        mapContainer.add(map);
    }
}