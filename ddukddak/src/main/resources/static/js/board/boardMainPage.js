const boardMainTop = document.querySelector("#boardMainTop");

if (boardMainTop != null ) {
    boardMainTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    });
}


/* 사이드바 트렌지션 부드럽게 */
document.addEventListener('DOMContentLoaded', function() {
    const category = document.getElementById('categoryNav');
    let lastScrollY = window.scrollY;
    let isTicking = false;
    let targetOffset = 0;
    let animationFrame;

    function onScroll() {
        const currentScrollY = window.scrollY;
        const deltaY = currentScrollY - lastScrollY;

        targetOffset += deltaY * 0.2; // Adjust the factor for desired effect
        targetOffset = Math.max(Math.min(targetOffset, 20), -20); // Limit the movement range

        if (!isTicking) {
            window.requestAnimationFrame(() => {
                category.style.transform = `translateY(${targetOffset}px)`;
                isTicking = false;
            });
            isTicking = true;
        }

        lastScrollY = currentScrollY;
        if (animationFrame) {
            cancelAnimationFrame(animationFrame);
        }

        animationFrame = requestAnimationFrame(resetPosition);
    }

    function resetPosition() {
        targetOffset *= 0.9; // Damping factor to bring back to original position
        if (Math.abs(targetOffset) < 0.1) {
            targetOffset = 0;
            cancelAnimationFrame(animationFrame);
        } else {
            category.style.transform = `translateY(${targetOffset}px)`;
            animationFrame = requestAnimationFrame(resetPosition);
        }
    }

    window.addEventListener('scroll', onScroll);
});



/* 광고 더보기 눌렀을때 */
const slideshow = document.querySelector(".boardAdContainer");
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
        // 슬라이드 번호가 이미지 개수를 넘어가면 1번째 슬라이드로
        if (n < 1) { slideIndex = slides.length; }
        // 슬라이드 번호가 1보다 작으면 마지막 슬라이드로
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
 