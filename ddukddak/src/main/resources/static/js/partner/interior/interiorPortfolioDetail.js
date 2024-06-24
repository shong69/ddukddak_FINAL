

const portDetailTop = document.querySelector("#portDetailTop");

if (portDetailTop != null ) {
    portDetailTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    });
}

/* 이미지 */
const slideshow = document.querySelector(".projectImgContainer");
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

document.querySelector("#interiorMatchBtn").addEventListener("click", () => {

  console.log(loginMember);

  if(loginMember == null) {
    alert("로그인 후 사용가능한 서비스입니다.");
    redirectToLogin();
    return;
  }

  location.href = "/myChat/chatWithInter";

});
