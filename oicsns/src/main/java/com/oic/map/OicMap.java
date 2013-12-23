/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.map;

import com.oic.client.OicCharacter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 *
 * @author B2020
 */
public class OicMap {
    private Collection<OicCharacter> characters = Collections.synchronizedCollection(new LinkedHashSet<OicCharacter>());
    private int mapId;
    private String mapName;
    private String path;
    private Position pos;
    private Object[] objects;
    private long[][] charspace = {{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};

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

    public boolean setCharacter(OicCharacter character){
        this.characters.add(character);
        
        for(int i = 0; i < charspace.length; i++){
            for(int j =  charspace[i].length /2; j > 0; j--){
                for(int k = 0; k < 2; k++){
                    if(charspace[i][j+k] == 0){
                        charspace[i][j+k] = character.getUserId();
                        Position pos = character.getPos();
                        pos.setX((j + k)* pos.getWidth());
                        pos.setY(600 - (i * pos.getHeight()));
                        character.setPos(pos);
                        return true;
                    }
                }
            }
        }
        return false;
        
    }
    public void removeCharacter(long userId){
        for(OicCharacter charcter : this.characters){
            if(charcter.getUserId() == userId){
                for(long[] i : charspace){
                    for(long j : i){
                        if(j == userId){
                            j = 0;
                        }
                    }
                }
                this.characters.remove(charcter);
            }
        }
    }
    
    public int getUserCont(){
        return characters.size();
    }
    
    public OicCharacter getUser(long userId){
        for(OicCharacter c : characters){
            if(c.getUserId() == userId){
                return c;
            }
        }
        return null;
    }
    public Position getUserPosition(long userId){
        OicCharacter c = getUser(userId);
        if(c != null){
            return c.getPos();
        }else{
            return null;
        }
    }
}
