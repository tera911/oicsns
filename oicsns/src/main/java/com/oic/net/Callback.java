/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oic.net;

import com.oic.utils.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Morimoto
 */
/**
 *
 * @author Morimoto
 */
public class Callback extends HttpServlet {

    private String client_id = "1033974121566-g6abtigoinr1m6mlppe3fmog4fuvil5h.apps.googleusercontent.com";
    private String client_secret = "GDgW7-lTNAVbvop4GhG-Hrss";
    private String access_token;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        if(request.getParameter("code") == null){           //OAuthでのcodeがない場合
            response.sendRedirect("/");
        }else if(request.getParameter("register") != null){ //ID登録後のリフレッシュ作業
            session.setAttribute("alreadyId", true);
            return;
        }
        if(session.isNew()){
            session.setMaxInactiveInterval(300);
        }
        String email = "";
        try {
            getToken(code); System.out.println(code);
            email = getEmailAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("@oic.jp$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.find()){
            Pattern numberPattern = Pattern.compile("^[a-zA-Z][0-9]{4}");
            Matcher numberMatcher = numberPattern.matcher(email.toLowerCase());
            if(!numberMatcher.find()){
                response.getWriter().println("このアドレスは使用できません。");
                session.invalidate();
                return;
            }
            
            String studentNumber = numberMatcher.group();
            String key = DigestUtils.md5Hex(String.valueOf(new Date().getTime()));
            session.setAttribute("studentNumber", studentNumber);
            session.setAttribute("key", key);  //タイムスタンプをmd5でハッシュ化
            registerData(studentNumber, key, session);
            response.sendRedirect("/");
        }else{
            response.getWriter().println("このアドレスは使用できません。");
            session.invalidate();
        }
        
    }

    private void getToken(String code) throws Exception {
        String uri = "https://accounts.google.com/o/oauth2/token";
        String callback = "http://sakura.st-sweet.com:8080/callback";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(uri);          //POST用
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost("prxsrv.oic.jp", 8080, "http")).build();
        httpPost.setConfig(config);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");    //Header指定
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", client_id));
        nvps.add(new BasicNameValuePair("client_secret", client_secret));
        nvps.add(new BasicNameValuePair("redirect_uri", callback));
        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("code", code));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        
        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode != 200){
            return;
        }
        String jsonText = EntityUtils.toString(response.getEntity());
        System.out.println(jsonText);
        JSONObject json = (JSONObject)new JSONParser().parse(jsonText);
        access_token = json.get("access_token").toString();
        
    }
    
    private String getEmailAddress() throws Exception{
        String requestURI = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(requestURI);
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost("prxsrv.oic.jp", 8080, "http")).build();
        httpGet.setConfig(config);
        httpGet.setHeader("Authorization","OAuth " + access_token);
        HttpResponse response = client.execute(httpGet);
        
        JSONObject json = (JSONObject)new JSONParser().parse(EntityUtils.toString(response.getEntity()));
        
        String email = json.get("email").toString();
        return email;
    }

    private void registerData(String studentNumber, String key, HttpSession session) {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT * FROM user WHERE studentnumber = ?";
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, studentNumber);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                rs.close();
                ps.close();
                con.close();
                session.setAttribute("alreadyId", false);
                return;
            }
            rs.close();
            ps.close();
            sql = "UPDATE user SET secretkey = ? WHERE studentnumber = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, studentNumber);
            ps.executeUpdate();
            ps.close();
            session.setAttribute("alreadyId", true);
        }catch(SQLException e){
             try{    ps.close();    }catch(Exception e1){}
        }
    }
}
