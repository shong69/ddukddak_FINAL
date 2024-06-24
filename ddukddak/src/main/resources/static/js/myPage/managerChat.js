/****************문의종료****************/
function finishChat(){
	alert("상담이 종료됩니다");
	window.close();
}

/************************상담대상선택*************************/
const customerBtn = document.querySelector("#customer");
const partnerBtn = document.querySelector("#partner");
function customerChat(){
	partnerBtn.classList.add("display-none");
	customerBtn.disabled = true;
	customerBtn.classList.remove("c-chat"); //선택 액션 없애기

	//해당하는 문의 카테고리 불러오기
	customerChatting();
}

function partnerChat(){
	customerBtn.classList.add("display-none");
	partnerBtn.disabled =true;
	partnerBtn.classList.remove("c-chat");

	//해당하는 문의 카테고리 불러오기
	partnerChatting();
}

/***********************상담 카테고리선택***********************/

const chattingContent = document.querySelector(".display-chatting");
function customerChatting () {
	//1. 문의 카테고리 불러오기->선택
	const categorys = {
		'제품정보':1,
		'주문 및 결제':2,
		'적립금 및 포인트':3,
		'게시글 작성 및 관리':4,
		'전문가 상담':5,
		'3d홈디자인 기능':6,
		'사이트 사용 문제':7
	}


	const li = document.createElement("li");
	li.classList.add("target-chat");
	

	const img = document.createElement("img");
	img.src = "/images/default/main.jpg";

	const div = document.createElement("div");
	const div1= document.createElement("div");
	div1.classList.add("target-name");
	div1.innerText = "뚝딱봇";

	const div2 = document.createElement("div");
	div2.classList.add("chat");
	div2.innerText="원하시는 문의 종류를 선택해 주세요.";
	div.append(div1, div2);
	Object.entries(categorys).forEach(([key, value])=>{
		const p =document.createElement("button");
		p.setAttribute("type","button");
		p.classList.add("b-chat");
		p.classList.add("c-chat");
		p.innerText = key;
		const functionName = `choose${value}`;
		console.log(`choose${value}`);
		p.addEventListener("click",function(event){
			window[functionName](event);

		})
		div.append(p);
		
	})
	const span = document.createElement("span");
	span.classList.add("chatDate");
	span.innerText = getCurrentTimeAMPM();

	div.append(span);
	li.append(img, div);
	chattingContent.append(li);

	scrollBtm();
}

//제품 정보
function choose1(event){

	const li = document.createElement("li");
	li.classList.add("target-chat");

	const img = document.createElement("img");
	img.src = "/images/default/main.jpg";

	const div = document.createElement("div");
	const div1= document.createElement("div");
	div1.classList.add("target-name");
	div1.innerText = "뚝딱봇";

	const p = document.createElement("p");
	p.classList.add("chat");
	p.innerText="제품 정보는\n해당 제품의 상세 페이지에서 열람 가능합니다.\n자세한 문의는 제품 페이지 내의 문의 내역에 질문을 남겨주세요.";
	div.append(div1, p);
	const span = document.createElement("span");
	span.classList.add("chatDate");
	span.innerText = getCurrentTimeAMPM();
	div.append(span);
	li.append(img, div);
	chattingContent.append(li);

	const parentTd = event.target.parentElement;
	console.log(parentTd.children);
	Array.from(parentTd.children).forEach(element =>{
		console.log(element);
		if(element.tagName ==='BUTTON'){
			element.disabled = true;
			element.classList.remove("c-chat");
		}
	})
	// 1초 후에 다시 함수 실행
	setTimeout(() => {
		customerChatting();
	}, 2500);
	
	scrollBtm();
}

//주문, 결제 정보
function choose2(){

	const li = document.createElement("li");
	li.classList.add("target-chat");

	const img = document.createElement("img");
	img.src = "/images/default/main.jpg";

	const div = document.createElement("div");
	const div1= document.createElement("div");
	div1.classList.add("target-name");
	div1.innerText = "뚝딱봇";

	const p = document.createElement("p");
	p.classList.add("chat");
	p.innerText="";
	div.append(div1, p);
	const span = document.createElement("span");
	span.classList.add("chatDate");
	span.innerText = getCurrentTimeAMPM();
	div.append(span);
	li.append(img, div);
	chattingContent.append(li);

	document.getElementById('inputChatting').removeAttribute('disabled');


	//주문번호 입력하는 경우
	document.querySelector("#send").addEventListener("click", () => {
		const obj = {
			"category" : firstValue,
			"inquiry" : document.getElementById('inputChatting').value
		}
		
			/* 입력값 비동기로 보내기 */
			fetch("/userChat/recommend", {
			method: "POST",
			headers: {"Content-Type": "application/json"},
			body: JSON.stringify(obj)
			})
			.then(resp => resp.text())
			.then(result => {
				console.log(result);
			});
	})

	scrollBtm();
}

//주문 및 결제
function choose3(){

}

//적립금 및 포인트
function choose4(){

}

//3d 홈디자인 기능
function choose5(){

}


//사이트 사용 문제
function choose6(){

}










function getCurrentTimeAMPM() {
    const now = new Date();
    let hours = now.getHours();
    const minutes = now.getMinutes();
    let ampm = hours >= 12 ? 'PM' : 'AM';

    // 12시간 형식으로 변환
    hours = hours % 12;
    hours = hours ? hours : 12; // 0시는 12시로 표시

    // 분이 한 자릿수인 경우 앞에 0을 붙입니다.
    const formattedMinutes = minutes < 10 ? '0' + minutes : minutes;

    // 시간과 분을 합쳐서 반환
    const currentTime = hours + ':' + formattedMinutes + ' ' + ampm;
    return currentTime;
}
function scrollBtm(){
	chattingContent.scrollTop = chattingContent.scrollHeight - chattingContent.clientHeight;

}