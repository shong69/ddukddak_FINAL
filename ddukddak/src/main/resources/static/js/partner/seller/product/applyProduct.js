const bigCategory = document.querySelector("#bigCategory");
const smallcategory = document.querySelector("#smallCategory");
const plusButton = document.querySelector("#productPlusButton");
const optionBox = document.querySelector("#optionBox")

let selectOption = 1;

const radio2 = document.querySelector("#radio2");

radio2.checked = true;

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





/* 이미지 업로드 */
function getImageFiles(e) {
  const uploadFiles = [];
  const files = e.currentTarget.files;
  const imagePreview = document.querySelector('.image-preview');
  const docFrag = new DocumentFragment();

  if ([...files].length >= 7) {
    alert('이미지는 최대 6개 까지 업로드가 가능합니다.');
    return;
  }

  // 파일 타입 검사
  [...files].forEach(file => {
    if (!file.type.match("image/.*")) {
      alert('이미지 파일만 업로드가 가능합니다.');
      return
    }

    // 파일 갯수 검사
    if ([...files].length < 7) {
      uploadFiles.push(file);
      const reader = new FileReader();
      reader.onload = (e) => {
        const preview = createElement(e, file);
        imagePreview.appendChild(preview);
      };
      reader.readAsDataURL(file);
    }
  });
}

function createElement(e, file) {
  const li = document.createElement('li');
  const img = document.createElement('img');
  img.setAttribute('src', e.target.result);
  img.setAttribute('data-file', file.name);
  li.appendChild(img);

  return li;
}

const realUpload = document.querySelector('.real-upload');
const upload = document.querySelector('.upload');

upload.addEventListener('click', () => realUpload.click());

realUpload.addEventListener('change', getImageFiles);

// input file 커스텀 - 파일명 붙이기
const fileTarget = $('.form__input--file_wrap input');

fileTarget.on('change', function () { 
  var files = $(this)[0].files;
  var fileArr = [];
  for (var i = 0; i < files.length; i++) {
    fileArr.push(files[i].name);
}
  
// 파일명 노출방법1: 배열 값 사이에 공백 추가
var fileList = fileArr.join(', '); // 배열 값을 쉼표와 공백으로 연결
$(this).siblings('.form__span--file').text(fileList);

});