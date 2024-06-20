const modal = document.querySelector(".modal");
const changeStatus = document.querySelector("#changeStatus");
const productListBox = document.querySelector("#productListBox");

// 상품가격 포맷
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const productPriceElements = document.getElementsByClassName("productPrice");

for (let i = 0; i < productPriceElements.length; i++) {
    let productPrice = productPriceElements[i].textContent.trim();
    productPriceElements[i].textContent = formatNumberWithCommas(productPrice);
    productPriceElements[i].innerText += '원';
}


// 상품제목 글자수제한
function truncateText(text, maxLength) {
    return text.length > maxLength ? text.slice(0, maxLength - 3) + "..." : text;
}

const itemTitle = document.querySelectorAll(".productName");

itemTitle.forEach(elements => {
    const longText = elements.innerHTML;
    const truncated = truncateText(longText, 40);

    elements.innerHTML = truncated;
});

function updateStatus(checkbox) {
    // 모든 체크박스를 체크 해제합니다.
    const checkboxes = document.querySelectorAll('input[name="sale"]');
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
        const checkbox = document.querySelector(`input[name="sale"][value="${status}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    } else {
        // URL 파라미터가 없는 경우 '전체' 체크박스를 기본적으로 체크합니다.
        const checkbox = document.querySelector('input[name="sale"][value="A"]');
        if (checkbox) {
            checkbox.checked = true;
        }
    }
});

// 판매상태 변경
changeStatus.addEventListener("click", () => {
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

        if(status == '판매중') {
            productStatus.style.color = 'green';
        }else if(status == '판매중지') {
            productStatus.style.color = 'red';
        }

        const productDate = document.createElement("h5");
        productDate.innerText = "20240603";

        div.append(productNo);
        div.append(productName);
        div.append(productOption);
        div.append(productStatus);
        div.append(productDate);

        productListBox.append(div);
    })


    const changeButton = document.querySelectorAll(".changeButton");


    changeButton.forEach(element => {
        element.addEventListener("click", e => {
            const obj = [];

        selectedEls.forEach(elements => {
            obj.push(elements.value);
        })

        const map = {
            "obj" : obj,
            "status" : e.target.innerText
        }

        fetch("/partner/seller/product/changeStatus", {
            method: "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(map)
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0) {
                alert("변경되었습니다");
                window.location.reload();
            }else {
                alert("변경 실패");
            }
        });
        })
    })

});


const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("변경을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
})

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