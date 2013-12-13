/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import junit.framework.TestCase;
import org.json.simple.JSONObject;

/**
 *
 * @author b2020
 */
public class TestValiadtion extends TestCase {
    
    public TestValiadtion(String testName) {
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

    public void testJsonEmpty(){
        JSONObject json = new JSONObject();
        Validators v = new Validators(json);
        v.add("name", v.required());
        assertFalse(v.validate());
        
        json.put("name", "");
        v = new Validators(json);
        v.add("name", v.required());
        assertFalse(v.validate());
        
        json.put("name", "test");
        v = new Validators(json);
        v.add("name", v.required());
        assertTrue(v.validate());
    }
    
    public void testStudentId(){
        JSONObject json = new JSONObject();
        Validators v = new Validators(json);
        v.add("student", v.studentId());
        assertFalse(v.validate());
        
        json.put("student", "aaaa");
        v = new Validators(json);
        v.add("student", v.studentId());
        assertFalse(v.validate());
        
        json.put("student", "z9999");
        v = new Validators(json);
        v.add("student", v.studentId());
        assertTrue(v.validate());        
    }
    
    public void testMaxLength(){
        JSONObject json = new JSONObject();
        json.put("name", "aaa");
        Validators v = new Validators(json);
        v.add("name", v.maxLength(3));
        assertTrue(v.validate());
        json.put("name", "aaaa");
        v = new Validators(json);
        v.add("name", v.maxLength(3));
        assertFalse(v.validate());
    }
    
 
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
