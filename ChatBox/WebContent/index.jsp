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
<link href="css/style.css" rel="stylesheet" /> 
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	function insertChat(who, text) {
		var control = "";
		if (who == "me") {
			control = '<li>' + '<div class="chat self">'
					+ '<div class="user-photo">' + '</div>'
					+ '<p class="chat-message">' + text + '</p>' + '</div>'
					+ '</li>';
		} else {
			control = '<li>' + '<div class="chat friend">'
					+ '<div class="user-photo">' + '</div>'
					+ '<p class="chat-message">' + text + '</p>' + '</div>'
					+ '</li>';
		}
		$("ul").append(control);
		// 		$('#container').css("scrollTop", $("#container").offset().top);
		// 		 $('#container').animate({scrollTop: $('#container')[0].scrollHeight}, 2000);

	}
	// 	var $cont = $('#container');

	// 		var height = $('#container').height();

	$(document).ready(function() {
		// 		$("#send").click(function(e) {
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
				var div = $("#container");
				div.scrollTop(div.prop('scrollHeight'));
				alert(div.prop('scrollHeight'));

				// 				var div = $('#container'), height = div.height();
				// 				 div.animate({scrollTop: height}, 500);
				// 				alert(height);
				// 				var od = document.getElementById("container");
				// 				alert(od.scrollHeight);
				// 				od.scrollTop = od.scrollHeight;
				// 				alert(od.scrollTop);

				// 				alert($('#container').height());
				// 				$('#container').animate({
				// 					scrollTop : $('#container').height()
				// 				}, 10000);

				//   			    $('#container').animate({"scrollTop": $('#container')[0].scrollHeight}, 1000);
				// 				$('#container').scrollTo('100%');

			}
			;
		});
	});
</script>

</head>
<body>
	<h1 id="title">Chatbox Web Application</h1>
	<hr>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="chatbox col-sm-6">
			<div class="chatlogs">
				<ul id="container">
				</ul>
			</div>
			<div class="chat-form">
				<!-- 			<textarea id="txtarea" name="txtms"></textarea> -->
				<!-- 			<button id="send">Send</button> -->
				<input type="text" id="txt" placeholder="Type a message" style="width:450px">
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</body>
</html>