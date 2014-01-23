/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.net;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author kxhtj529
 *//**
 *
 * @author kxhtj529
 */
public class Callback extends HttpServlet{
    private String client_id = "502764282977-p30gid0pcl8b928ehqr5uno428prbuic.apps.googleusercontent.com";
    private String client_secret = "_iQ1zpHD_deeA1aRs95yydyc";
    
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(request.getParameter("code"));
            try{
                getToken(request.getParameter("code"));
            }catch(Exception e){e.printStackTrace();}
        }
        
        private void getToken(String code) throws Exception{
            String uri = "https://accounts.google.com/o/oauth2/token?"
                    + "code="+URLEncoder.encode(code, "utf-8")
                    + "&redirect_uri=http%3a%2f%2fsakura%2est%2dsweet%2ecom"
                    + "&client_id="+ client_id 
                    + "&client_secret=" + client_secret 
                    + "&grant_type=authorization_code";
            System.out.println(uri);
            
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            DefaultHttpClient client = new DefaultHttpClient(params);
            
            HttpPost httpPost = new HttpPost(uri);
            
            httpPost.addHeader("content-type","application/x-www-form-urlencoded");
            
            HttpResponse response = client.execute(httpPost);
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(header.getName() + ": " + header.getValue());
            }
 
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
            System.out.println(response.getStatusLine());
            
        }
    
}
