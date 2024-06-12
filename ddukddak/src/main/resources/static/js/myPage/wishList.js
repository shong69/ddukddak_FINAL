function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const ProductPrice = document.querySelectorAll(".productPrice");


for (let i = 0; i < ProductPrice.length; i++) {
    let productPrice = ProductPrice[i].textContent.trim(); // 요소의 텍스트 내용 가져오기

    ProductPrice[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    ProductPrice[i].innerHTML += '원';

}