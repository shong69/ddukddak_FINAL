const addTarget = document.querySelector("#addTarget"); // 추가 버튼

const addTargetPopupLayer = document.querySelector("#addTargetPopupLayer"); // 팝업 레이어

const closeBtn = document.querySelector("#closeBtn"); // 닫기 버튼

const inputCloseBtn = document.querySelector("#inputCloseBtn"); //input값 삭제 버튼

const targetInput = document.querySelector("#targetInput"); // 사용자 검색

const resultArea = document.querySelector("#resultArea"); // 검색 결과

const chattingContent = document.querySelector(".chatting-content"); // 채팅방 영역

const prevMessage = document.querySelector(".prev-message"); // 채팅방 선택 전 메세지


let selectChattingNo; // 선택한 채팅방 번호
let selectTargetNo; // 현재 채팅 대상
let selectTargetName; // 대상의 이름
let selectTargetProfile; // 대상의 프로필


// 검색 팝업 레이어 열기
addTarget.addEventListener("click", e => {
	addTargetPopupLayer.classList.toggle("popup-layer-close");
	targetInput.focus();
});

// 검색 팝업 레이어  닫기
closeBtn.addEventListener("click", e => {
	addTargetPopupLayer.classList.toggle("popup-layer-close");
	targetInput.value = "";
	resultArea.innerHTML = "";
});

//검색 팝업의 인풋 값 삭제
inputCloseBtn.addEventListener("click", ()=>{
	targetInput.value="";
});


// 사용자 검색(ajax)
targetInput.addEventListener("input", e => {

	const query = e.target.value.trim();

	// 입력된게 없을 때
	if(query.length == 0){
		resultArea.innerHTML = ""; // 이전 검색 결과 비우기
		return;
	}


	// 입력된게 있을 때
	if(query.length > 0){
		fetch("/chatting/I-selectTarget?query="+query)
		.then(resp => resp.json())
		.then(list => {
			console.log(list);

			resultArea.innerHTML = ""; // 이전 검색 결과 비우기

			if(list.length == 0){
				const li = document.createElement("li");
				li.classList.add("result-row");
				li.innerText = "일치하는 회원이 없습니다";
				resultArea.append(li);
			}

			for(let member of list){
				// li요소 생성(한 행을 감싸는 요소)
				const li = document.createElement("li");
				li.classList.add("result-row");
				li.setAttribute("data-id", member.memberNo);

				// 프로필 이미지 요소
				const img = document.createElement("img");
				img.classList.add("result-row-img");
				
                if(member.profileImg == null){
					img.setAttribute("src", "/images/default/main.jpg");
				}else{
					img.setAttribute("src", member.profileImg);
				}
				

				let nickname = member.memberNickname;
				let email = member.memberEmail;

				const div = document.createElement("div");
				div.classList.add("interInfo");
				const nicknameSpan = document.createElement("span");
				nicknameSpan.classList.add("nicknameSpan");
				const emailSpan = document.createElement("span");
				emailSpan.classList.add("emailSpan");
				/*span.innerHTML = `${nickname} ${email}`.replace(query, `<mark>${query}</mark>`);*/
				nicknameSpan.innerHTML = nickname.replace(query, `<mark>${query}</mark>`);
				emailSpan.innerHTML = email.replace(query, `<mark>${query}</mark>`);
				
				div.append(nicknameSpan, emailSpan);

				// 요소 조립(화면에 추가)
				li.append(img, div);
				resultArea.append(li);

				// li요소에 클릭 시 채팅방에 입장하는 이벤트 추가
				li.addEventListener('click', chattingEnter);
			}

		})
		.catch(err => console.log(err) );
	}
});


// 채팅방 입장 또는 선택 함수
function chattingEnter(e){

	console.log(e.target); // 실제 클릭된 요소
	console.log(e.currentTarget); // 이벤트 리스트가 설정된 요소

	const targetNo = e.currentTarget.getAttribute("data-id");
	console.log(targetNo);
	fetch("/chatting/I-enter?targetNo="+targetNo)
	.then(resp => resp.text())
	.then(chattingNo => {
		console.log(chattingNo); 
		
		selectRoomList(); // 채팅방 목록 조회
		
		setTimeout(()=>{ 
			const itemList = document.querySelectorAll(".chatting-item")
			for(let item of itemList) {		
				if(item.getAttribute("chat-no") == chattingNo){
					item.focus();
					item.click();
					addTargetPopupLayer.classList.toggle("popup-layer-close");
					targetInput.value = "";
					resultArea.innerHTML = "";
					return;
				}
			}

		}, 200);

	})
	.catch(err => console.log(err));
}



// 비동기로 채팅방 목록 조회
function selectRoomList(){
	fetch("/chatting/roomList")
	.then(resp => resp.json())
	.then(roomList => {
		console.log(roomList);

		// 채팅방 목록 출력 영역 선택
		const chattingList = document.querySelector(".chatting-list");

		// 채팅방 목록 지우기
		chattingList.innerHTML = "";

		// 시간순으로 정렬 (최근 시간이 위로 오도록 내림차순 정렬)
		roomList.sort((a, b) => new Date(b.sendTime) - new Date(a.sendTime));
				
		// 조회한 채팅방 목록을 화면에 추가
		for(let room of roomList){
			const li = document.createElement("li");
			li.classList.add("chatting-item");
			li.setAttribute("chat-no", room.chattingNo);
			li.setAttribute("target-no", room.targetNo);

			if(room.chattingNo == selectChattingNo){
				li.classList.add("select");
			}

			// item-header 부분
			const itemHeader = document.createElement("div");
			itemHeader.classList.add("item-header");

			const listProfile = document.createElement("img");
			listProfile.classList.add("list-profile");

			if(room.targetProfile == null){
				listProfile.setAttribute("src", "/images/default/main.jpg");
			}else{
				listProfile.setAttribute("src", room.targetProfile);
			}
			

			itemHeader.append(listProfile);

			// item-body 부분
			const itemBody = document.createElement("div");
			itemBody.classList.add("item-body");

			const p = document.createElement("p");

			const targetName = document.createElement("span");
			targetName.classList.add("target-name");
			targetName.innerText = room.targetNickName;
			
			const recentSendTime = document.createElement("span");
			recentSendTime.classList.add("recent-send-time");
			recentSendTime.innerText = room.sendTime;
			
			
			p.append(targetName, recentSendTime);
			
			
			const div = document.createElement("div");
			
			const recentMessage = document.createElement("p");
			recentMessage.classList.add("recent-message");

			if(room.lastMessage != undefined){
				recentMessage.innerHTML = room.lastMessage;
			}
			
			div.append(recentMessage);

			itemBody.append(p,div);

			// 현재 채팅방을 보고있는게 아니고 읽지 않은 개수가 0개 이상인 경우 -> 읽지 않은 메세지 개수 출력
			if(room.notReadCount > 0 && room.chattingNo != selectChattingNo ){
			// if(room.chattingNo != selectChattingNo ){
				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);
			}else{

				// // 현재 채팅방을 보고있는 경우
				// // 비동기로 해당 채팅방 글을 읽음으로 표시
				// fetch("/chatting/I-updateReadFlag",{
				// 	method : "PUT",
				// 	headers : {"Content-Type": "application/json"},
				// 	body : JSON.stringify({"chattingNo" : selectChattingNo,
				// 							"memberNo" : loginPartnerNo,
				// 							"senderType" : "MEMBER"})
				// })
				// .then(resp => resp.text())
				// .then(result => console.log(result))
				// .catch(err => console.log(err));

			}
			

			li.append(itemHeader, itemBody);
			chattingList.append(li);
		}

		roomListAddEvent();
	})
	.catch(err => console.log(err));

}






// 채팅 메세지 영역
const display = document.getElementsByClassName("display-chatting")[0];


// 채팅방 목록에 이벤트를 추가하는 함수 
function roomListAddEvent(){
	const chattingItemList = document.getElementsByClassName("chatting-item");
	
	for(let item of chattingItemList){
		item.addEventListener("click", e => {
			console.log(item);
			// 클릭한 채팅방의 번호 얻어오기
			//const id = item.getAttribute("id");
			//const arr = id.split("-");
			// 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
			selectChattingNo = item.getAttribute("chat-no");
			selectTargetNo = item.getAttribute("target-no");

			selectTargetProfile = item.children[0].children[0].getAttribute("src");
			selectTargetName = item.children[1].children[0].children[0].innerText;

			if(item.children[1].children[1].children[1] != undefined){
				item.children[1].children[1].children[1].remove();
			}

	
			// 모든 채팅방에서 select 클래스를 제거
			for(let it of chattingItemList) it.classList.remove("select")
	
			// 현재 클릭한 채팅방에 select 클래스 추가
			item.classList.add("select");
	
			// 비동기로 메세지 목록을 조회하는 함수 호출
			selectChattingFn();
		});
	}
}




// 비동기로 메세지 목록을 조회하는 함수
function selectChattingFn() {

	fetch("/chatting/I-selectMessage?"+`chattingNo=${selectChattingNo}&memberNo=${loginPartnerNo}`)
	.then(resp => resp.json())
	.then(messageList => {
		console.log(messageList);

		prevMessage.classList.add("display-none");
		chattingContent.classList.remove("display-none");

		// <ul class="display-chatting">
		const ul = document.querySelector(".display-chatting");

		ul.innerHTML = ""; // 이전 내용 지우기

		// 메세지 만들어서 출력하기
		for(let msg of messageList){
			console.log(msg);
			//<li>,  <li class="my-chat">
			const li = document.createElement("li");

			// 보낸 시간
			const formattedTime = formatSendTime(msg.sendTime);
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = formattedTime;

			// 메세지 내용
			const p = document.createElement("p");
			p.classList.add("chat");
			p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML

			// 내가 작성한 메세지인 경우 -> senderNo로만 따지면 안됨
			if(loginPartnerNo == msg.senderNo &&
				msg.senderType == 'PARTNER'
			){ 
				li.classList.add("my-chat");
				
				li.append(span, p);
				
			}else{ // 상대가 작성한 메세지인 경우
				li.classList.add("target-chat");

				// 상대 프로필
				// <img src="/resources/images/user.png">
				const img = document.createElement("img");
				img.setAttribute("src", selectTargetProfile);
				
				const div = document.createElement("div");

				// 상대 이름
				const b = document.createElement("b");
				b.classList.add("target-name")
				b.innerText = selectTargetName; // 전역변수

				const br = document.createElement("br");

				div.append(b, br, p, span);
				li.append(img,div);

			}

			ul.append(li);
			display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
		}

	})
	.catch(err => console.log(err));


	// 현재 채팅방을 보고있는 경우
	// 비동기로 해당 채팅방 글을 읽음으로 표시
	fetch("/chatting/I-updateReadFlag",{
		method : "PUT",
		headers : {"Content-Type": "application/json"},
		body : JSON.stringify({"chattingNo" : selectChattingNo,
								"memberNo" : loginPartnerNo,
								"senderType" : "MEMBER"})
	})
	.then(resp => resp.text())
	.then(result => console.log(result))
	.catch(err => console.log(err));

}


// ----------------------------------------------------------------------------------------------------------------

// sockjs를 이용한 WebSocket 구현

// 로그인이 되어 있을 경우에만
// /chattingSock 이라는 요청 주소로 통신할 수 있는  WebSocket 객체 생성
let chattingSock;


if(loginPartnerNo != ""){
	chattingSock = new SockJS("/chattingSock");
}



// 채팅 입력
const send = document.getElementById("send");

const sendMessage = () => {
	const inputChatting = document.getElementById("inputChatting");

	if (inputChatting.value.trim().length == 0) {
		alert("채팅을 입력해주세요.");
		inputChatting.value = "";
	} else {
		var obj = {
			"senderType" : "PARTNER",
			"senderNo": loginPartnerNo,
			"targetType" : "MEMBER",
			"targetNo": selectTargetNo,
			"chattingNo": selectChattingNo,
			"messageContent": inputChatting.value,
		};
		console.log(obj)

		// JSON.stringify() : 자바스크립트 객체를 JSON 문자열로 변환
		chattingSock.send(JSON.stringify(obj));

		inputChatting.value = "";
	}
}

// 엔터 == 제출
// 쉬프트 + 엔터 == 줄바꿈
// inputChatting.addEventListener("keyup", e => {
// 	if(e.key == "Enter"){ 
// 		if (!e.shiftKey) {
// 			sendMessage();
// 		}
// 	}
// })

//전송 시간 형식 바꾸기
function formatSendTime(sendTime) {
    // '2024.06.16 01:30' -> '2024-06-16T01:30'
    const isoDateTime = sendTime.replace(' ', 'T').replace(/\./g, '-');
    const date = new Date(isoDateTime);
    const now = new Date();

    const isToday = date.getFullYear() === now.getFullYear() &&
                    date.getMonth() === now.getMonth() &&
                    date.getDate() === now.getDate();

    if (isToday) {
        return date.toTimeString().split(' ')[0].slice(0, 5); // '01:30'
    } else {
        return sendTime.split(' ')[0].slice(0,10); // '2024.06.16'
    }
}

// WebSocket 객체 chattingSock이 서버로 부터 메세지를 통지 받으면 자동으로 실행될 콜백 함수
chattingSock.onmessage = function(e) {
	// 메소드를 통해 전달받은 객체값을 JSON객체로 변환해서 obj 변수에 저장.
	const msg = JSON.parse(e.data);

	console.log(msg);


	// 현재 채팅방을 보고있는 경우
	if(selectChattingNo == msg.chattingNo){


		const ul = document.querySelector(".display-chatting");
	
		// 메세지 만들어서 출력하기
		//<li>,  <li class="my-chat">
		const li = document.createElement("li");
		const formattedTime = formatSendTime(msg.sendTime);

		// 보낸 시간
		const span = document.createElement("span");
		span.classList.add("chatDate");
		span.innerText = formattedTime;
	
		// 메세지 내용
		const p = document.createElement("p");
		p.classList.add("chat");
		p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML
	
		// 내가 작성한 메세지인 경우
		if(loginPartnerNo == msg.senderNo &&
			msg.senderType == 'PARTNER'
		){ 
			li.classList.add("my-chat");
			
			li.append(span, p);
			
		}else{ // 상대가 작성한 메세지인 경우
			li.classList.add("target-chat");
	
			// 상대 프로필
			// <img src="/resources/images/user.png">
			const img = document.createElement("img");
			img.setAttribute("src", selectTargetProfile);
			
			const div = document.createElement("div");
	
			// 상대 이름
			const b = document.createElement("b");
			b.innerText = selectTargetName; // 전역변수
	
			const br = document.createElement("br");
	
			div.append(b, br, p, span);
			li.append(img,div);
	
		}
	
		ul.append(li)
		display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
	}



	selectRoomList();
}




// 문서 로딩 완료 후 수행할 기능
document.addEventListener("DOMContentLoaded", ()=>{
	
	// 채팅방 목록에 클릭 이벤트 추가
	roomListAddEvent(); 
	selectRoomList();
	// 보내기 버튼에 이벤트 추가
	send.addEventListener("click", sendMessage);
});

