/*이미지 크기 조절*/
function resizeImageTo(img, maxWidth, maxHeight) {
    // 이미지의 원본 크기
    var imgWidth = img.width;
    var imgHeight = img.height;

    // 이미지를 지정된 최대 너비와 높이로 맞추기 위한 비율 계산
    var widthRatio = maxWidth / imgWidth;
    var heightRatio = maxHeight / imgHeight;

    // 이미지가 지정한 크기보다 큰 경우 축소
    // 이미지가 지정한 크기보다 작은 경우 확대
    if (imgWidth > maxWidth || imgHeight > maxHeight) {
        var ratio = Math.min(widthRatio, heightRatio);
        img.width = imgWidth * ratio;
        img.height = imgHeight * ratio;
    } else {
        var ratio = Math.max(widthRatio, heightRatio);
        img.width = imgWidth * ratio;
        img.height = imgHeight * ratio;
    }
}
var img = document.getElementById('.profile-img');
resizeImageTo(img, 100, 100);



/*회원 정보 버튼 클릭 시 화면 전환*/

/*변경 버튼 */
const myPageMainBtn = document.querySelector(".myPageMainBtn");
const myPageSubBtns = document.querySelectorAll(".myPageSubBtn");
/*취소 버튼 */
const cancelBtns = document.querySelectorAll(".cancel");
/*변경 확인 버튼 */
const confirmBtns = document.querySelectorAll(".confirm");

//변경 버튼 클릭 시 display 변경
//작성 상태에서 취소 시 이전 상태로 돌아가기(input 값 없애고 display:""처리)
//등록 시 해당 영역 폼 제출 비동기

//변경 버튼 클릭 시 수정 영역이 보이도록
myPageMainBtn.addEventListener("click",()=>{
    const parentTd = myPageMainBtn.parentElement.parentElement;
    parentTd.style.display = "none";
    parentTd.nextElementSibling.style.display = "";
});
myPageSubBtns.forEach(btn => {
    btn.addEventListener("click", ()=>{
        const parentTd = btn.parentElement.parentElement.parentElement.parentElement;
        parentTd.style.display = "none";
        parentTd.nextElementSibling.style.display="";
        parentTd.log("td가 보여요");
    });
});

//취소 버튼 클릭 시 dispaly=""
//사진 변경 시 
const profileCancelBtn = document.querySelector(".profile-del-btn");
profileCancelBtn.addEventListener("click", e=>{
    const parentTd = e.target.parentElement.parentElement;
    parentTd.style.display="none";
    parentTd.previousElementSibling.style.display= "";
});

//취소 버튼 클릭 시(나머지의 경우) 
cancelBtns.forEach(btn =>{
    btn.addEventListener("click",()=>{
        const parentTd =btn.parentElement.parentElement.parentElement;
        parentTd.style.display ="none";
        parentTd.previousElementSibling.style.display="";

        //작성했던 input 값 없애기
        const inputs = parentTd.querySelectorAll('input');
        inputs.forEach(input =>{
            input.value = '';
        });
    })
});

//확인 버튼 클릭 시 display="" 처리, form 제출하기(비동기)


