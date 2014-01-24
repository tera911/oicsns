/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    game.func.changeMap = function(mapid) {
        if (!game.changeMap) {
            game.changeMap = true;
            $('#map').animate({opacity: 0}, 200);
            $('#chatwindowGroup').animate({opacity: 0}, 200);
            $('[id$="Menubar"]').animate({opacity: 0}, 200);
            $('[id^="character_"]').animate({opacity: 0}, 200);
            game.func.transferMap(mapid);
            wait(
                    function() {
                        return game.nextmap > 0;
                    },
                    function() {
                        $('#map').animate({opacity: 1}, 200);
                        $('#chatwindowGroup').animate({opacity: 1}, 200);
                        $('[id$="Menubar"]').animate({opacity: 1}, 200);
                        $('[id^="character_"]').animate({opacity: 1}, 200);
                        game.mapUserIdList = [];
                        game.userlist = {};
                        game.func.getUserInfo();
                        wait(function() {
                            return game.user.mapid === game.nextmap;
                        }, function() {
                            game.func.createCharacter(game.user);
                            game.nextmap = -1;
                            game.changeMap = false;
                            game.func.update();
                            //マップ画像更新
                            game.func.getMapInfo(game.user.mapid);
                            wait(function(){
                                return game.mapinfo.mapid > 0;
                            },
                            function(){
                                //マップの画像変える
                                $('#content').css('background-image','url('+ game.mapinfo.imgpath +')')
                                .css('background-repeat','no-repeat')
                                .css('background-size','180%');
                            },50, thread[7]);
                        }, 50, thread[4]);
                    }, 500, thread[6]);

        }
    }
    game.func.sendChat_all = function(text) {
        if (text.length < 1) {
            return false;
        }
        obj = {};
        obj.method = "allchat";
        obj.text = text;
        game.ws.sendJSON(obj);
        return true;
    };
    game.func.recieveChat_all = function(userid, text) {
        var color;
        var username;
        if (userid == game.user.userid) {
            username = game.user.username;
            color = "green";    //自キャラの場合は文字をgreenに
        } else {
            username = game.userlist[userid].username;
            color = "";         //他キャラの場合は文字色変更なし
        }
        $('<p>[全体]<b>[' + username + "]</b> " + text + '</p>').appendTo('#fw_all .jspPane').addClass(color);
        $('#character_' + userid.fillZero(5) + ' .chat').text(text).fadeIn(150, function() {
            $(this).delay(3000).fadeOut(150);
        });

        /* 児童スクロール */
        var jsp = $('#fw_all').data('jsp');
        jsp.reinitialise();
        jsp.scrollToBottom();
    }
    game.func.mapListView = function() {
        $('#floors div').each(function() {
            var mapid = $(this).data('mapid').toString();
            if ($('#mapfloor_' + mapid).length) {
                return;
            }
            var floor = $('<div class="floor" id="mapfloor_' + mapid + '"></div>').appendTo('#map').hide();
            try {
                var i = 0;
                var roomcode = ['a', 'b', 'c', 'd'];
                var firstroom = $('<div></div>').appendTo(floor);
                for (i = 1; i <= 3; i++) {
                    $('<div class="room_' + roomcode[i - 1] + '" data-mapid="' + mapid + i + '"></div>').appendTo(firstroom).append('<p>' + game.maplist[mapid + i] + '</p>');
                }
                $('<div class="room_' + roomcode[i - 1] + '" data-mapid="' + mapid + i + '"></div>').appendTo(floor).append('<p>' + game.maplist[mapid + i] + '</p>');
            } catch (e) {
                console.log(e);
                floor.remove();
            }
        });
        wait(function() {
            return $('[id^="mapfloor_"]').length > 0;
        }, function() {
            $('#floors div').click(function() {
                var mapid = $(this).data('mapid');
                if (mapid === -1) {
                    return;
                }
                $(this).parent().fadeOut(function() {
                    $('#mapfloor_' + mapid).fadeIn(100);
                    $('#map .label').text(mapid + ' F');
                });
            });
            $('#map [class^="room"]').click(function() {
                game.func.changeMap($(this).data('mapid'));
            });
            $('#floors').fadeIn(50);
            $('#map .back').click(function() {
                $('div[id^=mapfloor_]').fadeOut(50, function() {
                    $('#map .label').text('floor');
                    $('#floors').fadeIn(50);
                });
            });
            $('#map .close').click(function() {
                $('#map').fadeOut();
            });
        }, 100,thread[5]);
    };
    game.func.mapOtherCharacterView = function() {
        for (var i in game.mapUserIdList) {
            if (i === 'clone') {
                continue;
            }
            (function() {
                var idx = i;
                game.userlist = [];
                game.func.getUserInfo(game.mapUserIdList[i]);
                wait(function() {
                    return typeof game.userlist[game.mapUserIdList[idx]] !== 'undefined';
                },
                        function() {
                            game.func.createCharacter(game.userlist[game.mapUserIdList[idx]]);
                        }, 50, thread[10 + idx]);
            })();
        }

    };
    game.func.createCharacter = function(character) {
        //console.log("create character.");
        var userid = character.userid.fillZero(5);
        var avatarid = character.avatarid.fillZero(5);
        var username = character.username;
        var pos = character.pos;

        var char = $('<div id="character_' + userid + '"></div>');
        char.data("userid", userid);
        char.addClass('character');
        char.append('<div class="chat" style="display:none;"></div>').
                append('<div class="avatar"></div>').
                append('<div class="name"></div>');
        $('.avatar', char).append('<canvas></canvas>');
        var canvas = $('canvas', char)[0];
        canvas.width = pos.width;
        canvas.height = pos.height;
        var img = new Image();
        img.src = "/img/avatar/" + avatarid + ".png";
        img.onload = (function() {
            var ctx = canvas.getContext('2d');
            ctx.drawImage(img, 0, 0, 100, 150);
        });
        $('.name', char).text(username);
        char.css('top', pos.y);
        char.css('left', pos.x);

        if ($('#character_' + userid).length) {
            this.removeCharacter(userid);
        }
        $('#content').append(char);

    };
    game.func.removeCharacter = function(userid) {
        $('#character_' + userid).hide(100).remove();
    }
    game.func.checkDuplication = function(username) {
        var message = $('#profile .message');
        $('[for="username"]', message).remove();
        game.checkduplication = -1;
        if (username == "") {
            message.append('<label for="username" class="error">ハンドルネームを入力してください。</label>');
            return;
        }
        game.func.duplication(username);
        wait(function() {
            return game.checkduplication > -1;
        }, function() {
            if (game.checkduplication == 0) {
                message.append('<label for="username" class="success">このハンドルネームは使えます</label>');
            } else if (game.checkduplication == 1) {
                message.append('<label for="username" class="error">このハンドルネームは使用されています。</label>');
            } else {
                message.append('<label for="username" class="error">このハンドルネームは使用できません。</label>');
            }
        },50, thread[9]);
    };
    // game.func.setProfile; login.jspに
});