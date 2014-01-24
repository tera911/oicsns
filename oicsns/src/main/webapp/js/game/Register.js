/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    game.func.register = function() {
        $('#content').delay(1000).load("part/profile.jsp", function() {
            for (var i = 1; i < 47; i++) {
                            var img = new Image();
                            var avatarid = i.fillZero(5);
                            img.src = "/img/avatar/" + avatarid + ".png";
                            img.alt = avatarid;
                            img.className = "avatar_pic";
                            $(img).data("avatarid",avatarid);
                            $('#avatar_window').append(img);
                        }
            s = document.createElement('script');
            s.src = "/js/profile.js";
            $('head').append(s);
            $('#confirm').click(function() {
                game.func.checkDuplication($('#username').val());
            });
        });
    };
});