$(function() {
    $("ul.sub_list").hide();
    $("ul#ul_menu>li").click(function() {
        $("ul:not(:animated)", this).slideDown();
        //ドロップアップ時、背景色を変更する処理かく予定$("#ul_menu li a").
    });
    $('#ul_menu .sub_list a').click(function() {

        $('#ul_menu > li > a').text($(this).text());
        //$(this).attr('href').substr(1,5); 
        $("ul#ul_menu>li ul").slideUp();
        return false;
    });
    change(0);
});

//チャット受信時にIndexを探る方法　これで要素番号を取得できる
//$('#tabGroup a').index($('#tabGroup a.tab_active'));

/* フォームの表示切り替え 及び タブの色を変える処理 */
function change(i) {
    if (i == 0) {	//allを選択したときの処理
        document.getElementById('tab_all').className = 'tab_active';
        document.getElementById('tab_room').className = 'tab_inactive';
        document.getElementById('tab_wis').className = 'tab_inactive';
        document.getElementById('tab_circle').className = 'tab_inactive';

        document.getElementById('tab_all_txt').className = 'txt_active';
        document.getElementById('tab_room_txt').className = 'txt_inactive';
        document.getElementById('tab_wis_txt').className = 'txt_inactive';
        document.getElementById('tab_circle_txt').className = 'txt_inactive';

        document.getElementById('fw_all').style.display = 'block';
        document.getElementById('fw_room').style.display = 'none';
        document.getElementById('fw_wis').style.display = 'none';
        document.getElementById('fw_circle').style.display = 'none';
        $('#fw_all').jScrollPane({
            contentWidth: '366px',
            showArrows: true,
            arrowScrollOnHover: true
        });
    }
    else if (i == 1) {	//roomを選択したときの処理
        document.getElementById('tab_all').className = 'tab_inactive';
        document.getElementById('tab_room').className = 'tab_active';
        document.getElementById('tab_wis').className = 'tab_inactive';
        document.getElementById('tab_circle').className = 'tab_inactive';

        document.getElementById('tab_all_txt').className = 'txt_inactive';
        document.getElementById('tab_room_txt').className = 'txt_active';
        document.getElementById('tab_wis_txt').className = 'txt_inactive';
        document.getElementById('tab_circle_txt').className = 'txt_inactive';

        document.getElementById('fw_all').style.display = 'none';
        document.getElementById('fw_room').style.display = 'block';
        document.getElementById('fw_wis').style.display = 'none';
        document.getElementById('fw_circle').style.display = 'none';
        $('#fw_room').jScrollPane({
            contentWidth: '366px',
            showArrows: true,
            arrowScrollOnHover: true
        });
    }
    else if (i == 2) {			//wisを選択したときの処理
        document.getElementById('tab_all').className = 'tab_inactive';
        document.getElementById('tab_room').className = 'tab_inactive';
        document.getElementById('tab_wis').className = 'tab_active';
        document.getElementById('tab_circle').className = 'tab_inactive';

        document.getElementById('tab_all_txt').className = 'txt_inactive';
        document.getElementById('tab_room_txt').className = 'txt_inactive';
        document.getElementById('tab_wis_txt').className = 'txt_active';
        document.getElementById('tab_circle_txt').className = 'txt_inactive';

        document.getElementById('fw_all').style.display = 'none';
        document.getElementById('fw_room').style.display = 'none';
        document.getElementById('fw_wis').style.display = 'block';
        document.getElementById('fw_circle').style.display = 'none';
        $('#fw_wis').jScrollPane({
            contentWidth: '366px',
            showArrows: true,
            arrowScrollOnHover: true
        });
    }
    else if (i == 3) {  //circle
        document.getElementById('tab_all').className = 'tab_inactive';
        document.getElementById('tab_room').className = 'tab_inactive';
        document.getElementById('tab_wis').className = 'tab_inactive';
        document.getElementById('tab_circle').className = 'tab_active';

        document.getElementById('tab_all_txt').className = 'txt_inactive';
        document.getElementById('tab_room_txt').className = 'txt_inactive';
        document.getElementById('tab_wis_txt').className = 'txt_inactive';
        document.getElementById('tab_circle_txt').className = 'txt_active';

        document.getElementById('fw_all').style.display = 'none';
        document.getElementById('fw_room').style.display = 'none';
        document.getElementById('fw_wis').style.display = 'none';
        document.getElementById('fw_circle').style.display = 'block';

        $('#fw_circle').jScrollPane({
            contentWidth: '366px',
            showArrows: true,
            arrowScrollOnHover: true,
            hideFocus: false
        });
    }
}
function chat_all(text){
    $('#fw_all .jspPane').append('<p>'+text+'</p>');
    var jsp = $('#fw_all').data('jsp');
    jsp.reinitialise();
    jsp.scrollToBottom();
}