/*회원 정보 버튼 클릭 시 화면 전환*/

/*변경 버튼 */
const myPageMainBtn = document.querySelector(".myPageMainBtn");
const myPageSubBtns = document.querySelectorAll(".myPageSubBtn");
/*취소 버튼 */
const cancelBtns = document.querySelectorAll(".cancel");
const pwCancelBtn = document.querySelector(".pwCancel");
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
        //소셜 로그인의 경우 이메일과 비밀번호 변경 막기
        //memberType 이 k나 N인 경우 막기
        if(loginMember.socialLoginType != 'D' && parentTd.className=='password-area'){
            alert("소셜 로그인의 경우 비밀번호 변경이 불가합니다");
            return;
        }
        if(loginMember.socialLoginType != 'D' && parentTd.className=='email-area'){
            alert("소셜 로그인의 경우 이메일 변경이 불가합니다");
            return;
        }
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

//비밀번호 취소 시 내용 삭제 + 비밀번호 보이기 여부 초기화
const viewPwBtns = document.querySelectorAll(".fa-eye");
const unviewPwBtns = document.querySelectorAll(".fa-eye-slash");

pwCancelBtn.addEventListener("click", ()=>{
    const parentTd =pwCancelBtn.parentElement.parentElement.parentElement;
    parentTd.style.display ="none";
    parentTd.previousElementSibling.style.display="";

    //작성했던 input 값 없애기
    const inputs = parentTd.querySelectorAll('input');
    inputs.forEach(input =>{
        input.value = '';
    });
    viewPwBtns.forEach(btn=>{
        btn.classList.remove('display-none');
    });
    unviewPwBtns.forEach(btn=>{
        btn.classList.add('display-none');
    });
    //알람 문구 초기화
    currentPwAlert.innerText="";
    newPwAlert.innerText="";
    newPwConfirmAlert.innerText="";


})

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

/*****회원 정보 변경 확인 버튼 클릭 시 display="" 처리 + form 제출하기(비동기)*****/

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
        console.log(file);
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
        profileImg.src = "/images/default/main.jpg";
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
            return;
        }
        alert("이미지가 변경되었습니다.");
    });

}






//-------------------------------------------------------------------

//2. 비밀번호 변경
const pwConfirmBtn = document.querySelector("#pwConfirmBtn");

const currentPw = document.querySelector('input[name="currentPw"]');
const currentPwAlert = document.querySelector(".currentPwAlert");

const newPw = document.querySelector('input[name="newPw"]');
const newPwAlert = document.querySelector(".newPwAlert");

const confirmNewPw = document.querySelector('input[name="confirmNewPw"]');
const newPwConfirmAlert = document.querySelector(".newPwConfirmAlert");

//*비밀번호 미입력 시 알람 문구 
const pwObj = { //유효성 확인 객체
    currentPw : false,
    newPw : false,
    confirmNewPw : false
}

    //1. 현재 비밀번호 : 글자수 검사
currentPw.addEventListener("input", e=>{
    if(e.target.value.trim().length <4){
        currentPwAlert.innerText =  "4자 이상 입력해주세요.";
        return;
    }else{
        currentPwAlert.innerText = "";
        pwObj.currentPw = true;
    }
});
    //2. 새 비밀번호 : 글자수, 유효성 검사
newPw.addEventListener("input", e=>{

    const inputValue = e.target.value;

    const regExp = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;

    if(inputValue.trim().length <8 || inputValue.trim().length >16){
        newPwAlert.innerText = "8~16자 사이의 비밀번호를 입력해주세요";
        pwObj.newPw=false;
        return;

    }else if(!regExp.test(inputValue)){
        newPwAlert.innerText = "숫자, 영문 대소문자, 특수문자를 조합해 주세요.";
        pwObj.newPw=false;
        return;
        
    }else{
        //유효한 경우
        newPwAlert.innerText = "";
        pwObj.newPw = true;
    }

    if(confirmNewPw.value.length>0){
        checkPw();
    }

});
    //3. 새 비밀번호 확인 : 새 비밀번호와 동일한지 검사
confirmNewPw.addEventListener("input", ()=>{
    //같을 경우
    if(newPw.value == confirmNewPw.value){
        newPwConfirmAlert.innerText="";
        pwObj.confirmNewPw= true; 
        return;
    }
    //다를 경우
    newPwConfirmAlert.innerText="비밀번호가 일치하지 않습니다.";
    pwObj.confirmNewPw=false;
});




//*눈 클릭 시 비밀번호 보였다 안보였다
// const viewPwBtns = document.querySelectorAll(".fa-eye"); 위에서 선언함
// const unviewPwBtns = document.querySelectorAll(".fa-eye-slash");

//비번 보이도록하기
viewPwBtns.forEach( btn =>{
    btn.addEventListener("click", ()=>{
        btn.classList.add('display-none');
        btn.nextElementSibling.classList.remove('display-none');
        btn.previousElementSibling.type="text"; //input의 type을 text로 바꾸기
    });
})

unviewPwBtns.forEach(btn =>{
    btn.addEventListener("click", ()=>{
        btn.classList.add("display-none");
        btn.previousElementSibling.classList.remove('display-none');
        btn.previousElementSibling.previousElementSibling.type="password";
    })
})


//*비밀번호 변경하기-비동기
// pwObj가 모두 true인 경우 넘기기
pwConfirmBtn.addEventListener("click", e=>{

    let pass="true";
    for(let key in pwObj){
        if(!pwObj[key]) pass="false";
    }
    if(pass=="false") {
        alert("비밀번호 입력이 유효하지 않습니다.\n다시 시도해 주세요");
        return;
    }

    const obj = {
        "currentPw" : currentPw.value,
        "newPw" : newPw.value,
        "confirmNewPw" : confirmNewPw.value
    }

    //비동기로 비밀번호 값들 넘기기
    fetch("/myPage/memberInfo/password",{
        method: "POST",
        headers : {"Content-Type" : "application/json"},
        body: JSON.stringify(obj)
    })
    .then(resp => resp.json())
    .then(result => {
        alert(result.message);
        currentPw.value = "";
        newPw.value = "";
        confirmNewPw.value = "";
    })
    .catch(error =>{
        console.error('Error',error);
    })
});




//-------------------------------------------------------------------
// 3. 이메일 변경
const emailInput = document.querySelector("input[name='email-input']"); //이메일 input
const authBtn = document.querySelector(".emailAuthBtn"); //인증 버튼
const authInput = document.querySelector("input[name='auth-input']") //인증번호 input

const emailConfirmBtn = document.querySelector(".emailConfirmBtn"); //변경 버튼

//타이머
let authTimer; //타이머 역할을 할 setInterval을 저장할 변수
const initMin = 4; //타이머 초기값(분)
const intitSec= 59; //타이머 초기값 (초)
const initTime = "05:00";

let min = initMin;
let sec= intitSec;

// 1. 이메일 형식 유효성 검사 + 메일 발송
authBtn.addEventListener("click", async()=>{
    //공백
    if(emailInput.value.trim().length ===0){
        alert("이메일 주소를 입력해주세요.");
        return;
    }

    //유효성
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!regExp.test(emailInput.value)){
        alert("알맞은 이메일 형식이 아닙니다.");
        return;
    }

    try{
        //이메일 중복검사
        const emailDupCheck = await fetch("/myPage/memberInfo/emailDup?memberEmail="+emailInput.value);
        const dupResult = await emailDupCheck.text();
        console.log(dupResult);
        if(dupResult == 1){
            alert("이미 사용 중인 이메일입니다.");
            return;
        }

        try{
            //인증메일 보내기
            const emailSend = await fetch("/email/updateEmail",{
                method:"POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({email:emailInput.value})
            });

            if(!emailSend.ok){
                alert("인증번호 발송에 실패하였습니다\n다시 시도해주세요");
                return;
            }else if(emailSend.ok){
                alert("입력해주신 이메일 주소로 인증번호가 발송되었습니다.");
                emailConfirmBtn.disabled = false;
            }
        }catch(Error){
            console.error("Error : ", Error);
            alert("메일 발송 중 오류 발생");
        }
        
    }catch(Error){
        console.error("Error : ", Error);
        alert("오류가 발생했습니다. 다시 시도해주세요");
    }


    //타이머 시작
    authTimer = setInterval(()=>{

        //0분 0초인 경우("00:00"출력 후에)
        if(min == 0 && sec == 0){
            clearInterval(authTimer);
            return;
        }

        //0초인 경우(0초를 출력한 후)
        if(sec==0){
            sec= 60;
            min--;
        }

        //평상시에는 1초씩 감소
        sec--; //1초 감소

        console.log(min, sec); //콘솔 창에서 남은 시간 확인
    }, 1000); //1초 지연시간
});



// 인증 버튼 클릭 - 비동기
emailConfirmBtn.addEventListener("click", () => {
    if (min === 0 && sec === 0) { // 타이머가 00:00인 경우
        alert("인증번호 입력 제한시간을 초과하였습니다.");
        return;
    }
    if (authInput.value.length < 6) { // 인증번호는 6자리임 -> 제대로 입력 안한 경우
        alert("인증번호를 정확히 입력해 주세요");
        return;
    }

    // 인증번호 검증 - 비동기
    const obj = {
        "email": emailInput.value,
        "authKey": authInput.value
    };

    fetch("/email/checkAuthKey", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if (result == 0) {
            alert("인증번호를 잘못 입력하였습니다.\n 다시 시도해주세요.");
            return;
        }
        clearInterval(authTimer);

        // 이메일 변경하기
        fetch("/myPage/memberInfo/emailUpdate?memberEmail=" + emailInput.value)
            .then(resp => resp.text())
            .then(result => {
                if (result == 0) {
                    alert("이메일 변경 실패");
                    return;
                }
                alert("인증이 완료 후 이메일이 변경되었습니다.");
                emailInput.value = "";
                authInput.value = "";

                const emailDiv = document.querySelector("#emailValue");
                emailDiv.innerText = emailInput.value;

                // 변경 폼 숨기기
                const changePhoneNumArea = document.querySelector(".change-email-area");
                changePhoneNumArea.style.display = 'none';

                // 원래 폼 다시 보이기
                const beforeModify = document.querySelector(".email-area");
                beforeModify.style.display = '';
            })
            .catch(error => {
                console.log(error);
            });
        })
        .catch(error => {
            console.log(error);
        });
});



//-------------------------------------------------------------------

// 4. 닉네임
const nicknameInput = document.querySelector("input[name='nicknameInput']");
const nicknameConfirmBtn  = document.querySelector(".nicknameConfirmBtn");


nicknameConfirmBtn.addEventListener("click", ()=>{
    inputValue = nicknameInput.value;
    console.log("바뀜!!");
    const regExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,8}$/;

    if(inputValue.trim().length <2 || inputValue.trim().length >8){
        alert("2~8자 사이의 닉네임을 입력해주세요");
        return;

    } 
    if(!regExp.test(inputValue)){
        alert("이모티콘 및 자모음, 공백, 특수문자 사용은 불가합니다.");
        return;
        
    }
    //닉네임 중복 검사
    //닉네임 변경 및 변경 횟수 검사
    fetch("/myPage/memberInfo/updateMemberNickname?memberNickname=" + inputValue)
    .then(resp => resp.text())
    .then(result =>{
        console.log(result);
        if(result == -2){
            alert("중복 닉네임입니다.\n다시 시도해주세요.");
        }
        else if(result==-1){
            alert("닉네임 변경 횟수가 초과하였습니다.");
            return;
        }else{
            alert(`닉네임이 변경되었습니다(${result}/4)`);
            nicknameInput.value = "";
            const nicknameDiv1 = document.querySelector("#nicknameDiv1");
            const nicknameDiv2 = document.querySelector("#nicknameDiv2");
            nicknameDiv1.innerHTML = inputValue;
            nicknameDiv2.innerHTML = inputValue;

            // 변경 폼 숨기기
            const changePhoneNumArea = document.querySelector(".change-nickname-area");
            changePhoneNumArea.style.display = 'none';

            // 원래 폼 다시 보이기
            const beforeModify = document.querySelector(".nickname-area");
            beforeModify.style.display = '';
        }
        
    }).catch(error=>{
        console.log(error);
    })
});


//---------------------------------------

//5. 휴대폰 번호 변경
const phoneNum = document.querySelector("input[name='phoneNum']");
const pAuthInput = document.querySelector("input[name='p-auth-input']");
const pAuthBtn = document.querySelector(".p-authBtn");
const phoneConfirmBtn = document.querySelector(".phoneConfirmBtn");

let pmin = initMin;
let psec= intitSec;

// 1. 휴대폰 번호 형식 유효성 검사 + 메일 발송
pAuthBtn.addEventListener("click", async()=>{
    //공백
    if(phoneNum.value.trim().length ===0){
        alert("휴대폰 번호를 입력해주세요.");
        return;
    }

    //유효성
    const regExp = /^\d{3}\d{3,4}\d{4}$/;

    if(!regExp.test(phoneNum.value)){
        alert("알맞은 번호 형식이 아닙니다.");
        return;
    }

    try{
        //중복검사
        const phoneDupCheck = await fetch("/myPage/memberInfo/phoneDup",{
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify({phoneNum : phoneNum.value})
        });

        const dupResult = await phoneDupCheck.text();

        if(dupResult == 1){
            alert("이미 사용 중인 번호입니다.");
            return;
        }

        try{
            //인증 문자 보내기
            const phoneNumSend = await fetch("/sms/updatePhoneNum",{
                method:"POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({phoneNum:phoneNum.value})
            });

            if(!phoneNumSend.ok){
                alert("인증번호 발송에 실패하였습니다\n다시 시도해주세요");
                return;
            }else if(phoneNumSend.ok){
                alert("입력해주신 휴대폰으로 인증번호가 발송되었습니다.");
                phoneConfirmBtn.disabled = false;
            }
        }catch(Error){
            console.error("Error : ", Error);
            alert("메일 발송 중 오류 발생");
        }
        
    }catch(Error){
        console.error("Error : ", Error);
        alert("오류가 발생했습니다. 다시 시도해주세요");
    }


    //타이머 시작
    authTimer = setInterval(()=>{

        //0분 0초인 경우("00:00"출력 후에)
        if(pmin == 0 && psec == 0){
            clearInterval(authTimer);
            return;
        }

        //0초인 경우(0초를 출력한 후)
        if(psec==0){
            psec= 60;
            pmin--;
        }

        //평상시에는 1초씩 감소
        psec--; //1초 감소

        console.log(pmin, psec); //콘솔 창에서 남은 시간 확인
    }, 1000); //1초 지연시간
});



// 인증 버튼 클릭 - 비동기
phoneConfirmBtn.addEventListener("click", () => {
    if (phoneConfirmBtn.disabled) alert("인증 번호가 미입력 상태입니다.");
    if (pmin === 0 && psec === 0) { // 타이머가 00:00인 경우
        alert("인증번호 입력 제한시간을 초과하였습니다.");
        return;
    }
    if (pAuthInput.value.length < 6) { // 인증번호는 6자리임 -> 제대로 입력 안한 경우
        alert("인증번호를 정확히 입력해 주세요");
        return;
    }

    // 인증번호 검증 - 비동기
    const obj = {
        "smsTel": phoneNum.value,
        "smsAuthKey": pAuthInput.value
    };

    fetch("/sms/checkSmsAuthKey", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(obj)
    })
        .then(resp => resp.text())
        .then(result => {
            if (result == 0) {
                alert("인증번호를 잘못 입력하였습니다.\n 다시 시도해주세요.");
                return;
            }
            clearInterval(authTimer);

            // 휴대폰 번호 변경하기
            fetch("/myPage/memberInfo/phoneNumUpdate", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ updatePhoneNum: phoneNum.value })
            })
                .then(resp => resp.text())
                .then(result => {
                    if (result == 0) {
                        alert("휴대폰 번호 변경 실패");
                        return;
                    }
                    alert("인증이 완료 후 휴대폰 정보가 변경되었습니다.");

                    let phoneDiv = document.querySelector("#phoneDiv");
                    phoneDiv.innerText = phoneNum.value;

                    phoneNum.value = "";
                    pAuthInput.value = "";

                    // 변경 폼 숨기기
                    const changePhoneNumArea = document.querySelector(".change-phoneNum-area");
                    changePhoneNumArea.style.display = 'none';

                    // 원래 폼 다시 보이기
                    const beforeModify = document.querySelector(".phoneNum-area");
                    beforeModify.style.display = '';

                    return;
                })
                .catch(error => {
                    console.log(error);
                });
        })
        .catch(error => {
            console.log(error);
        });
});



