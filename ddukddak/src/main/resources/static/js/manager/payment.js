// ***** 1. 체크 박스 ***** 
const selectAll = document.querySelector('thead input[type="checkbox"]');
const selectItems = document.querySelectorAll('tbody input[type="checkbox"]');

// 전체 선택 체크박스 클릭 이벤트
selectAll.addEventListener('click', () => {
    selectItems.forEach(checkbox => {
        checkbox.checked = selectAll.checked;
    });
});

// 개별 선택 체크박스 클릭 이벤트
selectItems.forEach(checkbox => {
    checkbox.addEventListener('click', () => {
        if (!checkbox.checked) {
            selectAll.checked = false;
        } else {
            const allChecked = Array.from(selectItems).every(item => item.checked);
            selectAll.checked = allChecked;
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    // 날짜 형식 변경
    document.querySelectorAll('.payment-date').forEach(function (element) {
        const originalDate = element.textContent;
        const date = new Date(originalDate);
        const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
        element.textContent = formattedDate;
    });

    // 금액 형식 변경
    document.querySelectorAll('.amount').forEach(function (element) {
        const originalAmount = element.textContent;
        const formattedAmount = Number(originalAmount).toLocaleString('ko-KR') + '원';
        element.textContent = formattedAmount;
    });
});

function toggle(source) {
    checkboxes = document.getElementsByName('selectedPayments');
    for (var i = 0, n = checkboxes.length; i < n; i++) {
        checkboxes[i].checked = source.checked;
    }
}

document.querySelectorAll('.confirmBtn').forEach(element => {

    element.addEventListener('click', e => {
        alert('서비스 준비중입니다.');
    })

});

document.getElementById('multiPassBtn').addEventListener('click', () => {
    alert('서비스 준비중입니다.');
});