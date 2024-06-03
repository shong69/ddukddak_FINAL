const productCreateButton = document.querySelector("#productCreateButton");
const modal = document.querySelector(".modal");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox")


productCreateButton.addEventListener("click", () => {
    modal.style.display = "flex";
});


let selectOption = 1;

plusButton.addEventListener("click", () => {
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
});

const createButton = document.querySelector("#createButton");
const productName = document.querySelector("#productName");
const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const price = document.querySelector("#price");

createButton.addEventListener("click", e => {
    if(productName.value.trim().length === 0 ||
        bigCategory.options[bigCategory.selectedIndex].value == 'none' ||
        smallcategory.options[smallcategory.selectedIndex].value == 'none' ||
        price.value.trim().length === 0 ||
        selectOption == 0) {
            alert("입력을 완료해주세요");
            e.preventDefault();
        } else {
            alert("등록 완료");
            modal.style.display = "none";
        }
})


const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("등록을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
})


const categoryOptions = {
  'furniture': ['침대',
            '매트리스/토퍼',
            '테이블/식탁/책상',
            '소파',
            '서랍/수납장',
            '거실장/TV장',
            '선반',
            '진열장/책장',
            '의자',
            '행거/옷장'],

  'electronic': ['냉장고',
                'TV',
                '세탁기/건조기',
                '청소기',
                '주방가전',
                '에어컨',
                '계절가전',
                '컴퓨터/노트북',
                '모니터',
                '복합기/프린터/스캐너'],
  'kitchenware': ['그릇/식기',
                '냄비/프라이팬/솥',
                '컵/잔/텀블러',
                '수저/커트러리',
                '주방수납/정리',
                '식기건조대',
                '보관/용기/도시락',
                '주방잡화',
                '조리도구',
                '칼/도마/커팅기구'],
    'deco' : ['디퓨저/캔들',
            '플라워/식물',
            '홈갤러리',
            '인테리어소품',
            '시계',
            '월데코/장식',
            '데스크/디자인문구',
            'DIY/취미/공예',
            '파티/완구',
            '크리스마스'],
    'storage' : ['서랍장/트롤리',
                '리빙박스/바구니',
                '빨래바구니/햄퍼',
                '행거',
                '선반',
                '옷걸이',
                '옷정리/이불정리',
                '화장대/테이블정리',
                '현관/신발정리',
                '후크/수납걸이'],
    'household' : ['욕실용품',
                    '수건/타월',
                    '청소용품',
                    '세탁용품',
                    '생활잡화',
                    '자동차용품'],
    'baby' : ['완구/교구',
                '유아동침구/패브릭',
                '매트/안전용품',
                '유아동가구',
                '유아의류/잡화',
                '기저귀/기저귀용품',
                '세탁/위생용품',
                '수유용품',
                '유아/아동식기',
                '스킨케어/욕실용품'
    ]
};


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


