const modal = document.querySelector(".modal");
const sellerButton = document.querySelector(".sellerButton");
const productListBox = document.querySelector("#productListBox");

function sellerConfirmModal() {
    productListBox.innerHTML = "";
    modal.style.display = "flex";

    const query = 'input[name="selectProduct"]:checked';
    const selectedEls = document.querySelectorAll(query);

    if (selectedEls.length === 0) {
        alert("선택된 항목이 없습니다.");
        modal.style.display = "none";
        return;
    }

    selectedEls.forEach(elements => {
        const tr = elements.closest('tr');

        const orderNo = tr.querySelector('.orderNo').innerText;
        const productName = tr.querySelector('.productName').innerText;
        const orderStatus = tr.querySelector('.orderStatus').innerText;
        const orderQuantity = tr.querySelector('.orderQuantity').innerText;
        const orderDate = tr.querySelector('.orderDate').innerText;
        const shipmentCompleteDate = tr.querySelector('.shipmentCompleteDate').innerText;
        const orderItemNo = tr.querySelector('.orderItemNo').value;

        const div = document.createElement("div");
        div.classList.add("elementBox");

        const orderNoElem = document.createElement("h5");
        orderNoElem.innerText = orderNo;

        const productNameElem = document.createElement("h5");
        productNameElem.innerText = productName;

        const orderStatusElem = document.createElement("h5");
        orderStatusElem.innerText = orderStatus;

        const orderQuantityElem = document.createElement("h5");
        orderQuantityElem.innerText = orderQuantity;

        const orderDateElem = document.createElement("h5");
        orderDateElem.innerText = orderDate;

        const shipmentCompleteDateElem = document.createElement("h5");
        shipmentCompleteDateElem.innerText = shipmentCompleteDate;

        div.append(orderNoElem);
        div.append(productNameElem);
        div.append(orderStatusElem);
        div.append(orderQuantityElem);
        div.append(orderDateElem);
        div.append(shipmentCompleteDateElem);

        productListBox.append(div);
    });

    const shipmentButton = document.querySelector("#shipmentButton");
    const shipmentRejection = document.querySelector("#shipmentRejection");

    shipmentButton.replaceWith(shipmentButton.cloneNode(true));
    shipmentRejection.replaceWith(shipmentRejection.cloneNode(true));

    document.querySelector("#shipmentButton").addEventListener("click", () => handleShipment(selectedEls, 'ship'));
    document.querySelector("#shipmentRejection").addEventListener("click", () => handleShipment(selectedEls, 'reject'));
}

function handleShipment(selectedEls, action) {
    const obj = Array.from(selectedEls).map(elements => {
        const tr = elements.closest('tr');
        return {
            orderNo: tr.querySelector('.orderNo').innerText,
            productName: tr.querySelector('.productName').innerText,
            orderStatus: tr.querySelector('.orderStatus').innerText,
            orderQuantity: tr.querySelector('.orderQuantity').innerText,
            orderDate: tr.querySelector('.orderDate').innerText,
            shipmentCompleteDate: tr.querySelector('.shipmentCompleteDate').innerText,
            orderItemNo: tr.querySelector('.orderItemNo').value,
            partnerNo: loginPartnerMember
        };
    });
    console.log(obj);

    const map = {
        "obj": obj
    }
    const endpoint = action === 'ship' ? "/partner/seller/product/shipmentAccept" : "/partner/seller/product/shipmentReject";
    fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(map)
    })
    .then(resp => resp.json())
    .then(result => {
        console.log(action);
        if (result > 0) {
            alert(action === 'ship' ? "출고되었습니다." : "출고가 거절되었습니다.");
            window.location.reload();
        } else {
            alert(action === 'ship' ? "출고 실패" : "거절 실패");
        }
    });
}

sellerButton.addEventListener("click", () => sellerConfirmModal());

const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if (confirm("변경을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
});

/* 전체선택버튼 */
const selectAll = document.querySelector('#selectAllCheckBox');
const checkboxes = document.getElementsByName('selectProduct');

selectAll.addEventListener('change', () => {
    checkboxes.forEach(elements => {
        elements.checked = selectAll.checked;
    });
});

function updateStatus(checkbox) {
    const checkboxes = document.querySelectorAll('input[name="shipment"]');
    checkboxes.forEach(cb => cb.checked = false);
    checkbox.checked = true;
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('status', checkbox.value);
    currentUrl.searchParams.set('cp', '1');
    window.location.href = currentUrl.toString();
}

function getStatusFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('status');
}

document.addEventListener('DOMContentLoaded', function () {
    const status = getStatusFromUrl();
    if (status) {
        const checkbox = document.querySelector(`input[name="shipment"][value="${status}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    } else {
        const checkbox = document.querySelector('input[name="shipment"][value="A"]');
        if (checkbox) {
            checkbox.checked = true;
        }
    }
});
