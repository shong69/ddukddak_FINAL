/*회원 정보 버튼 클릭 시 화면 전환*/

/*변경 버튼 */
const myPageMainBtn = document.querySelector(".myPageMainBtn");
const myPageSubBtns = document.querySelectorAll(".myPageSubBtn");
/*취소 버튼 */
const cancelBtns = document.querySelectorAll(".cancel");
/*변경 확인 버튼 */
const confirmBtns = document.querySelectorAll(".confirm");

//변경 버튼 클릭 시 display 변경
//작성 상태에서 취소 시 이전 상태로 돌아가기(input 값 없애고 display:""처리)
//등록 시 해당 영역 폼 제출 비동기

//변경 버튼 클릭 시 수정 영역이 보이도록
myPageMainBtn.addEventListener("click",()=>{
    const parentTd = myPageMainBtn.parentElement.parentElement;
    parentTd.style.display = "none";
    parentTd.nextElementSibling.style.display = "";
});
myPageSubBtns.forEach(btn => {
    btn.addEventListener("click", ()=>{
        const parentTd = btn.parentElement.parentElement.parentElement.parentElement;
        parentTd.style.display = "none";
        parentTd.nextElementSibling.style.display="";
        console.log("td가 보여요");
    });
});

//취소 버튼 클릭 시 dispaly=""
//사진 변경 시 
const profileCancelBtn = document.querySelector(".profile-del-btn");
profileCancelBtn.addEventListener("click", e=>{
    const parentTd = e.target.parentElement.parentElement;
    parentTd.style.display="none";
    parentTd.previousElementSibling.style.display= "";
});

//취소 버튼 클릭 시(나머지의 경우) 
cancelBtns.forEach(btn =>{
    btn.addEventListener("click",()=>{
        const parentTd =btn.parentElement.parentElement.parentElement;
        parentTd.style.display ="none";
        parentTd.previousElementSibling.style.display="";

        //작성했던 input 값 없애기
        const inputs = parentTd.querySelectorAll('input');
        inputs.forEach(input =>{
            input.value = '';
        });
    })
});



//-----------------------------------------------------------

/*회원 정보 변경 확인 버튼 클릭 시 display="" 처리 + form 제출하기(비동기)*/

//1. 사진 변경
const profile = document.querySelector("#profile-form");

let statusCheck = -1; //이미지 상태 변수 (-1:초기 , 0:삭제, 1:새이미지 선택)

let backupInput; //input type="file" 값 변경 시 변경된 상태 저장 변수


if(profile != null){
    const profileImg =document.querySelector("#profile-img");
    let imageInput = document.querySelector("#inputImage");

    const deleteImage = document.querySelector(".delete-image");

    const changeImageFn = e =>{
        const maxSize =  1024 * 1024 * 5;   //5MB
        const file = e.target.files[0];

        //*업로드 파일이 없는 경우(취소한 경우)
        if(file == undefined){ 
            console.log("파일 로드 후 취소");

            const temp = backupInput.cloneNode(true);

            imageInput.after(backupInput);
            imageInput.remove();
            imageInput = backupInput;  //백업 대입하기
            imageInput.addEventListener("change", changeImageFn);
            backupInput = temp;
            return;
        }

        //*선택된 파일이 최대 크기를 초과한 경우
        if(file.size > maxSize) {
            alert("5MB 이하의 이미지 파일을 등록해 주세요.");

            if(statusCheck == 1){ //초기상태에서는 빈칸으로 바꾸기
                imageInput.value ='';
            }else{
                const temp = backupInput.cloneNode(true); //백업의 백업
                imageInput.after(backupInput);
                imageInput.remove();
                imageInput = backupInput;
                imageInput.addEventListener("change", changeImageFn);
                backupInput = temp; //백업의 백업을 백업으로 바꾸기
            }
            return;
        }
            //*선택한 이미지 미리볼 수 있게 만들기
        const reader = new FileReader();

        reader.readAsDataURL(file); //읽어온 파일을 BASE64 인코딩 형태로 읽어와 변수에 저장
        reader.addEventListener("load", e=>{
            const url = e.target.result;
            profileImg.setAttribute("src", url);
            statusCheck = 1;  //이미지 선택 상태로 변경
            backupInput = imageInput.cloneNode(true); //백업하기
        });
    };
    //이미지 등록 input의 값이 바뀔 때마다 위 과정 거치기
    imageInput.addEventListener("change", changeImageFn);


    //*x버튼 클릭 시 기본 이미지로 변경하기
    deleteImage.addEventListener("click", ()=>{
        profileImg.src = "/images/profile/main.jpg";

        imageInput.value = "";
        backupInput = undefined;
        statusCheck = 0; //삭제 상태
    });
    
    //*form(id=profile) 제출 시
    profile.addEventListener("submit", e=>{
        let flag = true;
        if(loginMemberProfileImg == null && statusCheck==1) flag=false;
        if(loginMemberProfileImg != null && statusCheck ==0) flag = false;
        if(loginMemberProfileImg != null && statusCheck == 1) flag = false;

        if(flag){
            e.preventDefault();
            alert("이미지가 변경되지 않았습니다.");
        }
    });

}

function updateProfileImage() {
    const formData = new FormData(profile);
    
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/profileImg', true); // POST 형식, endpoint:/porfileImg, 비동기 여부 : true

    xhr.onload = function() { //서버로부터 응답 도착 시 함수 실행
        if(xhr.status=== 200) {
            alert('성공적으로 변경됨');
        }else{
            alert('실패');
        }
    };
    xhr.send(formData); //서버로 데이터 변경 요청

//2. 비밀번호 변경
const pwConfirmBtn = document.querySelector("#pwConfirmBtn");

pwConfirmBtn.addEventListener("click", ()=>{
    const param = {

    }
})
