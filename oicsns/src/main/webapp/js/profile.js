$(function() {
    $('.jscrollpane').jScrollPane({
        showArrows: true,
        arrowScrollOnHover: true
    });
    $(".avatar_pic").click(function() {
        var avatar = $('#full_avatar');
        avatar.attr('src', this.src);
        avatar.attr('alt', this.alt);
        avatar.data('avatarid', $(this).data('avatarid'));
    });
    $('#hidegrade').click(function() {		/*学年ON/OFF*/
        if (this.checked) {
            $('#hidegradelabel').text("表示");
            $('#hidegrade').val(1);
        } else {
            $('#hidegradelabel').text("非表示");
            $('#hidegrade').val(0);
        }
    });
    $('#hidegender').click(function() {		/*性別ON/OFF*/
        if (this.checked) {
            $('#hidegenderlabel').text("表示");
            $('#hidegender').val(1);
        }else {
            $('#hidegenderlabel').text("非表示");
            $('#hidegender').val(0);
        }
    });
    $('#hidebirthday').click(function() {	/*生年月日ON/OFF*/
        if (this.checked) {
            $('#hidebirthdaylabel').text("表示");
            $('#hidebirthday').val(1);
        }else {
            $('#hidebirthdaylabel').text("非表示");
            $('#hidebirthday').val(0);
        }
    });
    var avatarid = (Math.floor(Math.random() * 20)+1).fillZero(5);
    $('#avatar_bg').append('<img id="full_avatar" alt="拡大アバター" src="img/avatar/'+avatarid+'.png" data-avatarid="'+avatarid+'">');
    $('#sendbutton').click($('#userdata').validate);
    
    jQuery.validator.addMethod(
            "stunumMatch",
            function(value, element) {
                reg = new RegExp("^[a-zA-Z]{1}[0-9]{4}");
                return this.optional(element) || reg.test(value);
            },
            "学籍番号を正しく入力してください"
            );
    jQuery.validator.addMethod(
            "checkDuplication",
            function(value, element){
                
                return this.optional(element) || game.checkduplication == 0;
            },
            "重複確認をしてください。"
        );
    $("#userdata").validate({
        submitHandler: function(){
            game.func.setProfile();
        },
        rules: {
            student_id: {
                required: true,
                stunumMatch: true
            },
            username: {
                required: true,
                maxlength: 12,
                checkDuplication: true
            },
            grade : {
                required: true
            },
            sex : {
                required: true
            },
            birthday_year :{
                required: true
            },
            birthday_month :{
                required: true
            },
            birthday_day :{
                required: true
            },
            comment: {
                maxlength: 150
            }
        },
        messages: {
            student_id: {
                required: "学籍番号を入力してください。"
            },
            username: {
                required: "ハンドルネームを入力してください。",
                maxlength: "ハンドルネームは最大12文字です。"
            },
            grade :{
                required: "学年を選んでください。"
            },
            sex :{
                required: "性別を選んでください。"
            },
            birthday_year :{
                required: "誕生日-年が正しくありません。"
            },
            birthday_month :{
                required: "誕生日-月が正しくありません。"
            },
            birthday_day :{
                required: "誕生日-日が正しくありません。"
            },
            comment: {
                maxlength: "コメントは最大150文字です"
            }
        },
        //エラーメッセージを任意の位置に表示
        errorPlacement: function(error, element) {
            error.appendTo($(".left_form .message"));
        }
    });
});