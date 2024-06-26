/* 선택된 이미지 미리보기 */
const inputImageList = document.querySelectorAll(".inputImg"); 
const deleteImageList = document.querySelectorAll(".delete-img");
let imgCount = imgList.length;

// 1. 기존 이미지 길이만큼 배열만들기
let imgArray = new Array(imgCount);

console.log(imgArray.length);

// 2. 배열에 기존 img 태그 다 넣어놓기
previewList.forEach((img, index) => {
  imgArray[index] = img;
});

console.log(imgArray);

// 3. 만약 x 버튼(삭제 시) 해당 인덱스에 null 값 넣기
// 4. 만약 수정시 해당 인덱스에 File 객체 넣기
// 5. submit 요청 시 해당 배열의 모든 요소를 for을 이용하여 순차 접근해서 
// 요소 중 null이 있다면 -> 서브밋 안되게
// 요소 중 null이 없다면 -> File 객체가 있는 인덱스의 번호와 File 객체를 서버로 넘기기
// formData 

// x버튼이 눌러져 삭제된 이미지의 순서를 저장
// * Set : 중복 저장 X, 순서 유지 X
const deleteOrder = new Set();



// 이미지 선택 이후 취소를 누를 경우를 대비한 백업 이미지
// (백업 원리 -> 복제품으로 기존 요소를 대체함)
const backupInputList = new Array(inputImageList.length);


/* ***** input 태그 값 변경 시(파일 선택 시) 실행할 함수 ***** */
/**
 * @param inputImage : 파일이 선택된 input 태그
 * @param order : 이미지 순서
 */
const changeImageFn = (inputImage, order) => {

  // byte단위로 10MB 지정
  const maxSzie = 1024 * 1024 * 10;

  // 업로드된 파일 정보가 담긴 객체를 얻어와 변수에 저장
  const file = inputImage.files[0];


  // ------------- 파일 선택 -> 취소 해서 파일이 없는 경우 ----------------
  if(file == undefined){
    console.log("파일 선택 취소됨");

    // 같은 순서(order)번째 backupInputList 요소를 얻어와 대체하기

    /* 한 번 화면에 추가된 요소는 재사용(다른 곳에 또 추가) 불가능 */

    // 백업본을 한 번 더 복제
    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp); // 백업본을 다음 요소로 추가
    inputImage.remove();    // 원본을 삭제
    inputImage = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입

    // 백업본에 없는 이벤트 리스너를 다시 추가
    inputImage.addEventListener("change", e => {
      changeImageFn(e.target, order);
    })

    return;
  }


  // ---------- 선택된 파일의 크기가 최대 크기(maxSize) 초과 ---------

  if(file.size > maxSzie){
    alert("10MB 이하의 이미지를 선택해주세요");

    // 해당 순서의 backup 요소가 없거나, 
    // 요소는 있는데 값이 없는 경우 == 아무 파일도 선택된적 없을 때
    if(backupInputList[order] == undefined
        || backupInputList[order].value == ''){

      inputImage.value = ""; // 잘못 업로드된 파일 값 삭제
      return;
    }

    // 이전에 정상 선택 -> 다음 선택에서 이미지 크기 초과한 경우
    // 백업본을 한 번 더 복제
    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp); // 백업본을 다음 요소로 추가
    inputImage.remove();    // 원본을 삭제
    inputImage = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입
 
    // 백업본에 없는 이벤트 리스너를 다시 추가
    inputImage.addEventListener("change", e => {
     changeImageFn(e.target, order);
    })

    return;
  }




  // ------------ 선택된 이미지 미리보기 --------------

  const reader = new FileReader(); // JS에서 파일을 읽고 저장하는 객체

  // 선택된 파일을 JS로 읽어오기 -> reader.result 변수에 저장됨
  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    const url = e.target.result;

    // img 태그(.preview)에 src 속성으로 url 값을 대입
    previewList[order].src = url;

    // 같은 순서 backupInputList에 input태그를 복제해서 대입
    backupInputList[order] = inputImage.cloneNode(true);

    // 이미지가 성공적으로 읽어진 경우
    // deleteOrder에서 해당 순서를 삭제
    deleteOrder.delete(order);
  });
}

for(let i=0 ; i<inputImageList.length ; i++){

  // **** input태그에 이미지가 선택된 경우(값이 변경된 경우) ****
  inputImageList[i].addEventListener("change", e => {
    
    changeImageFn(e.target, i);
    
    console.log(inputImageList[i].files);

    imgArray[i] = inputImageList[i].files;

    console.log(imgArray);
  })
    
  
  // **** x 버튼 클릭 시 ****
  deleteImageList[i].addEventListener("click", () => {
    
    // img, input, backup의 인덱스가 모두 일치한다는 특징을 이용
    
    // 삭제된 이미지 순서를 deleteOrder에 기록
    
    // 미리보기 이미지가 있을 때에만
    if(previewList[i].getAttribute("src") != null 
    &&  previewList[i].getAttribute("src") != ""  ){
      imgArray[i] = null;
    }
    
    console.log(imgArray);
    previewList[i].src       = ""; // 미리보기 이미지 제거
    inputImageList[i].value  = ""; // input에 선택된 파일 제거
    backupInputList[i]       = undefined; // 백업본 제거
    
    
  });
}

// =======================================================================================

// 유효성 검사
document.querySelector("#updateMyHouseForm").addEventListener("submit", e => {

  const boardTitle = document.querySelector("#boardTitle");
  const boardContent = document.querySelector("#boardContent");

  for(let i = 0; i < imgArray.length; i ++) {
    if(imgArray[i] == null) {
      alert("빈 집들이 이미지를 등록해주세요.");
      e.preventDefault();
      break;
    }
  }

  if(boardTitle.value.trim().length === 0) {
    alert("집들이 제목을 입력해주세요.");
    e.preventDefault();
    boardTitle.focus();
    return;
  }

  if(boardContent.value.trim().length === 0) {
    alert("집들이 내용을 입력해주세요.");
    e.preventDefault();
    boardContent.focus();
    return;
  }

});


document.querySelector("#cancelBtn").addEventListener("click", () => {

  if(!confirm("집들이 게시글 수정을 취소하시겠습니까?")) {
    return;
  }
  
  alert("집들이 게시글 수정이 취소되었습니다.");
  location.href = "detail/" + boardNo;
})