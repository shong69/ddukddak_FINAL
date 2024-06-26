/* 선택된 이미지 미리보기 */
// const previewList = document.querySelectorAll(".preview"); // img 태그 5개
// const inputImageList = document.querySelectorAll(".projectImg"); // input 태그 5개
// const deleteImageList = document.querySelectorAll(".delete-img"); // x버튼 5개



// 이미지 선택 이후 취소를 누를 경우를 대비한 백업 이미지
// (백업 원리 -> 복제품으로 기존 요소를 대체함)
// const backupInputList = new Array(inputImageList.length);


// /* ***** input 태그 값 변경 시(파일 선택 시) 실행할 함수 ***** */
// /**
//  * @param projectImg : 파일이 선택된 input 태그
//  * @param order : 이미지 순서
//  */
// const changeImageFn = (projectImg, order) => {

//   // byte단위로 10MB 지정
//   const maxSzie = 1024 * 1024 * 10;

//   // 업로드된 파일 정보가 담긴 객체를 얻어와 변수에 저장
//   const file = projectImg.files[0];


//   // ------------- 파일 선택 -> 취소 해서 파일이 없는 경우 ----------------
//   if(file == undefined){
//     console.log("파일 선택 취소됨");

//     // 같은 순서(order)번째 backupInputList 요소를 얻어와 대체하기

//     /* 한 번 화면에 추가된 요소는 재사용(다른 곳에 또 추가) 불가능 */

//     // 백업본을 한 번 더 복제
//     const temp = backupInputList[order].cloneNode(true);

//     projectImg.after(temp); // 백업본을 다음 요소로 추가
//     projectImg.remove();    // 원본을 삭제
//     projectImg = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입

//     // 백업본에 없는 이벤트 리스너를 다시 추가
//     projectImg.addEventListener("change", e => {
//       changeImageFn(e.target, order);
//     })

//     return;
//   }


//   // ---------- 선택된 파일의 크기가 최대 크기(maxSize) 초과 ---------

//   if(file.size > maxSzie){
//     alert("10MB 이하의 이미지를 선택해주세요");

//     // 해당 순서의 backup 요소가 없거나, 
//     // 요소는 있는데 값이 없는 경우 == 아무 파일도 선택된적 없을 때
//     if(backupInputList[order] == undefined
//         || backupInputList[order].value == ''){

//         projectImg.value = ""; // 잘못 업로드된 파일 값 삭제
//       return;
//     }

//     // 이전에 정상 선택 -> 다음 선택에서 이미지 크기 초과한 경우
//     // 백업본을 한 번 더 복제
//     const temp = backupInputList[order].cloneNode(true);

//     projectImg.after(temp); // 백업본을 다음 요소로 추가
//     projectImg.remove();    // 원본을 삭제
//     projectImg = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입
 
//     // 백업본에 없는 이벤트 리스너를 다시 추가
//     projectImg.addEventListener("change", e => {
//      changeImageFn(e.target, order);
//     })

//     return;
//   }


//   // ------------ 선택된 이미지 미리보기 --------------

//   const reader = new FileReader(); // JS에서 파일을 읽고 저장하는 객체

//   // 선택된 파일을 JS로 읽어오기 -> reader.result 변수에 저장됨
//   reader.readAsDataURL(file);

//   reader.addEventListener("load", e => {
//     const url = e.target.result;

//     // img 태그(.preview)에 src 속성으로 url 값을 대입
//     previewList[order].src = url;

//     // 같은 순서 backupInputList에 input태그를 복제해서 대입
//     backupInputList[order] = projectImg.cloneNode(true);
//   });

// }





// for(let i=0 ; i<inputImageList.length ; i++){

//   // **** input태그에 이미지가 선택된 경우(값이 변경된 경우) ****
//   inputImageList[i].addEventListener("change", e => {
//     changeImageFn(e.target, i);
//   })


//   // **** x 버튼 클릭 시 ****
//   deleteImageList[i].addEventListener("click", () => {

//     // img, input, backup의 인덱스가 모두 일치한다는 특징을 이용

//     previewList[i].src       = ""; // 미리보기 이미지 제거
//     inputImageList[i].value  = ""; // input에 선택된 파일 제거
//     backupInputList[i].value = ""; // 백업본 제거
//   });

// }



// // 이미지 추가 컨테이너 하나씩 보여주기

// document.addEventListener('DOMContentLoaded', () => {
//     const viewMoreBtn = document.querySelector(".viewMoreBtn");
//     const projectImg = document.querySelectorAll(".previewImg");

//     let currentVisibleIndex = 0; // Tracks the currently visible list

//     // Initially hide all project lists except the first one
//     projectImg.forEach((list, index) => {
//         if (index === currentVisibleIndex) {
//             list.classList.add('active');
//         }
//     });

//     if (viewMoreBtn != null) {
//         viewMoreBtn.addEventListener("click", () => {
//             if (currentVisibleIndex < projectImg.length - 1) {
//                 currentVisibleIndex++;
//                 projectImg[currentVisibleIndex].classList.add('active'); // Show the next hidden list
                
//                 // If all lists are shown, hide the button
//                 if (currentVisibleIndex >= projectImg.length - 1) {
//                     viewMoreBtn.style.display = 'none';
//                 }
//             }
//         });
//     }
// });


// /* 이미지 x 버튼 눌렸을때 */

// document.addEventListener('DOMContentLoaded', () => {
//   const delImgButtons = document.querySelectorAll(".delete-img");

//   if (delImgButtons != null) {
//      delImgButtons.forEach(button => {
//         button.addEventListener('click', (event) => {
//             const projectImg = event.target.closest('.projectImg');
//             if (projectImg) {
//                 projectImg.style.display = 'none'; // Hide the image container
//             }
//         });
//     });
//   }
// });


const largeImage = document.querySelector("#largeImage");
const mainImgText = document.querySelector("#mainImgText");
const mainImgHidden = document.querySelector("#mainImgHidden");
const imageUpload = document.getElementById('imageUpload');

if (imageUpload != null) {
    imageUpload.addEventListener('change', function(event) {
        
        const files = event.target.files;
        let existingPreviewContainer = document.querySelector('.preview-container');
        let guideText;

        if(files.length > 10) {
            alert("프로젝트 이미지 등록은 10개까지 가능합니다.");
            existingPreviewContainer.innerHTML = '';
            return;
        };
        
        if(mainImgHidden.value != "") {
            mainImgHidden.value = '';
            largeImage.style.display = 'none';
            mainImgText.style.display = 'none';
            largeImage.src = '';
        }
            
    
        if (existingPreviewContainer) {
            existingPreviewContainer.innerHTML = '';
        } else {
            existingPreviewContainer = document.createElement('div');
            existingPreviewContainer.classList.add('preview-container');
            guideText = document.createElement('h3');
            guideText.classList.add('guideText');
            guideText.innerText = '* 메인이미지를 선택해주세요(이미지 클릭)';
            event.target.parentElement.appendChild(guideText);
            event.target.parentElement.appendChild(existingPreviewContainer);
        }
    
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();
            reader.onload = function(e) {
                const div = document.createElement('div');
                const img = document.createElement('img');
                img.src = e.target.result;
                img.setAttribute('data-filename', file.name);
                img.addEventListener('click', function() {
                    updateMainImage(e.target.result, file.name);
                });
                div.appendChild(img);
                existingPreviewContainer.appendChild(div);
            }
            reader.readAsDataURL(file);
        }
    });

}

function updateMainImage(src, fileName) {
    mainImgText.classList.add('mainImgText');
    mainImgText.style.display = 'block';
    largeImage.src = src;
    largeImage.style.display = 'block';

    // Set the hidden input to the selected file name
    mainImgHidden.value = fileName;
}


const registProjectForm = document.querySelector("#registProjectForm");

registProjectForm.addEventListener("submit", e => {

    const images = document.getElementById('imageUpload').files;
    const mainImg = document.getElementById('mainImgHidden').value;

    console.log(images.length);

    if(images.length > 10) {
        alert("사진을 1장 이상 업로드해주세요.");
        e.preventDefault();
        return;
    }

    if(images.length === 0) {
        alert("사진을 1장 이상 업로드해주세요.");
        e.preventDefault();
        return;
    } else if (mainImg === "") {
        alert("메인이미지를 선택해주세요.");
        e.preventDefault();
        return;
    }

})