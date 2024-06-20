const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox");

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

bigCategory.children[0].remove();

  
  
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



document.addEventListener('DOMContentLoaded', function() {
  const delOption = document.querySelectorAll(".delOption");

  delOption.forEach(elements => {
    elements.addEventListener("click", e => {
      console.log(e.target);
      if(confirm(`"${e.target.parentElement.parentElement.querySelector(".optionName").value}"옵션을 삭제하시겠습니까?`)) {
        e.target.parentElement.parentElement.remove();
      }
    })
  })

  const addOptionButton = document.querySelectorAll(".addOption");

  addOptionButton.forEach(elements => {
    elements.addEventListener("click", e => {
      addOption(e.target);
    })
  })

  const delSubOntionButton = document.querySelectorAll(".minusOption");

  delSubOntionButton.forEach(elements => {
    elements.addEventListener("click", e => {
      delSubOption(e.target);
    })
  })

  // 상세이미지 삭제
  const delImgButton = document.querySelectorAll(".delButton");

  delImgButton.forEach(elements => {
    elements.addEventListener("click", e => {
      const imgNo = e.target.value

      fetch("/partner/seller/product/delImgs", {
        method: "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(imgNo)
        })
        .then(resp => resp.text())
        .then(result => {
          e.target.parentElement.remove();
        })
    })
  })

  const applyButton = document.querySelectorAll(".applyButton");

  // 적용 버튼 눌렀을 떄
  applyButton.forEach(elements => {
    elements.addEventListener("click", e => {
      const optionContentBox = e.target.parentElement;
      const plusEx = e.target.parentElement.parentElement.querySelector(".plusEx");
  
      let temp = true;
  
      const optionNameInput = e.target.parentElement.parentElement.querySelector(".optionName");
      const allInput = e.target.parentElement.getElementsByClassName("contentInput");
      const allCount = e.target.parentElement.getElementsByClassName("contentCountInput");  

      if(optionNameInput.value.trim().length === 0) {
        alert("옵션명을 입력해주세요")
        temp = false;
      }
      
      for(let i = 0; i < allInput.length; i ++) {
        if(allInput[i].value.trim().length === 0) {
          alert("모든 옵션값을 입력해주세요");
          temp = false;
        }
      }
      
      for(let i = 0; i < allCount.length; i ++) {
        if(allCount[i].value.trim().length === 0) {
          alert("모든 옵션값을 입력해주세요");
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
  
          optionContentBox.style.display = 'none';
          e.target.style.display = "none";
          e.target.nextElementSibling.style.display = 'none';
          plusEx.style.display = 'block';

          plusEx.addEventListener('click', () => {
            console.log(elements);
            optionContentBox.style.display = 'flex';
            plusEx.style.display = 'none';
            e.target.style.display = "block";
            e.target.nextElementSibling.style.display = 'block';
          })
      }
  })

  })

});

// 세부옵션 삭제
function delSubOption(product) {
  product.parentElement.parentElement.remove();
}

// 세부옵션 추가
function addOption(product) {

  var temp = true;

  console.log(product);
  const table = product.parentElement.parentElement.parentElement;

  const inputs = table.querySelectorAll("input")

  inputs.forEach(elements => {
    if(elements.value.trim().length === 0) {
      temp = false;
      return;
    }
  })


  if(temp) {
    const tr = document.createElement("tr");
  
    const td1 = document.createElement("td");
  
    const input1 = document.createElement("input");
    input1.classList.add("contentInput");
    input1.setAttribute('name', 'optionContent');
    input1.placeholder = "예시 : 빨강";
  
    const td2 = document.createElement("td");
  
    const input2 = document.createElement("input");
    input2.classList.add("contentCountInput");
    input2.setAttribute('name', 'optionCount');
  
    const td3 = document.createElement("td");
  
    const button = document.createElement("button");
    button.classList.add("minusOption");
    button.innerText = "-";
  
    td1.append(input1);
    td2.append(input2);
    td3.append(button);
  
    tr.append(td1);
    tr.append(td2);
    tr.append(td3);
  
    table.append(tr);

    button.addEventListener("click", () => {
      delSubOption(button);
    })
  } else {
    alert("옵션값 입력을 완료해주세요");
  }
  

}

const productPlusButton = document.querySelector("#productPlusButton");

// 옵션 추가
productPlusButton.addEventListener("click", e => {

  
  const allApply = document.getElementsByClassName("applyButton");

  for(let i = 0; i < allApply.length; i ++) {
    if(allApply[i].style.display != 'none') {
      alert("모든 옵션값을 적용 후에 추가해주세요");
      return;
    }
  }

  const optionContentContainer = document.querySelector('#optionBox');
  
  const box1 = document.createElement("div");
  box1.classList.add("optionMainBox");

  const h4 = document.createElement("h4");
  h4.innerText = "옵션명";

  const inputBox = document.createElement("input");
  inputBox.classList.add("optionName");
  inputBox.setAttribute('name', 'optionName');
  inputBox.placeholder = "예시 : 컬러";

  const h5 = document.createElement("h5");
  h5.innerText = "상세보기";
  h5.classList.add("plusEx");

  const box2 = document.createElement("div");
  box2.classList.add("optionContentBox");

  const table = document.createElement("table");
  const mainTr = document.createElement("tr");

  const mainTd1 = document.createElement("td");
  mainTd1.innerText = "옵션 내용";

  const mainTd2 = document.createElement("td");
  mainTd2.innerText = "재고 수량";

  const mainTd3 = document.createElement("td");

  const addButton = document.createElement("button");
  addButton.classList.add("addOption");
  addButton.innerText = "+";

  mainTd3.append(addButton);

  mainTr.append(mainTd1);
  mainTr.append(mainTd2);
  mainTr.append(mainTd3);
  
  const tr = document.createElement("tr");
  
    const td1 = document.createElement("td");
  
    const input1 = document.createElement("input");
    input1.classList.add("contentInput");
    input1.setAttribute('name', 'optionContent');
    input1.placeholder = "예시 : 빨강";
  
    const td2 = document.createElement("td");
  
    const input2 = document.createElement("input");
    input2.setAttribute('name', 'optionCount');
    input2.classList.add("contentCountInput");
  
    const td3 = document.createElement("td");
  
    const button = document.createElement("button");
    button.classList.add("minusOption");
    button.innerText = "-";

    const applyButton = document.createElement("button");
    applyButton.classList.add("applyButton");
    applyButton.type = 'button';
    applyButton.innerText = "적용";

    const delButtonBox = document.createElement("button");
    delButtonBox.classList.add("delOption");
    delButtonBox.type = 'button';
    delButtonBox.innerText = "삭제";
  
    td1.append(input1);
    td2.append(input2);
    td3.append(button);
  
    tr.append(td1);
    tr.append(td2);
    tr.append(td3);
  
    table.append(mainTr);
    table.append(tr);

    box2.append(table);
    box2.append(applyButton);
    box2.append(delButtonBox);

    box1.append(h4);
    box1.append(inputBox);
    box1.append(h5);
    box1.append(box2);

    optionContentContainer.append(box1);

    addButton.addEventListener("click", () => {
      addOption(addButton);
    })

    button.addEventListener("click", () => {
      delSubOption(button);
    })

    delButtonBox.addEventListener("click", () => {
      
      if(confirm(`"${inputBox.value}"옵션을 삭제하시겠습니까?`)) {
        box1.remove();
      }
    })

    // 적용 버튼 눌렀을 떄

    applyButton.addEventListener("click", e => {
      const optionContentBox = e.target.parentElement;
      const plusEx = e.target.parentElement.parentElement.querySelector(".plusEx");
  
      let temp = true;
  
      const optionNameInput = e.target.parentElement.parentElement.querySelector(".optionName");
      const allInput = e.target.parentElement.getElementsByClassName("contentInput");
      const allCount = e.target.parentElement.getElementsByClassName("contentCountInput"); 

      if(optionNameInput.value.trim().length === 0) {
        alert("옵션명을 입력해주세요")
        temp = false;
        return;
      }
      
      for(let i = 0; i < allInput.length; i ++) {
        if(allInput[i].value.trim().length === 0) {
          alert("모든 옵션값을 입력해주세요");
          temp = false;
          return;
        }
      }
      
      for(let i = 0; i < allCount.length; i ++) {
        if(allCount[i].value.trim().length === 0) {
          alert("모든 옵션값을 입력해주세요");
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
  
          optionContentBox.style.display = 'none';
          e.target.style.display = "none";
          e.target.nextElementSibling.style.display = 'none';
          plusEx.style.display = 'block';

          plusEx.addEventListener('click', () => {
            optionContentBox.style.display = 'flex';
            plusEx.style.display = 'none';
            e.target.style.display = "block";
            e.target.nextElementSibling.style.display = 'block';
          })
      }
  })


})

// 대표 이미지 미리보기
function readMainURL(fileInput) {
  if (fileInput.files && fileInput.files[0]) {
      var reader = new FileReader();
      reader.onload = function(e) {
          document.getElementById('preview').src = e.target.result;
      };
      reader.readAsDataURL(fileInput.files[0]);
  } else {
      document.getElementById('preview').removeAttribute('src');
  }

  console.log(fileInput.files[0]);
}
