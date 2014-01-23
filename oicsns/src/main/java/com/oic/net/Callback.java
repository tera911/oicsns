/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author kxhtj529
 */
/**
 *
 * @author kxhtj529
 */
public class Callback extends HttpServlet {

    private String client_id = "502764282977-1nto9ad95ng83k9hiplnfn0nn731tkuc.apps.googleusercontent.com";
    private String client_secret = "AIrHJ18P5wTiwG6w22tH1Aa3";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(request.getParameter("code"));
        try {
            getToken(request.getParameter("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getToken(String code) throws Exception {
        String uri = "https://accounts.google.com/o/oauth2/token";
        String callback = "http://sakura.st-sweet.com:8080/callback";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", client_id));
        nvps.add(new BasicNameValuePair("client_secret", client_secret));
        nvps.add(new BasicNameValuePair("redirect_uri", callback));
        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("code", code));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        
        
        for(Header header : httpPost.getAllHeaders()){
            System.out.println(header.getName() + ":" + header.getValue());
        }
        
        HttpResponse response = client.execute(httpPost);
        
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
