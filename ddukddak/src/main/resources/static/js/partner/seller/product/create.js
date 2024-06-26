const productCreateButton = document.querySelector("#productCreateButton");
const modal = document.querySelector(".modal");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox")
const createButton = document.querySelector("#createButton");
const productName = document.querySelector("#productName");
const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const price = document.querySelector("#price");


/* 전체선택버튼 */
const selectAll = document.querySelector('#selectAllCheckBox');
const checkboxes = document.getElementsByName('selectProduct');

selectAll.addEventListener('change', () => {
    if(selectAll.checked == true) {
        checkboxes.forEach(elements => {
          elements.checked = true;
        })
    } else {
        checkboxes.forEach(elements => {
            elements.checked = false;
        })
    }
});

// 상품가격 포맷
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim();
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice);
    productPriceElements[i].innerText += '원';
}

// 상품제목 글자수제한
function truncateText(text, maxLength) {
    return text.length > maxLength ? text.slice(0, maxLength - 3) + "..." : text;
}

const itemTitle = document.querySelectorAll(".productName");

itemTitle.forEach(elements => {
    const longText = elements.innerHTML;
    const truncated = truncateText(longText, 40);
    console.log(truncated);

    elements.innerHTML = truncated;
});


// 판매등록 버튼 클릭
const productApplyButton = document.querySelector("#productApplyButton");

productApplyButton.addEventListener("click", () => {
    var checkboxes = document.getElementsByName('selectProduct');
    var selectedValues = [];

    checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            selectedValues.push(checkbox.value);
        }
    });

    console.log(selectedValues);

    if(selectedValues.length == 0) {
        alert("선택된 상품이 없습니다");
    }else {
        if(confirm("선택한 상품을 판매등록 하시겠습니까?")) {
            fetch("/partner/seller/product/sellApplyProduct", {
                method: "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(selectedValues)
            })
            .then(resp => resp.text())
            .then(result => {
                if(result > 0) {
                    alert("판매등록이 완료되었습니다");
                    window.location.reload();
                }else {
                    alert("판매등록 실패");
                }
            })
        } else {
            alert("판매등록이 취소되었습니다");
        }
    }
});


// 재고생성 모달창
productCreateButton.addEventListener("click", () => {
    modal.style.display = "flex";
    productName.value = "";
    bigCategory.value = 'none';
    smallcategory.value = 'none';
    price.value = "";
    optionBox.innerHTML = "";
    document.querySelector("#preview").removeAttribute('src');
    document.querySelector("#filePlus").style.display = 'block';
    const subImgBox = document.getElementById('subImgBox');
    subImgBox.innerHTML = '';

    subImageFiles = [];

    // Clear preview image
    const preview = document.getElementById('preview');
    preview.src = '';
});

// 대표 이미지 미리보기
function readMainURL(fileInput) {
    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
            document.getElementById('filePlus').style.display = 'none';
        };
        reader.readAsDataURL(fileInput.files[0]);
    } else {
        document.getElementById('preview').removeAttribute('src');
    }

    console.log(fileInput.files[0]);
}




let selectOption = 1;

plusButton.addEventListener("click", () => {
    selectOption = 0;
    plusButton.style.display = "none";

    const box = document.createElement("div")
    box.classList.add("optionMainBox");

    const optionName = document.createElement("input");
    optionName.classList.add("optionName");
    optionName.placeholder = "예시 : 컬러";
    optionName.setAttribute('name', 'optionName');

    const delButton = document.createElement("button");
    delButton.classList.add("delButton");
    delButton.innerText = "삭제";

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
    contentInput.setAttribute('name', 'optionContent');

    contentCountInput.classList.add("contentCountInput");
    contentCountInput.setAttribute('name', 'optionCount');
    contentCountInput.type = 'number';

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
    box.append(delButton);
    box.append(optionContentBox);

    optionBox.append(box);




    /* 옵션 내용 추가 */
    addOption.addEventListener("click", () => {
        const clone = tr2.cloneNode(true);
        console.log(clone);
        console.log(clone.querySelector('.contentInput'));
        clone.querySelector('.contentInput').value = "";
        clone.querySelector('.contentCountInput').value = "";
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

        let temp = true;

        const allInput = document.getElementsByClassName("contentInput");
        const allCount = document.getElementsByClassName("contentCountInput");

        if(optionName.value.trim().length === 0) {
            alert("옵션명을 입력해주세요");
            temp = false;

            return;
        }

        for(let i = 0; i < allInput.length; i ++) {
            if(allInput[i].value.trim().length == 0) {
                alert("옵션값 작성을 완료해주세요");
                temp = false;

                return;
            }
            
        }

        for(let i = 0; i < allCount.length; i ++) {
            if(allCount[i].value.trim().length == 0) {
                alert("재고개수 작성을 완료해주세요");
                temp = false;

                return;
            }
            
        }       
        
        if(temp) {
            const cutInput1 = document.createElement("input");
            cutInput1.type = 'hidden';
            cutInput1.value = '/';
            cutInput1.setAttribute('name', 'optionContent');

            optionContentBox.append(cutInput1);

            const cutInput2 = document.createElement("input");
            cutInput2.type = 'hidden';
            cutInput2.value = '/';
            cutInput2.setAttribute('name', 'optionCount');

            optionContentBox.append(cutInput2);

            selectOption = 1;
            plusButton.style.display = "block";
            table.style.display = "none";
            applyButton.style.display = "none";
            delButton.style.display = "block";
        }
    })


    // 옵션 삭제버튼 눌렀을 떄
    delButton.addEventListener("click", e => {
        if(confirm(`'${e.target.parentElement.querySelector(".optionName").value}'옵션을 삭제하시겠습니까?`)) {
            e.target.parentElement.remove();
        } else {
            e.preventDefault();
        }
    })
});




const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("등록을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
})



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

// 상품삭제
function delProduct(productNo) {

    fetch("/partner/seller/product/delProduct", {
        method: "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(productNo)
    })
    .then(resp => resp.text())
    .then(result => {

        if(confirm("해당 상품을 삭제하시겠습니까?")) {
            if(result > 0) {
                alert("삭제 성공");
                window.location.reload();
            } else {
                alert("삭제 실패");
            }
        }
        

    })
};


