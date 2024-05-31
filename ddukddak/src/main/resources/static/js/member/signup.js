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
    "memberEmail" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "emailAuth" : false,
    "telAuth" : false
};

// -------------------------------------------------------------------------------












// ***************** 이메일 인증 ***********************

// 이메일 인증하기 버튼 클릭 시 이벤트
const emailAuth = document.getElementById('emailAuth');
const emailAuthDiv = document.getElementById('emailAuthDiv');
const emailAuthCheck= document.getElementById('emailAuthCheck');

// 인증하기 버튼
emailAuth.addEventListener('click', () => {

    emailAuthDiv.classList.remove('hidden');
    emailAuth.classList.add('hidden');

});

// 확인 버튼
emailAuthCheck.addEventListener('click', () => {

    emailAuthDiv.classList.add('hidden');

    // 인증 실패 시 인증하기 버튼 다시 살아나는 로직 구성 필요
    if(!checkObj.emailAuth) {
        emailAuthDiv.classList.add('hidden');
        emailAuth.classList.remove('hidden');

    }
});


// ------------------------------------------------------









// ***************** 전화번호 인증 ***********************


// 전화번호 인증하기 버튼 클릭 시 이벤트
const telAuth = document.getElementById('telAuth');
const telAuthDiv = document.getElementById('telAuthDiv');
const telAuthCheck = document.getElementById('telAuthCheck');

// 인증하기 버튼
telAuth.addEventListener('click', () => {

    telAuthDiv.classList.remove('hidden');
    telAuth.classList.add('hidden');


});

// 확인 버튼
telAuthCheck.addEventListener('click', () => {

    telAuthDiv.classList.add('hidden');

    // 인증 실패 시 인증하기 버튼 다시 살아나는 로직 구성 필요
    if(!checkObj.telAuth) {
        telAuthDiv.classList.add('hidden');
        telAuth.classList.remove('hidden');
    }
});


// ------------------------------------------------------