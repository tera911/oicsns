/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    game.func.openEula = function() {
        $('#content').children().remove();
        $("#content").unbind();
        $("#overlay").fadeOut();
        $("#content").css("display", "block");
        $('#content').delay(1000).load("eula.htm", function() {
            $('.jscrollpane').jScrollPane({
                showArrows: true,
                arrowScrollOnHover: true
            });
            $('#sendbutton').click(function(e) {
                e.preventDefault();
                if ($('input[name="agree"]')[0].checked) {
                    $('#content').children().remove();
                    game.func.register();
                }else if($('input[name="agree"]')[1].checked) {
                    location.href = "/";
                }else{
                    return false;
                }
            });
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
        $.get("part/map.htm", function(data) {
            $('#content').append(data);
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
            game.func.getMapUserList();
        }, 50, thread[0]);

        wait(function() {
            return game.mapUserIdList.length > 0;
        }, function() {
            game.func.mapOtherCharacterView();
        }, 50, thread[1]);
    };
    game.func.update = function() {
        console.log("update");
        if (game.changeMap) {
            return;
        }
        game.mapUserIdList = [];
        clearInterval(thread[3]);
        game.func.getMapUserList();

        wait(function() {
            return game.mapUserIdList.length > 0;
        }, function() {
            $('[id^="character_"]').each(function() {
                if (game.mapUserIdList.indexOf(parseInt($(this).data("userid"))) < 0) {
                    $(this).remove();
                }
            });
            game.func.mapOtherCharacterView();
        }, 50, thread[3]);
    };
});