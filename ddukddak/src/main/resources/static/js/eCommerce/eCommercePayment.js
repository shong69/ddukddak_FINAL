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

// 고유 주문 번호
function generateMerchantUid() {
    const date = new Date();
    
    // 날짜 형식 (YYYYMMDD)
    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    
    // 시간 형식 (HHMMSS)
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');
    
    // 밀리초 형식 (SSS)
    const milliseconds = date.getMilliseconds().toString().padStart(3, '0');
    
    // 고유한 주문 번호 생성 (ORD-YYYYMMDDHHMMSSSSS)
    return `ORD-${year}${month}${day}${hours}${minutes}${seconds}${milliseconds}`;
}

// 예시 출력
console.log(generateMerchantUid());




const agree = document.getElementById('agree'); // 동의 버튼

const payBtn = document.getElementById('payBtn'); // 결제하기 버튼

// 상품 이름
const productName = document.getElementById('productName').innerText;

// 고유 주문 번호


const addr = document.getElementById('memberAddr').value;

// 총 금액 구해오기
const amountText = document.getElementById('totalPrice');

let text = amountText.innerText;
let number = text.replace(/[^0-9]/g, '');
const myAmount = parseInt(number, 10);



const onClickPay = async () => {

    if (!agree.checked) {
        alert('이용 약관 동의 체크 후 구매해 주세요.');
        return;
    }

    const merchantUid = generateMerchantUid();

    console.log(merchantUid); // 예: ORD-202406191741050820
    console.log(myAmount);
    console.log(nickName);
    console.log(productName);
    console.log(tel);
    console.log(email);
    console.log(addr);
    console.log(memberNo);

    try {
        // 주문 생성 요청

        const response = await fetch('/payment/createOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                merchant_uid: merchantUid,
                amount: myAmount,
                memberNo : memberNo
            })
        });
    
        const result = await response.text();
        // creatOrder insert 결과
        if(result == 0) {
            alert('주문 생성 실패로 인한 결제 요청 미진행')

            return;
        }
    
    
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
            alert(`${prepareData.message} : 결제 사전 검증에 실패하였습니다.`);
            
            return;
        }

        // 사전 검증 성공 시, prepareData에서 필요한 데이터 추출
        const serverMerchantUid = prepareData.merchant_uid;
        const serverAmount = parseInt(prepareData.amount, 10);

        // 사전 검증된 정보와 클라이언트 정보 비교
        if (serverMerchantUid !== merchantUid || serverAmount !== myAmount) {
            alert('사전 검증된 정보와 클라이언트 정보가 일치하지 않습니다.');
            return;
        }

        // 사전 검증이 완료되면 결제 요청
        IMP.init('imp82211881');

        IMP.request_pay(
            {
                pg: "kakaopay",
                pay_method: "card",
                merchant_uid: serverMerchantUid,
                name: productName,
                amount: serverAmount,
                buyer_email: email,
                buyer_name: nickName,
                buyer_tel: tel,
                buyer_addr: addr,
            },
            async function (rsp) {
                if (rsp.success) {
                    // 결제 성공 시 서버로 결제 검증 요청
                    const verifyResponse = await fetch('/payment/verify', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            imp_uid: rsp.imp_uid,
                            merchant_uid: rsp.merchant_uid,
                            amount: serverAmount
                        })
                    });

                    const verifyData = await verifyResponse.json();


                } else {
                    alert(`결제에 실패하였습니다. 오류 내용: ${rsp.error_msg}`);
                    // 실패 로직 (예: 결제 실패 페이지로 리디렉션)
                    window.location.href = "/myPage/cart";
                }
            }
        );
    } catch (error) {
        console.error('Error :', error);
    }
};





