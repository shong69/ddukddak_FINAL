// 필요하면 Member 쪽  findId.js 참고

// 전화번호 입력 input
const inputTel = document.getElementById('inputTel');

const telMsg = document.getElementById('telMsg');

// SMS 인증요청 버튼
const telAuthBtn = document.getElementById('telAuthBtn');

// SMS 인증키 입력(선 히든)
const telAuthHidden = document.getElementById('telAuthHidden');

// ********* SMS 인증 요청 클릭 시 *********
if(telAuthHidden.classList.contains('hidden')) {
    telAuthBtn.addEventListener('click', () => {
        telAuthHidden.classList.remove('hidden');
    })
    
}   


inputTel.addEventListener('focus', () => {
    if(inputTel.value.trim().length === 0) {
        telMsg.innerText = '전화번호를 입력해 주세요.'
    }

})

