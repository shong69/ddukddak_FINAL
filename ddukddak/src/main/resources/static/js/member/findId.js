const checkEmailObj = {
    "email" : false,
    "emailAuth" : false
};

// =================== 이메일 관련 요소 ===================
const emailChoice = document.getElementById('emailChoice'); // 체크 박스
const emailHiddenDiv = document.getElementById('emailHiddenDiv'); // 이메일 라디오 (히든)
const inputEmail = document.getElementById('inputEmail'); // 이메일 입력 인풋
const emailAuthBtn = document.getElementById('emailAuthBtn'); // 메일 인증 버튼
const emailAuthHidden = document.getElementById('emailAuthHidden'); // 인증 요청 버튼 (히든)
const emailAuthInput = document.getElementById('emailAuthInput'); // 인증문구 입력 인풋



// =================== 휴대폰 관련 요소 ===================
const telChoice = document.getElementById('telChoice');
const telHiddenDiv = document.getElementById('telHiddenDiv');

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


// 인증 방식 선택 시 처리
emailChoice.addEventListener('change', () => {
    if (emailChoice.checked) {
        telHiddenDiv.classList.add('hidden');
        emailHiddenDiv.classList.remove('hidden');
        inputEmail.setAttribute('name', 'memberEmail');
        emailAuthInput.setAttribute('name', 'authKey');
        inputTel.removeAttribute('name');
        telAuthInput.removeAttribute('name');
    }
});

telChoice.addEventListener('change', () => {
    if (telChoice.checked) {
        emailHiddenDiv.classList.add('hidden');
        telHiddenDiv.classList.remove('hidden');
        inputTel.setAttribute('name', 'memberTel');
        telAuthInput.setAttribute('name', 'smsAuthKey');
        inputEmail.removeAttribute('name');
        emailAuthInput.removeAttribute('name');
    }
});


// ****************** 이메일 ******************









// ****************** SMS *****************

const checkTelObj = {
    "tel" : false,
    "telAuth" : false
};

// 필요하면 Member 쪽  findId.js 참고

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
    if (checkTelObj.tel) {
        telAuthHidden.classList.remove('hidden');
        // 여기에 실제 SMS 인증 요청 비동기 코드를 추가 예정
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












































