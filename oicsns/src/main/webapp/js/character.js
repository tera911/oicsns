////画像オブジェクトに任意の画像を読み込み
//var img = new Image();
//img.src = "/img/avatar/testava.png";
////画像の読み込みが終わったら、canvasに貼付けを実行
//img.onload = (function() {
//    //canvas(000)のノードオブジェクト
//    var canvas = document.getElementById('000');
//    var canvas2 = document.getElementById('001');
//    //canvasのサイズ指定
//    canvas.width = 100;
//    canvas.height = 150;
//    canvas2.width = 100;
//    canvas2.height = 150;
//    //2Dコンテキストをctxに格納
//    var ctx = canvas.getContext('2d');
//    var ctx2 = canvas2.getContext('2d');
//    //読み込んだimgをcanvas(000)に貼付け
//    ctx.drawImage(img, 0, 0);
//    ctx2.drawImage(img, 0, 0);
//});
//画像のパス指定


//繰り返しで同じ変数に入れてやったほうが

/*
 * Objectを受け取る
 * userid : 000,
 * username : "",
 * avatar : 1,
 * pos:{x: 0, y: 0}
 */
function createCharacter(character){
    var userid = character.userid.fillZero(5);
    var username = character.username;
    var pos = character.pos;
    if($('#character_'+ userid).length){
        return;
    }
    var char = $('<div id="character_' + userid + '"></div>');
    char.addClass('character');
    char.append('<div class="chat" style="display:none;"></div>').
            append('<div class="avatar"></div>').
            append('<div class="name"></div>');
    $('.avatar', char).append('<canvas></canvas>');
    var canvas = $('canvas',char)[0];
    canvas.width = pos.width;
    canvas.height = pos.height;
    var img = new Image();
    img.src = "/img/avatar/" + userid + ".png";
    img.onload = (function(){
        var ctx = canvas.getContext('2d');
        ctx.drawImage(img,0,0,100,150);
    });
    $('.name', char).text(username);
    char.css('top',pos.y);
    char.css('left',pos.x);
   
    $('#content').append(char);
}
