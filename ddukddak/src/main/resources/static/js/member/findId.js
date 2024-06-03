// 이메일 인증 radio
const emailChoice = document.getElementById('emailChoice');
const emailHiddenDiv = document.getElementById('emailHiddenDiv');

// 이메일 인증요청 버튼
const emailAuthBtn = document.getElementById('emailAuthBtn');

// 이메일 인증키 입력(선 히든)
const emailAuthHidden = document.getElementById('emailAuthHidden');



// 전화 번호 인증 radio
const telChoice = document.getElementById('telChoice');
const telHiddenDiv = document.getElementById('telHiddenDiv');

// SMS 인증요청 버튼
const telAuthBtn = document.getElementById('telAuthBtn');

// SMS 인증키 입력(선 히든)
const telAuthHidden = document.getElementById('telAuthHidden');

// 초기 상태 설정
function setInitialState() {
    if (emailChoice.checked) {
        telHiddenDiv.classList.add('hidden');
        emailHiddenDiv.classList.remove('hidden');
    } else if (telChoice.checked) {
        emailHiddenDiv.classList.add('hidden');
        telHiddenDiv.classList.remove('hidden');
    }
}

// 라디오 버튼 상태 변경 시 처리
emailChoice.addEventListener('change', function() {
    if (emailChoice.checked) {
        telHiddenDiv.classList.add('hidden');
        emailHiddenDiv.classList.remove('hidden');
    }
});

telChoice.addEventListener('change', function() {
    if (telChoice.checked) {
        emailHiddenDiv.classList.add('hidden');
        telHiddenDiv.classList.remove('hidden');
    }
});


// ********* 이메일 인증 요청 클릭 시 *********


if(emailAuthHidden.classList.contains('hidden')) {
    emailAuthBtn.addEventListener('click', () => {
        emailAuthHidden.classList.remove('hidden');
    })
}

// 이메일 인증 번호 입력
const emailAuthKey = document.getElementById('emailAuthKey');

emailAuthKey.addEventListener('focus', () => {
    if (emailAuthKey.value.trim().length === 0) {
        emailAuthKey.classList.add('error');
    }
});

emailAuthKey.addEventListener('input', () => {
    if (emailAuthKey.value.trim().length === 0) {
        emailAuthKey.classList.add('error');
    } else {
        emailAuthKey.classList.remove('error');
    }
});





// ********* SMS 인증 요청 클릭 시 *********

if(telAuthHidden.classList.contains('hidden')) {
    telAuthBtn.addEventListener('click', () => {
        telAuthHidden.classList.remove('hidden');
    })
    
}   



// 페이지 로드 시 초기 상태 설정
setInitialState();


