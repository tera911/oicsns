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
    JSONObject json;
    TestLoginHandler testLogin;
    
    public TestLoginHandlerTest(String testName) {
        super(testName);
    }
    
    public void testDBAccess(){
        long loginId = -1;
        try{
            loginId = testLogin.login("tera09", "tera09");
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("LoginStatus", loginId, 4);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        json = new JSONObject();
        testLogin = new TestLoginHandler();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
