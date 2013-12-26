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
                    <%@include file="part/character.html" %>
		</div>
		<div id="footer">
			Copyright &copy; 2013 OIC-SNS All Rights Reserved.
		</div>
</body>
</html>
