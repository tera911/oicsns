<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>OIC SNS - RT2</title>
        <%@include file="part/script.html" %>
        <script type="text/javascript"><!--
            var thread = [];
            var game = {};
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
            game.user.userid;
            game.user.username;
            game.user.avatarid;
            game.user.mapid;

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
                    $('#content').delay(1000).load("eula.htm", function() {
                        game.func.setProgressbar(1);
                        $('.jscrollpane').jScrollPane({
                            showArrows: true,
                            arrowScrollOnHover: true
                        });
                        $('#sendbutton').click(function() {
                            if ($('input[name="agree"]')[0].checked) {
                                $('#content').children().remove();
                                game.func.showProgressbar();
                                game.func.register();
                            } else {
                                location.href = "/";
                            }
                        });
                    });
                    game.func.hideProgressbar();
                };
                game.func.register = function() {
                    $('#content').delay(1000).load("part/profile.html", function() {

                        for (var i = 1; i < 47; i++) {
                            var img = new Image();
                            img.src = "/img/avatar/" + i.fillZero(5) + ".png";
                            img.className = "avatar_pic";
                            $('#avatar_window').append(img);
                        }
                        s = document.createElement('script');
                        s.src = "/js/profile.js";
                        $('head').append(s);

                    });
                };
                /**
                 * ゲーム画面
                 * @returns {undefined}
                 */
                game.func.game = function() {
                    $("#content").unbind();
                    $('#content').children().remove();
                    $('#content').css('background', '#EEE');
                    $("#overlay").fadeOut();
                    this.showProgressbar();
                    $.get("part/map.htm", function(data) {
                        $('#content').append(data);
                        game.func.setProgressbar(0.25);
                    }, "html");
                    $.get("part/chatwindow.html", function(data) {
                        $('#content').append(data);
                        s = document.createElement('script');
                        s.src = "/js/chatwindow.js";
                        $('head').append(s);
                        $('#chatwindowGroup #inputWindow').keypress(function(e) {
                            if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
                                if (game.func.sendChat_all($(this).val())) {
                                    $('#chatwindowGroup #inputWindow').val('');
                                }
                            }
                        });
                        $('#chatwindowGroup #submitButton').click(function() {
                            if (game.func.sendChat_all($('#chatwindowGroup #inputWindow').val())) {
                                $('#chatwindowGroup #inputWindow').val('');
                            }
                        });
                        game.func.setProgressbar(0.5);
                    }, "html");
                    $.get("part/menubar.html", function(data) {
                        $('#content').append(data);
                        s = document.createElement('script');
                        s.src = "/js/menubar.js";
                        $('head').append(s);
                    }, "html");
                    this.getMapList();
                    this.getUserInfo();
                    wait(function() {
                        return game.user.userid > 0;
                    }, function() {
                        game.func.createCharacter(game.user);
                        game.func.setProgressbar(0.75);
                        game.func.getMapUserList();
                    }, 50, thread[0]);

                    wait(function() {
                        return game.mapUserIdList.length > 0;
                    }, function() {
                        game.func.mapOtherCharacterView();
                    }, 50, thread[1]);
                    this.hideProgressbar();
                };
                game.func.update = function() {
                    console.log("update");
                    game.mapUserIdList = [];
                    clearInterval(thread[3]);
                    game.func.getMapUserList();
                    wait(function() {
                        return game.mapUserIdList.length > 0;
                    }, function() {
                        game.func.mapOtherCharacterView();
                    }, 50, thread[3]);
                };
                game.func.changeMap = function(mapid) {
                    if (!game.changeMap) {
                        $('#map').animate({opacity: 0}, 200);
                        $('#chatwindowGroup').animate({opacity: 0}, 200);
                        $('[id$="Menubar"]').animate({opacity: 0}, 200);
                        $('[id^="character_"]').remove();
                        game.func.transferMap(mapid);
                        wait(
                                function() {
                                    return game.nextmap > 0;
                                },
                                function() {
                                    $('#map').animate({opacity: 1}, 200);
                                    $('#chatwindowGroup').animate({opacity: 1}, 200);
                                    $('[id$="Menubar"]').animate({opacity: 1}, 200);
                                    game.mapUserIdList = [];
                                    game.userlist = {};
                                    game.func.getUserInfo();
                                    wait(function(){
                                        return game.user.mapid === game.nextmap;
                                    },function(){
                                        game.func.createCharacter(game.user);
                                        game.func.getMapUserList();
                                        game.nextmap = -1;
                                    },50, thread[4]);
                                    wait(function() {
                                        return game.mapUserIdList.length > 0;
                                    }, function() {
                                        game.func.mapOtherCharacterView();
                                    }, 50, thread[6]);
                                }, 500, thread[5]);
                    }
                }
                game.func.faildLogin = function() {
                    alert("IDとパスワードを確認してください。");
                };
                game.func.showProgressbar = function() {
                    game.progress.val(0);
                    game.progressbar.show();
                };
                game.func.setProgressbar = function(n) {
                    game.progress.val(n);
                };
                game.func.hideProgressbar = function() {
                    game.progress.val(0);
                    game.progressbar.hide();
                };
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
                    }, 100);
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
                game.func.transferMap = function(mapid) {
                    obj = {};
                    obj.method = "transfermap";
                    obj.mapid = mapid;
                    game.ws.sendJSON(obj);
                };
                game.func.userLogout = function() {
                    obj = {};
                    obj.method = "logout";
                    game.ws.sendJSON(obj);
                };
                game.ws.onmessage = function(e) {
                    var data = JSON.parse(e.data);
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
                        case "getmaplist":
                            $.extend(true, game.maplist, data.maplist);
                            game.func.mapListView();
                            break;
                        case "getuserinfo":
                            if (!game.user.userid) {
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
                game.ws.sendJSON = function(obj) {
                    var json = JSON.stringify(obj);
                    this.send(json);
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
                    obj = {};
                    obj.method = "login";
                    obj.userid = uid;
                    obj.password = pw;
                    console.log(JSON.stringify(obj));
                    game.ws.send(JSON.stringify(obj));
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