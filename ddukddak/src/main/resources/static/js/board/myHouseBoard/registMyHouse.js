// 1번째
/*
const largeImage = document.querySelector("#largeImage");
const mainImgText = document.querySelector("#mainImgText");
const registMyHouseForm = document.querySelector("#registMyHouseForm");

document.getElementById('imageUpload').addEventListener('change', function(event) {
    const files = event.target.files;
    // input 에서 선택한 파일 가져오기

    // 기존의 preview-container가 있으면 삭제
    let existingPreviewContainer = document.querySelector('.preview-container');
    const largeImage = document.querySelector("#largeImage");

    largeImage.style.display='none';
    mainImgText.style.display='none';
    largeImage.src = '';
    

    if (existingPreviewContainer) {
        existingPreviewContainer.innerHTML='';
    } else {
        existingPreviewContainer = document.createElement('div');
        existingPreviewContainer.classList.add('preview-container');
        event.target.parentElement.appendChild(existingPreviewContainer);
        // input 태그 부모 태그인 div class=inputFile 태그안에 previewContainer 태그 추가
    }


    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = function(e) {   // 파일을 다 읽으면 함수 호출
            const div = document.createElement('div');
            const img = document.createElement('img');
            img.src = e.target.result;
            img.setAttribute('name', 'images');
            img.addEventListener('click', function() {
                displayLargeImage(e.target.result);
                img.setAttribute('name', 'mainImg');
            });
            div.appendChild(img);
            existingPreviewContainer.appendChild(div);
        }
        reader.readAsDataURL(file);
    }
});

function displayLargeImage(src) {

    mainImgText.classList.add('mainImgText');
    mainImgText.style.display = 'block';
    largeImage.src = src;
    largeImage.style.display = 'block';

};
*/


const largeImage = document.querySelector("#largeImage");
const mainImgText = document.querySelector("#mainImgText");

document.getElementById('imageUpload').addEventListener('change', function(event) {
    const files = event.target.files;
    let existingPreviewContainer = document.querySelector('.preview-container');
    largeImage.style.display = 'none';
    mainImgText.style.display = 'none';
    largeImage.src = '';

    if (existingPreviewContainer) {
        existingPreviewContainer.innerHTML = '';
    } else {
        existingPreviewContainer = document.createElement('div');
        existingPreviewContainer.classList.add('preview-container');
        event.target.parentElement.appendChild(existingPreviewContainer);
    }

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = function(e) {
            const div = document.createElement('div');
            const img = document.createElement('img');
            img.src = e.target.result;
            img.setAttribute('name', 'images');
            img.addEventListener('click', function() {
                updateMainImage(e.target.result, img);
            });
            div.appendChild(img);
            existingPreviewContainer.appendChild(div);
        }
        reader.readAsDataURL(file);
    }
});

function updateMainImage(src, selectedImg) {
    const previewContainer = document.querySelector('.preview-container');
    const images = previewContainer.querySelectorAll('img');

    // 모든 이미지의 name 속성을 images로 변경
    images.forEach(img => {
        
        img.setAttribute('name', 'images');
    });

    // 선택한 이미지의 name 속성을 mainImg로 변경
    selectedImg.setAttribute('name', 'mainImg');

    mainImgText.classList.add('mainImgText');
    mainImgText.style.display = 'block';
    largeImage.src = src;
    largeImage.style.display = 'block';
}

// --------------------------------------------------------------------------

/* 집들이 작성 유효성 검사 */

registMyHouseForm.addEventListener("submit", e => {

    const boardTitle = document.querySelector("#boardTitle");
    const boardContent = document.querySelector("#boardContent");

    if(boardTitle.value.trim().length == 0) {
        alert("집들이 제목을 입력해주세요.");
        boardTitle.focus();
        e.preventDefault();
        return;
    }

    if(boardContent.value.trim().length == 0) {
        alert("집들이 내용을 입력해주세요.");
        boardContent.focus();
        e.preventDefault();
        return;
    }

});

// ----------------------------------------------------------------------------

document.querySelector("#backToList").addEventListener("click", () => {

    location.href="/myHouse/main"

});