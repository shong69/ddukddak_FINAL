// 금액 먼저 구해야 함.
const totalPriceValue = document.getElementById('totalPriceValue').value;

const agree = document.getElementById('agree'); // 동의 버튼

const payBtn = document.getElementById('payBtn'); // 결제하기 버튼

// 상품 이름
const productName = document.getElementById('productName').innerText;

// 주소
const addr = document.getElementById('memberAddr').value;

function formatAddress(addr) {
    const match = addr.match(/^(\d{5})\s(.+)$/);

    if (match) {
        const postcode = match[1]; // 우편번호 (5자리 숫자)
        let address = match[2];    // 나머지 주소

        // 주소에서 첫 번째 공백만 제거합니다.
        address = address.replace(/^ /, '');

        return {
            postcode: postcode,
            address: address
        };
    } else {
        throw new Error('Invalid address format');
    }
}


// 총 금액 구해오기
//const amountText = document.getElementById('totalPrice');

console.log(totalPriceValue);

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


// Prepare payment failed. : 결제 사전 검증에 실패하였습니다.


const onClickPay = async () => {

    const totalPriceValue = document.getElementById('totalPriceValue').value;

    const merchantUid = generateMerchantUid();
    const formattedAddress = formatAddress(addr);

    const totalPriceValueNumber = Number(totalPriceValue);

    console.log('postcode : ' + formattedAddress.postcode );
    console.log('address : ' + formattedAddress.address );


    document.getElementById('merchantUid').value = merchantUid;
    // let text = amountText.innerText;
    // let number = text.replace(/[^0-9]/g, '');
    // const myAmount = parseInt(number, 10);

    
    if (!agree.checked) {
        alert('이용 약관 동의 체크 후 구매해 주세요.');
        return;
    }
    
    try {
        // 주문 생성 요청

        const response = await fetch('/payment/createOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                merchant_uid: merchantUid,
                amount: totalPriceValueNumber,
                memberNo : memberNo
            })
        });
    
        const result = await response.text();
        // creatOrder insert 결과
        if(result === 0) {

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
                amount: totalPriceValueNumber
            })
        });

        const prepareData = await prepareResponse.json();

        console.log("prepareData.message 테스트 : " + prepareData.message);
        // 여기에 이제 사전 검증 조건 
        if (prepareData.status !== 'success') {
            alert(`${prepareData.message} : 결제 사전 검증에 실패하였습니다.`);
    
            return;
        }

        // 사전 검증 성공 시, prepareData에서 필요한 데이터 추출
        const serverMerchantUid = prepareData.merchant_uid;
        const serverAmount = parseInt(prepareData.amount, 10);

        // 사전 검증된 정보와 클라이언트 정보 비교
        if (serverMerchantUid !== merchantUid || serverAmount !== totalPriceValueNumber) {
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
                buyer_postcode: formattedAddress.postcode,
                buyer_addr: formattedAddress.address
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

                    if (verifyData.status === 'success') {
                        alert('결제가 성공적으로 완료되었습니다.');
                        // 추가 로직 (예: 결제 성공 페이지로 리디렉션)
                        document.getElementById('paymentForm').submit();

                    } else {
                        alert('결제 검증에 실패하였습니다.');
                        // 추가 로직 (예: 결제 실패 페이지로 리디렉션)
                        window.location.href = "/myPage/cart";
                    }

                } else {
                    alert(`결제에 실패하였습니다. 오류 내용: ${rsp.error_msg}`);
                    // 실패 로직 (예: 결제 실패 페이지로 리디렉션)
                    // 결제 취소 상태 업데이트를 위한 서버 호출
                    console.log(rsp.error_msg);

                    await fetch('/payment/cancel', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            merchant_uid: serverMerchantUid,
                            message : rsp.error_msg
                        })
                    })
                    .then(resp => resp.text())
                    .then(result => {

                        if(result > 0) {
                            window.location.href = "/myPage/cart";
                        } else {
                            console.log('사용자 취소 업데이트 실패');
                        }
                        
                    })
                }
            }
        );
    } catch (error) {
        console.error('Error :', error);
    }
};





