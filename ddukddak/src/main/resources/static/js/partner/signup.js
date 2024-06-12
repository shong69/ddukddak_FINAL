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

// 2. 상호명
// /^[가-힣a-zA-Z0-9&\-_/.'\s]{1,30}$/;
/*
가-힣: 한글 문자
a-z: 소문자 영어 알파벳
A-Z: 대문자 영어 알파벳
0-9: 숫자
&: 앰퍼샌드
\-: 하이픈
_: 언더바
/: 슬래시
.: 마침표
': 아포스트로피
\s: 공백 문자
*/

const bsName = document.getElementById('bsName');

bsName.addEventListener('input', e => {

    const inputBsName = e.target.value;

    if(inputBsName.trim().length === 0) {
        checkObj.partnerBusinessName = false;
        bsName.value = "";
        return;
    }





});
