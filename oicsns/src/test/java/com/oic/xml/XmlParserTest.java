/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.xml;

import com.oic.Config;
import com.oic.map.OicMap;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 *
 * @author b2020
 */
public class XmlParserTest extends TestCase {
    
    public XmlParserTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
//    public void testMaptest(){
//        try {
//            new MapParser();
//        } catch (IOException ex) {
//            Logger.getLogger(XmlParserTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(XmlParserTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    public void testCreateInstance(){
        try{
            assertNotNull(new XmlParser().loadOicMap());
        }catch(ParserConfigurationException e){}
    }
    
    public void testViewMapxmlList(){
        try {
            File file = new File(getClass().getResource(Config.MAPFILE_PATH).toURI());
            for(File f : file.listFiles()){
               System.out.println(f.getPath());
            }
          //  System.out.println(file.listFiles());
        } catch (URISyntaxException ex) {
            Logger.getLogger(XmlParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
