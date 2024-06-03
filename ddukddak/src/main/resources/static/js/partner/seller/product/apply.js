const modal = document.querySelector(".modal");
const changeStatus = document.querySelector("#changeStatus");
const productListBox = document.querySelector("#productListBox");

changeStatus.addEventListener("click", () => {
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


    const saleing = document.querySelector("#saleing");
    const salestop = document.querySelector("#salestop");
    const nosale = document.querySelector("#nosale");


    saleing.addEventListener("click", () => {
        alert("변경되었습니다");
        selectedEls.forEach(elements => {
            elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText = '판매중';
            modal.style.display = 'none';
        })
    })

    salestop.addEventListener("click", () => {
        alert("변경되었습니다");
        selectedEls.forEach(elements => {
            elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText = '판매중지';
            modal.style.display = 'none';
        })
    })

    nosale.addEventListener("click", () => {
        alert("변경되었습니다");
        selectedEls.forEach(elements => {
            elements.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.innerText = '진열안함';
            modal.style.display = 'none';
        })
    })
});


const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("등록을 취소하시겠습니까?")) {
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