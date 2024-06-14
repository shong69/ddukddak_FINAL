document.querySelector("#reportBtn").addEventListener("click", () => {

    if(loginMemberNo == null) {
        alert("로그인 후 이용가능한 서비스입니다.");
        return;
    }

    document.getElementById("reportModal").style.display = "block";
});

document.querySelector('.close-button').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'none';
});

document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'none';
});

document.getElementById('confirmButton').addEventListener('click', e => {

    const reason = document.getElementsByName("reason");
    let isChecked = false;
    
    for(let i=0; i < reason.length; i++) {
        
        if(reason[i].checked) {

            isChecked = true;
            
            const obj = {
                "reportedMemberNo" : writerNo,
                "boardNo" : boardNo,
                "memberNo" : loginMemberNo,
                "reportReason" : reason[i].value
            }
            
            
            
            if(!confirm("해당 게시글을 신고하시겠습니까?")) {
                alert("신고 접수가 취소되었습니다.");
                return;
            }
            
            console.log(reason[i].value);
                
            fetch("/board/report", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(obj)
            })
            .then(resp => resp.text())
            .then(result => {

                if(!result > 0) {

                    alert('신고 접수에 실패하였습니다.');
                    document.getElementById('reportModal').style.display = 'none';
                    return;
                    
                } 
                
                alert("신고 접수가 완료되었습니다.")
                document.getElementById('reportModal').style.display = 'none';
                return;
                
            })
        } 
    }

    if(isChecked == false) {
        alert("게시글 신고사유를 선택해주세요.");
        return;
    }

});

// 모달 외부를 클릭했을 때 닫기
window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('reportModal')) {
        document.getElementById('reportModal').style.display = 'none';
    }
});