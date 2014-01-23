/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
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
});