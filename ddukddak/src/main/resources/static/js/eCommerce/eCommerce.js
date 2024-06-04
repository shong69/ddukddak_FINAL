/* 광고 배너 */
setInterval(function(){
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-300px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-600px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-900px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-1200px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-1500px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-1800px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "-2100px"})
    $('#slide2>ul').delay(3000);
    $('#slide2>ul').animate({marginLeft: "0px"})
});


const countDownTimer = function (id, date) {
    var _vDate = new Date(date); // 전달 받은 일자
    var _second = 1000;
    var _minute = _second * 60;
    var _hour = _minute * 60;
    var _day = _hour * 24;
    var timer;

    function showRemaining() {
        var now = new Date();
        var distDt = _vDate - now;

        var days = Math.floor(distDt / _day);
        var hours = Math.floor((distDt % _day) / _hour);
        var minutes = Math.floor((distDt % _hour) / _minute);
        var seconds = Math.floor((distDt % _minute) / _second);

        //document.getElementById(id).textContent = date.toLocaleString() + "까지 : ";
        document.getElementById(id).textContent = days + '일 ';
        document.getElementById(id).textContent += hours + '시간 ';
        document.getElementById(id).textContent += minutes + '분 ';
        document.getElementById(id).textContent += seconds + '초';
    }

    timer = setInterval(showRemaining, 1000);
}

var dateObj = new Date();
dateObj.setDate(dateObj.getDate() + 1);

countDownTimer('sample01', '06/08/2024');
countDownTimer('sample02', '06/05/2024');