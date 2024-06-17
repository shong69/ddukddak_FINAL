//전체 체크박스 설정
const totalCheck = document.querySelector("#totalCheck");
const checks = document.querySelectorAll('input[name="check"]');

totalCheck.addEventListener("change",function(){
    checks.forEach(check =>{
        check.checked  = this.checked;
    })
});

//삭제 버튼 클릭 시 
const delBtns = document.querySelectorAll(".myPageDelUpdateBtn");

delBtns.forEach(btn =>{
    btn.addEventListener("click", ()=>{

        
        //화면에서 사라지기
        btn.parentElement.parentElement.remove();

        const boxCount = document.querySelectorAll(".boxCount");
        const countBox = document.querySelectorAll(".countBox");

        console.log(boxCount.length);
        console.log(countBox);

        for(let i = 0; i < boxCount.length; i ++) {
            console.log(countBox[i]);
            countBox[i].innerHTML = i+1;
            console.log(countBox[i].innerText);
        }

        calculateTotalCount();
        calculateTotalPrice();
        calculatePrice();
        calculatePoint();
        formatNumber();


        //장바구니에서 삭제하기-비동기
        const cartId = btn.getAttribute("data-cartId");

        console.log(cartId);

        fetch("/myPage/cart/delProduct",{
            method:"POST",
            headers : {"Content-Type":"application/json"},
            body : JSON.stringify(cartId)
        })
        .then(resp =>resp.text())
        .then(result =>{
            if(result == 0){
                alert("장바구니에서 상품 삭제 실패\n다시 시도해주세요");
                return;
            }
            console.log("삭제 상품 개수"+result);
            alert("상품이 삭제되었습니다");

        });


        
    });
})

// 주문 금액 계산
function calculatePrice() {
    const priceElement = document.querySelectorAll(".price-input");
    const quantityElement = document.querySelectorAll(".quantity-input");

    for(let i = 0 ; i < priceElement.length; i ++) {

        priceElement[i].innerText = parseInt(priceElement[i].attributes[1].value) * parseInt(quantityElement[i].value) + '원';
    }
}

calculatePrice();



// 총 개수 계산
function calculateTotalCount() {
    const quantity = document.getElementsByClassName("quantity-input");
    const totalCount = document.querySelector("#totalCount")
    let totalCountNo = 0;
    
    for(let i = 0; i < quantity.length; i ++) {
        let quantityValue = parseInt(quantity[i].value);
        totalCountNo += quantityValue;
    }
    
    totalCount.innerText = totalCountNo;
}

calculateTotalCount();


// 총 가격 계산
function calculateTotalPrice() {
    const price = document.getElementsByClassName("price-input");
    const totalPrice = document.querySelector("#totalPrice");
    const quantityElement = document.querySelectorAll(".quantity-input");
    let totalPriceNo = 0;
    
    for(let i = 0; i < price.length; i ++) {
        totalPriceNo += parseInt(price[i].attributes[1].value) * parseInt(quantityElement[i].value);
    }
    
    totalPrice.innerText = totalPriceNo;
}

calculateTotalPrice();

// 예상포인트 계산
function calculatePoint() {
    const totalPrice = document.querySelector("#totalPrice");
    const pointArea = document.querySelector("#pointArea");

    pointArea.innerText = parseInt(totalPrice.innerText) * 0.01 
}

calculatePoint();


//구매버튼 클릭
const checkedItems = document.querySelectorAll('input[name="check"].checked'); 


function formatNumber() {
    function formatNumberWithCommas(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    
    const priceElement = document.getElementsByClassName("price-input");
    const totalPriceElement = document.querySelector("#totalPrice");
    const pointAreaElement = document.querySelector("#pointArea");
    
    for (let i = 0; i < priceElement.length; i++) {
        let productPrice = priceElement[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
        priceElement[i].innerText = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    }
    
    let productPrice2 = totalPriceElement.textContent.trim();
    totalPriceElement.innerText = formatNumberWithCommas(productPrice2);

    
    let productPrice3 = pointAreaElement.textContent.trim();
    pointAreaElement.innerText = formatNumberWithCommas(productPrice3);
}

formatNumber();


//////////////////////////////////
const selectedPurchage = document.querySelector(".selectedPurchage");

selectedPurchage.addEventListener("click", () => {
    const checkedElements = document.querySelectorAll('input[name="check"]:checked');

    for(let i = 0; i < checkedElements.length; i ++) {
        console.log(checkedElements[i].getAttribute('productNo-data'));
        console.log(checkedElements[i].getAttribute('productName-data'));

    }
})
