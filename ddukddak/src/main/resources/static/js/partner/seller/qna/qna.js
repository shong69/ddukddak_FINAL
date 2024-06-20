const modal = document.querySelector(".modal");
const orderNo = document.querySelector("#orderNo");
const userId = document.querySelector("#userId");
const productName = document.querySelector("#productName");
const option = document.querySelector("#option");
const qnaDate = document.querySelector("#qnaDate");

const borderTr = document.querySelectorAll(".borderTr");
console.log(borderTr);

borderTr.forEach(elements => {
    elements.addEventListener("click", () => {
        console.log(elements.children[0].innerHTML);

        modal.style.display = 'flex';

        orderNo.innerHTML = elements.children[0].innerHTML;
        userId.innerHTML = elements.children[1].innerHTML;
        productName.innerHTML = elements.children[2].innerHTML;
        option.innerHTML = elements.children[3].innerHTML;
        qnaDate.innerHTML = elements.children[4].innerHTML;
    })
})

const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("입력을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
})

const modify = document.querySelector("#modify");
const apply = document.querySelector("#apply");
const textarea = document.querySelector("#textarea");

modify.addEventListener("click", () => {

    if(textarea.value.trim().length === 0) {
        alert("입력을 완료해주세요");
    } else {
        alert("수정되었습니다.");
        modal.style.display = 'none';

    }
})

apply.addEventListener("click", () => {
    if(textarea.value.trim().length === 0) {
        alert("입력을 완료해주세요");
    } else {
        alert("등록되었습니다.");
        modal.style.display = 'none';

    }
})