<%-- 
    Document   : Login処理を動的に作成する
    Created on : 2014/01/24, 5:29:27
    Author     : Morimoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

$(function(){
<%
    String studentNumber    =   (String) session.getAttribute("studentNumber");
    String key              =   (String) session.getAttribute("key");
    Boolean alreadyId       =   (Boolean)session.getAttribute("alreadyId");
    if (studentNumber != null && key != null && alreadyId != null) {
        //IDとkeyが取得できている
        
        //ログイン可能
        if(alreadyId){
%>
$("#content").click(function() {
        $("#content").unbind();
        $("#overlay").fadeIn();
        game.func.userLogin("<%= studentNumber%>","<%= key%>");
});
<%
        }else{
%>
    game.func.openEula();
<%
        }
} else {
%>
$("#content").click(function() {
$("#content").unbind();
$("#overlay").fadeIn();
location.href = "https://accounts.google.com/o/oauth2/auth?redirect_uri=http%3A%2F%2Fsakura.st-sweet.com%3A8080%2Fcallback&response_type=code&client_id=502764282977-1nto9ad95ng83k9hiplnfn0nn731tkuc.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email&approval_prompt=force&access_type=online";
});
<%
    }
%>
});