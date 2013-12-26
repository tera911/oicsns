$(function(){
			$('.jscrollpane').jScrollPane({
				showArrows:true,
				arrowScrollOnHover:true
			});
		});
		$(function(){
			$(".avatar_pic").click(function(){
				document.getElementById("full_avatar").src = this.src;
				document.getElementById("full_avatar").alt = this.alt;
			});
		});
		$(function(){
			$('#hidegrade').click(function(){		/*学年ON/OFF*/
				if(this.checked){
					document.getElementById("hidegradelabel").innerText = "ON";
				}
				else {
					document.getElementById("hidegradelabel").innerText = "OFF";
				};
			});
			$('#hidegender').click(function(){		/*性別ON/OFF*/
				if(this.checked){
					document.getElementById("hidegenderlabel").innerText = "ON";
				}
				else {
					document.getElementById("hidegenderlabel").innerText = "OFF";
				};
			});
			$('#hidebirthday').click(function(){	/*生年月日ON/OFF*/
				if(this.checked){
					document.getElementById("hidebirthdaylabel").innerText = "ON";
				}
				else {
					document.getElementById("hidebirthdaylabel").innerText = "OFF";
				};
			});
		});
		$(function(){
			$("#sendbutton").click(function(){
				var studentid = document.getElementById("student_id").value;
				var username = document.getElementById("username").value;
				var avatarid = document.getElementById("full_avatar").alt;
				var grade = document.getElementById("grade").value;
				var gender = $('#userdata [name=sex]:checked').val();
				var birthday = $("#birthday_year").val() + "-" + $("#birthday_month").val() + "-" + $("#birthday_day").val() ;
				var comment = document.getElementById("comment").value;
				var vgrade = document.getElementById("hidegrade").checked;
				var vgender = document.getElementById("hidegender").checked;
				var vbirthday = document.getElementById("hidebirthday").checked;
                               
				console.log("method:\"getprofile\", studentid:\""  + studentid + "\", username:\""+ username + "\", avatarid:" + avatarid +", grade:" + grade + ", gender:"+ gender + ", birthday:" + birthday + ", comment:\"" + comment + "\", vgrade:" + vgrade + ", vgender:"  + vgender + ", vbirthday:" + vbirthday + ",");

			});
		});