
// 네이버 사용 불가 처리
// const tooltip = document.getElementById('tooltip');
// const elements = document.querySelectorAll('.disabled');

// elements.forEach(element => {
//     element.addEventListener('mouseover', (event) => {
//         const tooltipText = event.currentTarget.getAttribute('data-tooltip');
//         tooltip.textContent = tooltipText;
//         tooltip.style.display = 'block';
//     });

//     element.addEventListener('mousemove', (event) => {
//         tooltip.style.left = `${event.pageX + 10}px`;
//         tooltip.style.top = `${event.pageY + 10}px`;
//     });

//     element.addEventListener('mouseout', () => {
//         tooltip.style.display = 'none';
//     });
// });

// 네이버 팝업창 열기 
document.getElementById('naver-login-btn').addEventListener('click', function(event) {
    event.preventDefault();
    

    const width = 600;
    const height = 600;
    const left = (screen.width / 2) - (width / 2);
    const top = (screen.height / 2) - (height / 2);


    // 카카오 컨트롤러로 매핑
    const naverLoginUrl = '/naverLogin';
    window.open(naverLoginUrl, 'naver-login-popup', `width=${width},height=${height},top=${top},left=${left}`);
});




// 카카오 팝업창 열기 
document.getElementById('kakao-login-btn').addEventListener('click', function(event) {
    event.preventDefault();
    

    const width = 600;
    const height = 600;
    const left = (screen.width / 2) - (width / 2);
    const top = (screen.height / 2) - (height / 2);


    // 카카오 컨트롤러로 매핑
    const kakaoLoginUrl = '/kakaoLogin';
    window.open(kakaoLoginUrl, 'kakao-login-popup', `width=${width},height=${height},top=${top},left=${left}`);
});


// 구글 팝업창 열기
document.getElementById('google-login-btn').addEventListener('click', e => {


    e.preventDefault();
    
    const width = 600;
    const height = 600;
    const left = (screen.width / 2) - (width / 2);
    const top = (screen.height / 2) - (height / 2);

    const googleLoginUrl = '/googleLogin';
    window.open(googleLoginUrl, 'google-login-popup', `width=${width},height=${height},top=${top},left=${left}`);

})