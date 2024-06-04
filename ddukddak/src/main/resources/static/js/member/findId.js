const checkEmailObj = {
    "nickName" : false,
    "email" : false,
    "emailAuth" : false
};

const checkTelObj = {
    "tel" : false,
    "telAuth" : false
};

// =================== 이메일 관련 요소 ===================
const emailChoice = document.getElementById('emailChoice'); // 체크 박스
const emailHiddenDiv = document.getElementById('emailHiddenDiv'); // 이메일 라디오 (히든)
const inputEmail = document.getElementById('inputEmail'); // 이메일 입력 인풋
const emailAuthBtn = document.getElementById('emailAuthBtn'); // 메일 인증 버튼
const emailAuthHidden = document.getElementById('emailAuthHidden'); // 인증 요청 버튼 (히든)
const emailAuthInput = document.getElementById('emailAuthInput'); // 인증문구 입력 인풋
const emailMsg = document.getElementById('emailMsg');
const emailAuthMsg = document.getElementById('emailAuthMsg');


// 닉네임
const inputNickName = document.getElementById('inputNickName');

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




// submit 버튼의 disabled 상태를 토글하는 함수 (전화번호와 이메일 모두 체크)
function disabledCheckButton() {
    if ((checkTelObj.tel && telAuthInput.value.trim().length == 6) ||
        (checkEmailObj.email && emailAuthInput.value.trim().length == 6) && checkEmailObj.nickName) {
        findIdBtn.disabled = false;
    } else {
        findIdBtn.disabled = true;
    }
}


// ****************** 이메일 ******************


// 닉네임 유효성 검사 [닉네임의 경우 테두리 외 별도 msg 미제공]
const regExpNickname = /^[가-힣\w\d]{2,10}$/; // 특문 제외 2~10

// 닉네임 초기 포커스 (공백)
inputNickName.addEventListener('focus', e => {
    const inputNickNameValue = e.target.value;

    if(inputNickNameValue.trim().length ===0) {
        inputNickName.classList.add('errorB');
        checkEmailObj.nickName = false;
    }
})

// 닉네임 포커스 벗어날 때 (클래스 제거)
inputNickName.addEventListener('blur', () => {
    inputNickName.classList.remove('errorB');
});



// *** 닉네임 입력 시 ***
inputNickName.addEventListener('input', e => {

    const inputNickNameValue = e.target.value;

    if(inputNickNameValue.trim().length === 0) {
        inputNickName.classList.add('errorB');
        checkEmailObj.nickName = false;
        return;
    }

    if(!regExpNickname.test(inputNickNameValue)) {
        inputNickName.classList.add('errorB');
        checkEmailObj.nickName = false;
        return;
    }

    inputNickName.classList.remove('errorB'); // 일단 공백, 유효성만 통과하면 빨간 테두리 X
    checkEmailObj.nickName = true;
    
    // 닉네임, 이메일 일치 여부 비동기할 예정이기 때문에 주석 처리

    // fetch("/member/checkNickname?memberNickname=" + inputNickNameValue)
    // .then(resp => resp.text())
    // .then(count => {
    //     if(count == 0) {
            
    //         // 중복이 없을 경우 - 존재하지 않는 닉네임
    //         checkEmailObj.nickName = false;
            
    //         return;
    //     }

    //     // 공백, 유효성, 비동기 중복 통과 시 true 반환
    //     checkEmailObj.nickName = true;

    // });

});



// 정규식 이메일
const regExpEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


// 이메일 입력 창 포커스
inputEmail.addEventListener('focus', e => {

    const inputEmailValue = e.target.value;

    if(inputEmailValue.trim().length === 0) {
        inputEmail.classList.add('errorB');
        emailMsg.innerText = '이메일을 입력해 주세요.';
        emailMsg.classList.add('errorC');
        checkEmailObj.email = false;
    }
})

// 이메일 입력 창 포커스 벗어나면
inputEmail.addEventListener('blur', () => {
    inputEmail.classList.remove('errorB');
    emailMsg.innerText = '';
    emailMsg.classList.remove('errorC');
})



// *** 1. 이메일 입력 시 ***
inputEmail.addEventListener('input', e => {
    
    // 인증 후 재입력 시
    checkEmailObj.emailAuth = false;

    const inputEmailValue = e.target.value;
    
    // 공백
    if(inputEmailValue.trim().length === 0) {
        inputEmail.classList.add('errorB');
        emailMsg.innerText = '이메일을 입력해 주세요.';
        emailMsg.classList.add('errorC');
        checkEmailObj.email = false;
        return;
    }

    // 유효성
    if(!regExpEmail.test(inputEmailValue)) {
        inputEmail.classList.add('errorB');
        emailMsg.innerText = '이메일을 주소를 다시 확인해 주세요.';
        emailMsg.classList.add('errorC');
        checkEmailObj.email = false;
        return;
    }

    inputEmail.classList.remove('errorB');
    emailMsg.innerText = '';
    emailMsg.classList.remove('errorC');
    checkEmailObj.email = true;

})



// 2. 이메일 인증 요청 버튼 클릭 시
emailAuthBtn.addEventListener('click', async () => {

    // 이미 인증 요청 했다면
    if(!emailAuthHidden.classList.contains('hidden')) {
        alert('이미 인증 요청하셨습니다. 다시 입력 후 시도해 주세요.')

        checkEmailObj.email = false;
        checkEmailObj.emailAuth = false;
        emailAuthInput.value = "";
        inputEmail.value = "";
        emailAuthMsg.innerText ="";
        emailAuthHidden.classList.add('hidden');

        return;
    }

    // 이메일 유효성 x
    if(!checkEmailObj.email) {
        alert('이메일을 다시 확인 후 요청해 주세요.')
        return;
    }

    if(!checkEmailObj.nickName) {
        alert('닉네임을 다시 확인 후 요청해 주세요.')
        return;
    }
    
    const memberCheck = {
        "memberNickname" : inputNickName.value,
        "memberEmail" : inputEmail.value
    }

    try {

        // 닉네임, 이메일 회원 일치 여부(비동기)
        const memberCheckResp = await fetch("/member/memberCheck", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(memberCheck)
        });
        const memberCheckResult = await memberCheckResp.text();
        
        if (memberCheckResult == 0) {
            alert("입력하신 정보와 일치하는 회원이 존재하지 않습니다.");
            return;
        }

        // 회원 일치하면 이메일 발송(비동기)
        const emailSendResp = await fetch("/email/findId", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: inputEmail.value // 추후 JSON으로 변경해야할 수도 있음
        });
        const emailSendResult = await emailSendResp.text();
        if(emailSendResult == 1) {
            console.log("인증 번호 발송 성공");
            alert("입력해 주신 이메일 주소로 인증번호가 발송되었습니다.")
            emailAuthHidden.classList.remove('hidden');
            emailAuthInput.focus();
    
        } else {
            console.log("인증 번호 발송 실패");
        }
    
    } catch(err) {
        console.log('Error', err)
    }


    // // 닉네임, 이메일 회원 일치 여부(비동기)
    // fetch("/member/memberCheck", {
    //     method : "POST",
    //     headers : {"Content-Type" : "application/json"},
    //     body : JSON.stringify(memberCheck)
    // })
    // .then(resp => resp.text())
    // .then(result => {
        
    //     if(result == 0) {
    //         alert("입력하신 정보와 일치하는 회원이 존재하지 않습니다.")
    //         return;
    //     }

    // })

    // // 이메일 발송(비동기)
    // fetch("/email/findId", {
    //     method : "POST",
    //     headers : {"Content-Type" : "application/json"},
    //     body : inputEmail.value
    // })
    // .then(resp => resp.text())
    // .then(result => {
    //     if(result == 1) {
    //         console.log("인증 번호 발송 성공");
    //     } else {
    //         console.log("인증 번호 발송 실패");
    //     }

    // })

    // alert("입력해 주신 이메일 주소로 인증번호가 발송되었습니다.")


    // emailAuthKey = '123456'; // 임시(삭제 예정)

    // emailAuthHidden.classList.remove('hidden');
    // emailAuthInput.focus();

});



// 3. 이메일 인증 입력

// 포커스
emailAuthInput.addEventListener('focus', e => {

    const emailAuthValue = e.target.value;

    if(emailAuthValue.trim().length === 0) {
        emailAuthInput.classList.add('errorB');
        emailAuthMsg.classList.add('errorC');
        emailAuthMsg.innerText = "인증번호를 입력해 주세요.";
    }

})



// 인증번호 유효성
emailAuthInput.addEventListener('input', e => {

    const inputAuthKey = e.target.value;

    if (inputAuthKey.trim().length === 0) {
        emailAuthMsg.innerText = '인증번호를 입력해 주세요.';
        emailAuthMsg.classList.add('errorC');
        emailAuthInput.classList.add('errorB');
        checkTelObj.emailAuth = false;
    } else if (inputAuthKey.trim().length < 6 ) {
        emailAuthMsg.innerText = "";
        emailAuthInput.classList.add('errorB');
        emailAuthMsg.classList.remove('errorC');
        checkTelObj.emailAuth = false;
    } else {
        emailAuthMsg.innerText = "";
        emailAuthMsg.classList.remove('errorC');
        emailAuthInput.classList.remove('errorB');
    }

    disabledCheckButton();
});

// blur로 이메일 인증번호 검증하기
emailAuthInput.addEventListener('blur', () => {
    

    const obj = {
        "email" : inputEmail.value,
        "authKey" : emailAuthInput.value
    }

    fetch("/email/checkAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0) {
            checkEmailObj.emailAuth = false;
            return;
        }

        checkEmailObj.emailAuth = true;

    })


});


// ****************** SMS *****************



// 필요하면 Member 쪽  findId.js 참고

// 정규식: 전화번호
const regExpTel = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

// SMS 인증 코드(임시)
let smsAuthKey = "";



// (전화번호) 포커스 시 공백
inputTel.addEventListener('focus', () => {
    if(inputTel.value.trim().length === 0) {
        telMsg.innerText = '전화번호를 입력해 주세요.';
        telMsg.classList.add('errorC');
        inputTel.classList.add('errorB');
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
        telAuthInput.classList.add('errorB');
        telAuthMsg.classList.remove('errorC');
        checkTelObj.telAuth = false;
    } else {
        telAuthMsg.innerText = "";
        telAuthMsg.classList.remove('errorC');
        telAuthInput.classList.remove('errorB');

        if(smsAuthKey == telAuthInput.value) {
            checkTelObj.telAuth = true;
        }
    }

    disabledCheckButton();
});


// 폼 제출 시 유효성 검사
const findIdForm = document.getElementById('findIdForm');

findIdForm.addEventListener('submit', (e) => {


    if(emailChoice.checked) {
        if(!checkEmailObj.email || !checkEmailObj.emailAuth) {
            e.preventDefault();

            if(!checkEmailObj.nickName) {
                alert('유효한 닉네임을 입력해 주세요.');
                return;
            }

            if(!checkEmailObj.email) {
                alert('유효한 이메일을 입력해 주세요.')
                return;
            }  

            if(!checkEmailObj.emailAuth) {
                alert('메일 인증번호를 정확히 확인해 주세요.')
                return;
            }
        }
    }


    if(telChoice.checked) {
        
        if (!checkTelObj.tel || !checkTelObj.telAuth) {
            e.preventDefault();

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


// 체크 번갈아가면서 선택 시


// 인증 방식 선택 시 처리
emailChoice.addEventListener('change', () => {

    if (emailChoice.checked) {
        
        telHiddenDiv.classList.add('hidden');  
        emailHiddenDiv.classList.remove('hidden');
        inputEmail.setAttribute('name', 'memberEmail');
        emailAuthInput.setAttribute('name', 'authKey');
        inputNickName.setAttribute('name', 'memberNickname')
        inputTel.removeAttribute('name');
        telAuthInput.removeAttribute('name');
        inputTel.value = "";
        telAuthInput.value = "";
        telMsg.innerText = "";
        telAuthMsg.innerText = "";
        checkTelObj.tel = false;
        checkTelObj.telAuth = false;
        telAuthHidden.classList.add('hidden'); // 전화번호 인증키 입력란 히든처리

        disabledCheckButton();
    }
});

telChoice.addEventListener('change', () => {
    if (telChoice.checked) {
        
        emailHiddenDiv.classList.add('hidden'); // 이메일 전체 히든
        telHiddenDiv.classList.remove('hidden'); // 휴대폰 전체 히든 풀기
        inputTel.setAttribute('name', 'memberTel'); // 
        telAuthInput.setAttribute('name', 'smsAuthKey');
        inputNickName.removeAttribute('name')
        inputEmail.removeAttribute('name');
        emailAuthInput.removeAttribute('name');
        inputEmail.value = ""; // 이메일 인풋
        emailAuthInput.value = ""; // 이메일 인증키 인풋
        inputNickName.value = ""; // 닉네임 인풋 
        emailMsg.innerText = "";
        emailAuthMsg.innerText = "";
        checkEmailObj.email = false;
        checkEmailObj.emailAuth = false;
        emailAuthHidden.classList.add('hidden')

        disabledCheckButton();
    }
});