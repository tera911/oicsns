/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {

    game.func.getMapList = function() {
        obj = {};
        obj.method = "getmaplist";
        game.ws.sendJSON(obj);
    };
    game.func.getMapUserList = function() {
        obj = {};
        obj.method = "getmapuserlist";
        obj.mapid = game.user.mapid;
        game.ws.sendJSON(obj);
    };
    game.func.getUserInfo = function(userid) {
        if (typeof userid === "undefined") {
            obj = {};
            obj.method = "getuserinfo";
            game.ws.sendJSON(obj);
        } else {
            obj = {};
            obj.method = "getuserinfo";
            obj.userid = userid;
            obj.mapid = game.user.mapid;
            game.ws.sendJSON(obj);
        }
        console.log(obj);

    }
    game.func.duplication = function(username) {
        obj = {};
        obj.method = "duplication";
        obj.username = username;
        game.ws.sendJSON(obj);
    };
    game.func.transferMap = function(mapid) {
        obj = {};
        obj.method = "transfermap";
        obj.mapid = mapid;
        game.ws.sendJSON(obj);
    };
    game.func.getMapInfo = function(mapid) {
        obj = {};
        obj.method = "getmapinfo";
        obj.mapid = mapid;
        game.mapinfo.mapid = -1;
        game.ws.sendJSON(obj);
    };
    game.func.userLogout = function() {
        obj = {};
        obj.method = "logout";
        game.ws.sendJSON(obj);
    };
    game.func.userLogin = function(uid, pw) {
        obj = {};
        obj.method = "login";
        obj.userid = uid;
        obj.password = pw;
        //console.log(JSON.stringify(obj));
        game.ws.send(JSON.stringify(obj));

    }
});