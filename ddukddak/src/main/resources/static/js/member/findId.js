// 이메일 인증 radio
const emailChoice = document.getElementById('emailChoice');
const emailHiddenDiv = document.getElementById('emailHiddenDiv');

// 전화 번호 인증 radio
const telChoice = document.getElementById('telChoice');
const telHiddenDiv = document.getElementById('telHiddenDiv');


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

// 페이지 로드 시 초기 상태 설정
setInitialState();

