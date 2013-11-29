/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * マップ情報のxmlをロードしてパース
 * written by b2280@otani
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

class MapParser{
public MapParser() throws IOException, ParserConfigurationException{
      try {
            // DOMパーサ用ファクトリの生成
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 妥当性検証を行う
            factory.setValidating(true);
            // 名前空間を認識する
            factory.setNamespaceAware(true);
            //コメントを無視する
            factory.setIgnoringComments(true);
            //無視できる空白を無視する
            factory.setIgnoringElementContentWhitespace(true);
            // DOM Documentインスタンス用ファクトリの生成
            DocumentBuilder parser = factory.newDocumentBuilder();
            // xmlファイルのパース
            parser.parse(getClass().getResourceAsStream("/map/3A.xml"));//getResourceさんがパスを探してくれる
            // DOCUMENTノードの取得
            Document doc = parser.newDocument();
            
            System.out.println(doc.getNodeName());   // DOCUMENTノードのノード名
            System.out.println(doc.getNodeType());   // DOCUMENTノードのノードタイプ
            //全ての子ノードを辿る
            traceNodes(doc);
            
       }catch (SAXException e) { 
            System.out.println("XMLデータが不正です。");
            e.printStackTrace();
       }
}

static private void traceNodes(Node node){
    Node child = node.getFirstChild();//子ノード取得
    
    while (child!=null){
        printTextNode(child);           //テキストノードならテキストを表示
        
        traceNodes(child);              //さらに子ノードを辿る（再帰）
        child = child.getNextSibling(); //兄弟ノード(属性名が同じノード)を取得
    }
}

static private void printTextNode(Node node){
    //テキストノードであるかチェックしてから
    if(node.getNodeType() == Node.TEXT_NODE){
        System.out.println("テキストノード値 = " + node.getNodeValue()); // ノード値
    }
}
}