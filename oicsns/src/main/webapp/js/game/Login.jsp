<%-- 
    Document   : Login処理を動的に作成する
    Created on : 2014/01/24, 5:29:27
    Author     : Morimoto
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="true"%>

$(function(){
<%
    String studentNumber    =   (String) session.getAttribute("studentNumber");
    String key              =   (String) session.getAttribute("key");
    Boolean alreadyId       =   (Boolean)session.getAttribute("alreadyId");
    /*studentNumber = "b2020";
    key = "db7298a149d51e3c528a31a4bfa145e9";
    alreadyId = false;*/
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

game.func.setProfile = function() {
        obj = {};
        obj.method = "setprofile";
        obj.studentid = "<%= studentNumber %>"; //学籍番号
        obj.username = $('#username').val();
        obj.avatarid = parseInt($('#full_avatar').data('avatarid'));
        obj.grade = $('#grade').val();
        obj.gender = $('[name="sex"]').val();
        obj.birthday = $('#birthday_year').val() + "-" + $('#birthday_month').val() + "-" + $('#birthday_day').val();
        obj.comment = $('#comment').val();
        obj.secretkey = "<%= key %>";
        obj.vgrade = $('#hidegrade').val();
        obj.vgender = $('#hidegender').val();
        obj.vbirthday = $('#hidebirthday').val();

        game.ws.sendJSON(obj);
        wait(function() {
            return game.regist > -1;
        }, function() {
            if (game.regist == 0) {
                game.func.game();
            } else {
                alert("何かがおかしいようです。もう一度登録してください。");
            }
            game.regist = -1;
        },50, thread[5]);
    };
    game.func.refresh = function(){
        $.get("/callback?code=1&register=1",function(){
        game.func.userLogin("<%= studentNumber%>","<%= key%>");
        });
    }
});