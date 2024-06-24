/* 탑 버튼 */

const portTopButton = document.querySelector("#portTop");

if (portTopButton != null ) {
    portTopButton.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    });
}

/* 프로젝트 더보기 */
const viewMoreBtn = document.querySelector("#interiorProjectViewMore");
const portBotContainer = document.querySelector(".botContainer");
const portListMainContainer = document.querySelector(".portListMainContainer");


let currentVisibleIndex = 0; // Keeps track of currently visible containers

if (viewMoreBtn != null) {
    viewMoreBtn.addEventListener("click", () => {
        if (currentVisibleIndex < portListMainContainer.length) {
            portListMainContainer[currentVisibleIndex].style.display = 'block'; // Show the next hidden container
            currentVisibleIndex++;
            if (currentVisibleIndex >= portListMainContainer.length) {
                viewMoreBtn.style.display = 'none'; // Hide the button if all containers are shown
            }
        }
    });
}

// if(portListMainContainer != null) {

//     // Initially hide all portListMainContainer elements
//     portListMainContainer.forEach((container, index) => {
//         if (index >= currentVisibleIndex) {
//             container.style.display = 'none';
//         }
//     });
// }

const instertBtn = document.querySelector("#insertBtn");

if (instertBtn != null) {
    instertBtn.addEventListener("click", () => {
        location.href = "/partner/registProject";
    });
}

/* 이미지 */
const slideshow = document.querySelector(".slideshow-container");
const adViewMore = document.querySelector('.adViewMore');

const next = document.querySelector('.next');
const prev = document.querySelector('.prev');

if (slideshow != null) {
  let slideIndex = 1;
  let slideInterval;

  function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("mySlides");
    if (n > slides.length) { slideIndex = 1; }
    if (n < 1) { slideIndex = slides.length; }
    for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";
      slides[i].style.opacity = 0.4;
    }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(() => {
      slides[slideIndex - 1].style.opacity = 1;
    }, 30);
    if (adViewMore != null) {

      adViewMore.textContent = `${slideIndex} / ${slides.length}`;
    }
  }

  function plusSlides(n) {
    slideIndex += n;
    showSlides(slideIndex);
    resetInterval();
  }

  function currentSlide(n) {
    slideIndex = n;
    showSlides(slideIndex);
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
  if (next != null) {
    next.addEventListener('click', () => {
      plusSlides(1);
      resetInterval();
    });
  }

  if (prev != null) {
    prev.addEventListener('click', () => {
      plusSlides(-1);
      resetInterval();
    });
  }
}

const interiorMatchBtn = document.querySelector("#interiorMatchBtn");


if(interiorMatchBtn != null) {
  
  interiorMatchBtn.addEventListener("click", () => {
    
    console.log(loginMember);
  
    if(loginMember == null) {
      alert("로그인 후 이용가능한 서비스입니다.");
      redirectToLogin();
      return;
    }
  
    location.href = "/myChat/chatWithInter";

  });
    
}



/* 시공사 프로필 이미지 변경 */
const myPageMainBtn = document.querySelector(".myPageMainBtn");
const confirmBtn = document.querySelector(".confirm");

if (myPageMainBtn != null) {

  myPageMainBtn.addEventListener("click", () => {
    const parentCompo = myPageMainBtn.parentElement.parentElement;
    console.log(parentCompo);
    parentCompo.style.display="none";
    parentCompo.nextElementSibling.style.display = "";
  });
}

//취소 버튼 클릭 시 dispaly=""
//사진 변경 시 
const profileCancelBtn = document.querySelector(".profile-del-btn");
if(profileCancelBtn != null) {

  profileCancelBtn.addEventListener("click", e=>{
      const parentCompo = e.target.parentElement.parentElement.parentElement;
      console.log(parentCompo);
      parentCompo.style.display="none";
      console.log(parentCompo.previousElementSibling);
      parentCompo.previousElementSibling.style.display= "";
  });
}

//1. 사진 변경
const profile = document.querySelector("#profile-form");

let statusCheck = -1; //이미지 상태 변수 (-1:초기 , 0:삭제, 1:새이미지 선택)

let backupInput; //input type="file" 값 변경 시 변경된 상태 저장 변수

if(profile != null){
  const profileImg =document.querySelector("#profile-img");
  let imageInput = document.querySelector("#inputImage");
  const deleteImage = document.querySelector(".delete-image");

  const changeImageFn = e =>{
      const maxSize =  1024 * 1024 * 5;   //5MB
      const file = e.target.files[0];
      console.log(file);
      //*업로드 파일이 없는 경우(취소한 경우)
      if(file == undefined){ 
          console.log("파일 로드 후 취소");

          const temp = backupInput.cloneNode(true);

          imageInput.after(backupInput);
          imageInput.remove();
          imageInput = backupInput;  //백업 대입하기
          imageInput.addEventListener("change", changeImageFn);
          backupInput = temp;
          return;
      }

      //*선택된 파일이 최대 크기를 초과한 경우
      if(file.size > maxSize) {
          alert("5MB 이하의 이미지 파일을 등록해 주세요.");

          if(statusCheck == 1){ //초기상태에서는 빈칸으로 바꾸기
              imageInput.value ='';
          }else{
              const temp = backupInput.cloneNode(true); //백업의 백업
              imageInput.after(backupInput);
              imageInput.remove();
              imageInput = backupInput;
              imageInput.addEventListener("change", changeImageFn);
              backupInput = temp; //백업의 백업을 백업으로 바꾸기
          }
          return;
      }
          //*선택한 이미지 미리볼 수 있게 만들기
      const reader = new FileReader();

      reader.readAsDataURL(file); //읽어온 파일을 BASE64 인코딩 형태로 읽어와 변수에 저장
      reader.addEventListener("load", e=>{
          const url = e.target.result;
          profileImg.setAttribute("src", url);
          statusCheck = 1;  //이미지 선택 상태로 변경
          backupInput = imageInput.cloneNode(true); //백업하기
      });
  };
  //이미지 등록 input의 값이 바뀔 때마다 위 과정 거치기
  imageInput.addEventListener("change", changeImageFn);


  //*x버튼 클릭 시 기본 이미지로 변경하기
  deleteImage.addEventListener("click", ()=>{
      profileImg.src = "/images/default/main.jpg";
      imageInput.value = "";
      backupInput = undefined;
      statusCheck = 0; //삭제 상태
  });
  
  //*form(id=profile) 제출 시
  profile.addEventListener("submit", e=>{
      let flag = true;
      if(loginMemberProfileImg == null && statusCheck==1) flag=false;
      if(loginMemberProfileImg != null && statusCheck ==0) flag = false;
      if(loginMemberProfileImg != null && statusCheck == 1) flag = false;
      
      if(flag){
          e.preventDefault();
          alert("이미지가 변경되지 않았습니다.");
          return;
      }
      alert("이미지가 변경되었습니다.");
  });

}