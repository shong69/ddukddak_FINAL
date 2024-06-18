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
let reviewImgFiles = [];
function readImgURLs(input) {
    if (input.files && input.files.length > 0) {
        const reviewImgBox = document.getElementById('reviewImgBox');
        const totalReviewImages = reviewImgBox.children.length + input.files.length;

        if (totalReviewImages > 5) {
            alert('리뷰 이미지는 최대 7개까지 추가할 수 있습니다.');
            return;
        }

        Array.from(input.files).forEach(file => {
            // 이미지 파일인지 확인
            if (!file.type.startsWith('image/')) {
                alert('이미지 파일만 선택할 수 있습니다.');
                return;
            }

            reviewImgFiles.push(file); // 파일을 배열에 추가

            const reader = new FileReader();
            reader.onload = function(e) {
                const previewContainer = document.createElement('div');
                previewContainer.classList.add('preview-image-container');

                const preview = document.createElement('img');
                preview.classList.add('preview-image');
                preview.src = e.target.result;
                previewContainer.appendChild(preview);

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('delete-button');
                deleteButton.classList.add('fa-solid');
                deleteButton.classList.add('fa-trash-can');
                deleteButton.onclick = function() {
                    if (confirm("해당 사진을 삭제하시겠습니까?")) {
                        const index = Array.from(reviewImgBox.children).indexOf(previewContainer);
                        reviewImgFiles.splice(index, 1); // 배열에서 파일 제거
                        previewContainer.remove(); // 이미지 삭제
                    }
                };
                previewContainer.appendChild(deleteButton);

                reviewImgBox.appendChild(previewContainer);
            };
            reader.readAsDataURL(file);
        });

        // 리셋 input file field to allow adding the same file again
        input.value = '';
    }
}
function handleFormMission(event) {
    const form = document.getElementById('uploadForm');
    event.preventDefault(); // 기본 제출 동작 막기

    // FormData 객체 생성
    const formData = new FormData(form);

    // subImageFiles를 FormData에 추가
    subImageFiles.forEach(file => {
        formData.append('reviewImgs', file);
    });

    // Remove old subImgs if present
    const subImgsInput = form.querySelector('input[name="subImgs"]');
    if (subImgsInput) {
        subImgsInput.remove();
    }

    // Create new input elements for each file
    subImageFiles.forEach(file => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.setAttribute('name', 'subImgs');
        input.value = file; // This won't work for file objects, so we need to use FormData instead
        form.appendChild(input);
    });
    const productName = document.querySelector("#productName");
    const bigCategory = document.querySelector("#bigCategory");
    const smallcategory = document.querySelector("#smallCategory");
    const price = document.querySelector("#price");

    const contentCountInputVisual = document.getElementsByClassName(".contentCountInput");
    var temp = true;

    for(let i = 0; i < contentCountInputVisual.length; i ++) {
        if(contentCountInputVisual[i].type != 'hidden') {
            temp = false;
            break;
        }
    }

    if(productName.value.trim().length === 0 ||
        bigCategory.options[bigCategory.selectedIndex].value == 'none' ||
        smallcategory.options[smallcategory.selectedIndex].value == 'none' ||
        price.value.trim().length === 0 ||
        selectOption == 0
        ) {
            alert("입력을 완료해주세요");
            event.preventDefault();
        } else if(document.querySelector("#preview").src == 0) {
            alert("대표사진을 등록해주세요");
            event.preventDefault();
        } else if (!temp) {
            alert("옵션 등록을 완료해주세요");
            event.preventDefault();
        } else {
            // 서브 이미지 파일들이 FormData에 추가되었으므로 이제 submit을 호출
            fetch('/partner/seller/product/create', {
                method: 'POST',
                body: formData
            }).then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                alert("등록 완료");
                location.href='/partner/seller/product/create';
            })
            .catch((error) => {
                console.error('Error:', error);
                alert("등록 실패");
            });
        }

    // Allow form to submit
}

let reviewRating;  //별점

const stars = document.querySelectorAll(".fa-star");
console.log(stars);
stars.forEach((star, index) =>{
    star.addEventListener("click",()=>{
        if(!star.classList.contains("fill")){ //누른 별이 비어있는 경우
            for(let i = 0; i<=index; i++){
                console.log(i);
                stars[i].classList.remove('fa-regular');
                stars[i].classList.add('fa-solid');
                stars[i].classList.add('fill');

                
            }
            reviewRating = index + 1;
        }else{
            console.log(star.classList);
            for(let i = 4; i>=index; i--){
                
                stars[i].classList.remove('fa-solid');
                stars[i].classList.add('fa-regular');
                stars[i].classList.remove('fill');
            }
            reviewRating=0;
        }

    })
})

//비동기로 리뷰 보내기
function handleFormMission(event) {
    event.preventDefault();

    document.getElementById("reviewForm").addEventListener("submit", function(event){

        event.preventDefault;
        const formData = new FormData(this);
        
        formData.append("reviewRating", reviewRating);

        fetch("/eCommerce/reviewPost", {
            method : "POST",
            body : formData
        })
        .then(resp => resp.json())
        .then(result =>{
            console.log("결과", result);
            //리뷰 불러오기 함수
            selectReviewList();
        })
    })
}



//2. 페이지 진입 시 리뷰 리스트 불러오기
function selectReviewList() {
    
}

//3. 내가 쓴 리뷰 삭제 가능하기 비동기
