const checkObj = {
    "partnerId" : false,
    "partnerPw" : false,
    "partnerPwConfirm" : false,
    "partnerTel" : false,
    "partnerBusinessName" : false, // 상호명
    "partnerBusinessNum" : false, // 사업자등록번호
    "partnerCeoName" : false,
    "authKey" : false,
    "agree" : false
}


// 약관 전체 동의 기초 로직
const agreeAllBtn = document.getElementById('agreeAllBtn');
const agreeButtons = document.querySelectorAll('.agreeBtn');

agreeAllBtn.addEventListener('change', function() {
    if (this.checked) {
        agreeButtons.forEach(function(button) {
            button.checked = true;
        });
    } else {
        agreeButtons.forEach(function(button) {
            button.checked = false;
        });
    }
});

agreeButtons.forEach(function(button) {
    button.addEventListener('change', function() {
        if (document.querySelectorAll('.agreeBtn:checked').length === agreeButtons.length) {
            agreeAllBtn.checked = true;
        } else {
            agreeAllBtn.checked = false;
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

        fetch('/partner/checkBusinessNum?partnerBusinessNum=' + businessNum)
        .then(resp => resp.text())
        .then(count => {

            if(count == 1) { // 중복 O

                businessNumAuthMsg.innerText = '이미 존재하는 사업자등록번호입니다.'
                businessNumAuthMsg.classList.add('error');
                businessNumAuthMsg.classList.remove('confirm');
                checkObj.partnerBusinessNum = false;

                return;
            }

            businessNumAuthMsg.innerText = '인증되었습니다.'
            businessNumAuthMsg.classList.remove('error');
            businessNumAuthMsg.classList.add('confirm');
            checkObj.partnerBusinessNum = true;

        })

    } catch(err) {
        console.log(err);
    }

    bsNum.value = businessNumShow;
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
        bsNameMsg.innerText = "2~30자의 한글, 대/소문자, 숫자, 특수문자(& . , - · ') 사용해 주세요.";
        bsNameMsg.classList.remove('error', 'confirm');
        return;
    }

    const regExp = /^[가-힣a-zA-Z0-9&',.·-\s]{2,30}$/;

    if(!regExp.test(inputBsName)) {
        checkObj.partnerBusinessName = false;
        bsNameMsg.innerText = '유효하지 않은 상호명입니다.'
        bsNameMsg.classList.add('error');
        bsNameMsg.classList.remove('confirm');
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
                return;
            }

            bsNameMsg.innerText = '사용 가능한 상호명입니다.';
            bsNameMsg.classList.remove('error');
            bsNameMsg.classList.add('confirm');
            checkObj.partnerBusinessName = true;
        })

    } catch(err) {
        console.log(err);
    }

});

// ========================================================================

// 3. 이름 유효성 검사 

const ceoName = document.getElementById('ceoName');


// 이름 입력 시
ceoName.addEventListener('input', e => {

    const inputCeoName = e.target.value;

    if(inputCeoName.trim().length === 0) {
        checkObj.partnerCeoName = false;
        ceoName.value = ''; // 공백 방지
        return;
    }

    // 이름 2~10글자 한글
    const regExp = /^[가-힣]{2,10}$/;

    if(!regExp.test(inputCeoName)) {
        checkObj.partnerCeoName = false;
        return;
    }

    checkObj.partnerCeoName = true;

});