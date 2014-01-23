<%-- 
    Document   : Login処理を動的に作成する
    Created on : 2014/01/24, 5:29:27
    Author     : Morimoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

$(function(){
alert("<% out.print((String)session.getAttribute("studentNumber")); %>");
});