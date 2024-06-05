const checkTelObj = {
    "tel" : false,
    "telAuth" : false
};

// 필요하면 Member 쪽  findId.js 참고

// 전화번호 입력 input
const inputTel = document.getElementById('inputTel');

// 전화번호 유효성 span
const telMsg = document.getElementById('telMsg');

// 전화번호 인증 유효성 span
const telAuthMsg = document.getElementById('telAuthMsg');

// SMS 인증요청 버튼
const telAuthBtn = document.getElementById('telAuthBtn');

// SMS 인증키 입력(선 히든)
const telAuthHidden = document.getElementById('telAuthHidden');

// 인증 코드 입력 input
const telAuthInput = document.getElementById('telAuthInput');

// 아이디 찾기 버튼
const findIdBtn = document.getElementById('findIdBtn');

// 정규식: 전화번호
const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

// SMS 인증 코드
let smsAuthKey = "";

// submit 버튼의 disabled 상태를 토글하는 함수
function disabledCheckButton() {
    if (checkTelObj.tel && telAuthInput.value.trim().length == 6) {
        findIdBtn.disabled = false;
    } else {
        findIdBtn.disabled = true;
    }
}


// (전화번호) 포커스 시 공백
inputTel.addEventListener('focus', () => {
    if(inputTel.value.trim().length === 0) {
        telMsg.innerText = '전화번호를 입력해 주세요.';
        telMsg.classList.add('errorC');
        inputTel.classList.add('errorB');
    }
});

// (전화번호) 입력 시
inputTel.addEventListener('input', e => {

    const inputTelValue = e.target.value;

    // 공백 시
    if(inputTelValue.trim().length === 0) {
        telMsg.innerText = '전화번호를 입력해 주세요.';
        telMsg.classList.add('errorC');
        inputTel.classList.add('errorB');
        checkTelObj.tel = false;
        return;
    } 
    
    // 유효성 미충족 시
    if (!regExp.test(inputTelValue)) {

        telMsg.innerText = '유효한 전화번호를 입력해 주세요.';
        telMsg.classList.add('errorC');
        inputTel.classList.add('errorB');
        checkTelObj.tel = false;

        return;
    }

    // 유효성 충족 시
    telMsg.innerText = "";
    telMsg.classList.remove('errorC');
    inputTel.classList.remove('errorB');
    checkTelObj.tel = true;
    

    // (전화번호) 비동기로 중복 검사 체크


    /////////////////


    disabledCheckButton();
});





// SMS 인증 요청 클릭 시
telAuthBtn.addEventListener('click', () => {

    // 이미 인증 입력 시도 중이였다면
    if(!telAuthHidden.classList.contains('hidden')) {

        alert('이미 인증 요청하셨습니다. 다시 입력 후 시도해 주세요.')

        checkTelObj.tel = false;
        checkTelObj.telAuth = false;
        telAuthInput.value = "";
        inputTel.value = "";
        telAuthHidden.classList.add('hidden');

        return;
    }

    if (checkTelObj.tel) {
        telAuthHidden.classList.remove('hidden');
        // 여기에 실제 SMS 인증 요청 비동기 코드를 추가 예정
        // inputTel이 실제로 존재하지 않으면 return 구문 작성 필요

        smsAuthKey = '123456'; // 예시 설정

        checkTelObj.telAuth = false;
        telAuthInput.focus();
        disabledCheckButton();

    } else {
        alert('유효한 전화번호를 입력해 주세요.');
        inputTel.value = "";
        checkTelObj.tel = false;
        disabledCheckButton();
        return;
    }
});

// (인증번호 입력란) 포커스 시 공백
telAuthInput.addEventListener('focus', () => {
    if(telAuthInput.value.trim().length === 0) {
        telAuthMsg.innerText = '전화번호를 입력해 주세요.';
        telAuthMsg.classList.add('errorC');
        telAuthInput.classList.add('errorB');
    }
});


// 인증 코드 입력란의 값이 변경될 때
telAuthInput.addEventListener('input', e => {
    const inputAuthKey = e.target.value;

    if (inputAuthKey.trim().length === 0) {
        telAuthMsg.innerText = '인증번호를 입력해 주세요.';
        telAuthMsg.classList.add('errorC');
        telAuthInput.classList.add('errorB');
        checkTelObj.telAuth = false;
    } else if (inputAuthKey.trim().length < 6 ) {
        telAuthMsg.innerText = "";
        telAuthMsg.classList.remove('errorC');
        checkTelObj.telAuth = false;
    } else {
        telAuthMsg.innerText = "";
        telAuthMsg.classList.remove('errorC');
        telAuthInput.classList.remove('errorB');


    }

    disabledCheckButton();
});


// 폼 제출 시 유효성 검사
const findIdForm = document.getElementById('findIdForm');
findIdForm.addEventListener('submit', (e) => {


    if(smsAuthKey == telAuthInput.value) {
        checkTelObj.telAuth = true;
    }

    if (!checkTelObj.tel || !checkTelObj.telAuth) {
        e.preventDefault();
        if (!checkTelObj.tel) {
            alert('유효한 전화번호를 입력해 주세요.');
            return;
        }

        if (!checkTelObj.telAuth) {
            alert('인증번호를 다시 확인해 주세요.');
            return;
        }
    }

});






