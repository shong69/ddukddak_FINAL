const showInsertComment = (btn) => {

    // ** 답글 작성 textarea가 한 개만 열릴 수 있도록 만들기 **
    const temp = document.getElementsByClassName("commentInsertContent");
  
    if(temp.length > 0){ // 답글 작성 textara가 이미 화면에 존재하는 경우
  
      if(confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")){
        temp[0].nextElementSibling.remove(); // 버튼 영역부터 삭제
        temp[0].remove(); // textara 삭제 (기준점은 마지막에 삭제해야 된다!)
      
      } else{
        return; // 함수를 종료시켜 답글이 생성되지 않게함.
      }
    }

    // 답글을 작성할 textarea 요소 생성
  const textarea = document.createElement("textarea");
  textarea.classList.add("commentInsertContent");

  const commentContent = document.querySelector("#commentContent");
  
  // 답글 버튼의 부모의 뒤쪽에 textarea 추가
  // after(요소) : 뒤쪽에 추가
  commentContent.parentElement.after(textarea);


  // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
  const commentBtnArea = document.createElement("div");
  commentBtnArea.classList.add("comment-btn-area");

  const insertBtn = document.createElement("button");
  insertBtn.innerText = "등록";
  insertBtn.setAttribute("onclick", "insertChildComment(this)");

  const cancelBtn = document.createElement("button");
  cancelBtn.innerText = "취소";
  cancelBtn.setAttribute("onclick", "insertCancel(this)");


  // 답글 버튼 영역의 자식으로 등록/취소 버튼 추가
  commentBtnArea.append(insertBtn, cancelBtn);

  // 답글 버튼 영역을 화면에 추가된 textarea 뒤쪽에 추가
  textarea.after(commentBtnArea);
}

/* 답글 작성 취소 */
const insertCancel = (cancelBtn) => {

    // 취소 버튼 부모의 이전 요소(textarea) 삭제
    cancelBtn.parentElement.previousElementSibling.remove();
  
    // 취소 버튼이 존재하는 버튼영역 삭제
    cancelBtn.parentElement.remove();
}

/** 답글 (자식 댓글) 등록
 * @param {*} parentCommentNo : 부모 댓글 번호
 * @param {*} btn  :  클릭된 등록 버튼
 */
const insertChildComment = (parentCommentNo, btn) => {

    // 답글 내용이 작성된 textarea
    const textarea = btn.parentElement.previousElementSibling;
  
    // 유효성 검사
    if(textarea.value.trim().length == 0){
      alert("내용 작성 후 등록 버튼을 클릭해 주세요");
      textarea.focus();
      return;
    }

    // ajax를 이용해 댓글 등록 요청
  const data = {
    "commentContent" : textarea.value,
    "boardNo"        : boardNo,
    "memberNo"       : loginMemberNo,  // 또는 Session 회원 번호 이용도 가능
    "parentCommentNo" : parentCommentNo // 부모 댓글 번호
  };

  fetch("/comment", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(data) // data 객체를 JSON 문자열로 변환
  })

  .then(response => response.text())
  .then(result => {

    if(result > 0){
      alert("답글이 등록 되었습니다");
      selectCommentList(); // 댓글 목록을 다시 조회해서 화면에 출력
  
    } else{
      alert("답글 등록 실패");
    }

  })
  .catch(err => console.log(err));

}

// const thumbnails = Array.from(document.getElementsByClassName('thumbnail'));

// function swapImage(clickedThumbnail) {
//     const mainImage = document.getElementById('mainImage');
//     const mainImageSrc = mainImage.src;

//     // 큰 이미지와 클릭된 작은 이미지의 src를 교환합니다.
//     mainImage.src = clickedThumbnail.src;
//     clickedThumbnail.src = mainImageSrc;

//     // 클릭된 작은 이미지의 index를 가져옵니다.
//     const index = parseInt(clickedThumbnail.getAttribute('data-index'));

//     // 배열에서 해당 index의 이미지를 변경합니다.
//     thumbnails[index].src = mainImageSrc;
// }

// // 페이지 로드 시 초기 설정
// document.addEventListener('DOMContentLoaded', () => {
//     thumbnails.forEach((thumbnail, index) => {
//         thumbnail.setAttribute('data-index', index);
//     });
// });


const slideshow = document.querySelector(".boardAdContainer");

if (slideshow != null) {
    let slideIndex = 1;
    let slideInterval;
    
    function showSlides(n) {
        let i;
        let slides = document.getElementsByClassName("mySlides");
        let dots = document.getElementsByClassName("dot");
        if (n > slides.length) {slideIndex = 1}
        if (n < 1) {slideIndex = slides.length}
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
            slides[i].style.opacity = 0.4;
        }
        slides[slideIndex-1].style.display = "block";
        setTimeout(() => {
            slides[slideIndex-1].style.opacity = 1;
        }, 30);
        document.querySelector('.adViewMore').textContent = `${slideIndex} / ${slides.length}`;
    }

    function plusSlides(n) {
        showSlides(slideIndex += n);
        resetInterval();
    }

    function currentSlide(n) {
        showSlides(slideIndex = n);
        resetInterval();
    }

    function resetInterval() {
        clearInterval(slideInterval);
        slideInterval = setInterval(() => {
            plusSlides(1);
        }, 5000);
    }
    
    showSlides(slideIndex);

    resetInterval();

    document.querySelector('.next').addEventListener('click', () => plusSlides(1));
    document.querySelector('.prev').addEventListener('click', () => plusSlides(-1));

    
}

// 팝업 경로
let path;

// 팝업 사이즈
const popupW = 400;
const popupH = 360;

// 팝업 위치
let tempLeft = Math.ceil((window.screen.width - popupW)/2);
let tempTop = Math.ceil((window.screen.height - popupH)/2);

const reportBtn = document.querySelector("#reportBtn");

