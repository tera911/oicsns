/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.http.HttpMethod;

/**
 *
 * @author t
 */
public class OAuthTest extends TestCase {
    
    public OAuthTest(String testName) {
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
    
//    public void testOAuth() throws Exception{
//        String client_id = "502764282977-p30gid0pcl8b928ehqr5uno428prbuic.apps.googleusercontent.com";
//        String client_secret = "_iQ1zpHD_deeA1aRs95yydyc";
//        String callback = "http://sakura.st-sweet.com/";
//        String uri = "https://accounts.google.com/o/oauth2/token";
//        
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(uri);
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//        List<NameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("client_id", client_id));
//        nvps.add(new BasicNameValuePair("client_secret", client_secret));
//        nvps.add(new BasicNameValuePair("redirect_uri", callback));
//        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
//        nvps.add(new BasicNameValuePair("code","4/H9F2EDWiiInf3umUOeqCou5utq8w.4sQSMKs4sWAYgrKXntQAax1G-aJ-hwI"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        try{
//            HttpResponse response = client.execute(httpPost);
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
//            
//        }catch(IOException e){}
//        
//    }
}
