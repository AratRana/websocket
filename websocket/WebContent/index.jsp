<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.3.1.min.js"></script>
<title>Insert title here</title>
<%
int localPort = request.getLocalPort();
%>
<script type="text/javascript">
$(document).ready(function(){
	console.log("hello jquery");
});
         function WebSocketTest()
         {
            if ("WebSocket" in window)
            {
               //alert("WebSocket is supported by your Browser!");
               var hostName=window.location.hostname;
               // Let us open a web socket
               //var ws = new WebSocket("ws://localhost:8080/notificationcount");
               //var port = '<%=localPort%>';
               var port = window.location.port;
			   //var ws = new WebSocket("ws://localhost:"+port+"/websocket/notificationcount");
			   var ws = new WebSocket("ws://"+hostName+":"+port+"/websocket/notificationcount");
			   //var ws = new WebSocket("ws://localhost:7001/jda/websocket/notificationcount?LaunchParm=Initialize");
				
               ws.onopen = function()
               {
                  // Web Socket is connected, send data using send()
                  ws.send("System");
                  //alert("Message is sent...");
               };
				
               ws.onmessage = function (evt) 
               { 
                  var received_msg = evt.data;
                  //alert("Message is received..."+received_msg);
                  document.getElementById("sse").innerHTML="Message count:"+received_msg;
               };
				
               ws.onclose = function()
               { 
                  // websocket is closed.
                  console.log("Connection is closed..."); 
               };
					
               window.onbeforeunload = function(event) {
            	   ws.close();
               };
            }
            
            else
            {
               // The browser doesn't support WebSocket
               alert("WebSocket NOT supported by your Browser!");
            }
         }
      </script>
</head>
<body onload="javascript:WebSocketTest()">
	 <div id="sse">
         <!-- <a href="javascript:WebSocketTest()">Run WebSocket</a> -->
        
      </div>
</body>
</html>