const checkTelObj = {
    "telNm" : false,
    "tel" : false,
    "telAuth" : false
};
// =================== 휴대폰 관련 요소 ===================
const telChoice = document.getElementById('telChoice');
const telHiddenDiv = document.getElementById('telHiddenDiv');

// 이름 입력 input
const telNm = document.getElementById('telNm');

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


// submit 버튼의 disabled 상태를 토글하는 함수
function disabledCheckButton() {
    if (checkTelObj.telNm && checkTelObj.tel && telAuthInput.value.trim().length == 6) {
        findIdBtn.disabled = false;
    } else {
        findIdBtn.disabled = true;
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


// 이름 유효성
const regExpName = /^[가-힣]{2,10}$/; // 특문 제외 2~10


// 이름 초기 포커스 (공백)
telNm.addEventListener('focus', e => {
    const telNmValue = e.target.value;

    if(telNmValue.trim().length ===0) {
        telNm.classList.add('errorB');
        checkTelObj.telNm = false;
    }
})

// 이름 포커스 벗어날 때 (클래스 제거)
telNm.addEventListener('blur', () => {
    telNm.classList.remove('errorB');
});


// *** 이름 입력 시 ***
telNm.addEventListener('input', e => {

    const telNmValue = e.target.value;

    if(telNmValue.trim().length === 0) {
        telNm.classList.add('errorB');
        checkTelObj.telNm = false;
        return;
    }

    if(!regExpName.test(telNmValue)) {
        telNm.classList.add('errorB');
        checkTelObj.telNm = false;
        return;
    }

    telNm.classList.remove('errorB'); // 일단 공백, 유효성만 통과하면 빨간 테두리 X
    checkTelObj.telNm = true;
    
    disabledCheckButton();
    resetAuthStates();
});


// 정규식: 전화번호
const regExpTel = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;



// 1. (전화번호) 포커스 시
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


// (전화번호) 입력 시
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
    
    disabledCheckButton();
    resetAuthStates();
});


// 2. SMS 인증 요청 클릭 시
telAuthBtn.addEventListener('click', async () => {

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

    // 이름 다시 한 번 걸러주고
    if(!checkTelObj.telNm) {
        alert('이름을 다시 확인 후 요청해 주세요.')
        return;
    }

    // 버튼 비활성화(다중 클릭 방지)
    telAuthBtn.disabled = true; // 버튼 비활성화

    const partnerNTCheck = {
        "partnerCeoName" : telNm.value,
        "partnerTel" : inputTel.value
    }


    try {
        const partnerNTCheckResp = await fetch("/partner/partnerNTCheck", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(partnerNTCheck)
        });

        const partnerNTCheckResult = await partnerNTCheckResp.text();

        if(partnerNTCheckResult == 0) {
            alert("입력하신 정보와 일치하는 파트너가 존재하지 않습니다.");
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
        console.log('Error', err)
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

    disabledCheckButton();
});

// blur로 휴대폰 인증번호 검증

// telAuthInput.addEventListener('blur', () => {

//     const obj = {
//         "smsTel" : inputTel.value,
//         "smsAuthKey" : telAuthInput.value
//     }

//     fetch("/sms/checkSmsAuthKey", {
//         method : "POST",
//         headers : {"Content-Type" : "application/json"},
//         body : JSON.stringify(obj)
//     })
//     .then(resp => resp.text())
//     .then(result => {

//         if(result == 0) {
//             checkTelObj.telAuth = false;
//             return;
//         }

//         checkTelObj.telAuth = true;

//     })

//     disabledCheckButton();
// });



// 폼 제출 시 유효성 검사
const findIdForm = document.getElementById('findIdForm');

findIdForm.addEventListener('submit', (e) => {


    // if(emailChoice.checked) {
    //     if(!checkEmailObj.email || !checkEmailObj.emailAuth || !checkEmailObj.emailNm) {
    //         e.preventDefault();

    //         if(!checkEmailObj.emailNm) {
    //             alert('유효한 이름을 입력해 주세요.');
    //             return;
    //         }

    //         if(!checkEmailObj.email) {
    //             alert('유효한 이메일을 입력해 주세요.')
    //             return;
    //         }  

    //         if(!checkEmailObj.emailAuth) {
    //             alert('메일 인증번호를 정확히 확인해 주세요.')
    //             return;
    //         }
    //     }
    // }


    if(telChoice.checked) {
        
        if (!checkTelObj.tel || !checkTelObj.telAuth || !checkTelObj.telNm ) {
            e.preventDefault();

            if(!checkTelObj.telNm) {
                alert('유효한 이름을 입력해 주세요.');
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
        }
    }

});