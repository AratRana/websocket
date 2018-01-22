
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="websocket.js"></script>
</head>
<body>

	<div id="wrapper">
		<h1>Java Websocket Home</h1>
		<p>Welcome to the Java WebSocket.</p>
		<br />
		<div id="addLink">
			<div class="button">
				<a href="#" OnClick="showForm()">Add a Link</a>
			</div>
			<form id="addLinkForm">
				<h3>Add a new link</h3>
				<span>Link: <input type="text" name="link_name"
					id="link_name">
				</span> <input type="button" class="button" value="Add"
					onclick=formSubmit()> <input type="reset" class="button"
					value="Cancel" onclick=hideForm()>
			</form>
		</div>
		<br />
		<h3>Currently connected links:</h3>
		<div id="content"></div>
	</div>

</body>
</html>