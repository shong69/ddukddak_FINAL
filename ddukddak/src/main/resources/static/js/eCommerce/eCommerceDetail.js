const detailUnder = document.querySelector("#detailUnder");
const detailInfo = document.querySelector("#detailInfo");
const review = document.querySelector("#review");
const qna = document.querySelector("#qna");
const changeInfo = document.querySelector("#changeInfo");

const change = document.querySelector("#change");


detailInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--primary3)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    change.style.display = 'none';
});

review.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--primary3)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    change.style.display = 'none';
});

qna.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--primary3)';
    changeInfo.style.backgroundColor = 'var(--white)';

    change.style.display = 'none';
});

changeInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--primary3)';

    change.style.display = 'flex';
});

// mainImg 요소를 선택합니다
var mainImg = document.querySelector('.mainImg');

// 모든 subImg 요소를 선택합니다
var subImgs = document.querySelectorAll('.subImg');

subImgs[0].classList.add('border');

// 각 subImg에 클릭 이벤트 리스너를 추가합니다
subImgs.forEach(function(subImg) {
    subImg.addEventListener('click', function() {
        // 클릭된 이미지를 메인 이미지로 설정합니다
        mainImg.src = this.src;
        // 클릭된 이미지에 보더를 추가합니다
        subImgs.forEach(function(img) {
            img.classList.remove('border');
        });
        this.classList.add('border');

    });
});

// 화살표 요소를 선택합니다
var leftArrow = document.querySelector('.left-arrow');
var rightArrow = document.querySelector('.right-arrow');

// 왼쪽 화살표에 클릭 이벤트 리스너를 추가합니다
leftArrow.addEventListener('click', function() {
    // 현재 메인 이미지의 src를 가져옵니다
    var currentSrc = mainImg.src;
    // 현재 메인 이미지의 src가 subImgs 중 하나와 일치하는지 확인합니다
    var currentIndex = Array.from(subImgs).findIndex(function(img) {
        return img.src === currentSrc;
    });
    // 왼쪽으로 이동할 이미지의 인덱스를 계산합니다
    var nextIndex = (currentIndex - 1 + subImgs.length) % subImgs.length;
    // 메인 이미지를 이동합니다
    mainImg.src = subImgs[nextIndex].src;
    // 이동된 이미지에 보더를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');
});

// 오른쪽 화살표에 클릭 이벤트 리스너를 추가합니다
rightArrow.addEventListener('click', function() {
    // 현재 메인 이미지의 src를 가져옵니다
    var currentSrc = mainImg.src;
    // 현재 메인 이미지의 src가 subImgs 중 하나와 일치하는지 확인합니다
    var currentIndex = Array.from(subImgs).findIndex(function(img) {
        return img.src === currentSrc;
    });
    // 오른쪽으로 이동할 이미지의 인덱스를 계산합니다
    var nextIndex = (currentIndex + 1) % subImgs.length;
    // 메인 이미지를 이동합니다
    mainImg.src = subImgs[nextIndex].src;
    // 이동된 이미지에 보더를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');
});



function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("mainProductPrice");
const point = document.querySelector("#point");


for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    var priceNum = +productPrice

    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    productPriceElements[i].innerHTML += '원';

    point.innerHTML = `최대 +${Math.floor(priceNum*0.01)}pt`;
}