

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
            alert("집들이 이미지 등록은 10개까지 가능합니다.");
            existingPreviewContainer.innerHTML = '';
            return;
        }

        
        if(mainImgHidden.value != null) {
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

// --------------------------------------------------------------------------

/* 집들이 작성 유효성 검사 */

registMyHouseForm.addEventListener("submit", e => {

    const boardTitle = document.querySelector("#boardTitle");
    const boardContent = document.querySelector("#boardContent");
    const images = document.getElementById('imageUpload').files;
    const mainImg = document.getElementById('mainImgHidden').value;

    console.log(images.length);

    if(images.length > 10) {
        alert("집들이 이미지를 선택해주세요.");
        e.preventDefault();
        return;
    }

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

    location.href="main?boardCode=" + boardCode;

});