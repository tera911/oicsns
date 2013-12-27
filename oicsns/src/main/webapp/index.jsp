<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>OIC SNS - RT2</title>
        <%@include file="part/script.html" %>
        <script type="text/javascript"><!--
            var game = {};
            game.func = {};
            

            $(function() {
                game.ws = new WebSocket('ws://127.0.0.1:8080/ws');
                game.login = 0;
                game.progressbar = $('#progressbar');
                game.progress = $('#progressbar progress');
                game.func.openEula = function() {
                    game.func.showProgressbar()
                    $('#content').children().remove();
                    $("#content").unbind();
                    $("#overlay").fadeOut();
                    game.func.setProgressbar(0.25);
                    $("#content").css("display", "block");
                    game.func.setProgressbar(0.5);
                    $('#content').delay(1000).load("eula.htm",function(){
                        game.func.setProgressbar(1);
                        $('.jscrollpane').jScrollPane({
			showArrows:true,
			arrowScrollOnHover:true
                        });
                        $('#sendbutton').click(function(){
                        if($('input[name="agree"]')[0].checked){
                            $('#content').children().remove();
                            game.func.showProgressbar();
                            game.func.register();
                        }else{
                            location.href = "/";
                        }
                        });
                    });
                    game.func.hideProgressbar();
                };
                game.func.register = function(){
                    $('#content').delay(1000).load("part/profile.html",function(){
                        
                        for(var i = 1; i < 47; i++){
                            var img = new Image();
                            img.src = "/img/avatar/"+ i.fillZero(5) + ".png";
                            img.className = "avatar_pic";
                           $('#avatar_window').append(img); 
                        }
                        s = document.createElement('script');
                        s.src = "/js/profile.js";
                        $('head').append(s);
                        
                    });
                };
                game.func.view = function(){
                    
                };
                game.func.faildLogin = function() {
                    alert("IDとパスワードを確認してください。");
                };
                game.func.showProgressbar = function(){
                    game.progress.val(0);
                    game.progressbar.show();
                };
                game.func.setProgressbar = function(n){
                    game.progress.val(n);
                };
                game.func.hideProgressbar = function(){
                    game.progress.val(0);
                    game.progressbar.hide();
                };
                game.ws.onmessage = function(e) {
                    var data = JSON.parse(e.data);
                    console.log(data);
                    if (Object.prototype.toString.call(data.method).slice(8, -1) === "undefined") {
                        return;
                    }
                    switch (data.method) {
                        case "login":
                            if (data.status == 0) {
                                game.login = 1;
                               // game.func.openEula();
                            } else {
                                game.func.faildLogin();
                            }
                            break;
                    }
                };
                
                $("#content").click(function() {
                    $("#content").unbind();
                    $("#overlay").fadeIn();
                    $("#login").show();
                    $('#uid').focus();
                });
                $('#login input[name="login"]').submit(function() {
                    var uid = $("#uid").val();
                    var pw = $("#pw").val();
                    obj = {};
                    obj.method = "login";
                    obj.userid = uid;
                    obj.password = pw;
                    console.log(JSON.stringify(obj));
                    game.ws.send(JSON.stringify(obj));
                    return false;
                });
                $('#login input[name="register"]').click(function(){
                    game.func.openEula();
                    return false;
                });

            });
            //--></script>
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
                    <input type="submit" value="ログイン">
                    <input type="submit" name="register" value="登録">
                </p>
            </form>
        </div>
        <div id="info">
            <div id="overlay" class="ui-widget-overlay"></div>
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