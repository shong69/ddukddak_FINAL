document.addEventListener('DOMContentLoaded', function() {
    const interiorDetailViewBtns = document.querySelectorAll('.interiorDetailViewBtn');
    
    if (interiorDetailViewBtns) {
        interiorDetailViewBtns.forEach(item => {
            item.addEventListener('click', function() {
                const portfolioNo = this.getAttribute('data-portfolio-no');
                
                if (portfolioNo != 0) {
                    const url = `/interior/interiorPortfolio/${portfolioNo}`;
                    window.location.href = url;
                } else {
                    alert("해당 시공사의 포트폴리오가 존재하지 않습니다.");
                    console.error('Portfolio number not found');
                    return;
                }
            });
        });
    }
});


document.addEventListener('DOMContentLoaded', function() {
    const category = document.getElementById('ad1');
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
                if(category != null) {
                    category.style.transform = `translateY(${targetOffset}px)`;
                }
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
            if(category != null) {
                category.style.transform = `translateY(${targetOffset}px)`;
            }
            animationFrame = requestAnimationFrame(resetPosition);
        }
    }

    window.addEventListener('scroll', onScroll);
});

document.addEventListener('DOMContentLoaded', function() {
    const category = document.getElementById('ad2');
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
                if(category != null) {
                    category.style.transform = `translateY(${targetOffset}px)`;
                }
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
            if(category != null) {
                category.style.transform = `translateY(${targetOffset}px)`;
            }
            animationFrame = requestAnimationFrame(resetPosition);
        }
    }

    window.addEventListener('scroll', onScroll);
});