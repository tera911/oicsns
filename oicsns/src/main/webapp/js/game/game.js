/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var thread = [];
var game = {};

$(function() {
    game.func = {};
    game.maplist = {};
    game.mapinfo = {};
    /**
     * マップ変更が行われてるか？
     * @type Boolean
     */
    game.changeMap = false;
    /**
     * 次に移動するマップID
     * @type int
     */
    game.nextmap = -1;
    game.pos = {};
    game.mapUserIdList = {};
    game.user = {};
    /**
     * マップに存在しているユーザを保存するオブジェクト
     * キーはuserid
     * @type type
     */
    game.userlist = {};
    game.user.userid = -1;
    game.user.username;
    game.user.avatarid = -1;
    game.user.mapid = -1;

    game.ws = new WebSocket('ws://127.0.0.1:8080/ws');
    game.login = 0;

    game.func.faildLogin = function() {
        alert("IDとパスワードを確認してください。");
    };


    $("#content").click(function() {
        $("#content").unbind();
        $("#overlay").fadeIn();
        $("#login").show();
        $('#uid').focus();
    });
    $('#login input[name="login"]').bind('click submit', function() {
        var uid = $("#uid").val();
        var pw = $("#pw").val();
       game.func.userLogin(uid, pw);
        return false;
    });
    $('#login input[name="register"]').click(function() {
        game.func.openEula();
        return false;
    });
    $('#logout').click(function() {
        game.func.userLogout();
    });
});
