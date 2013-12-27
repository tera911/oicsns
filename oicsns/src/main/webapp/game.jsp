<%-- 
    Document   : game
    Created on : 2013/12/26, 18:56:08
    Author     : morimoto
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="part/script.html" %>
        <script>
            var ws;
            var obj;
            var d;
            $(function() {
                ws = new WebSocket("ws://127.0.0.1:8080/ws");
                ws.onmessage = function(e) {
                    console.log(e.data);
                    var data = JSON.parse(e.data);
                    console.log(data);
                     d = data;
                    if(data.method == "getchar"){
                        createCharacter(data);
                    }
                };
                obj = {};
                $('input[name="login"]').click(function() {
                    obj.method = "login";
                    obj.userid = "tera0911";
                    obj.password = "tera0911";
                    ws.send(JSON.stringify(obj));
                });
                $('input[name="character"]').click(function() {
                    obj.method = "cmd";
                    obj.cmd = "getchar";
                    ws.send(JSON.stringify(obj));
                });
            });
        </script>
    </head>
    <body>
        <div id="header">
            <ul>
                <li class="about"><a href="about.htm">当サイトについて</a>
                <li class="contact"><a href="contact.htm">通報&問い合わせ</a>
                <li id="logout">ログアウト
            </ul>
        </div>
        <div id="content">
            <%@include file="part/chatwindow.html" %>
            <%@include file="part/menubar.html" %>
            <input type="button" name="login" value="login">
            <input type="button" name="character" value="character">
        </div>
        <div id="footer">
            Copyright &copy; 2013 OIC-SNS All Rights Reserved.
        </div>
    </body>
</html>
