const checkObj = {
    "partnerId" : false,
    "partnerPw" : false,
    "partnerPwConfirm" : false,
    "partnerTel" : false,
    "partnerBusinessName" : false, // 상호명
    "partnerBusinessNum" : false, // 사업자등록번호
    "partnerCeoName" : false,
    "telAuth" : false,
    "allAgree" : false
}

// 가입하기 버튼
const signUpBtn = document.getElementById('signUpBtn');

// const disabledCheck = () => {
//     // 모든 입력 필드가 유효한지 확인
//     const allValid = Object.values(checkObj).every(value => value === true);
//     // 가입하기 버튼의 disabled 속성 업데이트
//     signUpBtn.disabled = !allValid;
// }



// 약관 전체 동의 기초 로직
const agreeAllBtn = document.getElementById('agreeAllBtn');
const agreeButtons = document.querySelectorAll('.agreeBtn');

agreeAllBtn.addEventListener('change', function() {
    if (this.checked) {
        agreeButtons.forEach(function(button) {
            button.checked = true;
            
        });
        checkObj.allAgree = true;
    } else {
        agreeButtons.forEach(function(button) {
            button.checked = false;
            
        });
        checkObj.allAgree = false;
        
    }
});

agreeButtons.forEach(function(button) {
    button.addEventListener('change', function() {
        if (document.querySelectorAll('.agreeBtn:checked').length === agreeButtons.length) {
            agreeAllBtn.checked = true;
            checkObj.allAgree = true;
        } else {
            agreeAllBtn.checked = false;
            checkObj.allAgree = false;
        }
    });
});

// ========================================================================

//  1. 사업자 인증

// 사업자 인증 버튼
const bsNumAuthBtn = document.getElementById('bsNumAuthBtn');
const bsNum = document.getElementById('bsNum');
const businessNumAuthMsg = document.getElementById('businessNumAuthMsg');

// 사업자 입력란 자동 포커스 기능
document.getElementById('bsNum1').addEventListener('input', function() {
    if (this.value.length === this.maxLength) {
        document.getElementById('bsNum2').focus();
    }
});

// 사업자 입력란 자동 포커스 기능
document.getElementById('bsNum2').addEventListener('input', function() {
    if (this.value.length === this.maxLength) {
        document.getElementById('bsNum3').focus();
    }
});


// 인증 버튼 클릭 시
bsNumAuthBtn.addEventListener('click', () => {
    
    // 123-12-12345 형식으로 보여주기용, 실제 - 제외 값  얻어오기
    const bsNum1 = document.getElementById('bsNum1').value;
    const bsNum2 = document.getElementById('bsNum2').value;
    const bsNum3 = document.getElementById('bsNum3').value;

    const businessNumShow = bsNum1 + '-' + bsNum2 + '-' + bsNum3;
    const businessNum = bsNum1 +  bsNum2 + bsNum3;

    if(bsNum1.length !== 3 || bsNum2.length !== 2 || bsNum3.length !== 5) {
        alert('사업자등록번호 형식이 올바르지 않습니다. 다시 입력해 주세요.');
        
        return;
    }

    try {  

        fetch("/partner/checkBusinessNum?partnerBusinessNum=" + businessNum)
        .then(resp => resp.text())
        .then(count => {

            if(count == 1) { // 중복 O

                businessNumAuthMsg.innerText = '이미 존재하는 사업자등록번호입니다.'
                businessNumAuthMsg.classList.add('error');
                businessNumAuthMsg.classList.remove('confirm');
                checkObj.partnerBusinessNum = false;
                bsNum.value = '';
                // disabled 체크 삭제
                return;
            }

            businessNumAuthMsg.innerText = '인증되었습니다.'
            businessNumAuthMsg.classList.remove('error');
            businessNumAuthMsg.classList.add('confirm');
            checkObj.partnerBusinessNum = true;
            bsNum.value = businessNumShow; // 인증 시에만 값 넣기
            // disabled 체크 삭제
        })
        .catch(err => {
            console.error('Fetch error:', err);
        });

    } catch(err) {
        console.log(err);
    }

    // disabled 체크 삭제
    
    // 123-12-1245 형식
    // 서버에서 - 제거하고 DB에 넣을 예정
    
});

// ========================================================================

// 2. 상호명 유효성 검사

// https://stibee.com/api/v1.0/emails/share/E6wGrFYFAyAOa0redVTDdfjjptdNL5o= 특수문자 사용 규칙 참고

const bsName = document.getElementById('bsName');
const bsNameMsg = document.getElementById('bsNameMsg');
bsName.addEventListener('input', e => {

    const inputBsName = e.target.value;

    if(inputBsName.trim().length === 0) {
        checkObj.partnerBusinessName = false;
        bsName.value = '';
        bsNameMsg.innerText = "2~30자의 한글, 대/소문자, 숫자, 특수문자(& . ( ) , - · ') 사용해 주세요.";
        bsNameMsg.classList.remove('error', 'confirm');
        // disabled 체크 삭제
        return;
    }

    const regExp = /^[가-힣a-zA-Z0-9&',.·\-()\s]{2,30}$/;

    if(!regExp.test(inputBsName)) {
        checkObj.partnerBusinessName = false;
        bsNameMsg.innerText = '유효하지 않은 상호명입니다.'
        bsNameMsg.classList.add('error');
        bsNameMsg.classList.remove('confirm');

        // disabled 체크 삭제

        return;
    }

    // 비동기 중복 확인
    try{
        fetch('/partner/checkBusinessName?partnerBusinessName=' + inputBsName)
        .then(resp => resp.text())
        .then(count => {
            
            if(count == 1) { // 중복 O
                bsNameMsg.innerText = '이미 사용중인 상호명입니다.';
                bsNameMsg.classList.add('error');
                bsNameMsg.classList.remove('confirm');
                checkObj.partnerBusinessName = false;
                // disabled 체크 삭제
                return;
            }

            bsNameMsg.innerText = '사용 가능한 상호명입니다.';
            bsNameMsg.classList.remove('error');
            bsNameMsg.classList.add('confirm');
            checkObj.partnerBusinessName = true;
            // disabled 체크 삭제
        })

        // disabled 체크 삭제
    } catch(err) {
        console.log(err);
    }

});

// ========================================================================

// 3. 대표자명 유효성 검사 

const ceoName = document.getElementById('ceoName');


// 대표자명 입력 시
ceoName.addEventListener('input', e => {

    const inputCeoName = e.target.value;

    if(inputCeoName.trim().length === 0) {
        checkObj.partnerCeoName = false;
        ceoName.value = ''; // 공백 방지
        // disabled 체크 삭제
        return;
    }

    // 이름 2~10글자 한글
    const regExp = /^[가-힣]{2,10}$/;

    if(!regExp.test(inputCeoName)) {
        checkObj.partnerCeoName = false;
        // disabled 체크 삭제
        return;
    }

    checkObj.partnerCeoName = true;
    // disabled 체크 삭제

});



// 4. 사업자등록번호는 '-' 제외 스트링 값 서버에서 추출



// ========================================================================

// 5. 아이디 유효성 검사
const partnerId = document.getElementById('partnerId');
const idMsg = document.getElementById('idMsg');

partnerId.addEventListener("input", e => {
    
    const inputId = e.target.value;

    // 입력된 아이디 없을 경우
    if(inputId.trim().length === 0) {
        
        idMsg.innerText = "아이디를 입력해 주세요.";
        idMsg.classList.remove('confirm', 'error');
        checkObj.partnerId = false;
        partnerId.value = "";
        // disabled 체크 삭제

        return;

    }

    // 5~20자의 영문 소문자, 숫자만 사용 가능합니다.
    const regExp = /^[a-z0-9]{5,20}$/;

    if(!regExp.test(inputId)) {
        idMsg.innerText = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.";
        idMsg.classList.add('error');
        idMsg.classList.remove('confirm');
        checkObj.partnerId = false;

        // disabled 체크 삭제

        return;
    }

    fetch("/partner/checkId?partnerId=" + inputId)
    .then(resp => resp.text()) 
    .then(count => {

        if(count > 0) { // 중복 O - 유니온 때문에 샘플아이디 겹치면 최대 2
            
            idMsg.innerText = "이미 사용중인 아이디입니다.";
            idMsg.classList.add('error');
            idMsg.classList.remove('confirm');
            checkObj.partnerId = false;
            // disabled 체크 삭제

            return;
        }

        idMsg.innerText = "사용 가능한 아이디입니다.";
        idMsg.classList.add('confirm');
        idMsg.classList.remove('error');
        checkObj.partnerId = true;
        // disabled 체크 삭제

    })    
    .catch(error => {
        console.log(error); 
    });

    // disabled 체크 삭제
});

// ========================================================================

// 6. 비밀번호 유효성 검사
// const regExp = ^(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$

const partnerPw = document.querySelector("#partnerPw");
const partnerPwConfirm = document.querySelector("#partnerPwConfirm");

const pwMsg = document.getElementById("pwMsg");
const pwConfirmMsg = document.querySelector("#pwConfirmMsg");

// 1) 비번 == 비번 확인 체크 함수 정의
const checkPw = () => {
    
    // 같음
    if(partnerPw.value === partnerPwConfirm.value && partnerPwConfirm.value.length > 0) {
        pwConfirmMsg.innerText = "\u2713";
        pwConfirmMsg.classList.add("confirm");
        pwConfirmMsg.classList.remove("error");
        checkObj.partnerPwConfirm = true;
        // disabled 체크 삭제
        return;
    }

    if(partnerPwConfirm.value.trim().length === 0) {

        checkObj.partnerPwConfirm = false;
        // disabled 체크 삭제
        return;
    }

    pwConfirmMsg.innerText = "비밀번호가 일치하지 않습니다.";
    pwConfirmMsg.classList.add("error");
    pwConfirmMsg.classList.remove("confirm");
    checkObj.partnerPwConfirm = false;
    // disabled 체크 삭제

};


// 2) 유효성 검사

// 비밀번호 입력 시
partnerPw.addEventListener("input", e => {

    const inputPw = e.target.value;

    // 미입력
    if(inputPw.trim().length === 0) {
        
        pwMsg.innerText = "8~16자의 영문 대/소문자, 숫자, 특수문자(필수) 사용해 주세요.";
        pwMsg.classList.remove("confirm", "error");
        checkObj.partnerPw = false;
        partnerPw.value = ""; // 처음 공백 방지

        checkPw();

        return;
    }

    const regExp = /^(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;

    // 정규식 X
    if(!regExp.test(inputPw)) {
        pwMsg.innerText = "비밀번호가 유효하지 않습니다.";
        pwMsg.classList.add("error");
        pwMsg.classList.remove("confirm");
        checkObj.partnerPw = false;
        
        checkPw();

        return;
    }

    // 정규식 O
    pwMsg.innerText = "\u2713";
    pwMsg.classList.add("confirm");
    pwMsg.classList.remove("error");
    checkObj.partnerPw = true;

    if(partnerPwConfirm.value.length > 0) {
        checkPw();
    } 
    
    // disabled 체크 삭제
});

// 비밀번호 확인 입력 시
partnerPwConfirm.addEventListener("input", e => {
    
    const inputConfirmPw = e.target.value;

    if(partnerPw.value.trim().length === 0 && inputConfirmPw.trim().length === 0) {
        pwConfirmMsg.innerText = '';
        pwConfirmMsg.classList.remove('confirm', 'error');
        checkObj.partnerPw = false;
        // disabled 체크 삭제
        
        return;
    }

    if(partnerPw.value.trim().length === 0) {
        pwConfirmMsg.innerText = '비밀번호를 먼저 입력해 주세요.';
        pwConfirmMsg.classList.add('error');
        pwConfirmMsg.classList.remove('confirm');
        checkObj.partnerPw = false;
        // disabled 체크 삭제

        return;

    }



    if(partnerPw.value.length > 0 && inputConfirmPw.trim().length === 0) {
        pwConfirmMsg.innerText = '비밀번호 확인을 입력해 주세요.';
        pwConfirmMsg.classList.add('error');
        pwConfirmMsg.classList.remove('confirm');
        checkObj.partnerPwConfirm = false;
        // disabled 체크 삭제

        return;
    }

    if(!checkObj.partnerPw) {
        
        pwConfirmMsg.innerText = '비밀번호를 먼저 확인해 주세요.';
        pwConfirmMsg.classList.add('error');

        return;
    }

    if(checkObj.partnerPw) {
        checkPw(); // 비교하는 함수 호출
        return;
    }


    // partnerPw = false
    checkObj.partnerPwConfirm = false;
    // disabled 체크 삭제
    
}); 


// ========================================================================

// 7. 휴대폰 인증

// 번호 input
const partnerTel = document.getElementById('partnerTel');

// 인증요청 버튼
const sendAuthBtn = document.getElementById('sendAuthBtn');

// 번호 관련 span
const telMsg = document.getElementById('telMsg');

// --------

// 인증번호 input
const smsAuthKey = document.getElementById('smsAuthKey');

// 인증하기 버튼
const checkAuthBtn = document.getElementById('checkAuthBtn');

// 인증 타이머
const telAuthTime = document.getElementById('telAuthTime');

// 인증 관련 span
const telAuthMsg = document.getElementById('telAuthMsg');

// 발송 여부 boolean
let sendSmsAuthkey = false;

const disabledAuthBtnCheck = () => {

    // 발송여부가 false / 이미 발송했다면 살아나는거 막기
    if(checkObj.partnerTel && !sendSmsAuthkey) {
        sendAuthBtn.disabled = false;
    } else {
        sendAuthBtn.disabled = true;
    }

    if(checkObj.partnerTel && checkObj.telAuth ) {
        checkAuthBtn.disabled = true;
    }

    if(checkObj.partnerTel && smsAuthKey.value.length == 6 && sendSmsAuthkey)  {
        checkAuthBtn.disabled = false;
    } else {
        checkAuthBtn.disabled = true;
    }
}


// 휴대폰 인증 시도 횟수
let authCount2 = 0;


// 00 : 00 형식 함수
function addZero(num) {
    if(num < 10) {
        return "0" + num;
    } else {
        return num;
    }   
}

let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수

const initMin = 4; // 타이머 초기값 (분)
const initSec = 59; // 타이머 초기값 (초)
const initTime = "05:00";

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;



// 휴대폰 입력 유효성 부터

partnerTel.addEventListener('input', e => {

    const inputTel = e.target.value;

    // 인증 요청 후 휴대폰 번호 수정 시 인증 초기화
    checkObj.telAuth = false;
    telAuthMsg.innerText = '';
    smsAuthKey.value = '';
    sendSmsAuthkey = false;
    clearInterval(authTimer);
    telAuthTime.innerText = '';

    if(inputTel.trim().length === 0) {

        checkObj.partnerTel = false;
        partnerTel.value = "";
        telMsg.innerText = '실제 사용하고 계신 휴대폰 번호를 입력해 주세요.';
        telMsg.classList.remove('error', 'confrim');

        disabledAuthBtnCheck();
        // disabled 체크 삭제
        return;
    }

    const regExp = /^01[0-9]{1}[0-9]{4}[0-9]{4}$/;

    if(!regExp.test(inputTel)) {

        telMsg.innerText = "유효한 휴대폰 번호를 입력해 주세요."
        telMsg.classList.add('error');
        telMsg.classList.remove('confirm');
        checkObj.partnerTel = false;
        disabledAuthBtnCheck();
        // disabled 체크 삭제

        return;
    }

    try {

        fetch("/partner/checkTel?partnerTel=" + inputTel)
        .then(resp => resp.text())
        .then(count => {

            if(count == 1) { // 중복

                telMsg.innerText = "이미 사용중인 휴대폰 번호입니다."
                telMsg.classList.add('error');
                telMsg.classList.remove('confirm');

                checkObj.partnerTel = false;
                disabledAuthBtnCheck();
                // disabled 체크 삭제
                return;
        
            }

            // 중복 X 경우
            telMsg.innerText = "사용 가능한 휴대폰 번호입니다. '인증하기' 버튼을 클릭하여 계정을 인증해주세요.";
            telMsg.classList.add('confirm');
            telMsg.classList.remove('error'); 
            checkObj.partnerTel = true;
            disabledAuthBtnCheck();
            // disabled 체크 삭제
        })

    } catch(err) {
        console.log(err);
    }
    disabledAuthBtnCheck();
    // disabled 체크 삭제
});


// 인증요청 버튼
sendAuthBtn.addEventListener('click', () => {

    // 재클릭 시 인증 초기화
    checkObj.telAuth = false;
    smsAuthKey.value = "";
    telAuthMsg.innerText = "";
    sendSmsAuthkey = false; // 발송 성공 시 boolean

    // 휴대폰 유효성 X
    if(!checkObj.partnerTel) {
        alert("휴대폰 번호를 다시 확인 후 요청해 주세요.");
        telMsg.innerText = "올바른 휴대폰 번호 형식으로 입력해 주세요.";
        telMsg.classList.add('error');
        telMsg.classList.remove('confirm');
        checkObj.partnerTel = false; 
        disabledAuthBtnCheck();
        // disabled 체크 삭제

        return;
    }
    

    sendAuthBtn.disabled = true; // 재발송 막기

    // 클릭시 타이머 숫자 초기화
    min = initMin;
    sec = initSec;

    // 이전 동작 중인 인터벌 클리어
    clearInterval(authTimer);

    // 비동기 SMS 발송
    fetch("/sms/sendOne", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : partnerTel.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 1) {
            smsAuthKey.readOnly = false; // 인증 입력창 풀기
            sendSmsAuthkey = true; // 발송 성공 시 boolean
            // sendAuthBtn.disabled = true; // 재발송 막기
            telMsg.innerText = '아래 입력창에 인증 번호를 입력 후 인증하기 버튼을 눌러주세요.'
            telMsg.classList.remove('error', 'confirm');
            console.log("인증 번호 발송 성공");
            alert("입력해 주신 휴대폰 번호로 인증번호가 발송되었습니다.");
        } else {
            console.log("인증번호 발송 실패");
            alert("금일 발송량 초과 혹은 서버 문제로 인해 인증 번호 발송에 실패했습니다.");
        }
    }).catch(error => {
        console.log(error);
    });

    telAuthTime.innerText = initTime;
    telAuthTime.classList.remove('confirm', 'error');

    authTimer = setInterval( () => {

        telAuthTime.innerText = `${addZero(min)}:${addZero(sec)}`;

        // 0분 0초인 경우
        if(min == 0 && sec == 0) {
            checkObj.telAuth = false;
            clearInterval(authTimer);
            telAuthTime.classList.add('error');
            telAuthTime.classList.remove('confirm');
            disabledAuthBtnCheck();
            // disabled 체크 삭제
            return;
        }

        // 0초인 경우(분 감소)
        if(sec == 0) {
            sec = 60;
            min--;
        }

        sec--;
        
    }, 1000);

    disabledAuthBtnCheck();
    // disabled 체크 삭제
});



// 인증하기 버튼
checkAuthBtn.addEventListener('click', () => {

    // 인증이 완료된 상태에서 수정 후 틀린 값으로 인증하기 재클릭 시
    if(checkObj.telAuth) {
        alert(`이미 인증이 완료되었으나, 잘못된 인증번호로 인증 시도하였습니다.\n인증번호를 올바르게 입력 후 다시 시도해 주세요.`);
        telMsg.innerText = '아래 입력창에 인증 번호를 입력 후 인증하기 버튼을 눌러주세요.'
        telMsg.classList.remove('error', 'confirm');
    }
    
    checkObj.telAuth = false;
    telAuthMsg.innerText = "";

    // 시간 초과
    if(min === 0 && sec === 0) {
    
        alert("인증번호 입력 제한시간을 초과하였습니다.");

        return;
    }

    if(smsAuthKey.value.length < 6) {
        alert("인증번호를 올바르게 입력해 주세요.");

        return;
    }

    
    const obj = {
        "smsTel" : partnerTel.value,
        "smsAuthKey" : smsAuthKey.value
    };

    fetch("/sms/checkSmsAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0) {
            
            authCount2++;

            if (authCount2 == 3) {
                alert("인증 실패 횟수가 3회를 초과하여 종료됩니다.");
                // 여기에 필요한 종료 로직을 추가합니다. 예: 페이지 이동, 입력 비활성화 등.
                checkObj.partnerTel = false;
                checkObj.telAuth = false;
                partnerTel.value = "";
                smsAuthKey.value = ""; // 인증키 입력 인풋
                telAuthMsg.innerText = "";
                sendSmsAuthkey = false;
                clearInterval(authTimer);
                telAuthTime.innerText = '';
                telMsg.innerText = '실제 사용하고 계신 휴대폰 번호를 입력해 주세요.';
                telMsg.classList.remove('error', 'confirm')

                authCount2 = 0;
                
                disabledAuthBtnCheck();
                // disabled 체크 삭제

                return;
            }


            alert(`인증번호가 일치하지 않습니다.\n3회 이상 인증 실패 시 인증이 종료됩니다. (현재 ${authCount2}회 실패)`);
            

            checkObj.telAuth = false;

            disabledAuthBtnCheck();
            // disabled 체크 삭제

            return;
        }
        

        // 인증 전체 완료 시
        clearInterval(authTimer);
        telAuthTime.innerText = "";

        telMsg.innerText = '\u2713 인증 완료'
        telMsg.classList.remove('error');
        telMsg.classList.add('confirm');

        telAuthMsg.innerText = '\u2713 인증 완료'
        telAuthMsg.classList.remove('error');
        telAuthMsg.classList.add('confirm');
        
        
        checkObj.telAuth = true;
        
        disabledAuthBtnCheck();
        // disabled 체크 삭제

    })

});

// 인증 키 입력 시 버튼 활성화 확인
smsAuthKey.addEventListener('input', () => {

    disabledAuthBtnCheck();

});


// ********** 회원 가입 버튼 클릭 시 **************** / 

const signUpForm = document.getElementById('signUpForm');

// form 제출
signUpForm.addEventListener("submit" , e => {

    for(let key in checkObj) {
        if(!checkObj[key]) {
            let str;

            switch(key) {

                case "partnerBusinessName" : 
                str = "상호명이 올바른 형식이 아니거나, 중복된 상호명입니다."; break;

                case "partnerCeoName" : 
                    str = "대표자명이 올바른 형식이 아닙니다."; break;

                case "partnerBusinessNum" :
                    str = "사업자등록번호가 올바른 형식이 아니거나, 중복된 사업자등록번호입니다."; break;
                
                case "partnerId" : 
                    str = "아이디가 올바른 형식이 아니거나, 중복된 아이디입니다."; break;
                
                case "partnerPw" : 
                    str = "비밀번호가 형식에 맞게 입력되지 않았습니다."; break;
                
                case "partnerPwConfirm" :
                    str = "비밀번호와 비밀번호 확인이 일치하지 않습니다."; break;
                
                case "partnerTel" :
                    str = "휴대폰 번호가 올바른 형식이 아니거나, 중복된 휴대폰 번호입니다."; break;

                case "telAuth" :
                    str = "휴대폰 인증이 정상적으로 진행되지 않았습니다."; break;

                case "allAgree" :
                    str = "파트너 등록 요청 시 약관 동의는 필수입니다. 모든 약관에 동의해 주세요."; break;
                    
            }

            alert(str);

            // document.getElementById(key).focus();

            // 첫 번째 요소에 포커스 설정
            const element = document.getElementsByName(key)[0];
            if (element) {
                element.focus();
            }

            e.preventDefault();

            return;
        }
    }


});



// checkObj 현재 상태 확인
const checkObjBtn = document.getElementById('checkObjBtn');

checkObjBtn.addEventListener('click', () => {
    console.clear(); // 이전 콘솔 값을 비웁니다
    console.log(document.getElementById('partnerType').value);
    console.log("Current state of checkObj:");
    for (let key in checkObj) {
        console.log(`${key}: ${checkObj[key]}`);
    }
});