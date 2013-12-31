/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.map;

import com.oic.client.OicCharacter;
import com.oic.connection.Connections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author B2020
 */
public class OicMap {
    
    private final Collection<OicCharacter> characters = Collections.synchronizedCollection(new LinkedHashSet<OicCharacter>());
    private int mapId;
    private String mapName;
    private String path;
    private Position pos;
    private Object[] objects;
    private long[][] charspace = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};

    private OicMap() {
    }

    public OicMap(int mapId, String mapName, String path, Position pos, Object[] objects) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.path = path;
        this.pos = pos;
        this.objects = objects;
    }

    /**
     * @return the mapId
     */
    public int getMapId() {
        return mapId;
    }

    /**
     * @param mapId the mapId to set
     */
    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    /**
     * @return the mapName
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @param mapName the mapName to set
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * @return the objects
     */
    public Object[] getObjects() {
        return objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public boolean setCharacter(OicCharacter character) {
        synchronized(characters){
            this.characters.add(character);
        }
        for (int y = 0; y < charspace.length; y++) {
            for (int x = charspace[y].length / 2; x > 0; x--) {
                for (int k = 0; k < 2; k++) {
                    if (charspace[y][x + k] == 0) {
                        charspace[y][x + k] = character.getUserId();
                        Position pos = character.getPos();
                        pos.setX((x + k) * pos.getWidth());
                        pos.setY(350 - (y * pos.getHeight()));
                        character.setPos(pos);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void removeCharacter(long userId) {
        synchronized(characters){
            Iterator<OicCharacter> it = characters.iterator();
            while(it.hasNext()){
            //for (OicCharacter charcter : characters) {
                OicCharacter character = it.next();
                if (character.getUserId() == userId) {
                    for(int y = 0; y < charspace.length; y++){
                        for(int x = 0; x < charspace[y].length; x++){
                            if(charspace[y][x] == userId){
                                charspace[y][x] = 0;
                            }
                        }
                    }
                    it.remove();
                }
            }
        }
    }
    
    public List<Long> getUserIdList(){
        List<Long> useridList = new ArrayList<>();
        synchronized(characters){
            for(OicCharacter c : characters){
                useridList.add(c.getUserId());
            }
        }
        return useridList;
    }

    public int getUserCont() {
        return characters.size();
    }

    public OicCharacter getUser(long userId) {
        synchronized(characters){
            Iterator<OicCharacter> it = characters.iterator();
            while(it.hasNext()){
                OicCharacter c = it.next();
                if(c.getUserId() == userId){
                    return c;
                }
            }
        }
        return null;
    }

    public Position getUserPosition(long userId) {
        OicCharacter c = getUser(userId);
        if (c != null) {
            return c.getPos();
        } else {
            return null;
        }
    }
    
    public void BroadCastMap(JSONObject json){
        Connections.mapBroadCastMessage(json, mapId);
    }
}
