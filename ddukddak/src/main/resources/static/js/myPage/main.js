

function selectOrderList() {

    fetch("/myPage/selectOrderList")
        .then(resp => resp.text())
        .then(result => {
            //console.log("result : " + result);
            if (result != null) {
                const orderList = JSON.parse(result);

                const container = document.getElementById('skuList');
                container.innerHTML = '';

                // 1. 주문 데이터를 주문 번호별로 그룹화
                const groupedOrders = orderList.reduce((acc, order) => {
                    if (!acc[order.orderNo]) {
                        acc[order.orderNo] = [];
                    }
                    acc[order.orderNo].push(order);
                    return acc;
                }, {}); //주문번호가 key이고 주문번호에 해당하는 주문들의 리스트가 value인 객체

                // 2. 그룹화된 데이터를 기반으로 DOM 요소 생성

                // 
                //Object.keys(groupedOrders).forEach(orderNo => {
                Object.keys(groupedOrders).sort((a, b) => b - a).forEach(orderNo => {

                    const orderGroup = groupedOrders[orderNo];
                    // 주문번호별 컨테이너
                    const orderDiv = document.createElement('div');
                    orderDiv.classList.add('order-group');

                    // 주문 번호 헤더 추가
                    const orderHeader = document.createElement('h3');
                    orderHeader.textContent = `주문 번호: ${orderGroup[0].merchantUid}`;
                    orderDiv.appendChild(orderHeader);

                    //총 가격 계산하기
                    let totalPrice = 0;
                    orderGroup.forEach(order => {
                        totalPrice += order.orderPrice;
                    });

                    // 가격을 1,000원 단위로 쉼표 포매팅
                    const formattedTotalPrice = totalPrice.toLocaleString();

                    const div = document.createElement('div');
                    div.classList.add('order-info');
                    div.innerHTML = `
                        <div class="orderTotalPrice">총 가격 ${formattedTotalPrice}원</div>
                        <div class="date-address">
                            <div>${orderGroup[0].orderDate} | 주문일</div>
                            <div>${orderGroup[0].deliveryAddress} | 배송지</div>
                        </div>
                    `;
                    orderDiv.append(div);

                    // 각 주문 항목을 div 안에 추가
                    orderGroup.forEach(order => {
                        const row = document.createElement('div');

                        // 각 주문 항목의 가격을 포매팅
                        const formattedOrderPrice = order.orderPrice.toLocaleString();

                        row.classList.add('order-item');
                        row.innerHTML = `
                            <div><img src="${order.productImg}" class="productImg"></div>
                            <div><a class="orderItemPath" href="#" onclick="findUrl(${order.productNo}); return false;">${order.productName} [${order.optionValue}]</a></div>
                            <div>${order.orderQuantity}개</div>
                            <div>${formattedOrderPrice} 원</div>
                            <div class="statusArea"><div class="status">${order.orderDelFl === 'Y' ? '주문 취소' : order.orderStatus}</div></div>
                        `;

                        const statusArea = row.querySelector('.statusArea');

                        // '배송완료' 상태인 경우 '구매확정' 버튼 추가
                        if (order.orderStatus === '배송완료') {
                            const confirmPurchaseBtn = document.createElement('button');
                            confirmPurchaseBtn.textContent = '구매확정';
                            confirmPurchaseBtn.classList.add("confirmPurchaseBtn");

                            confirmPurchaseBtn.addEventListener('click', () => {
                                confirmPurchase(order.orderItemNo, order.orderStatus);
                            });

                            statusArea.appendChild(confirmPurchaseBtn);
                        } else if (order.orderStatus === '결제완료') {
                            const orderDelBtn = document.createElement('button');
                            orderDelBtn.textContent = '주문취소';
                            orderDelBtn.classList.add("orderDelBtn");

                            orderDelBtn.addEventListener('click', () => {
                                orderDelete(order.orderItemNo, order.orderStatus);

                            });
                            statusArea.appendChild(orderDelBtn);
                        } else if (order.orderStatus === '구매확정') {
                            const reviewWriteBtn = document.createElement('button');
                            reviewWriteBtn.textContent = '리뷰작성';
                            reviewWriteBtn.classList.add("reviewWriteBtn");
                            //리뷰 작성 여부 확인
                            checkReviewWrite(order.orderItemNo).then(isWritten => {
                                if (!isWritten) { //리뷰 작성 안돼있는 경우
                                    reviewWriteBtn.addEventListener('click', () => {
                                        findUrl(order.productNo);
                                    });
                                    statusArea.appendChild(reviewWriteBtn);
                                }
                            });
                        }

                        orderDiv.appendChild(row);
                    });
                    // 그룹화된 주문을 컨테이너에 추가
                    container.appendChild(orderDiv);
                });
            }
        })
        .catch(error => console.error('Error : ', error));
}

selectOrderList();

// confirmPurchase 구매확정으로 변환 함수
function confirmPurchase(orderItemNo, orderStatus) {
    fetch(`/myPage/confirmPurchase?orderItemNo=${orderItemNo}&orderStatus=${orderStatus}`)
        .then(resp => resp.text())
        .then(result => {
            console.log(result);
            if (result == 1) {
                alert("구매 확정 되었습니다");
                selectOrderList();
            } else {
                alert("<error>구매 확정 실패")
            }
        }).catch(error => console.error('Error : ', error));
}

// orderDelete 주문취소로 변환 함수
function orderDelete(orderItemNo, orderStatus) {
    console.log(orderItemNo, orderStatus);
    fetch(`/myPage/orderDelete?orderItemNo=${orderItemNo}&orderStatus=${orderStatus}`)
        .then(resp => resp.text())
        .then(result => {
            console.log(result);
            if (result == 1) {
                console.log(result);
                alert("주문 취소 되었습니다");
                selectOrderList();
            } else {
                alert("<error>주문 취소 실패")
            }
        }).catch(error => console.error('Error : ', error));
}

// checkReviewWrite() true -> 리뷰 작성함 false -> 리뷰 작성 안됨
function checkReviewWrite(orderItemNo) {
    return fetch(`/myPage/checkReviewWrite?orderItemNo=${orderItemNo}`)
        .then(resp => resp.text())
        .then(result => {
            console.log(orderItemNo, result);
            if(result == 1){
                return true;
            }else{
                return false;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            return false; // 오류가 발생한 경우 false 반환
        });
}

function findUrl(productNo) {
    console.log(productNo);
    fetch("/myPage/selectCategory?productNo="+productNo)
    .then(resp => resp.json())
    .then(result => {
        console.log(result);
        if (result != null) { //리뷰 작성 성공
            const { bigCategory, category } = result;
            console.log(result.BIG_CATEGORY_NO);
            window.location.href = `/eCommerce/list/${result.BIG_CATEGORY_NO}/${result.CATEGORY_NO}/${productNo}/detail`;
        }
    }).catch(error => console.error('Error : ', error));
}
