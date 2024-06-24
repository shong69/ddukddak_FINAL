const detailUnder = document.querySelector("#detailUnder");
const detailInfo = document.querySelector("#detailInfo");
const review = document.querySelector("#review");
const qna = document.querySelector("#qna");
const changeInfo = document.querySelector("#changeInfo");

const explain = document.querySelector("#explain");
const change = document.querySelector("#change");
const reviewBox = document.querySelector("#reviewBox");
const qnaBox = document.querySelector("#qnaBox");

detailInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--primary3)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    explain.style.display = 'flex';
    change.style.display = 'none';
    reviewBox.style.display ='none';
    qnaBox.style.display = 'none';
});

review.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--primary3)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--white)';

    reviewBox.style.display ='flex';
    explain.style.display = 'none';
    change.style.display = 'none';
    qnaBox.style.display = 'none';
    openReviewCheck(productNo);
    
});

qna.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--primary3)';
    changeInfo.style.backgroundColor = 'var(--white)';

    qnaBox.style.display = 'flex';
    explain.style.display = 'none';
    change.style.display = 'none';
    reviewBox.style.display ='none';
});

changeInfo.addEventListener("click", () => {
    detailInfo.style.backgroundColor = 'var(--white)';
    review.style.backgroundColor = 'var(--white)';
    qna.style.backgroundColor = 'var(--white)';
    changeInfo.style.backgroundColor = 'var(--primary3)';

    explain.style.display = 'none';
    change.style.display = 'flex';
    reviewBox.style.display ='none';
    qnaBox.style.display = 'none';
});

// mainImg 요소를 선택합니다
var mainImg = document.querySelector('.mainImg');

// 모든 subImg 요소를 선택합니다
var subImgs = document.querySelectorAll('.subImg');

// subImgs의 첫 번째 요소에 border 클래스를 추가합니다
subImgs[0].classList.add('border');

// 각 subImg에 클릭 이벤트 리스너를 추가합니다
subImgs.forEach(function(subImg) {
    subImg.addEventListener('click', function() {
        // 클릭된 이미지를 메인 이미지로 설정합니다
        mainImg.src = this.src;
        // 클릭된 이미지에 border 클래스를 추가합니다
        subImgs.forEach(function(img) {
            img.classList.remove('border');
        });
        this.classList.add('border');

        // 클릭된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
        this.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
    });
});

// 화살표 요소를 선택합니다
var leftArrow = document.querySelector('.left-arrow');
var rightArrow = document.querySelector('.right-arrow');

// 왼쪽 화살표에 클릭 이벤트 리스너를 추가합니다
leftArrow.addEventListener('click', function() {
    // 현재 메인 이미지의 src를 가져옵니다
    var currentSrc = mainImg.src;
    // 현재 메인 이미지의 src가 subImgs 중 하나와 일치하는지 확인합니다
    var currentIndex = Array.from(subImgs).findIndex(function(img) {
        return img.src === currentSrc;
    });
    // 왼쪽으로 이동할 이미지의 인덱스를 계산합니다
    var nextIndex = (currentIndex - 1 + subImgs.length) % subImgs.length;
    // 메인 이미지를 이동합니다
    mainImg.src = subImgs[nextIndex].src;
    // 이동된 이미지에 border 클래스를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');

    // 이동된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
    subImgs[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
});

// 오른쪽 화살표에 클릭 이벤트 리스너를 추가합니다
rightArrow.addEventListener('click', function() {
    // 현재 메인 이미지의 src를 가져옵니다
    var currentSrc = mainImg.src;
    // 현재 메인 이미지의 src가 subImgs 중 하나와 일치하는지 확인합니다
    var currentIndex = Array.from(subImgs).findIndex(function(img) {
        return img.src === currentSrc;
        
    });
    // 다음으로 이동할 이미지의 인덱스를 계산합니다
    var nextIndex = (currentIndex + 1) % subImgs.length; // 바로 다음 이미지로 이동
    // 메인 이미지를 이동합니다
    mainImg.src = subImgs[nextIndex].src;
    // 이동된 이미지에 border 클래스를 추가합니다
    subImgs.forEach(function(img) {
        img.classList.remove('border');
    });
    subImgs[nextIndex].classList.add('border');

    // 이동된 이미지가 스크롤 컨테이너 안에 있는지 확인하고, 없다면 스크롤 컨테이너로 스크롤합니다
    subImgs[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
});


function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("mainProductPrice");
const ProductPrice = document.querySelectorAll(".productPrice");
const point = document.querySelector("#point");


for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim(); // 요소의 텍스트 내용 가져오기
    var priceNum = +productPrice

    productPriceElements[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    productPriceElements[i].innerHTML += '원';

    point.innerHTML = `최대 +${Math.floor(priceNum*0.01)}pt`;
}

ProductPrice.forEach(elements => {
    let productPrice = elements.textContent.trim(); // 요소의 텍스트 내용 가져오기

    elements.textContent = formatNumberWithCommas(productPrice);
    elements.innerHTML += '원';
})


// 옵션 선택시
// 모든 select 태그를 선택
const optionProductBox = document.querySelector("#optionProductBox");
const mainProductPrice = document.querySelector("#mainProductPrice");
const total = document.querySelector("#total");
const totalCount = document.querySelector("#totalCount");
var selectTags = document.querySelectorAll('.selectOption');

// 모든 select 태그의 옵션값이 0이 아닌지 확인하는 함수
function checkAllSelectValues() {
    var allValuesAreNotZero = true;
    selectTags.forEach(function(select) {
        if (select.value === '0') {
            allValuesAreNotZero = false;
        }
    });
    return allValuesAreNotZero;
}

// 총 가격을 업데이트하는 함수
function updateTotal() {
    let totalPrice = 0;
    const optionPrices = document.querySelectorAll('.optionPrice');
    optionPrices.forEach(function(optionPrice) {
        totalPrice += parseInt(optionPrice.innerText.replace(/[^0-9]/g, ''));
    });
    total.innerText = `총 ${formatNumberWithCommas(totalPrice)}원`;
}

// 총 수량을 업데이트하는 함수
function updateTotalCount() {
    let totalCountNumber = 0;
    const counts = document.querySelectorAll('.count');
    counts.forEach(function(countElement) {
        totalCountNumber += parseInt(countElement.innerText);
    });
    totalCount.innerText = `총 수량: ${totalCountNumber}개`;
}

// 선택된 옵션 값이 이미 존재하는지 확인하는 함수
function checkDuplicateOption() {
    const selectedOptions = Array.from(selectTags).map(select => select.options[select.selectedIndex].text).join(',');
    const optionElements = document.querySelectorAll('.options');
    let isDuplicate = false;

    optionElements.forEach(optionElement => {
        if (optionElement.innerText === selectedOptions) {
            isDuplicate = true;
        }
    });

    return isDuplicate;
}

// 모든 select 태그의 옵션값이 0이 아닌 경우에만 alert 창 띄우기
function showAlertIfAllValuesAreNotZero() {
    if (checkAllSelectValues()) {
        if (checkDuplicateOption()) {
            alert('이미 같은 옵션이 선택되었습니다.');
            selectTags.forEach(elements => {
                elements.value = 0;
            })
            return;
        }

        const div1 = document.createElement("div");
        div1.classList.add("selectOptionProduct");

        const close = document.createElement("h5");
        close.innerText = "X";
        close.classList.add("closeButton");

        
        const options = document.createElement("h4");
        options.classList.add("options");
        selectTags.forEach(elements => {
            const optionValue = document.createElement("input");
            optionValue.classList.add("optionValue");
            optionValue.type = 'hidden';

            div1.append(optionValue);

            optionValue.value = elements.value;
            options.innerText += elements.options[elements.selectedIndex].text + ',';
        })
        options.innerText = options.innerText.slice(0, -1);

        const div2 = document.createElement("div");
        div2.classList.add("underOption");

        const div3 = document.createElement("div");
        div3.classList.add("numberBox");
        const minusButton = document.createElement("button");
        minusButton.innerText = "-";
        minusButton.type = 'button';
        const numberBox = document.createElement("h4");
        numberBox.classList.add("count");
        numberBox.innerText = "1";
        const plusButton = document.createElement("button");
        plusButton.innerText = "+";
        plusButton.type = 'button';
        let count = 1;

        const totalPriceNo = mainProductPrice.attributes[3].value;
        const priceNum = parseInt(totalPriceNo);

        let inputPrice = count * priceNum;
        const optionPrice = document.createElement("h4");
        optionPrice.classList.add("optionPrice");

        div3.append(minusButton);
        div3.append(numberBox);
        div3.append(plusButton);

        div2.append(div3);
        div2.append(optionPrice);
        
        div1.append(close);
        div1.append(options);
        div1.append(div2);

        optionProductBox.append(div1);

        selectTags.forEach(elements => {
            elements.value = 0;
        })

        close.addEventListener("click", () => {
            div1.remove();
            updateTotal();
            updateTotalCount();
        })

        plusButton.addEventListener("click", () => {
            count += 1;
            inputPrice = count * priceNum;
            optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
            numberBox.innerText = count;
            updateTotal();
            updateTotalCount();
        })

        minusButton.addEventListener("click", () => {
            if(count == 1) {
                alert("상품을 1개 이상 선택해야 합니다.");
            } else {
                count -= 1;
                inputPrice = count * priceNum;
                optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
                numberBox.innerText = count;
                updateTotal();
                updateTotalCount();
            }
        })

        optionPrice.innerText = `${formatNumberWithCommas(inputPrice)}원`;
        updateTotal();
        updateTotalCount();
    }
}

// 모든 select 태그에 change 이벤트 리스너 추가
selectTags.forEach(function(select) {
    select.addEventListener('change', showAlertIfAllValuesAreNotZero);
});




// QNA
const qnaInserButton = document.querySelector("#qnaInserButton");
const write = document.querySelector("#write");
const applyQnaButton = document.querySelector("#applyQnaButton");
const myQna = document.querySelector("#myQna");
const qnaContainer = document.querySelector("#qnaContainer");
const tBody = document.querySelector("#tBody");
const allBox = document.querySelector("#allBox");
const allQna = document.querySelector("#allQna");


function maskString(str) {
    // 문자열 길이를 구함
    const length = str.length;

    // 8글자 이상일 경우 처리
    if (length >= 8) {
        return str.slice(0, 4) + '*'.repeat(length - 4);
    }

    // 8글자보다 짧을 경우 처리
    const halfLength = Math.ceil(length / 2);
    return str.slice(0, length - halfLength) + '*'.repeat(halfLength);
}



document.addEventListener('DOMContentLoaded', () => {
    const memberNo = document.querySelector("#memberNo");

    // 모든 QNA 불러오기
    fetch("/eCommerce/selectQna")
    .then(resp => resp.json())
    .then(result => {
        console.log(result);

        if (result.length == 0) {
            const h2 = document.createElement("h2");
            h2.innerText = "작성한 QNA가 존재하지 않습니다";
            h2.style.color = 'var(--gray5)';
            h2.style.fontWeight = '500';
            h2.style.textAlign = 'center';
            h2.style.marginTop = '20px';

            qnaContainer.append(h2);
        } else {
            result.forEach((qna) => {
                qna.memberId = maskString(qna.memberId);
                if(qna.partnerId != null) {
                    qna.partnerId = maskString(qna.partnerId);
                }

                if (qna.qnaAnswerStatus == 'N') {
                    qna.qnaAnswerStatus = '미답변';
                } else {
                    qna.qnaAnswerStatus = '답변완료';
                }

                let arr = [
                    qna.qnaAnswerStatus,
                    qna.qnaTitle,
                    qna.memberId,
                    qna.qnaWriteDate
                ];

                const tr = document.createElement("tr");
                tr.style.cursor = 'pointer';

                const contentTr = document.createElement("tr");
                contentTr.style.display = 'none';
                
                const answerTr = document.createElement("tr");
                answerTr.style.display = 'none';

                for (let key of arr) {
                    const td = document.createElement("td");
                    td.innerText = key;
                    tr.append(td);

                    const contentTd = document.createElement("td");
                    const answerTd = document.createElement("td");
                    if (key == qna.qnaTitle) {
                        contentTd.innerText = qna.qnaContent;
                        answerTd.innerText = "[답변] " + qna.qnaAnswer;
                    }

                    if (key == qna.memberId) {
                        answerTd.innerText = qna.partnerId;
                    }

                    if (key == qna.qnaWriteDate) {
                        answerTd.innerText = qna.qnaAnswerDate;
                    }

                    contentTr.append(contentTd);
                    answerTr.append(answerTd);
                }
                tBody.append(tr);
                tBody.append(contentTr);

                if(qna.qnaAnswer != null) {
                    tBody.append(answerTr);
                }

                tr.addEventListener("click", () => {
                    const display = (contentTr.style.display === 'none') ? 'table-row' : 'none';
                    if (display === 'table-row') {
                        contentTr.style.display = display;
                        answerTr.style.display = display;
                        contentTr.style.backgroundColor = 'rgb(248, 248, 248)';
                        answerTr.style.backgroundColor = 'rgb(248, 248, 248)';
                        tr.style.backgroundColor = 'rgb(248, 248, 248)';
                    } else {
                        contentTr.style.display = display;
                        answerTr.style.display = display;
                        tr.style.backgroundColor = 'white'; // tr의 배경색 원래대로 설정
                    }
                });

            });
        }
    });

    
    // QNA 입력
    qnaInserButton.addEventListener("click", () => {

        if(memberNo.value == 0) {
            alert("로그인 후 이용해주세요");
            redirectToLogin();
        } else {
            if( write.style.display == 'flex') {
                 write.style.display = 'none'
            } else {
                write.style.display = 'flex';
            }
        }
    })
    
    applyQnaButton.addEventListener("click", () => {
        const writeTitle = document.querySelector("#writeTitle");
        const writeQna = document.querySelector("#writeQna");
    
        if(writeTitle.value.trim().length === 0) {
            alert("QNA 제목을 작성해주세요");
        } else if(writeQna.value.trim().length === 0) {
            alert("QNA 내용을 작성해주세요");
        } else {
            const obj = {
                "qnaTitle" : writeTitle.value,
                "qnaContent" : writeQna.value
            }
    
            console.log(obj);
    
            fetch("/eCommerce/insertQna", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(obj)
            })
            .then(resp => resp.text())
            .then(result => {
                if(result > 0) {
                    alert("QNA 등록이 완료되었습니다");
                    writeTitle.value = "";
                    writeQna.value = "";
                    write.style.display = 'none';
                } else {
                    alert("QNA 등록 실패");
                }
            });
        }
    });

   // '내가 쓴 QNA' 버튼 클릭 이벤트
    myQna.addEventListener("click", () => {
        if (memberNo.value == 0) {
            alert("로그인 후 이용해주세요");
            redirectToLogin();
        } else {
            allBox.style.display = 'block';
            tBody.innerHTML = "";
            write.style.display = 'none';
            write.style.display = 'none';

            fetch("/eCommerce/myQna")
                .then(resp => resp.json())
                .then(result => {
                    console.log(result);

                    if (result.length == 0) {
                        const h2 = document.createElement("h2");
                        h2.innerText = "작성한 QNA가 존재하지 않습니다";
                        h2.style.color = 'var(--gray5)';
                        h2.style.fontWeight = '500';
                        h2.style.textAlign = 'center';
                        h2.style.marginTop = '20px';

                        qnaContainer.append(h2);
                    } else {
                        
                        result.forEach((qna) => {
                            qna.memberId = maskString(qna.memberId);
                            if(qna.partnerId != null) {
                                qna.partnerId = maskString(qna.partnerId);
                            }

                            if (qna.qnaAnswerStatus == 'N') {
                                qna.qnaAnswerStatus = '미답변';
                            } else {
                                qna.qnaAnswerStatus = '답변완료';
                            }

                            let arr = [
                                qna.qnaAnswerStatus,
                                qna.qnaTitle,
                                qna.memberId,
                                qna.qnaWriteDate
                            ];

                            const tr = document.createElement("tr");
                            tr.style.cursor = 'pointer';

                            const contentTr = document.createElement("tr");
                            contentTr.style.display = 'none';
                            
                            const answerTr = document.createElement("tr");
                            answerTr.style.display = 'none';

                            for (let key of arr) {
                                const td = document.createElement("td");
                                td.innerText = key;
                                tr.append(td);
            
                                const contentTd = document.createElement("td");
                                const answerTd = document.createElement("td");
                                if (key == qna.qnaTitle) {
                                    contentTd.innerText = qna.qnaContent;
                                    answerTd.innerText = "[답변] " + qna.qnaAnswer;
                                }

                                if (key == qna.memberId) {
                                    answerTd.innerText = qna.partnerId;
                                }

                                if (key == qna.qnaWriteDate) {
                                    answerTd.innerText = qna.qnaAnswerDate;
                                }

                                contentTr.append(contentTd);
                                answerTr.append(answerTd);
                            }
                            tBody.append(tr);
                            tBody.append(contentTr);
            
                            if(qna.qnaAnswer != null) {
                                tBody.append(answerTr);
                            }

                            tr.addEventListener("click", () => {
                                const display = (contentTr.style.display === 'none') ? 'table-row' : 'none';
                                if (display === 'table-row') {
                                    contentTr.style.display = display;
                                    answerTr.style.display = display;
                                    contentTr.style.backgroundColor = 'rgb(248, 248, 248)';
                                    answerTr.style.backgroundColor = 'rgb(248, 248, 248)';
                                    tr.style.backgroundColor = 'rgb(248, 248, 248)';
                                } else {
                                    contentTr.style.display = display;
                                    answerTr.style.display = display;
                                    tr.style.backgroundColor = 'white'; // tr의 배경색 원래대로 설정
                                }
                            });
                            
                            
                        });
                    }
                });
        }
    });

    // '전체 QNA' 버튼 클릭 이벤트
    allQna.addEventListener("click", () => {
        tBody.innerHTML = "";
        write.style.display = 'none';
        allBox.style.display = 'none';
        
        fetch("/eCommerce/selectQna")
            .then(resp => resp.json())
            .then(result => {
                console.log(result);

                if (result.length == 0) {
                    const h2 = document.createElement("h2");
                    h2.innerText = "작성한 QNA가 존재하지 않습니다";
                    h2.style.color = 'var(--gray5)';
                    h2.style.fontWeight = '500';
                    h2.style.textAlign = 'center';
                    h2.style.marginTop = '20px';

                    qnaContainer.append(h2);
                } else {
                    result.forEach((qna) => {
                        qna.memberId = maskString(qna.memberId);
                        if(qna.partnerId != null) {
                            qna.partnerId = maskString(qna.partnerId);
                        }

                        if (qna.qnaAnswerStatus == 'N') {
                            qna.qnaAnswerStatus = '미답변';
                        } else {
                            qna.qnaAnswerStatus = '답변완료';
                        }

                        let arr = [
                            qna.qnaAnswerStatus,
                            qna.qnaTitle,
                            qna.memberId,
                            qna.qnaWriteDate
                        ];

                        const tr = document.createElement("tr");
                        tr.style.cursor = 'pointer';
                        const contentTr = document.createElement("tr");
                        contentTr.style.display = 'none';

                        for (let key of arr) {
                            const td = document.createElement("td");
                            td.innerText = key;
                            tr.append(td);

                            const contentTd = document.createElement("td");
                            if (key == qna.qnaTitle) {
                                contentTd.innerText = qna.qnaContent;
                                contentTd.colSpan = arr.length; // 제목이 포함된 행에 대해 전체 열에 걸치도록 설정
                            }
                            contentTr.append(contentTd);
                        }
                        tBody.append(tr);
                        tBody.append(contentTr);

                        tr.addEventListener("click", () => {
                            const display = (contentTr.style.display == 'none') ? 'table-row' : 'none';
                            contentTr.style.display = display;
                        });
                    });
                }
            });
    });

})
