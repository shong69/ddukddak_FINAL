function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

const ProductPrice = document.querySelectorAll(".productPrice");


for (let i = 0; i < ProductPrice.length; i++) {
    let productPrice = ProductPrice[i].textContent.trim(); // 요소의 텍스트 내용 가져오기

    ProductPrice[i].textContent = formatNumberWithCommas(productPrice); // 포맷팅된 내용으로 설정
    ProductPrice[i].innerHTML += '원';

}

const delButton = document.querySelectorAll(".delButton");

delButton.forEach(elements => {
    elements.addEventListener("click", e => {
        e.preventDefault();

        var obj = {
            "memberNo" : e.target.attributes[0].value,
            "productNo" : e.target.attributes[1].value
        }

        fetch("/myPage/delWish", {
            method : "POST",
            headers : {"Content-Type": "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(temp => {
            if(temp > 0) {
                alert("위시리스트에서 삭제되었습니다");
                window.location.reload();
            }else {
                alert("위시리스트 삭제 실패");
            }
        });

        return false;
    })
})

function truncateText(text, maxLength) {
    return text.length > maxLength ? text.slice(0, maxLength - 3) + "..." : text;
}

const itemTitle = document.querySelectorAll(".item-title");

itemTitle.forEach(elements => {
    const longText = elements.innerHTML;
    const truncated = truncateText(longText, 60);
    console.log(truncated);

    elements.innerHTML = truncated;
});