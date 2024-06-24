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
async function customerChatting () {
	//1. 문의 카테고리 불러오기->선택
	const categorys = [
		'제품정보',
		'배송 및 반품',
		'주문 및 결제',
		'적립금 및 포인트',
		'게시글 작성 및 관리',
		'댓글 및 좋아요',
		'전문가 상담',
		'매칭 과정',
		'사이트 사용 문제',
		'3d홈디자인 기능'
	];
	const li = document.createElement("li");
	li.classList.add("target-chat");

	const img = document.createElement("img");
	img.src = "/images/default/main.jpg";

	const div = document.createElement("div");
	const div1= document.createElement("div");
	div1.classList.add("target-name");
	div1.innerText = "뚝딱봇";

	/* 처음 선택값 보관배열 */
	let firstValue = "";

	const div2 = document.createElement("div");
	div2.classList.add("chat");
	div2.innerText="아래 항목 중 원하시는 문의 종류를 선택해 주세요.";
	div.append(div1, div2);
	categorys.forEach(category=>{
		const p =document.createElement("button");
		p.setAttribute("type","button");
		p.classList.add("b-chat");
		p.classList.add("c-chat")
		p.innerText = category;
		div.append(p);


		p.addEventListener("click", e => {
			firstValue = p.innerText;

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
			div2.innerText=`${firstValue}에 대한 문의 종류에 해당하는 질문을 작성해주세요.`;

			div.append(div1, div2);
			li.append(img, div);
			chattingContent.append(li);

			/* input disable 풀기 */
			document.getElementById('inputChatting').removeAttribute('disabled');
		})
	});

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

	const span = document.createElement("span");
	span.classList.add("chatDate");

	span.innerText = getCurrentTimeAMPM();
	div.append(span);

	li.append(img, div);
	chattingContent.append(li);
	//2.  문의하기

	//답변하기
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

// 예시: 콘솔에 현재 시각을 출력합니다.
console.log(getCurrentTimeAMPM()); // 예상 출력: "7:43 AM"
