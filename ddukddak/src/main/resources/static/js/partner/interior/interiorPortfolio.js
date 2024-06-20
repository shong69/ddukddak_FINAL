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