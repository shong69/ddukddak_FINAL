document.querySelector("#reportBtn").addEventListener("click", () => {
    document.getElementById("reportModal").style.display = "block";
});

document.querySelector('.close-button').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'none';
});

document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'none';
});

document.getElementById('confirmButton').addEventListener('click', function() {

    const reason = document.getElementsByName("reason");
    let isChecked = false;
    
    for(let i=0; i < reason.length; i++) {

        if(reason[i].checked) {

            isChecked = true;

            if(!confirm("해당 게시글을 신고하시겠습니까?")) {
                alert("신고 접수가 취소되었습니다.");
                return;
            }
            alert('신고가 접수되었습니다.');
            document.getElementById('reportModal').style.display = 'none';
            return;
        } 

    }
    alert("게시글 신고사유를 선택해주세요.");
});

// 모달 외부를 클릭했을 때 닫기
window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('reportModal')) {
        document.getElementById('reportModal').style.display = 'none';
    }
});