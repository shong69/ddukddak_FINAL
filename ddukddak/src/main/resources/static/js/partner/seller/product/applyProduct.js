const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox");

console.log(optionBox);

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
    }
  })

  if(temp) {
    const tr = document.createElement("tr");
  
    const td1 = document.createElement("td");
  
    const input1 = document.createElement("input");
    input1.classList.add("contentInput");
    input1.setAttribute('name', 'opionContent');
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

productPlusButton.addEventListener("click", e => {
  const optionContentContainer = document.querySelector('#optionBox');
  
  const box1 = document.createElement("div");
  box1.classList.add("optionMainBox");

  const h4 = document.createElement("h4");
  h4.innerText = "옵션명";

  const inputBox = document.createElement("input");
  inputBox.classList.add("optionName");
  inputBox.setAttribute('name', 'optionName');
  inputBox.placeholder = "예시 : 컬러";

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
    input1.setAttribute('name', 'opionContent');
    input1.placeholder = "예시 : 빨강";
  
    const td2 = document.createElement("td");
  
    const input2 = document.createElement("input");
    input2.setAttribute('name', 'optionCount');
    input2.classList.add("contentCountInput");
  
    const td3 = document.createElement("td");
  
    const button = document.createElement("button");
    button.classList.add("minusOption");
    button.innerText = "-";

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
    box2.append(delButtonBox);

    box1.append(h4);
    box1.append(inputBox);
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

})