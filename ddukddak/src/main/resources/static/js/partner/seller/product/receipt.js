const modal = document.querySelector(".modal");
const orderReceipt = document.querySelector("#orderReceipt");
const orderRejection = document.querySelector("#orderRejection");
const productListBox = document.querySelector("#productListBox");
const closeButton = document.querySelector("#closeButton");

/* 주문 접수 */
orderReceipt.addEventListener("click", () => {
    productListBox.innerHTML = "";
    modal.style.display = "flex";

    const query = 'input[name="selectProduct"]:checked'
    const selectedEls = document.querySelectorAll(query);

    console.log(selectedEls);

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

        div.append(productNo);
        div.append(productName);
        div.append(productOption);
        div.append(productStatus);
        div.append(productDate);

        productListBox.append(div);
    })


    const orderOK = document.querySelector("#orderOK");
    const orderNO = document.querySelector("#orderNO");


    orderOK.addEventListener("click", () => {
        alert("주문이 접수되었습니다");
        modal.style.display = 'none';
    })

    orderNO.addEventListener("click", () => {
        if(confirm("취소하시겠습니까?")) {
            modal.style.display = 'none';
        }
    });

    
});

/* 주문 거절 */
orderRejection.addEventListener("click", () => {
    productListBox.innerHTML = "";
    modal.style.display = "flex";

    const query = 'input[name="selectProduct"]:checked'
    const selectedEls = document.querySelectorAll(query);

    console.log(selectedEls);

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


    const orderOK = document.querySelector("#orderOK");
    const orderNO = document.querySelector("#orderNO");


    orderOK.addEventListener("click", () => {
        alert("주문이 거절되었습니다");
        modal.style.display = 'none';
    })

    orderNO.addEventListener("click", () => {
        if(confirm("취소하시겠습니까?")) {
            modal.style.display = 'none';
        }
    });

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