/* 다음 주소 API 활용*/
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

// 주소 검색 버튼 클릭 시
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);


// ***************** 회원 가입 유효성 검사 ***********************
const checkObj = {
    "memberId" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberName" : false,
    "memberNickname" : false,
    "memberEmail" : false,
    "memberTel" : false,
    "emailAuth" : false,
    "telAuth" : false
};



// -------------------------------------------------------------------------------

// 가입하기 버튼
const signUpBtn = document.getElementById('signUpBtn');

const disabledCheck = () => {
    // 모든 입력 필드가 유효한지 확인
    const allValid = Object.values(checkObj).every(value => value === true);
    // 가입하기 버튼의 disabled 속성 업데이트
    signUpBtn.disabled = !allValid;
}



// ***** 1. 아이디 유효성 검사 *******
const memberId = document.getElementById('memberId');
const idMsg = document.getElementById('idMsg');

memberId.addEventListener("input", e => {
    
    const inputId = e.target.value;

    // 입력된 아이디 없을 경우
    if(inputId.trim().length === 0) {
        
        idMsg.innerText = "아이디를 입력해 주세요.";

        idMsg.classList.remove('confirm', 'error');

        checkObj.memberId = false;

        memberId.value = "";

        return;

    }

    // 5~20자의 영문 소문자, 숫자만 사용 가능합니다.
    const regExp = /^[a-z0-9]{5,20}$/;

    if(!regExp.test(inputId)) {
        idMsg.innerText = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.";
        idMsg.classList.add('error');
        idMsg.classList.remove('confirm');
        checkObj.memberId = false;
        return;
    }

    fetch("/member/checkId?memberId=" + inputId)
    .then(resp => resp.text()) 
    .then(count => {

        if(count == 1) { // 중복 O
            
            idMsg.innerText = "이미 사용중인 아이디입니다.";
            idMsg.classList.add('error');
            idMsg.classList.remove('confirm');
            checkObj.memberId = false;
            return;
        }

        idMsg.innerText = "사용 가능한 아이디입니다.";
        idMsg.classList.add('confirm');
        idMsg.classList.remove('error');
        checkObj.memberId = true;

    })    
    .catch(error => {
        console.log(error); 
    });

    disabledCheck();
});

// -----------------------------------------------------------------------



// ***** 2. 비밀번호 유효성 검사 *******
// const regExp = ^(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$

const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");

const pwMsg = document.getElementById("pwMsg");
const pwConfirmMsg = document.querySelector("#pwConfirmMsg");

// 1) 비번 == 비번 확인 체크 함수 정의
const checkPw = () => {
    
    // 같음
    if(memberPw.value === memberPwConfirm.value && memberPwConfirm.value.length > 0) {
        pwConfirmMsg.innerText = "\u2713";
        pwConfirmMsg.classList.add("confirm");
        pwConfirmMsg.classList.remove("error");
        checkObj.memberPwConfirm = true;
        return;
    }

    if(memberPwConfirm.value.trim().length === 0) {

        checkObj.memberPwConfirm = false;
        return;
    }

    pwConfirmMsg.innerText = "비밀번호가 일치하지 않습니다.";
    pwConfirmMsg.classList.add("error");
    pwConfirmMsg.classList.remove("confirm");
    checkObj.memberPwConfirm = false;

    disabledCheck();
}


// 2) 유효성 검사

// 비밀번호 입력 시
memberPw.addEventListener("input", e => {

    const inputPw = e.target.value;

    // 미입력
    if(inputPw.trim().length === 0) {
        
        pwMsg.innerText = "8~16자의 영문 대/소문자, 숫자, 특수문자(필수) 사용해 주세요.";
        pwMsg.classList.remove("confirm", "error");
        checkObj.memberPw = false;
        memberPw.value = ""; // 처음 공백 방지


        checkPw();

        return;
    }

    const regExp = /^(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;

    // 정규식 X
    if(!regExp.test(inputPw)) {
        pwMsg.innerText = "비밀번호가 유효하지 않습니다.";
        pwMsg.classList.add("error");
        pwMsg.classList.remove("confirm");
        checkObj.memberPw = false;
        
        checkPw();

        return;
    }

    // 정규식 O
    pwMsg.innerText = "\u2713";
    pwMsg.classList.add("confirm");
    pwMsg.classList.remove("error");
    checkObj.memberPw = true;

    if(memberPwConfirm.value.length > 0) {
        checkPw();
    } 
    disabledCheck();
});

// 비밀번호 확인 입력 시
memberPwConfirm.addEventListener("input", e => {
    
    const inputConfirmPw = e.target.value;

    if(memberPw.value.trim().length === 0) {
        pwConfirmMsg.innerText = '비밀번호를 먼저 입력해 주세요.';
        pwConfirmMsg.classList.add('error');
        pwConfirmMsg.classList.remove('confirm');
        checkObj.memberPw = false;
        
        return;
    }

    if(memberPw.value.length > 0 && inputConfirmPw.trim().length === 0) {
        pwConfirmMsg.innerText = '비밀번호 확인을 입력해 주세요.';
        pwConfirmMsg.classList.add('error');
        pwConfirmMsg.classList.remove('confirm');
        checkObj.memberPwConfirm = false;

        return;
    }

    if(!checkObj.memberPw) {
        
        pwConfirmMsg.innerText = '비밀번호를 먼저 확인해 주세요.';
        pwConfirmMsg.classList.add('error');

        return;
    }

    if(checkObj.memberPw) {
        checkPw(); // 비교하는 함수 호출
        return;
    }


    // memberPw = false
    checkObj.memberPwConfirm = false;
    disabledCheck();
});




// ------------------------------------------------------------


// ***** 3. 이름 유효성 검사 *******

const memberName = document.getElementById('memberName');


// 이름 입력 시
memberName.addEventListener('input', e => {

    const inputName = e.target.value;

    if(inputName.trim().length === 0) {
        checkObj.memberName = false;
        memberName.value = ''; // 공백 방지
        return;
    }

    // 이름 2~10글자 한글
    const regExp = /^[가-힣]{2,10}$/;

    if(!regExp.test(inputName)) {
        checkObj.memberName = false;
        return;
    }

    checkObj.memberName = true;

    disabledCheck();
});






// ------------------------------------------------------------


// ***** 4. 닉네임 유효성 검사 *******

const memberNickname = document.querySelector("#memberNickname");
const nickMsg = document.querySelector("#nickMsg");

memberNickname.addEventListener("input", e => {

    const inputNickname = e.target.value;

    // 입력 x
    if(inputNickname.trim().length === 0) {
        nickMsg.innerText = "2~10자의 한글, 영어, 숫자만 사용해 주세요.";
        nickMsg.classList.remove("error", "confirm");
        
        checkObj.memberNickname = false;
        nickMsg.value = "";
        return;

    }

    const regExp = /^[가-힣a-zA-Z0-9]{2,10}$/;

    // 닉네임 유효성 검사
    if(!regExp.test(inputNickname)) { // 유효 X
        nickMsg.innerText = "닉네임 형식이 유효하지 않습니다."
        nickMsg.classList.add("error");
        nickMsg.classList.remove("confirm");
        checkObj.memberNickname = false;
        return;
    }

    fetch("/member/checkNickname?memberNickname=" + inputNickname)
    .then(resp => resp.text())
    .then(count => {

        if(count == 1) { // 중복 O
            nickMsg.innerText = "이미 사용중인 닉네임입니다.";
            nickMsg.classList.add("error");
            nickMsg.classList.remove("confirm");
            checkObj.memberNickname = false;
            return;
        }

        nickMsg.innerText = "사용 가능한 닉네임입니다.";
        nickMsg.classList.add("confirm");
        nickMsg.classList.remove("error");
        checkObj.memberNickname = true;

    });
    disabledCheck();
});







// 이메일 인증하기 버튼 클릭 시 이벤트
const emailAuth = document.getElementById('emailAuth'); // 인증하기 [버튼]
const emailAuthDiv = document.getElementById('emailAuthDiv'); // 인증 영역(히든)
const emailAuthCheck= document.getElementById('emailAuthCheck'); // 인증키 입력 후 확인 버튼



const memberEmail = document.getElementById("memberEmail"); // 이메일 입력 input
const emailAuthKey = document.getElementById('emailAuthKey'); // 이메일 인증키 입력 input
const emailMsg = document.getElementById("emailMsg"); // 이메일 입력 시 span

const emailAuthTime = document.getElementById('emailAuthTime'); // 5분 타이머

// 전화번호 인증하기 버튼 클릭 시 이벤트
const telAuth = document.getElementById('telAuth'); // 인증하기 버튼
const telAuthDiv = document.getElementById('telAuthDiv'); // SMS 인증 영역
const telAuthCheck = document.getElementById('telAuthCheck'); // 확인 버튼

const memberTel = document.getElementById('memberTel'); // 휴대폰 번호 입력 인풋

const smsAuthKey = document.getElementById('smsAuthKey'); // sms 인증키 인풋

const telAuthTime = document.getElementById('telAuthTime'); // 05:00 타이머

const telMsg = document.getElementById('telMsg'); // 휴대폰 span


const authDisabledCheck = () => {
    
    if(!emailAuthDiv.classList.contains('hidden')) {
        telAuth.disabled = true;
    } else {
        telAuth.disabled = false;
    }

    if(!telAuthDiv.classList.contains('hidden')) {
        emailAuth.disabled = true;
    } else {
        emailAuth.disabled = false;
    }
}



// ***************** 5. 이메일 인증 ***********************

// 이메일 유효성 먼저
memberEmail.addEventListener('input', e => {

    
    // 이메일 인증 후 이메일이 변경될 경우
    checkObj.emailAuth = false;
    emailMsg.innerText = "";

    const inputEmail = e.target.value;

    if(inputEmail.trim().length === 0) {

        emailMsg.innerText = "실제 사용하고 계신 이메일을 입력해 주세요.";
        emailMsg.classList.remove('confirm', 'error');
        checkObj.memberEmail = false;
        memberEmail.value = "";
        return;
    }

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // 입력 받은 이메일이 정규식과 일치하지 않는 경우
    // (알맞은 이메일 형태가 아닌 경우)
    if( !regExp.test(inputEmail) ) {
        emailMsg.innerText = "올바른 이메일 형식으로 입력해 주세요.";
        emailMsg.classList.add('error'); 
        emailMsg.classList.remove('confirm');
        checkObj.memberEmail = false; 
        return;
    }

    fetch("/member/checkEmail?memberEmail=" + inputEmail)
    .then( resp => resp.text() )
    .then( count => {

        if(count == 1) { // 중복 O
            emailMsg.innerText = "이미 사용중인 이메일입니다.";
            emailMsg.classList.add('error');
            emailMsg.classList.remove('confirm');
            checkObj.memberEmail = false; 
            return;
        }
        
        // 중복 X 경우
        emailMsg.innerText = "사용 가능한 이메일입니다. '인증하기' 버튼을 클릭하여 계정을 인증해주세요.";
        emailMsg.classList.add('confirm');
        emailMsg.classList.remove('error'); 
        checkObj.memberEmail = true;
    })
    .catch(error => {
        console.log(error); 
    });

    disabledCheck();
    
})

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


// 인증하기 버튼
emailAuth.addEventListener('click', () => {

    //authDisabledCheck();

    // 재클릭 시 처리
    checkObj.emailAuth = false;
    memberEmail.value = "";
    emailMsg.innerText = "";

    

    // 핸드폰 인증 중에 클릭할 경우
    if(!telAuthDiv.classList.contains('hidden')) {
        checkObj.memberTel = false;
        checkObj.telAuth = false;
        memberTel.value = "";
        smsAuthKey.value = ""; // 인증키 입력 인풋
        telMsg.innerText = "";
        telAuthDiv.classList.add('hidden');
        telAuth.classList.remove('hidden');

        return;
    }

    // 이미 요청 했다면, 인증 영역이 이미 존재할 경우
    if(!emailAuthDiv.classList.contains('hidden')) {
        alert('이미 인증 요청하셨습니다. 다시 입력 후 시도해 주세요.');
        checkObj.memberEmail = false;
        checkObj.emailAuth = false;
        memberEmail.value = "";
        emailAuthKey.value = ""; // 인증키 입력 인풋
        emailMsg.innerText = "";
        emailAuthDiv.classList.add('hidden');

        return;
    }

    // 이메일 유효성 X
    if(!checkObj.memberEmail) {
        alert("이메일을 다시 확인 후 요청해 주세요.");
        emailMsg.innerText = "올바른 이메일 형식으로 입력해 주세요.";
        emailMsg.classList.add('error');
        emailMsg.classList.remove('confirm');
        checkObj.memberEmail = false; 
        return;
    }

    // 첫 요청, 유효성 O

    // 인증하기 없어지고, 확인 버튼 노출
    emailAuthDiv.classList.remove('hidden');
    emailAuth.classList.add('hidden');

    // 클릭시 타이머 숫자 초기화
    min = initMin;
    sec = initSec;

    // 이전 동작 중인 인터벌 클리어
    clearInterval(authTimer);

    // 비동기 메일 발송
    fetch("/email/signup", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 1) {
            console.log("인증 번호 발송 성공");
            alert("입력해 주신 이메일 주소로 인증번호가 발송되었습니다.");
        } else {
            console.log("인증번호 발송 실패");
            alert("인증 번호 발송에 실패했습니다. 다시 시도해 주세요.");
        }
    }).catch(error => {
        console.log(error);
    });

    emailAuthTime.innerText = initTime;
    emailAuthTime.classList.remove('confirm', 'error');

    authTimer = setInterval( () => {

        emailAuthTime.innerText = `${addZero(min)}:${addZero(sec)}`;

        // 0분 0초인 경우
        if(min == 0 && sec == 0) {
            checkObj.emailAuth = false;
            clearInterval(authTimer);
            emailAuthTime.classList.add('error');
            emailAuthTime.classList.remove('confirm');
            return;
        }

        // 0초인 경우(분 감소)
        if(sec == 0) {
            sec = 60;
            min--;
        }

        sec--;
        
    }, 1000);


});

let count = 0;

// 확인 버튼
emailAuthCheck.addEventListener('click', () => {

    // * 분기 처리
    checkObj.emailAuth = false;
    emailMsg.innerText = "";

    // 시간 초과
    if(min === 0 && sec === 0) {
        
        alert("인증번호 입력 제한시간을 초과하였습니다.");

        return;
    }

    if(emailAuthKey.value.length < 6) {
        alert("인증번호를 올바르게 입력해 주세요.");

        return;
    }

    const obj = {
        "email" : memberEmail.value,
        "emailAuth" : emailAuthKey.value
    };

    fetch("/email/checkAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0) {
            
            count++;

            if (count == 3) {
                alert("인증 실패 횟수가 3회를 초과하여 종료됩니다.");
                // 여기에 필요한 종료 로직을 추가합니다. 예: 페이지 이동, 입력 비활성화 등.
                checkObj.memberEmail = false;
                checkObj.emailAuth = false;
                memberEmail.value = "";
                emailAuthKey.value = ""; // 인증키 입력 인풋
                emailMsg.innerText = "";

                emailAuthDiv.classList.add('hidden');
                emailAuth.classList.remove('hidden');

                count = 0;

                return;
            }

            alert(`인증번호가 일치하지 않습니다. ${count}회 (3회 실패 시 종료)`);

            checkObj.emailAuth = false;
            

            return;
        }
        
        clearInterval(authTimer);

        emailMsg.innerText = '\u2713 인증 완료되었습니다.'
        emailMsg.classList.remove('error');
        emailMsg.classList.add('confirm');
        
        checkObj.emailAuth = true;

    })
    
    disabledCheck();
});








// ***************** 6. 전화번호 인증 ***********************







// 입력 유효성 부터

memberTel.addEventListener('input', e => {

    // 텔 인증 후 이메일이 변경될 경우
    checkObj.telAuth = false;
    telMsg.innerText = "";

    inputTel = e.target.value;

    if(inputTel.trim().length === 0) {

        checkObj.memberTel = false;
        memberTel.value = "";

        return;
    }

    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

    if(!regExp.test(inputTel)) {
        telMsg.innerText = "유효한 휴대폰 번호를 입력해 주세요."
        telMsg.classList.add('error');
        telMsg.classList.remove('confirm');
        checkObj.memberTel = false;

        return;
    }

    try {

        fetch("/member/checkTel?memberTel=" + inputTel)
        .then(resp => resp.text())
        .then(count => {

            if(count == 1) { // 중복

                telMsg.innerText = "이미 사용중인 휴대폰 번호입니다. 해당 번호를 계속 사용하시려면 아이디/비밀번호 찾기를 이용해 주세요."
                telMsg.classList.add('error');
                telMsg.classList.remove('confirm');

                checkObj.memberTel = false;
                
                return;
        
            }

            // 중복 X 경우
            telMsg.innerText = "사용 가능한 휴대폰 번호입니다. '인증하기' 버튼을 클릭하여 계정을 인증해주세요.";
            telMsg.classList.add('confirm');
            telMsg.classList.remove('error'); 
            checkObj.memberTel = true;
        })

    } catch(err) {
        console.log(err);
    }

    disabledCheck();
});






// 인증하기 버튼
telAuth.addEventListener('click', () => {

    //authDisabledCheck();

    // 재클릭 시 처리
    checkObj.telAuth = false;
    memberTel.value = "";
    telMsg.innerText = "";


    // 이메일 인증 중 시도
    if(!emailAuthDiv.classList.contains('hidden')) {
        
        checkObj.memberEmail = false;
        checkObj.emailAuth = false;
        memberEmail.value = "";
        emailAuthKey.value = ""; // 인증키 입력 인풋
        emailMsg.innerText = "";
        emailAuthDiv.classList.add('hidden');
        emailAuth.classList.remove('hidden');

        return;
    }

    // 이미 요청 했다면, 인증 영역이 이미 존재할 경우
    if(!telAuthDiv.classList.contains('hidden')) {
        checkObj.memberTel = false;
        checkObj.telAuth = false;
        memberTel.value = "";
        smsAuthKey.value = ""; // 인증키 입력 인풋
        telMsg.innerText = "";
        telAuthDiv.classList.add('hidden');

        return;
    }

    // 이메일 유효성 X
    if(!checkObj.memberTel) {
        alert("휴대폰 번호를 다시 확인 후 요청해 주세요.");
        telMsg.innerText = "올바른 휴대폰 번호 형식으로 입력해 주세요.";
        telMsg.classList.add('error');
        telMsg.classList.remove('confirm');
        checkObj.memberTel = false; 

        return;
    }


    // 인증하기 없어지고, 확인 버튼 노출
    telAuthDiv.classList.remove('hidden');
    telAuth.classList.add('hidden');

    // 클릭시 타이머 숫자 초기화
    min = initMin;
    sec = initSec;

    // 이전 동작 중인 인터벌 클리어
    clearInterval(authTimer);

    // 비동기 SMS 발송
    fetch("/sms/sendOne", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberTel.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 1) {
            console.log("인증 번호 발송 성공");
            alert("입력해 주신 휴대폰 번호로 인증번호가 발송되었습니다.");
        } else {
            console.log("인증번호 발송 실패");
            alert("인증 번호 발송에 실패했습니다. 다시 시도해 주세요.");
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
            return;
        }

        // 0초인 경우(분 감소)
        if(sec == 0) {
            sec = 60;
            min--;
        }

        sec--;
        
    }, 1000);

});

let count2 = 0;

// 확인 버튼
telAuthCheck.addEventListener('click', () => {

    checkObj.telAuth = false;
    telMsg.innerText = "";

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
        "tel" : memberTel.value,
        "telAuth" : smsAuthKey.value
    };

    fetch("/sms/checkSmsAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0) {
            
            count2++;

            if (count2 == 3) {
                alert("인증 실패 횟수가 3회를 초과하여 종료됩니다.");
                // 여기에 필요한 종료 로직을 추가합니다. 예: 페이지 이동, 입력 비활성화 등.
                checkObj.memberTel = false;
                checkObj.telAuth = false;
                memberTel.value = "";
                smsAuthKey.value = ""; // 인증키 입력 인풋
                telMsg.innerText = "";

                telAuthDiv.classList.add('hidden');
                telAuth.classList.remove('hidden');

                count2 = 0;

                return;
            }

            alert(`인증번호가 일치하지 않습니다. ${count2}회 (3회 실패 시 종료)`);

            checkObj.telAuth = false;
            

            return;
        }
        
        clearInterval(authTimer);

        telMsg.innerText = '\u2713 인증 완료되었습니다.'
        telMsg.classList.remove('error');
        telMsg.classList.add('confirm');
        
        checkObj.telAuth = true;

    })


    disabledCheck();
});


// ------------------------------------------------------


// ********** 회원 가입 버튼 클릭 시 **************** / 


const signUpForm = document.getElementById('signUpForm');

// form 제출
signUpForm.addEventListener("submit" , e => {

    for(let key in checkObj) {
        if(!checkObj[key]) {
            let str;

            switch(key) {
                case "memberId" : 
                    str = "아이디가 올바른 형식이 아니거나, 중복된 아이디입니다."; break;
                
                case "memberPw" : 
                    str = "비밀번호가 올바르게 입력되지 않았습니다."; break;
                
                case "memberPwConfirm" :
                    str = "비밀번호가 일치하지 않습니다."; break;
                
                case "memberName" : 
                    str = "이름이 올바른 형식이 아닙니다."; break;

                case "memberNickname" : 
                    str = "닉네임이 올바른 형식이 아니거나, 중복된 닉네임입니다."; break;

                case "memberEmail" :
                    str = "이메일이 올바른 형식이 아니거나, 중복된 이메일입니다."; break;
    
                case "memberTel" :
                    str = "전화번호가 유효하지 않습니다."; break;

                case "emailAuth" :
                    str = "이메일 인증이 정상적으로 진행되지 않았습니다."; break;

                case "telAuth" :
                    str = "휴대폰 인증이 정상적으로 진행되지 않았습니다."; break;
            }

            alert(str);

            document.getElementById(key).focus();

            e.preventDefault();

            return;
        }
    }

    const memberAddress = document.querySelectorAll("[name='memberAddress']");
    const addr0 = memberAddress[0].value.trim().length == 0; // true / false
    const addr1 = memberAddress[1].value.trim().length == 0;
    const addr2 = memberAddress[2].value.trim().length == 0;

    // 모두 true 인 경우만 true 저장
    const result1 = addr0 && addr1 && addr2; // 아무것도 입력 x

    // 모두 false 인 경우만 true 저장
    const result2 = !(addr0 || addr1 || addr2); // 모두 다 입력

    // 모두 입력 또는 모두 미입력이 아니면
    if( !(result1 || result2)) {
        alert("주소를 모두 작성 또는 미작성 해주세요.");
        e.preventDefault();
        return;
    }

});