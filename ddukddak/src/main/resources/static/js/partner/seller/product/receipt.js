const modal = document.querySelector(".modal");
const orderReceipt = document.querySelector("#orderReceipt");
const orderRejection = document.querySelector("#orderRejection");
const productListBox = document.querySelector("#productListBox");
const closeButton = document.querySelector("#closeButton");
const title = document.querySelector(".div-container");
const productUpdateDate = document.querySelectorAll(".productUpdateDate");
const orderQuantity = document.querySelectorAll(".orderQuantity");

const orderOK = document.querySelector("#orderOK");
const orderNO = document.querySelector("#orderNO");
let currentType = '';

const orderOKHandler = () => {
    alert(currentType === 'receipt' ? "주문이 접수되었습니다" : "주문이 거절되었습니다");
    modal.style.display = 'none';
};

const orderNOHandler = () => {
    if (confirm("취소하시겠습니까?")) {
        modal.style.display = 'none';
    }
};



function openModal(type) {
    productListBox.innerHTML = "";
    modal.style.display = "flex";

    const query = 'input[name="selectProduct"]:checked'
    const selectedEls = document.querySelectorAll(query);

    let totalOrderQuantity = 0;

    // console.log(selectedEls);

    const productUpdateDateMap = {};
    productUpdateDate.forEach((element, index) => {
        const productNo = element.closest('tr').querySelector('.productNo').value;
        productUpdateDateMap[productNo] = {
            updateDate: element.value,
            orderQuantity: orderQuantity[index].value
        };
    });

    selectedEls.forEach(elements => {
        const productNoValue = elements.closest('tr').querySelector('.productNo').value;
        const orderQuantityValue = elements.closest('tr').querySelector('.orderQuantity').value;
        totalOrderQuantity += parseInt(orderQuantityValue, 10);

        const div = document.createElement("div");
        div.classList.add("elementBox");

        const productNo = document.createElement("h5");
        productNo.innerText = productNoValue;

        const productName = document.createElement("h5");
        productName.innerText = elements.closest('tr').querySelector('td:nth-child(7)').innerText;

        const productOption = document.createElement("h5");
        productOption.innerText = elements.closest('tr').querySelector('td:nth-child(4)').innerText;

        const productStatus = document.createElement("h5");
        productStatus.innerText = elements.closest('tr').querySelector('td:nth-child(8)').innerText;

        const productDate = document.createElement("h5");
        if (productUpdateDateMap[productNoValue]) {
            productDate.innerText = productUpdateDateMap[productNoValue].updateDate;
        } else {
            productDate.innerText = "N/A";
        }

        div.append(productNo);
        div.append(productName);
        div.append(productOption);
        div.append(productStatus);
        div.append(productDate);

        productListBox.append(div);
    });

    const divContainer = document.querySelector(".div-container");
    divContainer.innerHTML = ''; 
    const h3 = document.createElement("h3");
    h3.classList.add("div-titleAdd");
    h3.innerText = `선택한 ${totalOrderQuantity}개의 상품 주문을 ${type === 'receipt' ? '접수' : '거절'}하시겠습니까?`;
    divContainer.appendChild(h3);


    const obj = [];
    selectedEls.forEach(elements => {
        const productNoValue = elements.closest('tr').querySelector('.productNo').value;
        const orderNoValue = elements.closest('tr').querySelector('.orderNo').value;
        obj.push({
            productNo: productNoValue,
            orderNo: orderNoValue,
            loginPartnerMember : loginPartnerMember
        });
        console.log(obj);
    });

    const map = {
        "obj": obj,
        "status": type === 'receipt' ? '접수' : '거절'
    };

    orderOK.addEventListener("click", () => {
        fetch("/partner/seller/product/acceptReceipt", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(map)
        })
        .then(resp => resp.text())
        .then(result => {
            if (result > 0) {
                alert(type === 'receipt' ? "접수되었습니다" : "거절되었습니다");
                window.location.reload();
            } else {
                alert(type === 'receipt' ? "접수 실패" : "거절 실패");
            }
        });
    });
    orderNO.addEventListener("click", orderNOHandler);
}

orderReceipt.addEventListener("click", () => openModal('receipt'));
orderRejection.addEventListener("click", () => openModal('rejection'));


/* 주문 거절 */
orderRejection.addEventListener("click", () => {
    productListBox.innerHTML = "";
    modal.style.display = "flex";

    const query = 'input[name="selectProduct"]:checked'
    const selectedEls = document.querySelectorAll(query);

    // console.log(selectedEls);

    selectedEls.forEach(elements => {
        const div = document.createElement("div");
        div.classList.add("elementBox");

        const productNo = document.createElement("h5");
        productNo.innerText = elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText;

        const productName = document.createElement("h5");
        productName.innerText = elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText;

        const productOption = document.createElement("h5");
        productOption.innerText = elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.innerText;


        const productStatus = document.createElement("h5");
        const status = elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText;
        productStatus.innerText = status;

        const productDate = document.createElement("h5");
        productDate.innerText = "20240603";

        const rejectionDiv = document.createElement("textarea");
        rejectionDiv.classList.add("rejectionDiv");
        rejectionDiv.placeholder = "거절사유를 입력해 주세요";


        div.append(productNo);
        div.append(productName);
        div.append(productOption);
        div.append(productStatus);
        div.append(productDate);

        productListBox.append(div);
        productListBox.append(rejectionDiv);
    })
});

closeButton.addEventListener("click", () => {
    if(confirm("취소하시겠습니까?")) {
        modal.style.display = "none";
        return;
    }
});




/* 전체선택버튼 */
const selectAll = document.querySelector('#selectAllCheckBox');
const checkboxes = document.getElementsByName('selectProduct');

selectAll.addEventListener('change', () => {
    if(selectAll.checked == true) {
        checkboxes.forEach(elements => {
          elements.checked = true;
        })
    } else {
        checkboxes.forEach(elements => {
            elements.checked = false;
        })
    }
});



function updateStatus(checkbox) {
    // 모든 체크박스를 체크 해제합니다.
    const checkboxes = document.querySelectorAll('input[name="receipt"]');
    checkboxes.forEach(cb => cb.checked = false);

    // 클릭된 체크박스를 체크합니다.
    checkbox.checked = true;

    // URL 파라미터를 업데이트합니다.
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('status', checkbox.value);
    currentUrl.searchParams.set('cp', '1');
    window.location.href = currentUrl.toString();
}

function getStatusFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('status');
}

document.addEventListener('DOMContentLoaded', function() {
    const status = getStatusFromUrl();

    if (status) {
        const checkbox = document.querySelector(`input[name="receipt"][value="${status}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    } else {
        // URL 파라미터가 없는 경우 '전체' 체크박스를 기본적으로 체크합니다.
        const checkbox = document.querySelector('input[name="receipt"][value="A"]');
        if (checkbox) {
            checkbox.checked = true;
        }
    }
});