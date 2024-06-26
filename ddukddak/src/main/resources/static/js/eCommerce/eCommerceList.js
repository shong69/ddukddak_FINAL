function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
}

const orderSorting = document.getElementById('orderSorting');

orderSorting.addEventListener('change', function() {
    const selectedOption = this.value;
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('sort', selectedOption);
    window.location.href = currentUrl;
});


var currentUrl = window.location.href

var queryString = currentUrl.split('?')[1];

if (queryString) {

    var queryParams = queryString.split('&');


    for (var i = 0; i < queryParams.length; i++) {
        if (queryParams[i].indexOf('sort=') !== -1) {

            var sortValue = queryParams[i].split('=')[1];

            orderSorting.value = sortValue;

        }
    }
} else {
    console.log("쿼리 파라미터가 없습니다.");
}



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

document.addEventListener('DOMContentLoaded', function() {
    const category = document.getElementById('ad3');
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
