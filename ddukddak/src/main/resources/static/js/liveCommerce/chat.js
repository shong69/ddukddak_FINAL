const socket = new SockJS("/lcSock");

socket.addEventListener("message", e => {
    const msg = JSON.parse(e.data);
    const messagesDiv = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.textContent = `${msg.name}: ${msg.str}`;
    messagesDiv.appendChild(messageElement);
});

function sendMessage() {
    const name = document.getElementById('nameInput').value;
    const str = document.getElementById('messageInput').value;

    const obj = {
        "name": name,
        "str": str
    };

    socket.send(JSON.stringify(obj));
}