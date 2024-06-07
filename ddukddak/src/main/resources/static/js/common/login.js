/* 쿠키 */

const getCookie = (key) => {

    // document.cookie="test"+"="+"user01";
    // arr.map(param) => new arr of param
    const cookies = document.cookie;
    const cookieList = cookies.split("; ") // ["K=V", "K=V"]
                                          .map(el => el.split("=")); // ["K","V"]
    // console.log(cookieList);

    const obj = {};

    for (let i=0; i < cookieList.length; i++) {
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k]=v;
    }
    return obj[key];
    // console.log(cookies); //== saveId=email
}
// console.log(getCookie("saveId"));

/* 일반회원 쿠키 */
const loginId = document.querySelector("#inputId");

if(loginId != null) { // loginform is shown
    // cookie k = "saveId" : value
    const saveId = getCookie("saveId");
    
    if(saveId != undefined) {
        loginId.value = saveId;

        document.querySelector("#saveId").checked= true;

    }
}

/* 파트너 쿠키 */
const partnerLoginId = document.querySelector("#partnerSaveId");

if(partnerLoginId != null) { // loginform is shown
    // cookie k = "saveId" : value
    const saveId = getCookie("partnerSaveId");
    
    if(saveId != undefined) {
        partnerLoginId.value = saveId;

        document.querySelector("#saveId").checked= true;

    }
}
