/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.xml;

import com.oic.Config;
import com.oic.map.OicMap;
import com.oic.map.Position;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XMLを読み込んでゲーム内オブジェクトに変換する
 * @author Morimoto
 */
public class XmlParser {
    private static final Logger LOG = Logger.getLogger(XmlParser.class.getName());
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    
    public XmlParser() throws ParserConfigurationException{
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }
    
    /**
     * OicのMapデータをロードする
     * @return List<OicMap>
     */
   public List<OicMap> loadOicMap(){
       List<OicMap> oicmaps = new ArrayList<>();
       try{
            File mapdir = new File(getClass().getResource(Config.MAPFILE_PATH).toURI());
            for(File f : mapdir.listFiles()){
                oicmaps.add(parseOicMap(f));
            }
       }catch(Exception e){
          LOG.log(Level.WARNING, "Map Load Faild.{0}",e);
       }
       return oicmaps;
   }
    /**
     * ファイルインスタンスからoicMapインスタンスを生成する
     * @param file
     * @return
     * @throws SAXException
     * @throws IOException 
     */
    public OicMap parseOicMap(File file) throws SAXException,IOException{
        OicMap oicMap = new OicMap(0,null,null,null,null);
        Document doc = builder.parse(file);
        
        Element root = doc.getDocumentElement(); //ドキュメントのroot取得
        NodeList children = root.getChildNodes(); 
                
        for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            if(child instanceof Element == false){//取得する際に必要なデータは排除
                continue;
            }
            switch(child.getNodeName()){
                case "id":
                  //  System.out.println(child.getNodeName() + " : " + child.getTextContent());
                    oicMap.setMapId(Integer.parseInt(child.getTextContent()));
                break;
                case "name":
                    oicMap.setMapName(child.getTextContent());
                break;
                case "path":
                    oicMap.setPath(child.getTextContent());
                break;
                case "position":
                    Position pos = new Position(-1, -1, 0, 0);//positionインスタンス
                    NodeList positionNodeList = child.getChildNodes(); //子ノードリスト取得
                    for(int j = 0; j < positionNodeList.getLength(); j++){
                        Node position = positionNodeList.item(j);
                        if(position instanceof Element == false){//Elementではないものは排除
                            continue;
                        }
                        switch(position.getNodeName()){
                            case "x" :
                                pos.setX(Integer.parseInt(position.getTextContent()));
                            break;
                            case "y" :
                                pos.setX(Integer.parseInt(position.getTextContent()));
                            break;
                        }
                    }
                    oicMap.setPos(pos);
                break;
                case "object":
                break;
            }
        }
        return oicMap;
    }
    
}
