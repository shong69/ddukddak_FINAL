
document.querySelector('.close-button').addEventListener('click', function() {
    document.getElementById('modal').style.display = 'none';
});

document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('modal').style.display = 'none';
});

document.getElementById('confirmButton').addEventListener('click', function() {
    alert('신고가 접수되었습니다.');
    document.getElementById('modal').style.display = 'none';
});

// 모달 외부를 클릭했을 때 닫기
window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('modal')) {
        document.getElementById('modal').style.display = 'none';
    }
});