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