function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
}

// 정렬순서 변경
const orderSorting = document.querySelector("#orderSorting");
const products = document.querySelectorAll(".products");

orderSorting.addEventListener("change", e => {
    ordervalue = e.target.value;

    console.log(products);

    if(ordervalue == 1) {
        console.log("1");
    } else if(ordervalue == 2) {
        console.log("2");
    } else if(ordervalue == 3) {
        console.log("3");
    } else if(ordervalue == 4) {
        console.log("4");
    }
})