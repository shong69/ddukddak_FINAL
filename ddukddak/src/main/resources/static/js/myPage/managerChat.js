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
		'사이트 사용 문제':7,
		'문의 종료':0
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
	}, 2000);
	
	scrollBtm();
}


//주문, 결제 정보
function choose2(event){

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
	p.innerText="원하시는 주문 번호를 입력해주세요.";
	div.append(div1, p);
	const span = document.createElement("span");
	span.classList.add("chatDate");
	span.innerText = getCurrentTimeAMPM();
	div.append(span);
	li.append(img, div);
	chattingContent.append(li);

	document.getElementById('inputChatting').removeAttribute('disabled');


	//주문번호 입력하는 경우
	document.querySelector("#send").addEventListener("click", async() => {
		const value = document.querySelector("#inputChatting").value;
		const obj = {
			"value" : value,
		}

		/* 입력값 비동기로 보내기 */
		const resp =await fetch("/myChat/orderInfo", {
		method: "POST",
		headers: {"Content-Type": "application/json"},
		body: JSON.stringify(obj)
		})
		const result = await resp.json();
		if(result && result.length > 0){
			console.log(result);

			//주문정보 출력
			
			let orderText = `회원님이 ${result[0].orderDate}에 주문하신 상품은 다음과 같습니다\n`;

			result.forEach(orderInfo => {
				orderText += `${orderInfo.productName}[${orderInfo.optionValue}] ${orderInfo.orderQuantity}개 \n-> ${orderInfo.orderStatus}\n`;
			});

			const li = document.createElement("li");
			li.classList.add("target-chat");

			const img = document.createElement("img");
			img.src = "/images/default/main.jpg";

			const div = document.createElement("div");
			const div1 = document.createElement("div");
			div1.classList.add("target-name");
			div1.innerText = "뚝딱봇";

			const p = document.createElement("p");
			p.classList.add("chat");
			p.innerText = orderText;

			div.append(div1, p);
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = getCurrentTimeAMPM();
			div.append(span);
			li.append(img, div);
			chattingContent.append(li);



				// 1초 후에 다시 함수 실행
			setTimeout(() => {
				customerChatting();
			}, 2000);
			
			const parentTd = event.target.parentElement;
			console.log(parentTd.children);
			Array.from(parentTd.children).forEach(element =>{
				console.log(element);
				if(element.tagName ==='BUTTON'){
					element.disabled = true;
					element.classList.remove("c-chat");
				}
			})


			scrollBtm();
		}
		else{
			//조회 실패
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
			p.innerText="조회에 실패하였습니다.\n다시 입력해주세요.";
			div.append(div1, p);
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = getCurrentTimeAMPM();
			div.append(span);
			li.append(img, div);
			chattingContent.append(li);

			document.querySelector("#inputChatting").value="";
			
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
			}, 2000);
			
			scrollBtm();
				} 
	});

	document.getElementById('inputChatting').setAttribute(disabled);
	scrollBtm();
}

//적립금 및 포인트
function choose3(event){
	
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
	p.innerText=`회원님께서 구매하신 총 금액의 1%가 포인트로 쌓이게 됩니다.\n회원님이 현재 보유하신 포인트 금액은 ${memberPoint}pt 입니다`;
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
	}, 2000);
	
	scrollBtm();
}

//게시글 작성 및 관리
function choose4(event){
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
	p.innerText="뚝딱뚝딱에서는\n집들이 게시판과 노하우 게시판에 게시글을 작성할 수 있습니다.";
	const p1 = document.createElement("p");
	p1.classList.add("chat");
	p1.innerText="집들이 게시판에서는 \n회원님의 집 내부와 인테리어를 뽐내주세요!\n마찬가지로 노하우 게시판에서도 \n회원님이 가진 특별한 집 꾸미기 지식을 뽐내주시면 됩니다~";
	div.append(div1, p,p1);
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
	}, 2000);
	
	scrollBtm();
}

//전문가 상담
function choose5(event){
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
	p.innerText="포트폴리오 게시판에서는 뚝딱뚝딱과 함께하고 있는 시공사, 인테리어 회사들을 둘러 볼 수 있습니다.";
	const p1 = document.createElement("p");
	p1.classList.add("chat");
	p1.innerText="포트폴리오를 살펴보시고 전문가에게 채팅해주세요! 빠르게 답변해드릴게요 :) ";
	div.append(div1, p,p1);
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
	}, 2000);
	
	scrollBtm();
}

//3d 홈디자인 기능
function choose6(event){
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
	p.innerText="뚝딱뚝딱에서는 3d 기능을 사용하여\n내 방 꾸미기를 미리 해 볼 수 있습니다!";
	const p1 = document.createElement("p");
	p1.classList.add("chat");
	p1.innerText="원하는 요소를 배치하여 마음대로 꾸며보세요!\n결과물을 다운받아 시공사에게 컨택할 수 있습니다";
	div.append(div1, p, p1);
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
	}, 2000);
	
	scrollBtm();
}


//사이트 사용 문제
function choose7(){
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
	p.innerText="뚝딱뚝딱 사이트를 이용하며 불편하신 점은 \n아래 메일 주소로 연락 부탁드립니다.";
	const p1 = document.createElement("p");
	p1.classList.add("chat");
	p1.innerText="대표 이메일 : saem.hong.95@gmail.com";
	div.append(div1, p,p1);
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
	}, 2000);
	
	scrollBtm();
}

function choose0(event){
	
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
	p.innerText="문의 종료를 원하신다면 상단 우측의 '문의 종료' 버튼을 클릭해주세요";
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
	}, 2000);
	
	scrollBtm();
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

//주문 날짜 리턴
function formatDate(dateString) {
    const date = new Date(dateString);
    const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
    return date.toLocaleDateString('ko-KR', options);
}
//주문 상품 + 옵션 리턴
function formatProductList(products) {
    return products.map(product => `${product.name}[${product.option}] ${product.quantity}개`).join(', ');
}