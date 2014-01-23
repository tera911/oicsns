<%@ page language="java" contentType="text/html; charset=windows-31j"
         pageEncoding="windows-31j" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>OIC SNS - RT2</title>
        <%@include file="part/script.html" %>
        <script type="text/javascript" src="./js/game/game.js"></script>
        <script type="text/javascript" src="./js/game/MessageListener.js"></script>
        <script type="text/javascript" src="./js/game/ServerMessage.js"></script>
        <script type="text/javascript" src="./js/game/WebSocket.js"></script>
        <script type="text/javascript" src="./js/game/Register.js"></script>
        <script type="text/javascript" src="./js/game/System.js"></script>
        <script type="text/javascript" src="./js/game/Window.js"></script>
        
        <script type="text/javascript" src="./js/game/Login.jsp"></script>
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
            <% 
                String number = (String)session.getAttribute("studentNumber"); 
                String key = (String)session.getAttribute("key");
                if(number != null){
                    out.println(number);
                    out.println(key);
                }
            %>
            <form id="login">
                <p>
                    <label for="uid">ID:</label>
                    <input type="text" class="txt" id="uid">
                </p>
                <p>
                    <label for="pw">パスワード:</label>
                    <input type="password" class="txt" id="pw">
                </p>
                <p align=right>
                    <input type="submit" name="login" value="ログイン">
                    <input type="button" name="register" value="登録">
                </p>
            </form>
            <div id="overlay" class="ui-widget-overlay"></div>
        </div>
        <div id="info">

            <div id="progressbar">
                <p>loading now...</p>
                <progress></progress>
            </div>
        </div>
        <div id="footer">
            Copyright &copy; 2013 OIC-SNS All Rights Reserved.
        </div>
    </body>
</html>