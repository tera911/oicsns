$(function() {
    game.ws.onmessage = function(e) {
        var data = JSON.parse(e.data);
        Otani = data;
        console.log(data);
        if (typeof data.method === "undefined") {
            return;
        }
        switch (data.method) {
            case "login":
                if (data.status == 0) {
                    game.login = 1;
                    game.func.game();
                } else {
                    game.func.faildLogin();
                }
                break;
            case "allchat":
                game.func.recieveChat_all(data.userid, data.text);
                break;
            case "duplication":
                game.checkduplication = data.result;
                break;
            case "getmaplist":
                $.extend(true, game.maplist, data.maplist);
                game.func.mapListView();
                break;
            case "getuserinfo":
                if (game.user.userid < 0) {
                    $.extend(true, game.user, data);
                } else if (game.user.userid === data.userid) {
                    $.extend(true, game.user, data);
                } else {
                    //game.userlist[data.userid] = data;
                    //$.extend(true, game.userlist[data.userid], data);
                    if (data.userid === game.user.userid) {
                        return;
                    }
                    game.userlist[data.userid] = data;
                }
                break;
            case "getmapuserlist":
                var userlist = data.userlist.filter(function(x, i, self) {
                    return self.indexOf(x) === i
                });
                $.extend(true, game.mapUserIdList, userlist);
                break;
            case "posupdate":
                game.func.update();
                break;
            case "transfermap":
                game.nextmap = data.mapid;
                break;
            case "logout":
                if (data.status === 0) {
                    location.href = "/";
                }
                break;
        }
    };
});