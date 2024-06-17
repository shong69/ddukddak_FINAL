const detailUnder = document.querySelector("#detailUnder");
const detailInfo = document.querySelector("#detailInfo");
const review = document.querySelector("#review");
const qna = document.querySelector("#qna");
const changeInfo = document.querySelector("#changeInfo");

const explain = document.querySelector("#explain");
const change = document.querySelector("#change");
const reviewBox = document.querySelector("#reviewBox");

detailInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--primary3)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    explain.style.display = 'flex';
    change.style.display = 'none';
    reviewBox.style.display ='none';
});

review.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--primary3)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    reviewBox.style.display ='flex';
    explain.style.display = 'none';
    change.style.display = 'none';
    
});

qna.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--primary3)';
    changeInfo.style.backgroundColor = 'var(--white)';

    explain.style.display = 'none';
    change.style.display = 'none';
    reviewBox.style.display ='none';
});

changeInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--primary3)';

    explain.style.display = 'none';
    change.style.display = 'flex';
    reviewBox.style.display ='none';
});

// mainImg 요소를 선택합니다
var mainImg = document.querySelector('.mainImg');

// 모든 subImg 요소를 선택합니다
var subImgs = document.querySelectorAll('.subImg');

// subImgs의 첫 번째 요소에 border 클래스를 추가합니다
subImgs[0].classList.add('border');

// 각 subImg에 클릭 이벤트 리스너를 추가합니다
subImgs.forEach(function(subImg) {
    subImg.addEventListener('click', function() {
        // 클릭된 이미지를 메인 이미지로 설정합니다
        mainImg.src = this.src;
        // 클릭된 이미지에 border 클래스를 추가합니다
        subImgs.forEach(function(img) {
            img.classList.remove('border');
        });
        this.classList.add('border');

        // 클릭된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
        this.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
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
    // 이동된 이미지에 border 클래스를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');

    // 이동된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
    subImgs[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
});

// 오른쪽 화살표에 클릭 이벤트 리스너를 추가합니다
rightArrow.addEventListener('click', function() {
    // 현재 메인 이미지의 src를 가져옵니다
    var currentSrc = mainImg.src;
    // 현재 메인 이미지의 src가 subImgs 중 하나와 일치하는지 확인합니다
    var currentIndex = Array.from(subImgs).findIndex(function(img) {
        return img.src === currentSrc;
    });
    // 다음으로 이동할 이미지의 인덱스를 계산합니다
    var nextIndex = (currentIndex + 1) % subImgs.length; // 바로 다음 이미지로 이동
    // 메인 이미지를 이동합니다
    mainImg.src = subImgs[nextIndex].src;
    // 이동된 이미지에 border 클래스를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');

    // 이동된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
    subImgs[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
});


function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("mainProductPrice");
const ProductPrice = document.querySelectorAll(".productPrice");
const point = document.querySelector("#point");


for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    var priceNum = +productPrice

    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    productPriceElements[i].innerHTML += '원';

    point.innerHTML = `최대 +${Math.floor(priceNum*0.01)}pt`;
}

ProductPrice.forEach(elements => {
    let productPrice = elements.textContent.trim(); // 요소의 텍스트 내용 가져오기

    elements.textContent = formatNumberWithCommas(productPrice);
    elements.innerHTML += '원';
})


// 옵션 선택시
// 모든 select 태그를 선택
const optionProductBox = document.querySelector("#optionProductBox");
const mainProductPrice = document.querySelector("#mainProductPrice");
const total = document.querySelector("#total");
const totalCount = document.querySelector("#totalCount");
var selectTags = document.querySelectorAll('.selectOption');

// 모든 select 태그의 옵션값이 0이 아닌지 확인하는 함수
function checkAllSelectValues() {
    var allValuesAreNotZero = true;
    selectTags.forEach(function(select) {
        if (select.value === '0') {
            allValuesAreNotZero = false;
        }
    });
    return allValuesAreNotZero;
}

// 총 가격을 업데이트하는 함수
function updateTotal() {
    let totalPrice = 0;
    const optionPrices = document.querySelectorAll('.optionPrice');
    optionPrices.forEach(function(optionPrice) {
        totalPrice += parseInt(optionPrice.innerText.replace(/[^0-9]/g, ''));
    });
    total.innerText = `총 ${formatNumberWithCommas(totalPrice)}원`;
}

// 총 수량을 업데이트하는 함수
function updateTotalCount() {
    let totalCountNumber = 0;
    const counts = document.querySelectorAll('.count');
    counts.forEach(function(countElement) {
        totalCountNumber += parseInt(countElement.innerText);
    });
    totalCount.innerText = `총 수량: ${totalCountNumber}개`;
}

    // 선택된 옵션 값이 이미 존재하는지 확인하는 함수
    function checkDuplicateOption() {
        const selectedOptions = Array.from(selectTags).map(select => select.options[select.selectedIndex].text).join(',');
        const optionElements = document.querySelectorAll('.options');
        let isDuplicate = false;

        optionElements.forEach(optionElement => {
            if (optionElement.innerText === selectedOptions) {
                isDuplicate = true;
            }
        });

        return isDuplicate;
    }

    // 모든 select 태그의 옵션값이 0이 아닌 경우에만 alert 창 띄우기
    function showAlertIfAllValuesAreNotZero() {
        if (checkAllSelectValues()) {
            if (checkDuplicateOption()) {
                alert('이미 같은 옵션이 선택되었습니다.');
                selectTags.forEach(elements => {
                    elements.value = 0;
                })
                return;
            }

            const div1 = document.createElement("div");
            div1.classList.add("selectOptionProduct");

            const close = document.createElement("h5");
            close.innerText = "X";
            close.classList.add("closeButton");

            
            const options = document.createElement("h4");
            options.classList.add("options");
            selectTags.forEach(elements => {
                const optionValue = document.createElement("input");
                optionValue.classList.add("optionValue");
                optionValue.type = 'hidden';

                div1.append(optionValue);

                optionValue.value = elements.value;
                options.innerText += elements.options[elements.selectedIndex].text + ',';
            })
            options.innerText = options.innerText.slice(0, -1);

            const div2 = document.createElement("div");
            div2.classList.add("underOption");

            const div3 = document.createElement("div");
            div3.classList.add("numberBox");
            const minusButton = document.createElement("button");
            minusButton.innerText = "-";
            minusButton.type = 'button';
            const numberBox = document.createElement("h4");
            numberBox.classList.add("count");
            numberBox.innerText = "1";
            const plusButton = document.createElement("button");
            plusButton.innerText = "+";
            plusButton.type = 'button';
            let count = 1;

            const totalPriceNo = mainProductPrice.attributes[3].value;
            const priceNum = parseInt(totalPriceNo);

            let inputPrice = count * priceNum;
            const optionPrice = document.createElement("h4");
            optionPrice.classList.add("optionPrice");

            div3.append(minusButton);
            div3.append(numberBox);
            div3.append(plusButton);

            div2.append(div3);
            div2.append(optionPrice);
            
            div1.append(close);
            div1.append(options);
            div1.append(div2);

            optionProductBox.append(div1);

            selectTags.forEach(elements => {
                elements.value = 0;
            })

            close.addEventListener("click", () => {
                div1.remove();
                updateTotal();
                updateTotalCount();
            })

            plusButton.addEventListener("click", () => {
                count += 1;
                inputPrice = count * priceNum;
                optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
                numberBox.innerText = count;
                updateTotal();
                updateTotalCount();
            })

            minusButton.addEventListener("click", () => {
                if(count == 1) {
                    alert("상품을 1개 이상 선택해야 합니다.");
                } else {
                    count -= 1;
                    inputPrice = count * priceNum;
                    optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
                    numberBox.innerText = count;
                    updateTotal();
                    updateTotalCount();
                }
            })

            optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
            updateTotal();
            updateTotalCount();
        }
    }

    // 모든 select 태그에 change 이벤트 리스너 추가
    selectTags.forEach(function(select) {
        select.addEventListener('change', showAlertIfAllValuesAreNotZero);
    });


//리뷰 기능
//0. 전체 별점 구해서 총 평점 내보내기
//1. 리뷰 등록 비동기 + (사진, 텍스트, 별점)도 올리기

//2. 페이지 진입 시 리뷰 리스트 불러오기

//3. 내가 쓴 리뷰 삭제 가능하기 비동기
