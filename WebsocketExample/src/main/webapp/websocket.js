/**
 * 
 */
window.onload = init;
var socket = new WebSocket("ws://localhost:8080/WebsocketExample/actions");
socket.onmessage = onMessage;

function onMessage(event) {
	var link = JSON.parse(event.data);
	if (link.action === "add") {
		console.log(link);
		printLinkElement(link);
	}
}
function showForm() {
	document.getElementById("addLinkForm").style.display = '';
}
function hideForm() {
	document.getElementById("addLinkForm").style.display = "none";
}

function formSubmit() {
	var form = document.getElementById("addLinkForm");
	var name = form.elements["link_name"].value;
	hideForm();
	document.getElementById("addLinkForm").reset();
	addLink(name);
}

function addLink(name) {
	var LinkAction = {
		action : "add",
		name : name
	};
	socket.send(JSON.stringify(LinkAction));
}
function init() {
	hideForm();
}
function printLinkElement(link) {
	var content = document.getElementById("content");
	varlinkDiv = document.createElement("div");
	content.appendChild(varlinkDiv);

	console.log(link);
	var linkName = document.createElement("span");
	linkName.innerHTML = link.link;
	varlinkDiv.appendChild(linkName);

	console.log(varlinkDiv);
}