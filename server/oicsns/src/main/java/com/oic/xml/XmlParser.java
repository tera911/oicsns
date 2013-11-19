/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.xml;

import com.oic.map.OicMap;
import java.io.File;
import java.io.IOException;
import java.net.URI;
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
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    
    public XmlParser() throws ParserConfigurationException{
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }
    
    public void loadXml(URI uri) throws SAXException,IOException{
      File f = new File(uri);
        Document doc = builder.parse(f);
    }
    
    public OicMap parseOicMap(URI uri) throws SAXException,IOException{
        OicMap oicMap = new OicMap();
        File f = new File(uri);
        Document doc = builder.parse(f);
        
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();
        
        for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            if(child instanceof Element){
                Element childElement = (Element) child;
                System.out.println(childElement.getTagName());
            }
        }
        
        return oicMap;
    }
    
}
