document.addEventListener("DOMContentLoaded", function() {
    const email = document.getElementById('email').value;
    const nickname = document.getElementById('nickname').value;
    const pw = document.getElementById('pw').value;

    // 사용자 정보를 이용해 Ajax 요청 보내기
    const userInfo = {
        email: email,
        nickname: nickname,
        pw: pw
    };

    fetch('/member/naverData', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userInfo)
    })
    .then(response => response.text())
    .then(data => {
        console.log('Server response:', data);

        if(data == 1) {
            // 로그인 성공
            alert(`${nickname}님 환영합니다 :)`)
            window.opener.location.href = '/'; // 부모 창을 메인 페이지로 리다이렉트
            window.close(); // 팝업 창 닫기
            
        } else if(data == 2) {
            alert('이메일이 이미 존재하는 아이디가 있습니다. \n해당 아이디로 로그인해 주세요');
            window.opener.location.href = '/member/login'; // 부모 창을 로그인 페이지로 리다이렉트
            window.close(); // 팝업 창 닫기
            
        } else if(data == 3) {
            alert(`${nickname}님의 네이버 아이디로 가입을 환영합니다 :)\n원활한 사이트 이용을 위해 기본 정보를 추가 및 수정해 주세요.`)
            window.opener.location.href = '/myPage/memberInfo'; // 부모 창을 마이페이지로 리다이렉트
            window.close(); // 팝업 창 닫기
            
        } else if (data == 4) {
            // 로그인, 중복, 회원가입 실패
            alert("로그인 처리 중 오류가 발생했습니다.");
            window.close(); // 팝업 창 닫기
        } else {
            // 알 수 없는 응답
            alert("알 수 없는 응답 오류가 발생했습니다.");
            window.close(); // 팝업 창 닫기
        }

    })
    .catch((error) => {
        console.error('Error:', error);
    });
});