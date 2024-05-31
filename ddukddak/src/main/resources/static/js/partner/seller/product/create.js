const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox")

plusButton.addEventListener("click", () => {
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




    /* 옵션내용 추가 */
    addOption.addEventListener("click", () => {
        const minusOptionButton = document.querySelectorAll(".minusOption");
        console.log(minusOptionButton)
        const clone = tr2.cloneNode(true);
        table.append(clone);
    })
});

