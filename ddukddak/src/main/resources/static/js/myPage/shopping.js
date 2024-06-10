//전체 체크박스 설정
const totalCheck = document.querySelector("#totalCheck");
const checks = document.querySelectorAll('input[name="check"]');

totalCheck.addEventListener("change",function(){
    checks.forEach(check =>{
        check.checked  = this.checked;
    })
});

//삭제 버튼 클릭 시 
const delBtns = document.querySelectorAll(".myPageDelUpdateBtn");

delBtns.forEach(btn =>{
    btn.addEventListener("click", ()=>{


        //장바구니에서 삭제하기-비동기
        const productNo = btn.getAttribute("data-productNo");
        const optionNo = btn.getAttribute("data-optionNo");

        console.log(productNo);
        console.log(optionNo);
        fetch("/myPage/cart/delProduct",{
            method:"POST",
            headers : {"Content-Type":"application/json"},
            body : JSON.stringify({
                productNo : productNo,
                optionNo : optionNo})
        }).then(resp =>resp.text())
        .then(result =>{
            if(result == 0){
                alert("장바구니에서 상품 삭제 실패\n다시 시도해주세요");
                return;
            }
            console.log("삭제 상품 개수"+result);
            alert("상품이 삭제되었습니다");

        });

        //화면에서 사라지기
        btn.parentElement.parentElement.remove();

        //상품 목록 불러오기 비동기
    });
})

//상품 수량 수정
console.log()



//구매버튼 클릭
const checkedItems = document.querySelectorAll('input[name="check"].checked'); 
