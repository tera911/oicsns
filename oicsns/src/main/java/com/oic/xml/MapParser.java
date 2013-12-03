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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class MapParser{
public MapParser() throws IOException, ParserConfigurationException{
      try {
            // DOMパーサ用ファクトリの生成
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 妥当性検証を行う　けどDTDに適合しているか見るっぽいので今回は無意味？
            //factory.setValidating(true);
            // 名前空間を認識する
            factory.setNamespaceAware(true);
            //コメントを無視する
            factory.setIgnoringComments(true);
            //無視できる空白を無視する
            factory.setIgnoringElementContentWhitespace(true);
            // DOM Documentインスタンス用ファクトリの生成
            DocumentBuilder parser = factory.newDocumentBuilder();
            // xmlファイルのパース
            Document doc = parser.parse(getClass().getResourceAsStream("/map/3A.xml"));//getResourceさんがパスを探してくれる
            // DOCUMENTノードの取得
            //Document doc = parser.newDocument();
            System.out.println("document NODE:"+doc.getNodeName());   // DOCUMENTノードのノード名
            System.out.println(doc.getNodeType());   // DOCUMENTノードのノードタイプ
            Element element = doc.getDocumentElement();  // 最初の要素ノード<map>の取得
            System.out.println(element.getNodeName());   // ノード名
            System.out.println(element.getNodeType());   // ノードタイプ    
            if (element.hasChildNodes()) {
                NodeList list = element.getChildNodes();    // 子要素を含んだノードを列挙
                //全ての子ノードを辿る            
                traceNodes(list);           
            }else{
                System.out.println("子ノードがないお");
            }

            
       }catch (SAXException e) { 
            System.out.println("XMLデータが不正です。");
            e.printStackTrace();
       }
}

static private void traceNodes(NodeList list){
    Node node;
    // <id>からスタート
    for (int iNode=0; (node = list.item(iNode) )!=null; iNode++){//子ノードが無くなるまで
        //System.out.println("ノード名"+node.getNodeName());   // ノード名
        //NodeList childNodes = node.getChildNodes();//子ノードリスト取得
        //Node childNode;
        printElementValue(node);         //タグ要素内のテキストを表示
//        for (int iItem=0; (childNode = childNodes.item(iItem))!=null; iItem++){
//            System.out.println("ノード"+childNode.getNodeValue());  // ノード
//            printElementValue(childNode);         //タグ要素内のテキストを表示
//
//        }
        
        //traceNodes(child);              //さらに子ノードを辿る（再帰）
        //child = child.getNextSibling(); //兄弟ノード(属性名が同じノード)を取得
      
    }
    
//    for(int index=0; index<list.getLength(); index++){
//        NamedNodeMap attrs = list.getAttributes();  // NamedNodeMapの取得
//        
//    }
    
    
//    if(attrs != null){
//        // 存在する属性ノードの数だけ繰り返す
//        for(int index=0; index<attrs.getLength(); index++){
//            Node attr = attrs.item(index);  // 属性ノード
//            System.out.print("属性名 = " + attr.getNodeName()); // 属性の名前
//            System.out.println(", 値 = " + attr.getNodeValue()); // 属性の値
//        }
//    }
     
      

}

    static private void printElementValue(Node childNode){
        //要素タグかチェック
        if (childNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            System.out.println("タグ名 = " + childNode.getNodeName() );//タグ名
            // 要素タグ内の内容を参照
            Node node = childNode.getFirstChild();

            // 更に子要素タグを持っていないかチェック
            NodeList ChildNodes;
            Node child;            
            if( (ChildNodes = node.getChildNodes()) !=null ){
                //ChildNodes = node.getChildNodes();
                //子要素タグとその内容を参照する
                for(int iNode=0; (child = ChildNodes.item(iNode))!=null; iNode++){
                    if (child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {//要素タグかチェック
                        System.out.println("タグ名 = " + child.getNodeName() );//タグ名
                        System.out.println("テキストノード値 = " + child.getNodeValue() ); // ノード値
                    }
                }
            }
            //子要素を持っていない時
            else if(node.getNodeType() == org.w3c.dom.Node.TEXT_NODE){//テキストノードであるかチェック
               System.out.println("テキストノード値 = " + node.getNodeValue() ); // ノード値
            }    
        }//end of 要素タグIF

    }

}