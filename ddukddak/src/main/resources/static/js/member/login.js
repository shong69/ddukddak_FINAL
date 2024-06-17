// 네이버 로그인 호출
var naver_id_login = new naver_id_login("uoiYUgBnF7qZ2w07iyBY", "http://localhost/oauth/naverCallback");
var state = naver_id_login.getUniqState();
naver_id_login.setButton("green", 3,50);
naver_id_login.setDomain("http://localhost");
naver_id_login.setState(state);
naver_id_login.setPopup();
naver_id_login.init_naver_id_login();
