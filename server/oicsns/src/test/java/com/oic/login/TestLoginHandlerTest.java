/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.login;

import junit.framework.TestCase;
import org.json.simple.JSONObject;

/**
 *  TestLogin処理のテスト
 * DBにアクセスしてログインできるか。
 * @author morimoto
 */
public class TestLoginHandlerTest extends TestCase {
    
    public TestLoginHandlerTest(String testName) {
        super(testName);
    }
    
    public void testDBAccess(){
        JSONObject json = new JSONObject();
        
        TestLoginHandler testLogin = new TestLoginHandler();
        int status = -1;
        try{
            status = testLogin.login(1, "test123");
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("LoginStatus", status, 1);
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
}
