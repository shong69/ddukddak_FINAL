const boardMainTop = document.querySelector("#boardMainTop");

if (boardMainTop != null) {
    boardMainTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });
}

/* 사이드바 트렌지션 부드럽게 */
document.addEventListener('DOMContentLoaded', function () {
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
        console.log("Showing slide number:", n);
        let i;
        let slides = document.getElementsByClassName("mySlides");

        if (slides != null && slides.length > 0) {
            if (n > slides.length) { slideIndex = 1; }
            if (n < 1) { slideIndex = slides.length; }

            for (i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
                slides[i].style.opacity = 0.2;
            }

            slides[slideIndex - 1].style.display = "block";
            setTimeout(() => {
                slides[slideIndex - 1].style.opacity = 1;
            }, 30);

            if (adViewMore != null) {
                adViewMore.textContent = `${slideIndex} / ${slides.length}`;
            }
        } else {
            console.log("No slides found.");
        }
    }

    function plusSlides(n) {
        console.log("Moving slides by:", n);
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
        console.log("Resetting interval");
        clearInterval(slideInterval);
        slideInterval = setInterval(() => {
            plusSlides(1);
        }, 5000);
    }

    showSlides(slideIndex);
    resetInterval();

    if (next != null) {
        next.addEventListener('click', () => {
            console.log("Next button clicked");
            plusSlides(1);
            resetInterval();
        });
    }

    if (prev != null) {
        prev.addEventListener('click', () => {
            console.log("Prev button clicked");
            plusSlides(-1);
            resetInterval();
        });
    }
}

/* 숨긴처리 */
document.addEventListener('DOMContentLoaded', (event) => {
    const tipBoardMainContainers = document.querySelectorAll('.tipBoardMainContainer');
    const viewMoreBtn = document.querySelector("#tipBoardViewMore");
    let currentVisibleIndex = 0; // Tracks the currently visible list

    // Show the first container
    if (tipBoardMainContainers.length > 0) {
        tipBoardMainContainers[0].classList.remove('hidden');
    }

    // Handle "View More" button click
    if (viewMoreBtn != null) {
        viewMoreBtn.addEventListener("click", () => {
            if (currentVisibleIndex < tipBoardMainContainers.length - 1) {
                currentVisibleIndex++;
                tipBoardMainContainers[currentVisibleIndex].classList.remove('hidden'); // Show the next hidden list

                // If all lists are shown, hide the button
                if (currentVisibleIndex >= tipBoardMainContainers.length - 1) {
                    viewMoreBtn.style.display = 'none';
                }
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', (event) => {
    const portListMainContainers = document.querySelectorAll('.portListMainContainer');
    const tipBoardMainContainer = document.querySelectorAll('.tipBoardMainContainer');
    const viewMoreBtn = document.querySelector("#portListViewMore");
    let currentVisibleIndex = 0; // Tracks the currently visible list

    // Show the first container
    if (portListMainContainers.length > 0) {
        portListMainContainers[0].classList.remove('hidden');
    }

    if (tipBoardMainContainer.length > 0) {
        tipBoardMainContainer[0].classList.remove('hidden');
    }

    // Handle "View More" button click
    if (viewMoreBtn != null) {
        viewMoreBtn.addEventListener("click", () => {
            if (currentVisibleIndex < portListMainContainers.length - 1 && currentVisibleIndex < tipBoardMainContainer.length) {
                currentVisibleIndex++;
                portListMainContainers[currentVisibleIndex].classList.remove('hidden'); // Show the next hidden list
                tipBoardMainContainer[currentVisibleIndex].classList.remove('hidden');
                // If all lists are shown, hide the button
                if (currentVisibleIndex >= portListMainContainers.length - 1 && currentVisibleIndex >= tipBoardMainContainer.length - 1) {
                    viewMoreBtn.style.display = 'none';
                }
            }
        });
    }
});

const likeCount = document.querySelector("#likeCount");
const boardLike = document.querySelector("#boardLike")

if (boardLike != null) {
    boardLike.addEventListener("click", e => {
        if (loginMemberNo == null) {
            alert("로그인 후 이용해주세요.");
            return;
        }

        const obj = {
            "memberNo": loginMemberNo,
            "boardNo": boardNo,
            "likeCheck": likeCheck
        }

        // 좋아요 INSERT / DELETE 비동기 요청
        fetch("/board/like", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(obj)
        })
            .then(resp => resp.text())
            .then(count => {
                console.log(count);

                if (count == -1) {
                    console.log("좋아요 실패");
                    return;
                }

                likeCheck = likeCheck == 0 ? 1 : 0;

                e.target.classList.toggle("fa-regular");
                e.target.classList.toggle("fa-solid");

                likeCount.innerText = count;
            });
    })
}
