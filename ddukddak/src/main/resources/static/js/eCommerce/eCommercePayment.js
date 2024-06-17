// 주문번호 규칙 생성 
let orderCounter = sessionStorage.getItem('orderCounter');
if (!orderCounter) {
    orderCounter = 0;
}
orderCounter++;
sessionStorage.setItem('orderCounter', orderCounter);

const getFormattedDate = () => {
    const date = new Date();
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const seconds = ('0' + date.getSeconds()).slice(-2);

    return `${year}${month}${day}${hours}${minutes}${seconds}${String(orderCounter).padStart(4, '0')}`;
};


const payBtn = document.getElementById('payBtn'); // 결제하기 버튼


const onClickPay = async () => {
    
    // 총 금액 구해오기
    const amountText = document.getElementById('amount');

    let text = amountText.innerText;
    let number = text.replace(/[^0-9]/g, '');
    let myAmount = parseInt(number, 10);

    const merchantUid = getFormattedDate();
    console.log(merchantUid); // 예: 202404281808330001
    console.log(myAmount);

    IMP.init('imp82211881');

    IMP.request_pay(
        {
            pg: "kakaopay",
            pay_method: "card", // 생략가
            merchant_uid: merchantUid, // 상점에서 생성한 고유 주문번호
            name: "주문명:결제테스트",
            amount: myAmount,
            buyer_email: "test@portone.io",
            buyer_name: "구매자이름",
            buyer_tel: "010-1234-5678",
            buyer_addr: "서울특별시 강남구 삼성동",
            buyer_postcode: "123-456",
            m_redirect_url: "{모바일에서 결제 완료 후 리디렉션 될 URL}",
        },
        function (rsp) {
          // callback 로직
          /* ...중략... */
        },
    );

    
};

payBtn.addEventListener('click', onClickPay);




