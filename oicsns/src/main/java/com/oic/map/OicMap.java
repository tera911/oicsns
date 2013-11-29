/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.map;

/**
 *
 * @author B2020
 */
public class OicMap {
    private int mapId;
    private String mapName;
    private String path;
    private Position pos;
    private Object[] objects;

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
    
}
