const modal = document.querySelector(".modal");
const orderNo = document.querySelector("#orderNo");
const userId = document.querySelector("#userId");
const productName = document.querySelector("#productName");
const option = document.querySelector("#option");
const qnaDate = document.querySelector("#qnaDate");
const qnaContent = document.querySelector("#qnaContent");
const textarea = document.querySelector("#textarea");

const borderTr = document.querySelectorAll(".borderTr");
console.log(borderTr);

var qnaNo = 0;


document.addEventListener('DOMContentLoaded', () => {
    const answerStatus = document.querySelectorAll(".answerStatus");

    answerStatus.forEach(elements => {
        if(elements.innerText == 'Y') {
            elements.innerText = '답변완료'
            elements.previousElementSibling.style.color = 'green';
        } else {
            elements.innerText = '미답변'
            elements.previousElementSibling.style.color = 'red';
        }
    })
});



borderTr.forEach(elements => {
    elements.addEventListener("click", () => {
        console.log(elements.children[0].innerHTML);

        modal.style.display = 'flex';

        orderNo.innerHTML = elements.children[0].innerHTML;
        qnaNo = elements.children[0].innerHTML;
        userId.innerHTML = elements.children[1].innerHTML;
        productName.innerHTML = elements.children[2].innerHTML;
        option.innerHTML = elements.children[3].innerHTML;
        qnaDate.innerHTML = elements.children[4].innerHTML;

        qnaContent.innerHTML = elements.children[5].value;
        textarea.innerHTML = elements.children[6].value;
    })
})


const closeButton = document.querySelector("#closeButton");

closeButton.addEventListener("click", () => {
    if(confirm("입력을 취소하시겠습니까?")) {
        modal.style.display = "none";
    }
})
const apply = document.querySelector("#apply");

apply.addEventListener("click", () => {
    if(textarea.value.trim().length === 0) {
        alert("입력을 완료해주세요");
    } else {
        const obj = {
            "qnaAnswer" : textarea.value,
            "qnaNo" : qnaNo
        }

        console.log(obj);

        fetch("/partner/seller/insertQnaAnswer", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0) {
                alert("등록되었습니다.");
                modal.style.display = 'none';
                window.location.reload();
            } else {
                alert("등록 실패");
            }
        });

    }
})