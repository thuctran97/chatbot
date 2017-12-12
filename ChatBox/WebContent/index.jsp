<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath}/">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="js/jquery.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<link href="css/jquery-ui.min.css" rel="stylesheet" />
<script src="js/jquery-ui.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<script src="js/bootstrap.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	function insertChat(who, text) {
		var control = "";
		if (who == "me") {
			control = '<li>' + '<div class="chat self" >'
					+ '<div>' +'<img src="image/user.png"  class="user-photo">'+ '</div>'
					+ '<p class="chat-message">' + text + '</p>' + '</div>'
					+ '</li>';
		} else {
			control = '<li>' + '<div class="chat friend" >'
					+ '<div >' +'<img src="image/bot.png" class="user-photo">'+ '</div>'
					+ '<p class="chat-message" >' + text + '</p>' + '</div>'
					+ '</li>';
		}
		$("ul").append(control);
	}
	$(document).ready(function() {
		$("#txt").keyup(function(e) {
			if (e.keyCode == 13) {
				var mymessage = document.getElementById("txt").value;
				insertChat("me", mymessage);
				$(this).val('');
				$.ajax({
					type : 'POST',
					url : "chatbox.php",
					data : {
						mes : mymessage
					},
					success : function(data) {
						insertChat("you", data);
					}
				});
// 				var div = $(".chatlogs");
// 				div.scrollTop(div.prop('scrollHeight')+1000);
				$('.chatlogs').animate({scrollTop: $('.chatlogs')[0].scrollHeight}, 500);
			}
			;
		});
	});
</script>
<link href="css/style.css" rel="stylesheet" /> 
</head>
<body>
	<h1 id="title">Chatbot hướng dẫn giải toán khảo sát hàm số</h1>
	<hr>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="chatbox col-sm-6">
			<div class="chatlogs">
				<ul id="container" style="list-style-type:none">
					<li>
						<div class="chat friend">
							<div>
								<img src="image/bot.png" class="user-photo">
							</div>
							<p class="chat-message">Xin chào mình có thể giúp gì cho bạn</p>
						</div>
					</li>
				</ul>
			</div>
			<div class="chat-form">
				<input type="text" id="txt" placeholder="Type a message" style="width:650px">
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</body>
</html>