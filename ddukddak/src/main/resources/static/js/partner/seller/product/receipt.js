const modal = document.querySelector(".modal");
const orderReceipt = document.querySelector("#orderReceipt");
const orderRejection = document.querySelector("#orderRejection");
const productListBox = document.querySelector("#productListBox");

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
    })

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
    })

});


const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("접수관리를 취소하시겠습니까?")) {
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