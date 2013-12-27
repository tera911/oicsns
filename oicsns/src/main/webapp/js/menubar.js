/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
                            //メニューマウスオーバーで白くする処理
                            $('.icon').hover(
                                //マウスオーバー
                                function(){
                                $(this).css( 'background-color', 'rgba(255,255,255,0.3)' );
                                },
                                //マウスアウト
                                function(){
                                $(this).css( 'background-color' , 'rgba(255,255,255,0)' );
                                }
                            );
                            //メニューバーを最小化する処理
                            $("#max_btn").click(function(){
                                $("#maxMenubar").toggle('slide',{direction:'right'},function(){
                                    $("#minMenubar").toggle('slide',{direction:'right'});
                                });
                            });
                            //メニューバーを最大化する処理
                            $("#min_btn").click(function(){
                                $("#minMenubar").toggle('slide',{direction:'right'});
                                $("#maxMenubar").toggle('slide',{direction:'right'});
                            });
                            $('#map_btn').click(function(){
                                $('#map').fadeIn();
                            });
                        });