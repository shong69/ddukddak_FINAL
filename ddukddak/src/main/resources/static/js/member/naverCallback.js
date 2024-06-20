
// 네이버 로그인 콜백 처리
var naver_id_login = new naver_id_login("uoiYUgBnF7qZ2w07iyBY", "http://localhost/member/naverCallback");
// 접근 토큰 값 출력
// alert(naver_id_login.oauthParams.access_token);

// 네이버 사용자 프로필 조회
naver_id_login.get_naver_userprofile("naverSignInCallback()");
// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function

// name하고, email만 받자
// id는 키값

// naver_id_login.getProfileData('email')
// naver_id_login.getProfileData('name')
// naver_id_login.getProfileData('id')

async function naverSignInCallback() {

    const id = naver_id_login.getProfileData('id');
    const name = naver_id_login.getProfileData('name');
    const email = naver_id_login.getProfileData('email');

    const obj = {
        'id' : id,
        'name' : name,
        'email' : email
    };

    try {
        
        const loginResponse = await fetch('/naverLogin', {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(obj)
        });
        const loginResult = await loginResponse.text();

        console.log("loginResult(서버 응답) : " + loginResult)
        
        if(loginResult == 1) {
            // 로그인 성공
            alert(`${name}님 환영합니다 :)`)
            window.opener.location.href = '/'; // 부모 창을 메인 페이지로 리다이렉트
            window.close(); // 현재 창 닫기
        } else if(loginResult == 2) {
            alert('이메일이 이미 존재하는 아이디가 있습니다. \n해당 아이디로 로그인해 주세요');
            window.opener.location.href = '/member/login'; // 부모 창을 로그인 페이지로 리다이렉트
            window.close(); // 현재 창 닫기
        } else if(loginResult == 3) {
            alert(`${name}님의 네이버 아이디로 가입을 환영합니다 :)\n원활한 사이트 이용을 위해 기본 정보를 추가 및 수정해 주세요.`)
            window.opener.location.href = '/myPage/memberInfo'; // 부모 창을 마이페이지 리다이렉트
            window.close(); // 현재 창 닫기
        } else if (loginResult == 4) {
            // 로그인, 중복, 회원가입 실패
            alert("로그인 처리 중 오류가 발생했습니다.");
        } else {
            // 알 수 없는 응답
            alert("알 수 없는 응답 오류가 발생했습니다.");
        }



    } catch(err) {
        alert('로그인 처리 중 오류 발생')
    }
}