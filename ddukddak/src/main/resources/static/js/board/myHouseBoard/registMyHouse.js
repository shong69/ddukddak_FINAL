const largeImage = document.querySelector("#largeImage");
const mainImgText = document.querySelector("#mainImgText");


document.getElementById('imageUpload').addEventListener('change', function(event) {
    const files = event.target.files;
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
    }


    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = function(e) {
            const div = document.createElement('div');
            const img = document.createElement('img');
            img.src = e.target.result;
            img.addEventListener('click', function() {
                displayLargeImage(e.target.result);
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


// --------------------------------------------------------------------------

/* 집들이 작성 유효성 검사 */