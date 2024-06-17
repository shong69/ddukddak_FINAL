const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox");

let selectOption = 1;

const radio2 = document.querySelector("#radio2");

radio2.checked = true;

bigCategory.addEventListener('change', () => {
  // 기존 소분류 옵션을 모두 제거
  smallcategory.innerHTML = '<option value="none">소분류</option>';

  const selectedCategory = bigCategory.value;

  fetch("/partner/seller/product/selectSmallCategory", {
        method: "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(selectedCategory)
    })
    .then(resp => resp.text())
    .then(result => {
        
        const smallCategoryList = JSON.parse(result);

        
        smallCategoryList.forEach(elements => {
            const option = document.createElement('option');
            option.value = elements.categoryNo;
            option.textContent = elements.categoryName;
            smallcategory.appendChild(option);
        })
    })

});

bigCategory.addEventListener("click", e => {
  e.target.children[0].remove();
})

  
  
bigCategory.addEventListener('change', () => {

  // 기존 소분류 옵션을 모두 제거
  smallcategory.innerHTML = '<option value="none">소분류</option>';

  const selectedCategory = bigCategory.value;

  console.log(selectedCategory);

  if (categoryOptions != 'none') {
      categoryOptions[selectedCategory].forEach(sub => {
        const option = document.createElement('option');
        option.value = sub;
        option.textContent = sub;
        smallcategory.appendChild(option);
    });
  }
});

  const addoption = document.querySelector("#addoption");
  addoption.style.display = 'none';

  function createOption() {
    selectOption = 0;
    plusButton.style.display = "none";

    const box = document.createElement("div")
    box.classList.add("optionMainBox");

    const optionName = document.createElement("input");
    optionName.classList.add("optionName");
    optionName.placeholder = "예시 : 컬러";

    const optionContentBox = document.createElement("div");
    optionContentBox.classList.add("optionContentBox");

    const table = document.createElement("table");
    const tr1 = document.createElement("tr");
    const tr2 = document.createElement("tr");
    const td1 = document.createElement("td");
    const td2 = document.createElement("td");
    const td3 = document.createElement("td");
    const td4 = document.createElement("td");
    const td5 = document.createElement("td");
    const td6 = document.createElement("td");
    const addOption = document.createElement("button");
    const minusOption = document.createElement("button");
    const contentInput = document.createElement("input");
    const contentCountInput = document.createElement("input");

    const applyButton = document.createElement("button");

    contentInput.classList.add("contentInput");
    contentInput.placeholder = "예시 : 빨강";

    contentCountInput.classList.add("contentCountInput");

    td1.innerText = "옵션 내용";
    td1.style.fontSize = "12px";
    td2.innerText = "재고 수량";
    td2.style.fontSize = "12px";

    applyButton.classList.add("applyButton");
    applyButton.innerText = "등록";
    applyButton.type = "button";

    addOption.innerText = "+";
    addOption.type = "button";
    addOption.classList.add("addOption");
    minusOption.innerText = "-";
    minusOption.type = "button";
    minusOption.classList.add("minusOption");

    td3.append(contentInput);
    td4.append(contentCountInput);
    td5.append(addOption);
    td6.append(minusOption);

    
    tr1.append(td1);
    tr1.append(td2);
    tr1.append(td5);
    
    tr2.append(td3);
    tr2.append(td4);
    tr2.append(td6);

    table.append(tr1);
    table.append(tr2);

    optionContentBox.append(table);
    optionContentBox.append(applyButton);

    box.append(optionName);
    box.append(optionContentBox);

    optionBox.append(box);




    /* 옵션 내용 추가 */
    addOption.addEventListener("click", () => {
        const clone = tr2.cloneNode(true);
        table.append(clone);

        // 새로 추가된 minusOption 버튼에 이벤트 리스너 추가
        const newMinusOption = clone.querySelector(".minusOption");
        newMinusOption.addEventListener("click", (e) => {
            if (table.querySelectorAll('.minusOption').length > 1) {
                e.target.closest('tr').remove();
            }
        });
    });

    // 기존 minusOption 버튼에 이벤트 리스너 추가
    minusOption.addEventListener("click", (e) => {
        if (table.querySelectorAll('.minusOption').length > 1) {
            e.target.closest('tr').remove();
        }
    });

    // 적용 버튼 눌렀을 떄
    applyButton.addEventListener("click", () => {

        if(optionName.value.trim().length === 0 ||
        contentInput.value.trim().length === 0 ||
        contentCountInput.value.trim().length === 0) {
            alert("작성을 완료해주세요");
        } else {
            selectOption = 1;
            plusButton.style.display = "block";
            table.style.display = "none";
            applyButton.style.display = "none";
        }
    })
  }
       
  createOption();

document.querySelectorAll('input[name="productOption"]').forEach(radio => {
    radio.addEventListener('change', (event) => {
      const selectedValue = event.target.value;
      
      if(selectedValue == 1) {
        addoption.style.display = 'flex';
      }else {
        addoption.style.display = 'none';
      }
    });
 });


  
  plusButton.addEventListener("click", () => {
    createOption();
  });





/* 메인 이미지 업로드 */
function getMainImageFile(e) {
  const filesMain = e.currentTarget.files;
  const imagePreviewMain = document.querySelector('.image-preview-main');

  // 파일 타입 검사
  if (filesMain.length > 0) {
    const file = filesMain[0];
    if (!file.type.match("image/.*")) {
      alert('이미지 파일만 업로드가 가능합니다.');
      return;
    }

    const reader = new FileReader();
    reader.onload = (e) => {
      imagePreviewMain.innerHTML = ''; // 기존 미리보기 초기화
      const preview = createElement(e, file);
      imagePreviewMain.appendChild(preview);
    };
    reader.readAsDataURL(file);
  }
}

const realUploadMain = document.querySelector('.real-upload-main');
const uploadMain = document.querySelector('.upload-main');

uploadMain.addEventListener('click', () => realUploadMain.click());

realUploadMain.addEventListener('change', e => {
  getMainImageFile(e);
});

// input file 커스텀 - 파일명 붙이기
const fileTargetMain = document.querySelector('.real-upload-main');

fileTargetMain.addEventListener('change', function () { 
  const files = this.files;
  const fileArr = [];
  for (let i = 0; i < files.length; i++) {
    fileArr.push(files[i].name);
  }
  
  // 파일명 노출 부분 제거
  /*
  const fileList = fileArr.join(', '); // 배열 값을 쉼표와 공백으로 연결
  this.nextElementSibling.textContent = fileList;
  */
});




// 상세 이미지 추가

const delImgs = [];

document.addEventListener('DOMContentLoaded', function() {
  const delButton = document.querySelectorAll('.delButton');

  delButton.forEach(elements => {
    elements.addEventListener("click", e=> {
      if(confirm("해당 사진을 삭제하시겠습니까?")) {
        delImgs.push(e.target.value);
        e.target.parentElement.remove();
      }
    })
  })
});