
/*
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
*/

const largeImage = document.querySelector("#largeImage");
const mainImgText = document.querySelector("#mainImgText");
const mainImgHidden = document.querySelector("#mainImgHidden");
const imageUpload = document.getElementById('imageUpload');

if (imageUpload != null) {
    imageUpload.addEventListener('change', function(event) {
        
        
        if(mainImgHidden.value != "") {
            mainImgHidden.value = '';
            largeImage.style.display = 'none';
            mainImgText.style.display = 'none';
            largeImage.src = '';
        }
            
        const files = event.target.files;
        let existingPreviewContainer = document.querySelector('.preview-container');
        let guideText;
    
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

// --------------------------------------------------------------------------

/* 집들이 작성 유효성 검사 */

registMyHouseForm.addEventListener("submit", e => {

    const boardTitle = document.querySelector("#boardTitle");
    const boardContent = document.querySelector("#boardContent");
    const images = document.getElementById('imageUpload').files;
    const mainImg = document.getElementById('mainImgHidden').value;

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


    if (images.length === 0) {
        alert('사진을 1장 이상 업로드해주세요.');
        e.preventDefault();
        return;
    } else if (mainImg === "") {
        alert('메인이미지를 선택해주세요.');
        e.preventDefault();
        return;
    }

});

// ----------------------------------------------------------------------------

document.querySelector("#backToList").addEventListener("click", () => {

    location.href="/myHouse/main?boardCode=" + boardCode;

});