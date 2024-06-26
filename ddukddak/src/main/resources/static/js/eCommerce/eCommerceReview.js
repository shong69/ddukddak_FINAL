
//-------------------------------------------------------------

//1. 리뷰 등록 비동기 + (사진, 텍스트, 별점)도 올리기
//  1)사진 등록하기
let reviewImgFiles = [];
function readImgURLs(input) {
    if (input.files && input.files.length > 0) {
        const reviewImgBox = document.getElementById('reviewImgBox');
        const totalReviewImages = reviewImgBox.children.length + input.files.length;
        const maxSize = 10 * 1024 * 1024; // 사진 최대 크기 10MB
        const maxImages = 5;
        let isValid = true;
        let totalSize = 0;

        if (totalReviewImages > maxImages) {
            alert('리뷰 이미지는 최대 5개까지 추가할 수 있습니다.');
            return;
        }

        // 추가할 파일의 크기 합산
        Array.from(input.files).forEach(file => {
            totalSize += file.size;
        });
        //파일 업로드 총 크기 확인
        if (totalSize > maxSize) {
            alert('모든 파일의 총 크기는 10MB를 초과할 수 없습니다.');
            return;
        }
        Array.from(input.files).forEach(file => {
            // 이미지 파일인지 확인
            if (!file.type.startsWith('image/')) {
                alert('이미지 파일만 선택할 수 있습니다.');
                isValid = false;
                return;
            }
            // 파일 크기 확인
            if (file.size > maxSize) {
                alert('파일 크기가 너무 큽니다. 최대 허용 크기는 10MB입니다.');
                isValid = false;
                return;
            }
        });

        if (!isValid) {
            return;
        }

        Array.from(input.files).forEach(file => {
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
                deleteButton.classList.add('delete-button', 'fa-solid', 'fa-trash-can');

                deleteButton.addEventListener("click", () => {
                    if (confirm("해당 사진을 삭제하시겠습니까?")) {
                        const index = Array.from(reviewImgBox.children).indexOf(previewContainer);
                        reviewImgFiles.splice(index, 1); // 배열에서 파일 제거
                        previewContainer.remove(); // 이미지 삭제
                    }
                });

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
            for(let i = index; i<stars.length; i++){
                
                stars[i].classList.remove('fa-solid');
                stars[i].classList.add('fa-regular');
                stars[i].classList.remove('fill');
            }
            reviewRating=index;
        }

    })
})

console.log(reviewImgFiles);
//  3) 비동기로 리뷰 작성(+수정)하기
async function handleFormMissionReview(event) {
    event.preventDefault();
    console.log(reviewRating);
    const form = document.getElementById('reviewForm');
    const formData = new FormData(form);
    console.log(typeof formData);

    // 리뷰 이미지 파일 FormData에 추가
    reviewImgFiles.forEach(file => {
        console.log(file);
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

    // // FormData 내용을 콘솔에 출력 (모든 키와 값을 출력)
    // for (let [key, value] of formData.entries()) {
    //     console.log(key, typeof value);
    // }

    // reviewRating과 reviewContent 입력 여부 검사
    if (!formData.has('reviewRating') || reviewRating === 0) {
        alert('별점을 등록해 주세요.');
        return;
    }
    if (!formData.get('reviewContent')) {
        alert('리뷰 내용을 입력해 주세요.');
        return;
    }
    // reviewNo 가져오기
    const reviewNo = document.querySelector("#hiddenReviewNo").value;
    const url = reviewNo ? "/eCommerce/updateReview" : "/eCommerce/reviewPost";
    const resp = await fetch(url, {
        method: 'POST',
        body: formData
    })
    const result = await resp.text();
    if(result > 0){
        alert(reviewNo ? "리뷰가 수정되었습니다." : "리뷰가 등록되었습니다.");

        //리뷰 작성 영역 새로고침

        getReviewCount(productNo); //리뷰 개수 업데이트
        openReviewCheck(productNo); //리뷰 작성 가능 여부 
        getAvgReviewScore(productNo) // 평점 업데이트
        selectReviewList(productNo); // 리뷰 목록 새로 고침

    }else{
        alert(reviewNo ? "리뷰 수정 실패\n다시 입력해 주세요" : "리뷰 등록 실패\n다시 입력해 주세요");
        return;
    }

}

function calcleReviewSubmit(event){
     const commentContent = document.querySelector("#commentContent");
     commentContent.value = "";
     const productRate = document.querySelector("#productRate");
     console.log(productRate.children);
     for(let i = 0; i<productRate.childElementCount;i++){
        if(productRate.children[i].classList.contains("fa-solid")){
            productRate.children[i].classList.remove("fa-solid");
            productRate.children[i].classList.add("fa-regular");
        }
     }
     const reviewForm = document.querySelector("#reviewForm");
     reviewForm.classList.add("display-none");
}




//2. 리뷰 열었을 때 선택한 멤버가 리뷰 작성 권한이 있는지(상품id과 주문테이블에 겹치는지) 확인
async function openReviewCheck(productNo){

    //리뷰 작성 권한 비동기로 알아오기
    const reviewForm = document.querySelector("#reviewForm");
    const optionDiv = document.querySelector("#optionDiv");
    const optionDiv2 =document.querySelector(".optionDiv2");
    //로그인 여부 확인
    if(typeof loginMember === 'undefined' || loginMember === null){
        console.log(loginMember);
        reviewForm.classList.add('display-none');
        selectReviewList(productNo);
        return;
    }
    const reviewAuthList = await checkReviewAuth(productNo);
    console.log(reviewAuthList);
    if(reviewAuthList&& reviewAuthList.length > 0){//작성 하지 않은 리뷰가 존재하는 경우
        //id=reviewForm 노출시키기
        reviewForm.classList.remove('display-none');
        //별점 재설정
        stars.forEach(star=>{
            if(star.classList.contains("fa-solid")){
                star.classList.remove("fa-solid");
                star.classList.remove("fill");
                star.classList.add("fa-regular");
            }
        })
        //글 삭제
        document.querySelector("#commentContent").value = "";
        //이미지 삭제
        if(document.querySelector("#reviewImgBox")?.childElementCount != null){
            document.querySelector("#reviewImgBox").innerHTML = "";
        }
        //옵션 영역 재설정
        if(optionDiv2){
            optionDiv2.remove();
        }
        if(optionDiv.classList.contains("display-none")){
            optionDiv.classList.remove("display-none");
        }
        //옵션 재생성
        const reviewOptSelect =document.querySelector("select.reviewOptSelect");
        if(reviewOptSelect?.childElementCount !=null) { //select 가 존재하면
            const parentDiv = reviewOptSelect.parentElement;
            parentDiv.innerHTML = "";
        }

        const div = document.createElement("div");
        div.innerText ="주문상품 기록";
        const select = document.createElement("select");
        select.classList.add("reviewOptSelect");
        
        //옵션 추가하기
        reviewAuthList.forEach(item =>{
            const option = document.createElement("option");
            option.value = `${item.orderItemNo}`;
            option.innerHTML = `주문일:${item.orderDate} | 주문옵션:${item.optionValue} ${item.orderQuantity}개`;
            option.setAttribute("data-value", `${item.optionValue}`); //data-value에 optionvalue 넣어서 옵션명 리뷰에 저장하기
            select.append(option);
        })
        optionDiv.append(div,select);
    }else{
        reviewForm.classList.add('display-none');
    }
    selectReviewList(productNo)
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
        console.log(result.length);
        const reviewList = result;
        const reviewContainer = document.getElementById("reviewContainer");
        reviewContainer.innerHTML = "";

        if(reviewList == null || reviewList.length == 0){
            const li = document.createElement("li");
            li.classList.add("noReviewLi");
            const div = document.createElement("div");
            div.innerHTML = "-리뷰가 존재하지 않습니다-";
            div.classList.add("noReviewArea");
            li.append(div);
            reviewContainer.append(li);
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
                    if(review.reviewUpdateDate != null){
                        commentWriteDate.textContent = review.reviewUpdateDate; //리뷰 작성일 적기
                    }
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

                    
                    editArea.append(updateBtn, delBtn);                    
                    reviewWrite.append(infoArea, editArea);
                    li.append(reviewWrite);

                    //이미지 배열 추가----------------------------------
                    console.log(review.imgList);
                    if (review.imgList && review.imgList.length > 0) {
                        const reviewImg = document.createElement("div");
                        reviewImg.classList.add("reviewImg");
                        const imgRow = document.createElement("div");

                        review.imgList.forEach(imgUrl => {
                            console.log(imgUrl);
                            const img = document.createElement("img");
                            img.src = imgUrl;
                            img.classList.add("oneReviewImg");
                            imgRow.append(img);
                        });

                        reviewImg.append(imgRow); 
                        li.append(reviewImg);
                    }


                    //옵션, 별점-----------------------------------------
                    const additionalInfoArea = document.createElement("div");
                    additionalInfoArea.classList.add("additionalInfoArea");

                    const rateDiv =  document.createElement("span");
                    rateDiv.classList.add("rateDiv");
                    const rateTitle = document.createElement("span");
                    rateTitle.innerHTML=`평점 &nbsp`;
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

                    // - 리뷰 콘텐트 더보기 버튼
                    const moreBtn = document.createElement('input');
                    moreBtn.classList.add("moreBtn");
                    moreBtn.type = "checkbox";
                    if(content.innerText.length>184){
                        contentArea.append(moreBtn);
                    }

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
                getReviewCount(productNo);//개수 업데이트
                openReviewCheck(productNo); //리뷰 작성 가능 여부 
                getAvgReviewScore(productNo) // 평점 업데이트
            } else {
                alert("리뷰 삭제에 실패했습니다.");
            }
        })
        .catch(error => console.error('Error:', error));
    }

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
        //reviewNo 넣어주기
        document.querySelector("#hiddenReviewNo").value = reviewNo;

        //수정 영역 안보이도록
        const editAreas = document.querySelectorAll(".editArea");
        console.log(editAreas,"didi");
        editAreas.forEach(editArea=>{
            
            editArea.style.display = "none";
            console.log(editArea.display);
        }) 

        //#review 에 내용 넣기
        const review = result;
        console.log(review);
        const reviewForm = document.querySelector("#reviewForm");
        if(reviewForm.classList.contains("display-none")){
            reviewForm.classList.remove("display-none");
        }

        //원래 optionDiv 가리고 새로 만들어서 삽입하기
        const optionDiv = document.querySelector("#optionDiv"); 
        optionDiv.classList.add("display-none");
        //주문 옵션
        const reviewArea = document.querySelector("#reviewArea");
        const children = reviewArea.children;

        const optionDiv2 = document.createElement("div");
        optionDiv2.classList.add("optionDiv2");
        reviewArea.insertBefore(optionDiv2,children[1]);

        const div = document.createElement("div");
        div.innerText ="주문상품";
        const select = document.createElement("select");
        select.classList.add("reviewOptSelect");
        
        //옵션 추가하기
        const option = document.createElement("option");
        option.value = `${review.orderItemNo}`;
        option.innerHTML = `주문일:${review.orderDate} | 주문옵션:${review.optionValue} ${review.orderQuantity}개`;
        option.setAttribute("data-value", `${review.optionValue}`); //data-value에 optionvalue 넣어서 옵션명 리뷰에 저장하기
        select.append(option);
        console.log(option);
        
        optionDiv2.append(div,select);

        // 리뷰 내용
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
                preview.src = img;
                const values = img.split('/');
                preview.value = values[values.length-1];
                console.log(preview.value);
                previewContainer.appendChild(preview);

                const deleteButton = document.createElement('button');
                deleteButton.type = "button";
                deleteButton.classList.add('delete-button');
                deleteButton.classList.add('fa-solid');
                deleteButton.classList.add('fa-trash-can');
                deleteButton.addEventListener("click", e=>{
                const imageSrc = preview.src.split('/').pop();
                if (confirm("해당 사진을 삭제하시겠습니까?")) {
                    fetch(`/eCommerce/delImg`, {
                        method: 'DELETE',
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ imageSrc: imageSrc }) // 필요한 데이터 추가
                    })
                    .then(resp => resp.text())
                    .then(result => {
                        console.log(result);
                        previewContainer.remove(); // 이미지 삭제
                    })
                    .catch(error => console.error('Error:', error));
                }
                })
                previewContainer.appendChild(deleteButton);

                reviewImgBox.appendChild(previewContainer);
            });
        reviewForm.scrollIntoView({behavior : 'smooth'});
        }
        
    });

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


