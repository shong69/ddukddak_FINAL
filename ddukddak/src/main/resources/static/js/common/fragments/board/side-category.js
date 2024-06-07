
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


