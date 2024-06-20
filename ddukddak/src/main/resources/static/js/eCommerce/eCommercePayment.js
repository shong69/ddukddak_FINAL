// 가격 스트링 포맷
document.addEventListener('DOMContentLoaded', () => {
    const priceElements = document.querySelectorAll('.price');
    const totalPriceElement = document.getElementById('totalPrice');

    priceElements.forEach(priceElement => {
        priceElement.innerText = formatToKoreanWon(priceElement.innerText);
    });

    totalPriceElement.innerText = formatToKoreanWon(totalPriceElement.innerText);
});

function formatToKoreanWon(numberString) {
    let number = parseInt(numberString.replace(/[^0-9]/g, ''), 10);
    return number.toLocaleString('ko-KR') + '원';
}







const agree = document.getElementById('agree'); // 동의 버튼

const payBtn = document.getElementById('payBtn'); // 결제하기 버튼

// 상품 이름
const productName = document.getElementById('productName').innerText;

// 고유 주문 번호
const merchantUid = document.getElementById('merchantUid').value;

const addr = document.getElementById('memberAddr').value;

// 총 금액 구해오기
const amountText = document.getElementById('totalPrice');

let text = amountText.innerText;
let number = text.replace(/[^0-9]/g, '');
let myAmount = parseInt(number, 10);


console.log(merchantUid); // ORD-202404281808330001

const onClickPay = async () => {
    if (!agree.checked) {
        alert('이용 약관 동의 체크 후 구매해 주세요.');
        return;
    }

    console.log(merchantUid); // 예: ORD-202406191741050820
    console.log(myAmount);
    console.log(nickName);
    console.log(productName);
    console.log(tel);
    console.log(email);
    console.log(addr);

    try {
        // 사전 검증 요청
        const prepareResponse = await fetch('/payment/prepare', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                merchant_uid: merchantUid,
                amount: myAmount
            })
        });

        const prepareData = await prepareResponse.json();

        // 여기에 이제 사전 검증 조건 
        if (prepareData.status !== 'success') {
            alert('결제 사전 검증에 실패하였습니다.');
            return;
        }

        // 사전 검증이 완료되면 결제 요청
        IMP.init('imp82211881');

        IMP.request_pay(
            {
                pg: "kakaopay",
                pay_method: "card",
                merchant_uid: merchantUid,
                name: productName,
                amount: myAmount,
                buyer_email: email,
                buyer_name: nickName,
                buyer_tel: tel,
                buyer_addr: addr,
            },
            function (rsp) {
                if (rsp.success) {
                    // 결제 성공 시 서버로 결제 검증 요청
                    verifyPayment(rsp.imp_uid, rsp.merchant_uid, myAmount);
                } else {
                    alert(`결제에 실패하였습니다. 오류 내용: ${rsp.error_msg}`);
                    // 실패 로직 (예: 결제 실패 페이지로 리디렉션)
                    window.location.href = "/myPage/cart";
                }
            }
        );
    } catch (error) {
        console.error('사전 검증 중 오류 발생:', error);
        alert('사전 검증 중 오류가 발생했습니다.');
    }
};


// 결제 검증 함수
const verifyPayment = async (impUid, merchantUid, myAmount) => {
    try {
        const response = await fetch('/payment/verify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                imp_uid: impUid,
                merchant_uid: merchantUid
            })
        });

        const data = await response.json();

        if (data.status === 'success') {
            alert('결제가 성공적으로 완료되었습니다.');
            window.location.href = '/paymentSuccess'; // 결제 성공 페이지로 리디렉션
        } else {
            alert('결제 검증에 실패하였습니다.');
            window.location.href = '/paymentFailure'; // 결제 실패 페이지로 리디렉션
        }
    } catch (error) {
        console.error('결제 검증 중 오류 발생:', error);
        alert('결제 검증 중 오류가 발생했습니다.');
        window.location.href = '/paymentFailure'; // 결제 실패 페이지로 리디렉션
    }
};
// 버튼 클릭 시
// payBtn.addEventListener('click', onClickPay);


