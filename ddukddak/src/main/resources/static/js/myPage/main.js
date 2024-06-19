document.addEventListener("DOMContentLoaded",()=>{
    selectOrderList();
});


function selectOrderList(){
    fetch("/myPage")
    .then(resp => resp.json())
    .then(result => {
        if(result != null){
            const orderList = result;

                //같은 주문(orderNo)에 해당할 경우 div로 묶어주기
                //주문취소인 경우 주문상태 '주문취소'
                //결제완료 상태인 경우 주문취소 버튼
                //배송완료 상태인 경우 구매확정 버튼
                //구매확정 상태인 경우 리뷰작성 버튼


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
            Object.keys(orderList).forEach(orderNo => {
                const orderGroup = orderList[orderNo];
                // 주문번호별 컨테이너
                const orderDiv = document.createElement('div');
                orderDiv.classList.add('order-group');
                
                // 주문 번호 헤더 추가
                const orderHeader = document.createElement('h3');
                orderHeader.textContent = `주문 번호: ${orderNo}`;
                orderDiv.appendChild(orderHeader);
                //주문 정보(총가격/배송지/주문일)

                //총 가격 계산하기
                let totalPrice=0;
                orderGroup.forEach(order=>{
                    totalPrice += order.orderPrice;
                })
                console.log(totalPrice);

                const div = document.createElement('div');
                div.classList.add('order-info');
                div.innerHTML=`
                    <div class="orderTotalPrice">총 가격${totalPrice}원</div>
                    <div class="date-address">
                        <div>주문일 ${orderList[orderNo][0].orderDate}</div>
                        <div>배송지 ${orderList[orderNo][0].deliveryAddress}</div>
                    </div>
                `;
                orderDiv.append(div);
                // 각 주문 항목을 div 안에 추가
                orderGroup.forEach(order => {
                    const row = document.createElement('div');

                    row.classList.add('order-item');
                    row.innerHTML = `
                        <div><img src="${order.productImg}" class="product-image"></div>
                        
                        <div>${order.product} [${order.optionValue}]</div>
                        <div>${order.orderQuantity}</div>
                        <div>${order.orderPrice} 원</div>
                        <div class="statusArea">${order.orderDelFl == 'Y' ? '주문 취소' : order.orderStatus}</div>
                    `;
                    
                    
                    // '배송완료' 상태인 경우 '구매확정' 버튼 추가
                    if (order.orderStatus === '배송완료') {
                        const confirmPurchaseBtn = document.createElement('button');
                        confirmPurchaseBtn.textContent = '구매확정';
                        confirmPurchaseBtn.addEventListener('click', () => {
                            // 주문취소 버튼 클릭 시 주문 상태 변경 로직 추가
                            confirmPurchaseBtn(order.orderItemNo);
                            
                        });
                        
                        statusArea.appendChild(confirmPurchaseBtn);
                    //결제완료 -> 주문취소
                    }else if(order.orderStatus ==='결제완료'){
                        const orderDelBtn = document.createElement('button');
                        orderDelBtn.textContent = '주문취소';
                        orderDelBtn.addEventListener('click', () => {
                            //  버튼 클릭 시 주문 상태 변경 로직 추가
                            orderDelete(order.orderItemNo);
                        });
                        statusArea.appendChild(orderDelBtn);
                    //구매확정 -> 리뷰작성
                    }else if(order.orderStatus === '구매확정'){
                        const reviewWriteBtn = document.createElement('button');
                        reviewWriteBtn.textContent = '리뷰작성';
                        //리뷰 작성 여부 확인
                        if(!checkReviewWrite(orderNo, order.orderItemNo)){ //리뷰 작성 안돼있는 경우
                            reviewWriteBtn.addEventListener('click', () => {
                                // 구매확정 버튼 클릭 시 주문 상태 변경 로직 추가
                                fetch("/myPage/selectCategoty?productNo="+order.productNo)
                                .then(resp => resp.json())
                                .then(result =>{
                                    if(result != null){//리뷰 작성 성공
                                        const{bigCategory, category} = result;
                                        window.location.href=`/eCommerce/list/${bigCategory}/${category}/${order.productNo}/detail`;
                                    }
                                }).catch(error=>console.error('Error : ',error));
                            });
                            statusArea.appendChild(reviewWriteBtn);
                        }else{ // 해당 주문 상품에 대해 리뷰를 이미 작성한 경우
                            reviewWriteBtn.disabled = true; //비활성화 처리
                            statusArea.appendChild(reviewWriteBtn);
                        }

                    }

                    orderDiv.appendChild(row);
                });
                // 그룹화된 주문을 컨테이너에 추가
                container.appendChild(orderDiv);
            }
    )}

    })
}


//confirmPurchaseBtn 구매확정으로 변환
function confirmPurchaseBtn(orderItemNo) {
    fetch("/myPage/confirmPurchase?orderItemNo="+orderItemNo)
    .then(resp => resp.text())
    .then(result => {
        if(result != null){
            alert("구매 확정 되었습니다");
        }else{
            alert("<error>구매 확정 실패")
        }
    })
}

//orderDelete 주문취소로 변환
function orderDelete(orderItemNo) {
    fetch("/myPage/orderDelete?orderItemNo=" + orderItemNo)
        .then(resp => resp.text())
        .then(result => {
            if (result != null) {
                alert("주문 취소 되었습니다");
            }else{
                alert("<error>주문 취소 실패")
            }
        });
}
//checkReviewWrite() true -> 리뷰 작성함 false -> 리뷰 작성 N
function checkReviewWrite(orderNo, orderItemNo) {
    fetch("/myPage/checkReviewWrite?orderNo=${orderNo}&orderItemNo=${orderItemNo}")
    .then(resp => resp.text())
    .then(result =>{
        if(result == '1'){
            return true; //작성 됨
        }else{
            return false; //작성 안됨
        }
    })
    return false; // 리뷰 작성 안 되어 있다고 가정
}
