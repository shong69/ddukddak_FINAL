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


const countDownTimer = function (i, date) {
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

        const samples = document.getElementsByClassName("sample");
        samples[i].textContent = days + '일 ';
        samples[i].textContent += hours + '시간 ';
        samples[i].textContent += minutes + '분 ';
        samples[i].textContent += seconds + '초';
    }

    timer = setInterval(showRemaining, 1000);
}

var dateObj = new Date();
dateObj.setDate(dateObj.getDate() + 1);

countDownTimer('0', '07/01/2024');
countDownTimer('1', '06/29/2024');
countDownTimer('2', '06/15/2024');
countDownTimer('3', '06/20/2024');


function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");
const shoppintItems = document.querySelector("#shopping-items");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
}


/* 베스트 상품 클릭했을 때 */
const selectBest = document.querySelectorAll(".selectBest");
const bestContainer = document.querySelector("#bestContainer");

selectBest.forEach(elements => {

    elements.addEventListener("click", e => {
        let valueNo = e.target.attributes[0].value
        fetch("/eCommerce/selectBestProduct", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(valueNo)
        })
        .then(resp => resp.text())
        .then(result => {

            const bestList = JSON.parse(result);

            bestContainer.innerHTML = "";

            bestList.forEach( (product) => {
                let arr = [product.productName,
                            product.productPrice];

                    for(let key of arr){
                        bestContainer.innerHTML = key;
                    }
                });

        })
    })

})