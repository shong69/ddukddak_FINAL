const detailUnder = document.querySelector("#detailUnder");
const detailInfo = document.querySelector("#detailInfo");
const review = document.querySelector("#review");
const qna = document.querySelector("#qna");
const changeInfo = document.querySelector("#changeInfo");

const explain = document.querySelector("#explain");
const change = document.querySelector("#change");
const reviewBox = document.querySelector("#reviewBox");
const qnaBox = document.querySelector("#qnaBox");

detailInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--primary3)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    explain.style.display = 'flex';
    change.style.display = 'none';
    reviewBox.style.display ='none';
    qnaBox.style.display = 'none';
});

review.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--primary3)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    reviewBox.style.display ='flex';
    explain.style.display = 'none';
    change.style.display = 'none';
    qnaBox.style.display = 'none';
    openReviewCheck(productNo);
    
});

qna.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--primary3)';
    changeInfo.style.backgroundColor = 'var(--white)';

    qnaBox.style.display = 'flex';
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
    qnaBox.style.display = 'none';
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




//-------------------------------------------------------------

//1. 리뷰 등록 비동기 + (사진, 텍스트, 별점)도 올리기
//  1)사진 등록하기
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


//   2) 전체 별점 구해서 총 평점 내보내기
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
console.log(typeof productNo);

//  3) 비동기로 리뷰 작성(+수정)하기
function handleFormMissionReview(event) {
    event.preventDefault();

    const form = document.getElementById('reviewForm');
    const formData = new FormData(form);
    console.log(typeof formData);

    // 리뷰 이미지 파일을 FormData에 추가
    reviewImgFiles.forEach(file => {
        formData.append('reviewImgs', file);
    });
    
    //orderItemNo 추가
    const orderItemNoSelect = document.querySelector(".reviewOptSelect");
    const option = orderItemNoSelect.options[orderItemNoSelect.selectedIndex]; 
    const optionValue = orderItemNoSelect.options[orderItemNoSelect.selectedIndex].getAttribute("data-value"); //옵션명 가져오기
    console.log(optionValue);//옵션명 불러오기 여부 확인
    formData.append("orderItemNo", option.value);
    formData.append("optionValue", optionValue); 


    formData.append('reviewRating', reviewRating); // 별점 추가
    formData.append('productNo', productNo); //상품 번호 추가

    // FormData 내용을 콘솔에 출력 (모든 키와 값을 출력)
    for (let [key, value] of formData.entries()) {
        console.log(key, typeof value);
    }

    // reviewNo 가져오기
    const reviewNo = document.querySelector("#hiddenReviewNo").value;
    console.log(reviewNo?1:2);
    const url = reviewNo ? "/eCommerce/updateReview" : "/eCommerce/reviewPost";
    console.log(typeof url);

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(result => {
        console.log('result:', result);  
        if(result > 0){
            alert(reviewNo ? "리뷰가 수정되었습니다." : "리뷰가 등록되었습니다.");
            getReviewCount(productNo); //리뷰 개수 업데이트
            selectReviewList(productNo); // 리뷰 목록 새로 고침
        }else{
            alert(reviewNo ? "리뷰 수정 실패" : "리뷰 등록 실패");
            return;
        }
    })
    .catch((error) => {
        console.error('Error:', error);
        alert(reviewNo ? "리뷰 수정에 실패했습니다." : "리뷰 등록에 실패했습니다.");
    });

}





//2. 리뷰 열었을 때 선택한 멤버가 리뷰 작성 권한이 있는지(상품id과 주문테이블에 겹치는지) 확인
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
            option.setAttribute("data-value", `${item.optionValue}`); //data-value에 optionvalue 넣어서 옵션명 리뷰에 저장하기
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

//3. 리뷰 작성 가능 주문 상품 목록
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


//4. 리뷰 리스트 불러오기
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
                    memberId.textContent = `@${review.memberId}`; //멤버 아이디 적기
                    const commentWriteDate = document.createElement("span");
                    commentWriteDate.classList.add("commentWriteDate");

                    commentWriteDate.textContent = review.reviewCreateDate; //리뷰 작성일 적기

                    infoArea.append(memberId, commentWriteDate);

                    const editArea = document.createElement("div");
                    editArea.classList.add("editArea");
                    editArea.style.display=loginMemberNo && review.memberNo == loginMemberNo?"block":"none";  // 작성한 멤버면 display = "block"; 주기

                    const updateBtn = document.createElement("button");
                    updateBtn.classList.add("updateBtn");
                    updateBtn.textContent = "수정";
                    updateBtn.onclick=function(){
                        updateFn(review.reviewNo, productNo)
                    };
                    const delBtn = document.createElement("button");
                    delBtn.classList.add("delBtn");
                    delBtn.textContent = "삭제";
                    delBtn.onclick=function(){
                        delReview(review.reviewNo, productNo)
                    };

                    //수정할 때 영역
                    
                    editArea.append(updateBtn, delBtn);                    
                    reviewWrite.append(editArea, infoArea);
                    li.append(reviewWrite);
                    //이미지 리스트 불러오기
                    fetch("eCommerce/loadImgs?reviewNo=" + review.reviewNo)
                    .then(resp => resp.json)
                    .then(imgs =>{

                    })
                    //이미지 배열 추가
                    console.log(review.imgList);
                    if (review.imgList && review.imgList.length > 0) {
                        const reviewImg = document.createElement("div");
                        reviewImg.classList.add("reviewImg");
                        const imgRow = document.createElement("div");

                        review.imgList.forEach(imgUrl => {
                            const img = document.createElement("img");
                            img.src = imgUrl;
                            img.classList.add("oneReviewImg");
                            imgRow.append(img);
                        });

                        reviewImg.append(imgRow); 
                        li.append();
                    }


                    //옵션, 별점-----------------------------------------
                    const additionalInfoArea = document.createElement("div");
                    additionalInfoArea.classList.add("additionalInfoArea");

                    const rateDiv =  document.createElement("span");
                    rateDiv.classList.add("rateDiv");
                    const rateTitle = document.createElement("span");
                    rateTitle.innerText="별점";
                    rateDiv.append(rateTitle);
                    const optionValue = document.createElement("span");
                    optionValue.classList.add("optionValue");
                    optionValue.innerText = `[ ${review.optionValue} ]`;

                    const stars = [];
                    for (let i = 0; i < 5; i++) {
                        const star = document.createElement("span");
                        star.classList.add("fa-star");
                        if (i < review.reviewRating) {
                            star.classList.add("fa-solid");
                        } else {
                            star.classList.add("fa-regular");
                        }
                        stars.push(star);
                    }
                    
                    rateDiv.append(...stars);
                    additionalInfoArea.append(rateDiv, optionValue);

                    //리뷰 콘텐트 추가-------------------------------------------------
                    
                    const contentArea = document.createElement("div");
                    contentArea.classList.add("reviewContent");

                    const content = document.createElement("p");
                    content.classList.add("content");
                    contentArea.append(content);
                    content.innerText = review.reviewContent;

                    li.append(additionalInfoArea, contentArea);

                    // - 리뷰 콘텐트 더보기 버튼 추가
                    const moreBtn = document.createElement('input');
                    moreBtn.classList.add("moreBtn");
                    moreBtn.type = "checkbox";

                    contentArea.append(moreBtn);
                    
                reviewContainer.append(li);
                }
            });
        }

    })
    .catch(error => console.error('Error:', error));
}



//5. 내가 쓴 리뷰 삭제 비동기
function delReview(reviewNo,productNo){
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
    getReviewCount(productNo);//개수 업데이트
}



//6. 내가 쓴 리뷰 수정하기 비동기
function updateFn(reviewNo,productNo) {
    //리뷰 불러오기
    fetch("/eCommerce/reloadReview?reviewNo="+reviewNo)
    .then(resp => resp.json())
    .then(result =>{
        if(result == null){
            //리뷰 불러오기 실패
            alert("리뷰 수정 실패");
            return;
        }
        //문서에서 존재하는 수정 영역 안보이게 하기------------------------------------
        const editAreas = document.querySelectorAll(".editArea");
        editAreas.forEach(editArea=>{
            
            editArea.display = "none";
            console.log(editArea.display);
        }) 

        //#review 에 해당 내용 넣기
        console.log(result);
        const review = result;

        const reviewForm = document.querySelector("#reviewForm");
        if(reviewForm.classList.contains("display-none")){
            reviewForm.classList.remove("display-none");
        }
        const commentContent = document.querySelector("#commentContent");
        commentContent.value = review.reviewContent;

        // 별점 채우기
        const stars = reviewForm.querySelectorAll(".fa-star");
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
    getReviewCount(productNo); //개수 업데이트
}


// QNA
const qnaInserButton = document.querySelector("#qnaInserButton");
const write = document.querySelector("#write");
const applyQnaButton = document.querySelector("#applyQnaButton");
const myQna = document.querySelector("#myQna");
const qnaContainer = document.querySelector("#qnaContainer");
const tBody = document.querySelector("#tBody");
const allBox = document.querySelector("#allBox");
const allQna = document.querySelector("#allQna");


function maskString(str) {
    // 문자열 길이를 구함
    const length = str.length;

    // 8글자 이상일 경우 처리
    if (length >= 8) {
        return str.slice(0, 4) + '*'.repeat(length - 4);
    }

    // 8글자보다 짧을 경우 처리
    const halfLength = Math.ceil(length / 2);
    return str.slice(0, length - halfLength) + '*'.repeat(halfLength);
}



document.addEventListener('DOMContentLoaded', () => {
    const memberNo = document.querySelector("#memberNo");

    // 모든 QNA 불러오기
    fetch("/eCommerce/selectQna")
    .then(resp => resp.json())
    .then(result => {
        console.log(result);

        if (result.length == 0) {
            const h2 = document.createElement("h2");
            h2.innerText = "작성한 QNA가 존재하지 않습니다";
            h2.style.color = 'var(--gray5)';
            h2.style.fontWeight = '500';
            h2.style.textAlign = 'center';
            h2.style.marginTop = '20px';

            qnaContainer.append(h2);
        } else {
            result.forEach((qna) => {
                qna.memberId = maskString(qna.memberId);
                if(qna.partnerId != null) {
                    qna.partnerId = maskString(qna.partnerId);
                }

                if (qna.qnaAnswerStatus == 'N') {
                    qna.qnaAnswerStatus = '미답변';
                } else {
                    qna.qnaAnswerStatus = '답변완료';
                }

                let arr = [
                    qna.qnaAnswerStatus,
                    qna.qnaTitle,
                    qna.memberId,
                    qna.qnaWriteDate
                ];

                const tr = document.createElement("tr");
                tr.style.cursor = 'pointer';

                const contentTr = document.createElement("tr");
                contentTr.style.display = 'none';
                
                const answerTr = document.createElement("tr");
                answerTr.style.display = 'none';

                for (let key of arr) {
                    const td = document.createElement("td");
                    td.innerText = key;
                    tr.append(td);

                    const contentTd = document.createElement("td");
                    const answerTd = document.createElement("td");
                    if (key == qna.qnaTitle) {
                        contentTd.innerText = qna.qnaContent;
                        answerTd.innerText = "답변 : " + qna.qnaAnswer;
                    }

                    if (key == qna.memberId) {
                        answerTd.innerText = qna.partnerId;
                    }

                    if (key == qna.qnaWriteDate) {
                        answerTd.innerText = qna.qnaAnswerDate;
                    }

                    contentTr.append(contentTd);
                    answerTr.append(answerTd);
                }
                tBody.append(tr);
                tBody.append(contentTr);

                if(qna.qnaAnswer != null) {
                    tBody.append(answerTr);
                }

                tr.addEventListener("click", () => {
                    const display = (contentTr.style.display === 'none') ? 'table-row' : 'none';
                    if (display === 'table-row') {
                        contentTr.style.display = display;
                        answerTr.style.display = display;
                        contentTr.style.backgroundColor = 'rgb(248, 248, 248)';
                        answerTr.style.backgroundColor = 'rgb(248, 248, 248)';
                        tr.style.backgroundColor = 'rgb(248, 248, 248)';
                    } else {
                        contentTr.style.display = display;
                        answerTr.style.display = display;
                        tr.style.backgroundColor = 'white'; // tr의 배경색 원래대로 설정
                    }
                });

            });
        }
    });

    
    // QNA 입력
    qnaInserButton.addEventListener("click", () => {

        if(memberNo.value == 0) {
            alert("로그인 후 이용해주세요");
            redirectToLogin();
        } else {
            if( write.style.display == 'flex') {
                 write.style.display = 'none'
            } else {
                write.style.display = 'flex';
            }
        }
    })
    
    applyQnaButton.addEventListener("click", () => {
        const writeTitle = document.querySelector("#writeTitle");
        const writeQna = document.querySelector("#writeQna");
    
        if(writeTitle.value.trim().length === 0) {
            alert("QNA 제목을 작성해주세요");
        } else if(writeQna.value.trim().length === 0) {
            alert("QNA 내용을 작성해주세요");
        } else {
            const obj = {
                "qnaTitle" : writeTitle.value,
                "qnaContent" : writeQna.value
            }
    
            console.log(obj);
    
            fetch("/eCommerce/insertQna", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(obj)
            })
            .then(resp => resp.text())
            .then(result => {
                if(result > 0) {
                    alert("QNA 등록이 완료되었습니다");
                    writeTitle.value = "";
                    writeQna.value = "";
                    write.style.display = 'none';
                } else {
                    alert("QNA 등록 실패");
                }
            });
        }
    });

   // '내가 쓴 QNA' 버튼 클릭 이벤트
    myQna.addEventListener("click", () => {
        if (memberNo.value == 0) {
            alert("로그인 후 이용해주세요");
            redirectToLogin();
        } else {
            allBox.style.display = 'block';
            tBody.innerHTML = "";
            write.style.display = 'none';
            write.style.display = 'none';

            fetch("/eCommerce/myQna")
                .then(resp => resp.json())
                .then(result => {
                    console.log(result);

                    if (result.length == 0) {
                        const h2 = document.createElement("h2");
                        h2.innerText = "작성한 QNA가 존재하지 않습니다";
                        h2.style.color = 'var(--gray5)';
                        h2.style.fontWeight = '500';
                        h2.style.textAlign = 'center';
                        h2.style.marginTop = '20px';

                        qnaContainer.append(h2);
                    } else {
                        
                        result.forEach((qna) => {
                            qna.memberId = maskString(qna.memberId);
                            if(qna.partnerId != null) {
                                qna.partnerId = maskString(qna.partnerId);
                            }

                            if (qna.qnaAnswerStatus == 'N') {
                                qna.qnaAnswerStatus = '미답변';
                            } else {
                                qna.qnaAnswerStatus = '답변완료';
                            }

                            let arr = [
                                qna.qnaAnswerStatus,
                                qna.qnaTitle,
                                qna.memberId,
                                qna.qnaWriteDate
                            ];

                            const tr = document.createElement("tr");
                            tr.style.cursor = 'pointer';

                            const contentTr = document.createElement("tr");
                            contentTr.style.display = 'none';
                            
                            const answerTr = document.createElement("tr");
                            answerTr.style.display = 'none';

                            for (let key of arr) {
                                const td = document.createElement("td");
                                td.innerText = key;
                                tr.append(td);
            
                                const contentTd = document.createElement("td");
                                const answerTd = document.createElement("td");
                                if (key == qna.qnaTitle) {
                                    contentTd.innerText = qna.qnaContent;
                                    answerTd.innerText = "답변 : " + qna.qnaAnswer;
                                }

                                if (key == qna.memberId) {
                                    answerTd.innerText = qna.partnerId;
                                }

                                if (key == qna.qnaWriteDate) {
                                    answerTd.innerText = qna.qnaAnswerDate;
                                }

                                contentTr.append(contentTd);
                                answerTr.append(answerTd);
                            }
                            tBody.append(tr);
                            tBody.append(contentTr);
            
                            if(qna.qnaAnswer != null) {
                                tBody.append(answerTr);
                            }

                            tr.addEventListener("click", () => {
                                const display = (contentTr.style.display === 'none') ? 'table-row' : 'none';
                                if (display === 'table-row') {
                                    contentTr.style.display = display;
                                    answerTr.style.display = display;
                                    contentTr.style.backgroundColor = 'rgb(248, 248, 248)';
                                    answerTr.style.backgroundColor = 'rgb(248, 248, 248)';
                                    tr.style.backgroundColor = 'rgb(248, 248, 248)';
                                } else {
                                    contentTr.style.display = display;
                                    answerTr.style.display = display;
                                    tr.style.backgroundColor = 'white'; // tr의 배경색 원래대로 설정
                                }
                            });
                            
                            
                        });
                    }
                });
        }
    });

    // '전체 QNA' 버튼 클릭 이벤트
    allQna.addEventListener("click", () => {
        tBody.innerHTML = "";
        write.style.display = 'none';
        allBox.style.display = 'none';
        
        fetch("/eCommerce/selectQna")
            .then(resp => resp.json())
            .then(result => {
                console.log(result);

                if (result.length == 0) {
                    const h2 = document.createElement("h2");
                    h2.innerText = "작성한 QNA가 존재하지 않습니다";
                    h2.style.color = 'var(--gray5)';
                    h2.style.fontWeight = '500';
                    h2.style.textAlign = 'center';
                    h2.style.marginTop = '20px';

                    qnaContainer.append(h2);
                } else {
                    result.forEach((qna) => {
                        qna.memberId = maskString(qna.memberId);
                        if(qna.partnerId != null) {
                            qna.partnerId = maskString(qna.partnerId);
                        }

                        if (qna.qnaAnswerStatus == 'N') {
                            qna.qnaAnswerStatus = '미답변';
                        } else {
                            qna.qnaAnswerStatus = '답변완료';
                        }

                        let arr = [
                            qna.qnaAnswerStatus,
                            qna.qnaTitle,
                            qna.memberId,
                            qna.qnaWriteDate
                        ];

                        const tr = document.createElement("tr");
                        tr.style.cursor = 'pointer';
                        const contentTr = document.createElement("tr");
                        contentTr.style.display = 'none';

                        for (let key of arr) {
                            const td = document.createElement("td");
                            td.innerText = key;
                            tr.append(td);

                            const contentTd = document.createElement("td");
                            if (key == qna.qnaTitle) {
                                contentTd.innerText = qna.qnaContent;
                                contentTd.colSpan = arr.length; // 제목이 포함된 행에 대해 전체 열에 걸치도록 설정
                            }
                            contentTr.append(contentTd);
                        }
                        tBody.append(tr);
                        tBody.append(contentTr);

                        tr.addEventListener("click", () => {
                            const display = (contentTr.style.display == 'none') ? 'table-row' : 'none';
                            contentTr.style.display = display;
                        });
                    });
                }
            });
    });

})

// 리뷰
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
    getReviewCount(productNo); //개수 업데이트
}

//전체 리뷰 개수 

async function getReviewCount(productNo) {
    
    try{
        const response = await fetch("/eCommerce/reviewCount?productNo="+productNo);
        const result = await response.text();
        const num = result;
        
        const reviewCounts = document.querySelectorAll(".totalReviewCount");
        reviewCounts.forEach(reviewCount =>{
            reviewCount.innerHTML = num;
        });
    }catch(error){
        console.log("ERROR : ",error);
    }

}

async function getAvgReviewScore(productNo) {
    
    try{
        const response = await fetch("/eCommerce/getAvgReviewScore?productNo="+productNo);
        const result = await response.text();

        const totalRating = document.querySelector(".totalRating");
        totalRating.innerHTML = result;
    }catch(error){
        console.log("ERROR : ",error);
    }

}
getReviewCount(productNo);

getAvgReviewScore(productNo);