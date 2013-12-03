/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.client;

import junit.framework.TestCase;

/**
 *
 * @author b2020
 */
public class OicGenderTest extends TestCase {
    
    public OicGenderTest(String testName) {
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

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testGenderName(){
        assertEquals(OicGender.MAN.toString(),"man".toUpperCase());
        assertEquals(OicGender.valueOf("man".toUpperCase()), OicGender.MAN);
         assertEquals(OicGender.getGender("man"), OicGender.MAN);
    }
}
