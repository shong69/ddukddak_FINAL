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
    openReviewCheck(productNo);
    
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

//1. 리뷰 등록 비동기 + (사진, 텍스트, 별점)도 올리기
let reviewImgFiles = [];
function readImgURLs(input) {
    if (input.files && input.files.length > 0) {
        const reviewImgBox = document.getElementById('reviewImgBox');
        const totalReviewImages = reviewImgBox.children.length + input.files.length;

        if (totalReviewImages > 6) {
            alert('리뷰 이미지는 최대 5개까지 추가할 수 있습니다.');
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

//2. 전체 별점 구해서 총 평점 내보내기
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

//3. 비동기로 리뷰 작성(+수정)하기
function handleFormMissionReview(event) {
    event.preventDefault();

    const form = document.getElementById('reviewForm');
    const formData = new FormData(form);

    // 리뷰 이미지 파일을 FormData에 추가
    reviewImgFiles.forEach(file => {
        formData.append('reviewImgs', file);
    });

    // 별점 추가
    formData.append('reviewRating', reviewRating);
    formData.append('productNo', productNo);
    // reviewNo 가져오기
    const reviewNo = document.querySelector("#hiddenReviewNo").value;
    const url = reviewNo ? '/eCommerce/updateReview' : '/eCommerce/reviewPost';

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);  //data 값 보고 조건문 추가하기
        alert(reviewNo ? "리뷰가 수정되었습니다." : "리뷰가 등록되었습니다.");
        selectReviewList(productNo); // 리뷰 목록 새로 고침
    })
    .catch((error) => {
        console.error('Error:', error);
        alert(reviewNo ? "리뷰 수정에 실패했습니다." : "리뷰 등록에 실패했습니다.");
    });
}




//리뷰 영역 선택 시 선택한 멤버가 리뷰 작성 권한이 있는지(상품id과 주문테이블에 겹치는지) 확인
async function openReviewCheck(productNo){

    //리뷰 작성 권한 비동기로 알아오기
    const reviewForm = document.querySelector("#reviewForm");
    const optionDiv = document.querySelector("#optionDiv");

    //로그인 여부 확인
    if(typeof loginMember === 'undefined' || loginMember === null){
        reviewForm.classList.add('display-none');
        selectReviewList(productNo);
        return;
    }
    const reviewAuthList = await checkReviewAuth(productNo);
    console.log(reviewAuthList);
    if(reviewAuthList&& reviewAuthList.length > 0){//작성 하지 않은 리뷰가 존재하는 경우
        //id=reviewForm 노출시키기
        reviewForm.classList.remove('display-none');
        
        
        const existingSelect = document.querySelector("select.reviewOptSelect");
        if(existingSelect) {
            optionDiv.removeChild(existingSelect);
        }
        const select = document.createElement("select");
        select.classList.add("reviewOptSelect");
        
        //옵션 추가하기
        reviewAuthList.forEach(item =>{
            const option = document.createElement("option");
            option.value = `${item.orderItemNo}`;
            option.innerHTML = `주문일:${item.orderDate} | 주문옵션:${item.optionValue} ${item.orderQuantity}개`;
            select.append(option);
            console.log(option);
        })
        optionDiv.append(select);
    }else{
        reviewForm.classList.add('display-none');
    }
    
    //리뷰 리스트 알아오기
    selectReviewList(productNo);
}

//리뷰 작성 가능 주문 상품 목록
async function checkReviewAuth(productNo){
    try {
        const response = await fetch("/eCommerce/checkReviewAuth?productNo=" + productNo);
        const result = await response.json();
        return result;
    } catch (error) {
        console.error('Error:', error);
        return [];
    }
}


//4.리뷰 리스트 불러오기
function selectReviewList(productNo){
    console.log(productNo);
    fetch("/eCommerce/selectReviewList?productNo="+productNo)
    .then(resp=>resp.json())
    .then(result => {
        console.log(result);
        const reviewList = result;
        const reviewContainer = document.getElementById("reviewContainer");
        reviewContainer.innerHTML = "";

        if(reviewList == null || reviewList.length == 0){
            const li = document.createElement("li");
            li.classList.add("noReviewLi");
            const div = document.createElement("div");
            div.innerHTML = "리뷰가 존재하지 않습니다";
            div.classList.add("noReviewArea");
            li.append(div);
        }else{
            reviewList.forEach(review=>{
                const li = document.createElement("li");
                li.classList.add("reviewRow");

                if(review.reviewDelFl == 'Y'){
                    const delDiv = document.createElement("div");
                    delDiv.classList.add("deleteReviewArea");
                    li.append(delDiv);
                }else{
                    const reviewWrite =document.createElement("div");
                    reviewWrite.classList.add("reviewWrite");

                    const infoArea = document.createElement("div");
                    infoArea.classList.add("infoArea");

                    const memberId = document.createElement("span");
                    memberId.classList.add("memberId");
                    memberId.textContent = loginMember.memberId; //멤버 아이디 적기
                    const commentWriteDate = document.createElement("span");
                    commentWriteDate.classList.add("commentWriteDate");
                    commentWriteDate.textContent = review.commebtWriteDate; //리뷰 작성일 적기
                    infoArea.append(memberId, commentWriteDate);

                    const editArea = document.createElement("div");
                    editArea.classList.add("editArea");
                    editArea.style.display=review.memberNo == loginMemberNo?"block":"none";  // 작성한 멤버면 display = "block"; 주기

                    const updateBtn = document.createElement("button");
                    updateBtn.classList.add("updateBtn");
                    updateBtn.textContent = "수정";
                    updateBtn.onclick(updateBtn(review.reviewNo));
                    const delBtn = document.createElement("button");
                    delBtn.classList.add("delBtn");
                    delBtn.textContent = "삭제";
                    delBtn.onclick(delReview(review.reviewNo));

                    //수정할 때 영역
                    console.log(loginMember.memberNo);
                    editArea.append(updateBtn, delBtn);                    

                    reviewWrite.append(infoArea, editArea);
                    //이미지 배열 추가
                    if (review.imgList && review.imgList.length > 0) {
                        const imgRow = document.createElement("div");
                        imgRow.classList.add("imgRow");

                        review.imgList.forEach(imgUrl => {
                            const img = document.createElement("img");
                            img.src = imgUrl;
                            img.classList.add("reviewImg");
                            imgRow.append(img);
                        });

                        reviewWrite.append(imgRow);
                    }
                    li.append(reviewWrite);
                }
                reviewContainer.append(li);

            });
        }

    })
    .catch(error => console.error('Error:', error));
}



//4. 내가 쓴 리뷰 삭제 비동기
function delReview(reviewNo){
    if(confirm("정말 리뷰를 삭제하시겠습니까?")){
        fetch(`/eCommerce/delReview?reviewNo=${reviewNo}`, {
            method: 'DELETE'
        })
        .then(resp => resp.text())
        .then(result => {
            if (result ==1) {
                alert("리뷰가 삭제되었습니다.");
                selectReviewList(productNo); // 리뷰 목록 새로 고침
            } else {
                alert("리뷰 삭제에 실패했습니다.");
            }
        })
        .catch(error => console.error('Error:', error));
    }

}

//5. 내가 쓴 리뷰 수정하기 비동기 -> 
function updateBtn(reviewNo) {
    //리뷰 불러오기
    fetch("/eCommerce/reloadReview?reviewNo="+reviewNo)
    .then(resp => resp.json())
    .then(result =>{
        if(result == null){
            //리뷰 불러오기 실패
            alert("리뷰 수정 실패");
            return;
        }
        //#review 에 해당 내용 넣기

        const review = result;

        const reviewForm = document.querySelector("#reviewForm");
        if(reviewForm.classList.contains("display-none")){
            reviewForm.classList.remove("display-none");
        }
        const commentContent = document.querySelector("#commentContent");
        commentContent.value = review.reviewContent;

        // 별점 채우기
        const stars = document.querySelectorAll(".fa-star");
        stars.forEach((star, index) => {
            if (index < review.reviewRating) {
                star.classList.remove('fa-regular');
                star.classList.add('fa-solid');
                star.classList.add('fill');
            } else {
                star.classList.remove('fa-solid');
                star.classList.add('fa-regular');
                star.classList.remove('fill');
            }
        });
        reviewRating = review.reviewRating; // 리뷰 별점 저장

        const reviewImgBox = document.querySelector('#reviewImgBox');
        reviewImgBox.innerHTML = ''; // 기존 이미지 초기화
        reviewImgFiles = []; // 이미지 파일 배열 초기화
        if(review.imgList && review.imgList.length>0){
            review.imgList.forEach(img=>{
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
            });
        }
        reviewForm.scrollIntoView({behavior : 'smooth'});

        document.querySelector("#hiddenReviewNo").value = reviewNo;
    });

}

