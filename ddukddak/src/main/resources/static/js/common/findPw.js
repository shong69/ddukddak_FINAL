// 다음 버튼
const nextBtn = document.getElementById('nextBtn');

// 인증 요청 인풋
const telHiddenDiv = document.getElementById('telHiddenDiv');

// 인증 요청 버튼
const telAuthBtn = document.getElementById('telAuthBtn');

// (첫 히든) 비밀번호 찾기 버튼 영역
const findPwBtnDiv = document.getElementById('findPwBtnDiv');


nextBtn.addEventListener("click", () => {

    if(telHiddenDiv.classList.contains('hidden')) {
        telHiddenDiv.classList.remove('hidden');
        nextBtn.classList.add('hidden');
        
    }
});





if(findPwBtnDiv.classList.contains('hidden')) {

    telAuthBtn.addEventListener("click", () => {
        findPwBtnDiv.classList.remove('hidden');
    });
}