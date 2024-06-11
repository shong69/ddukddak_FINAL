const checkTelObj = {
    "id" : false,
    "tel" : false,
    "telAuth" : false
}

const checkPwObj = {
    "newPw" : false,
    "newPwConfirm" : false
}



// 1. 아이디 입력 인풋
const inputId = document.getElementById('inputId');


// 2. 다음 버튼 (초기 disabled)
const nextBtn = document.getElementById('nextBtn');

// 3. 휴대폰 입력창 히든 div
const telHiddenDiv = document.getElementById('telHiddenDiv');

// 3-1. 휴대폰 입력 input
const inputTel = document.getElementById('inputTel');

// 3-2. 인증 요청 btn
const telAuthBtn = document.getElementById('telAuthBtn');

// 3-3. 휴대폰 입력창 하단 유효성 span (트림만 걸거임)
const telMsg = document.getElementById('telMsg');


// 4. 휴대폰 인증번호 입력 히든 div
const telAuthHidden = document.getElementById('telAuthHidden');

// 4-1. 휴대폰 인증번호 input
const telAuthInput = document.getElementById('telAuthInput');

// 4-2 휴대폰 인증번호 입력창 하단 유효성 span
const telAuthMsg = document.getElementById('telAuthMsg');



// 5. 비밀번호 찾기 히든
const findPwBtnDiv = document.getElementById('findPwBtnDiv');

const findPwBtn = document.getElementById('findPwBtn');

// -------------------------------------------------------------------------

// disabled 체크 (다음 버튼)
function disabledCheckButton1() {

    if(inputId.value.trim().length > 0) {
        nextBtn.disabled = false;
    } else {
        nextBtn.disabled = true;
    }
}


// 비밀번호 찾기 버튼
function disabledCheckButton2() {
    if(telAuthInput.value.trim().length == 6) {
        findPwBtn.disabled = false;
    } else {
        findPwBtn.disabled = true;
    }
}



// 인증 요청 후 내용 수정 시 리셋 함수
function resetAuthStates() {

    if(!telAuthHidden.classList.contains('hidden')) {

        // 전화번호 인증 상태 초기화
        checkTelObj.telAuth = false;
        telAuthHidden.classList.add('hidden');
        telAuthInput.value = "";
        telAuthMsg.innerText = "";

    }

}



// ----------------------------------------------------------


// 1. 아이디 입력창 입력 시
inputId.addEventListener('input', e => {

    const inputIdValue = e.target.value;
    
    if(inputIdValue.trim().length === 0) {
        inputId.classList.add('errorB');
        checkTelObj.id = false;
        return;
    }

    inputId.classList.remove('errorB');

    try {
        fetch("/common/idCheck", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : inputIdValue
        })
        .then(resp => resp.text())
        .then(result => {
            
            // 아이디 없음
            if(result == 0) {
                checkTelObj.id = false;
                return;
            }

            checkTelObj.id = true;    

        });

    } catch(err) {
        console.log(err);
    }

    disabledCheckButton1();
    resetAuthStates();
})


// 2. 다음으로 버튼 
nextBtn.addEventListener("click", () => {

    // 존재하지 않는 아이디일 경우
    if(!checkTelObj.id) {
        alert('입력하신 아이디를 찾을 수 없습니다.');
        return;
    }


    // 전화번호 입력창이 숨겨져 있다면
    if(telHiddenDiv.classList.contains('hidden')) {
        telHiddenDiv.classList.remove('hidden');
        findPwBtnDiv.classList.remove('hidden');
        nextBtn.classList.add('hidden');
    }

});

// 정규식: 전화번호
const regExpTel = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;



// (전화번호) 포커스 시
inputTel.addEventListener('focus', e => {

    const inputTelValue = e.target.value;

    if(inputTelValue.trim().length === 0) {
        telMsg.innerText = '전화번호를 입력해 주세요.';
        telMsg.classList.add('errorC');
        inputTel.classList.add('errorB');
        checkTelObj.tel = false;
    }


});

// 포커스 벗어나면
inputTel.addEventListener('blur', () => {
    telMsg.innerText = '';
    telMsg.classList.remove('errorC');
    inputTel.classList.remove('errorB');
});


// 3. 전화번호 입력 시
inputTel.addEventListener('input', e => {

    // 인증 요청 후 재입력 시
    checkTelObj.telAuth = false;

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
    if (!regExpTel.test(inputTelValue)) {

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
    
    disabledCheckButton2();
    resetAuthStates();
});


// 4. 인증 요청 버튼 클릭 시 
telAuthBtn.addEventListener("click", async () => {

    // 이미 인증 입력 시도 중이였다면
    if(!telAuthHidden.classList.contains('hidden')) {

        alert('이미 인증 요청하셨습니다. 다시 입력 후 시도해 주세요.')

        checkTelObj.tel = false;
        checkTelObj.telAuth = false;
        telAuthInput.value = "";
        inputTel.value = "";
        telAuthMsg.innerText = "";
        telAuthHidden.classList.add('hidden');

        return;
    }

    // 전화번호 유효성 x
    if(!checkTelObj.tel) {
        alert('전화번호를 다시 확인 후 요청해 주세요.')
        return;
    }

    // 아이디 혹시 모르니 다시 한 번 걸러주고
    if(!checkTelObj.id) {
        alert('아이디를 다시 확인 후 요청해 주세요.')
        return;
    }

    // 버튼 비활성화(다중 클릭 방지)
    telAuthBtn.disabled = true; // 버튼 비활성화

    // * 멤버, 파트너 전체 ID, TEL 일치 여부 확인 필요 *
    const commonITCheck = {
        "id" : inputId.value,
        "tel" : inputTel.value
    }

    try {
        const commonITCheckResp = await fetch("/common/commonITCheck", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(commonITCheck)
        });
        
        const commonITCheckResult = await commonITCheckResp.text();

        if(commonITCheckResult == 0) {
            alert("입력하신 정보와 일치하는 회원 또는 파트너가 존재하지 않습니다.");
            telAuthBtn.disabled = false; // 버튼 활성화
            return;
        }


        // sms 발송 결과 요청
        const smsSendResp = await fetch("/sms/sendOne", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: inputTel.value
        });


        // sms 발송 결과
        const smsSendResult = await smsSendResp.text();

        if(smsSendResult == 1) {
            
            console.log('인증 번호 발송 성공');
            alert("입력해 주신 휴대폰 번호로 인증번호가 발송되었습니다.");
            telAuthHidden.classList.remove('hidden');
            telAuthInput.focus();
        } else {
            console.log("인증 번호 발송 실패");
            alert("인증 번호 발송에 실패했습니다. 다시 시도해 주세요.");
        }
        


    } catch(err) {
        console.log(err);
    } finally {
        telAuthBtn.disabled = false; // 버튼 활성화
    }


});


// 3. 휴대폰 인증 입력

// (인증번호 입력란) 포커스 시 공백
telAuthInput.addEventListener('focus', e => {
    
    const telAuthValue = e.target.value;

    if(telAuthValue.trim().length === 0) {
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
        telAuthInput.classList.add('errorB');
        telAuthMsg.classList.remove('errorC');
        checkTelObj.telAuth = false;
    } else {
        telAuthMsg.innerText = "";
        telAuthMsg.classList.remove('errorC');
        telAuthInput.classList.remove('errorB');

    }

    // 입력 후 바로 엔터키 칠 경우 검증이 제대로 되지 않아 추가
    const obj = {
        "smsTel" : inputTel.value,
        "smsAuthKey" : telAuthInput.value
    }

    fetch("/sms/checkSmsAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0) {
            checkTelObj.telAuth = false;
            return;
        }

        checkTelObj.telAuth = true;

    })

    disabledCheckButton2();
});


// 숨길 영역 추가
const inputIdDiv = document.getElementById('inputIdDiv');
const telDiv = document.getElementById('telDiv');

// 4. 비밀번호 변경
const changePwDiv = document.getElementById('changePwDiv');
const changePwBtnDiv = document.getElementById('changePwBtnDiv');
const changePwBtn = document.getElementById('changePwBtn'); // 변경 버튼

const newPw = document.getElementById('newPw'); // 새 비밀번호 인풋
const pwMsg = document.getElementById('pwMsg');

const newPwConfirm = document.getElementById('newPwConfirm'); // 새 비밀번호 확인 인풋
const pwConfirmMsg = document.getElementById('pwConfirmMsg');


// 비밀번호 변경하기
function disabledCheckButton3() {
    if(checkPwObj.newPw && checkPwObj.newPwConfirm) {
        changePwBtn.disabled = false;
    } else {
        changePwBtn.disabled = true;
    }
}


// 비밀번호 찾기 버튼 클릭 시
findPwBtn.addEventListener('click', () => {

    if(!checkTelObj.id) {
        alert('유효한 아이디를 입력해 주세요.');
        return;
    }

    if (!checkTelObj.tel) {
        alert('유효한 전화번호를 입력해 주세요.');
        return;
    }
    if (!checkTelObj.telAuth) {
        alert('SMS 인증번호를 정확히 확인해 주세요.');
        return;
    }
    
    inputIdDiv.classList.add('hidden');
    telDiv.classList.add('hidden');
    telHiddenDiv.classList.add('hidden');
    findPwBtnDiv.classList.add('hidden');
    findPwBtn.classList.add('hidden');
    changePwDiv.classList.remove('hidden');
    changePwBtnDiv.classList.remove('hidden');

});


// 비밀번호 체크 함수
const checkPwFn = () => {

    // 같을 경우
    if(newPw.value === newPwConfirm.value) {
        pwConfirmMsg.innerText ='\u2713';
        pwConfirmMsg.classList.remove("errorC");
        pwConfirmMsg.classList.add('confirm');
        newPwConfirm.classList.add('confirmB');
        checkPwObj.newPwConfirm = true;
        return;
    }

    pwConfirmMsg.innerText = '비밀번호가 일치하지 않습니다.';
    pwConfirmMsg.classList.add("errorC");
    // newPwConfirm.classList.add("errorB");
    pwConfirmMsg.classList.remove('confirm');
    newPwConfirm.classList.remove('confirmB');
    checkPwObj.newPwConfirm = false;
    


}  


newPw.addEventListener('input', e => {

    const inputPw = e.target.value;

    if(inputPw.trim().length === 0) {
        
        pwMsg.innerText = '새 비밀번호를 입력해 주세요.';
        pwMsg.classList.add("errorC");
        newPw.classList.add("errorB");
        pwMsg.classList.remove('confirm');
        newPw.classList.remove('confirmB');
        checkPwObj.newPw = false;
        newPw.value = ""; // 처음 공백 방지
        
        checkPwFn();
        disabledCheckButton3();

        return;
    }

    newPw.classList.remove("errorB");

    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    // 정규식 미통과
    if(!regExp.test(inputPw)) {
        pwMsg.innerText = '비밀번호가 유효하지 않습니다.';
        pwMsg.classList.add("errorC");
        pwMsg.classList.remove('confirm');
        newPw.classList.remove('confirmB');
        checkPwObj.newPw = false;

        disabledCheckButton3();
        return;
    }

    // 공백 및 정규식 통과
    pwMsg.innerText = '\u2713';
    pwMsg.classList.remove("errorC");
    pwMsg.classList.add('confirm');

    newPw.classList.add('confirmB');

    checkPwObj.newPw = true;

    if(newPwConfirm.value.length > 0) {
        checkPwFn();
    }

    disabledCheckButton3();

});


newPwConfirm.addEventListener('input', e => {

    const inputConfirmPw = e.target.value;

    if(newPw.value.length > 0 && inputConfirmPw.trim().length === 0) {
        pwConfirmMsg.innerText = '새 비밀번호 확인을 입력해 주세요.';
        pwConfirmMsg.classList.add("errorC");
        newPwConfirm.classList.add('errorB');
        pwConfirmMsg.classList.remove('confirm');
        newPwConfirm.classList.remove('confirmB');
        checkPwObj.newPwConfirm = false;
        return;
    }

    newPwConfirm.classList.remove("errorB");

    if(checkPwObj.newPw) {
        checkPwFn();
        disabledCheckButton3();
        return;
    }

    checkPwObj.newPwConfirm = false;

    

})






// 폼 제출 시 유효성 검사
const findPwForm = document.getElementById('findPwForm');

findPwForm.addEventListener('submit', (e) => {

    if(!nextBtn.classList.contains('hidden')) {
        e.preventDefault();
        nextBtn.click();
        return;
    }

    if (!checkPwObj.newPw || !checkPwObj.newPwConfirm) {
        e.preventDefault();

        if(!checkPwObj.pw) {
            alert('유효한 아이디를 입력해 주세요.');
            return;
        }

        if (!checkPwObj.newPwConfirm) {
            alert('유효한 전화번호를 입력해 주세요.');
            return;
        }
    }
    

});

document.getElementById('findPwForm').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();  // Enter 키에 의한 폼 제출을 막음
        
        // "다음" 버튼이 표시되어 있고 활성화된 경우
        const nextBtn = document.getElementById('nextBtn');
        if (!nextBtn.classList.contains('hidden')) {
            nextBtn.click();
            return;
        }

        // "비밀번호 찾기" 버튼이 표시되어 있고 활성화된 경우
        const findPwBtn = document.getElementById('findPwBtn');
        if (!findPwBtn.classList.contains('hidden')) {
            findPwBtn.click();
            return;
        }

        // "비밀번호 변경" 버튼이 표시되어 있고 활성화된 경우
        const changePwBtn = document.getElementById('changePwBtn');
        if (!changePwBtn.classList.contains('hidden')) {
            document.getElementById('findPwForm').submit();
            return;
        }
    }
});

// 다음 버튼이 있을 때 엔터키 누를 경우에 대한 방지
// document.getElementById('inputId').addEventListener('keypress', function(event) {
//     if (event.key === 'Enter') {
//         event.preventDefault();  // Enter 키에 의한 폼 제출을 막음
//         if(!nextBtn.classList.contains('hidden')) document.getElementById('nextBtn').click();  // "다음" 버튼 클릭 이벤트 트리거
//     }
// });



