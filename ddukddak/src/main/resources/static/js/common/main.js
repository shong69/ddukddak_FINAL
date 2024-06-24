

const memberTel = document.getElementById('memberTel');

if (memberTel != null) {

    if(memberTel.value == '') {
        if(confirm(`휴대폰 번호 미등록 시 사이트 이용에 제한이 있을 수 있습니다.\n휴대폰 등록 페이지로 이동하시겠습니까?`)) {
            location.href = '/myPage/memberInfo';
        }
    }
}

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





if (chattingInput != null) {
    chattingInput.addEventListener("keypress", (e) => {
        if(e.key === 'Enter') {
            if(chattingInput.value.trim().length === 0) {
                alert("채팅을 입력해 주세요");
            } else {
                makeChatting2();
                chattingInput.value = "";
            }
        }
    
    });
    
}



function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
}

/* 탑버튼 */
    
const topButton = document.querySelector("#top");
    
topButton.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    })
});
