<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>OIC SNS - RT2</title>
        <%@include file="part/script.html" %>
        <script type="text/javascript"><!--
            var game = {};
            game.func = {};
            game.maplist = {};
            game.mapinfo = {};
            game.pos = {};
            game.mapUserIdList = {};
            game.userid;
            game.username;
            game.avatarid;
            game.mapid;


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
                        game.func.setProgressbar(0.75);
                    }, "html");
                    this.getMapList();
                    this.update();
                    this.hideProgressbar();
                };
                game.func.update = function() {
                    game.func.getUserInfo();
                    game.func.getMapUserList();
                };
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
                game.func.recieveChat_all = function(username, text) {
                    $('#fw_all .jspPane').append('<p>' + username + " : " + text + '</p>');
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
                            floor.remove();
                        }
                        ;
                    });
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
                };
                game.func.mapOtherCharacterView = function() {
                    for (var i in game.mapUserIdList) {
                        game.func.getUserInfo(game.mapUserIdList[i], game.mapid);
                    }
                };
                game.func.createCharacter = function(character) {
                    var userid = character.userid.fillZero(5);
                    var avatarid = character.avatarid.fillZero(5);
                    var username = character.username;
                    var pos = character.pos;
                    if ($('#character_' + userid).length) {
                        return;
                    }
                    var char = $('<div id="character_' + userid + '"></div>');
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

                    $('#content').append(char);
                };

                game.func.getMapList = function() {
                    obj = {};
                    obj.method = "getmaplist";
                    game.ws.send(JSON.stringify(obj));
                };
                game.func.getMapUserList = function() {
                    obj = {};
                    obj.method = "getmapuserlist";
                    obj.mapid = game.mapid;
                    game.ws.sendJSON(obj);
                };
                game.func.getUserInfo = function(userid, mapid) {
                    if (typeof userid === "undefined" || typeof mapid === "undefined") {
                        obj = {};
                        obj.method = "getuserinfo";
                        game.ws.sendJSON(obj);
                    } else {
                        obj = {};
                        obj.method = "getuserinfo";
                        obj.userid = userid;
                        obj.mapid = mapid;
                        game.ws.sendJSON(obj);
                    }

                }
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
                            if(game.userid){
                                game.userid = data.userid;
                                game.username = data.username;
                                $.extend(true, game.pos, data.pos);
                                game.mapid = data.mapid;
                                game.avatarid = data.avatarid;
                                game.func.createCharacter(data);
                            }else{
                                game.func.createCharacter(data);
                            }
                            break;
                        case "getmapuserlist":
                            var userlist = data.userlist.filter(function(x,i, self){return self.indexOf(x) === i});
                            $.extend(true, game.mapUserIdList, userlist);
                            game.func.mapOtherCharacterView();
                            break;
                        case "logout":
                            if (data.status == 0) {
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