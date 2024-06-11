function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
}

const orderSorting = document.getElementById('orderSorting');

orderSorting.addEventListener('change', function() {
    const selectedOption = this.value;
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('sort', selectedOption);
    window.location.href = currentUrl;
});


var currentUrl = window.location.href

var queryString = currentUrl.split('?')[1];

if (queryString) {

    var queryParams = queryString.split('&');


    for (var i = 0; i < queryParams.length; i++) {
        if (queryParams[i].indexOf('sort=') !== -1) {

            var sortValue = queryParams[i].split('=')[1];

            orderSorting.value = sortValue;

        }
    }
} else {
    console.log("쿼리 파라미터가 없습니다.");
}