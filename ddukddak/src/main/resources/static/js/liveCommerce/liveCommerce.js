
// 채팅
const chatting = document.querySelector("#chatting");
const chattingInput = document.querySelector("#chattingInput");
const chattingButton = document.querySelector("#chattingButton");

const chattingMessage = [
    "그림이 이뻐요",
    "이거 언제까지 하나요",
    "뚝딱뚝딱 화이팅",
    "Im tired",
    "달디달고달디달고달디단",
    "밤양갱",
    "갱갱갱",
    "꿔바로우 먹고싶다",
    "탕탕 후루후루",
    "아 졸려",
    "가위 바위 보",
    "가위",
    "보",
    "끝말잇기 할사람"
]

const chattingUser = [
    "user01",
    "user02",
    "user03",
    "user04",
    "asfeafeafafea",
    "mama12345",
    "tangfuru",
    "maratang",
    "soyoung",
    "seungjoo",
    "soomin",
    "sam",
    "youngmin"
]

function makeChatting1() {
    if (chattingUser != null && chattingMessage != null && chatting != null) {
        const box = document.createElement("div");
            box.classList.add("chattingBox");
    
            const user = document.createElement("div");
            user.classList.add("chattingUser");
            user.innerText = "@" + chattingUser[Math.floor(Math.random()*chattingUser.length)];
    
            const underBox = document.createElement("div");
            underBox.classList.add("chattingUnder");
    
            const chattingmessage = document.createElement("div");
            chattingmessage.innerText = chattingMessage[Math.floor(Math.random()*chattingMessage.length)];
            chattingmessage.classList.add("chattingmessage");
            
            const today = new Date();
            const hour = today.getHours();
            const min = today.getMinutes();
            const sec = today.getSeconds();
    
            const chattingTime = `${hour}:${min}:${sec}`;
    
            const timeBox = document.createElement("div");
            timeBox.innerText = chattingTime;
            timeBox.classList.add("chattingTime");
    
            underBox.append(chattingmessage);
            underBox.append(timeBox);
            box.append(user);
            box.append(underBox);
            chatting.append(box);
    
            const scrollHeight = chatting.scrollHeight;
    
            chatting.scrollTop = scrollHeight;
    }
}

function makeChatting2() {
    const box = document.createElement("div");
        box.classList.add("chattingBox2");

        const user = document.createElement("div");
        user.classList.add("chattingUser");
        user.innerText = "@" + chattingUser[Math.floor(Math.random()*chattingUser.length)];

        const underBox = document.createElement("div");
        underBox.classList.add("chattingUnder");

        const chattingmessage = document.createElement("div");
        chattingmessage.innerText = chattingInput.value;
        chattingmessage.classList.add("chattingmessage");
        
        const today = new Date();
        const hour = today.getHours();
        const min = today.getMinutes();
        const sec = today.getSeconds();

        const chattingTime = `${hour}:${min}:${sec}`;

        const timeBox = document.createElement("div");
        timeBox.innerText = chattingTime;
        timeBox.classList.add("chattingTime");

        underBox.append(timeBox);
        underBox.append(chattingmessage);
        box.append(user);
        box.append(underBox);
        chatting.append(box);

        const scrollHeight = chatting.scrollHeight;

        chatting.scrollTop = scrollHeight;
}
let intervalId;

function startInterval() {
    if (chattingUser != null && chattingMessage != null) { 
        intervalId = setInterval(function() {
            makeChatting1();
        }, 2000);
    }
}
function stopInterval() {
    clearInterval(intervalId);
}

window.onload = function() {
    startInterval();
    setTimeout(stopInterval, 600000);
};


$(document).ready(function(){
    $('.slider').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        infinite: true,
        autoplay: true,
        autoplaySpeed: 2000,
        prevArrow: '<button class="slick-prev" aria-label="Previous" type="button">←</button>',
        nextArrow: '<button class="slick-next" aria-label="Next" type="button">→</button>',
    });
});

