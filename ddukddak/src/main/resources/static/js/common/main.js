const topButton = document.querySelector("#top");

topButton.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    })
});

const chatting = document.querySelector("#chatting");

function startInterval() {
    intervalId = setInterval(function() {
        let chattingBox = document.createElement("div");
            chattingBox.innerText = "test";
            chattingBox.classList.add("chattingBox");
            chatting.append(chattingBox);
    }, 2000);
}
function stopInterval() {
    clearInterval(intervalId);
}

window.onload = function() {
    startInterval();
    setTimeout(stopInterval, 30000);
};
