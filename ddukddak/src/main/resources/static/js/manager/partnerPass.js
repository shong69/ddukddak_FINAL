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

// -------------------------------------------

const deleteBtn = document.querySelector('.deleteBtn');
const confirmBtns = document.querySelectorAll('.confirmBtn');
const refuseBtns = document.querySelectorAll('.refuseBtn');

// 승인 버튼 클릭 시 for문
confirmBtns.forEach(button => {
    button.addEventListener('click', e => handleApproval('confirm', e));
});

// 거절 버튼 클릭 시 for문
refuseBtns.forEach(button => {
    button.addEventListener('click', e => handleApproval('refuse', e));
});

// deleteBtn.addEventListener('click', handleDelete);


// 서버로 값 전달 함수
function handleApproval(action, e) {
    
    const button = e.currentTarget;
    const row = button.closest('tr');
    const partnerNo = row.querySelector('td:nth-child(2)').textContent;
    const partnerTel = document.getElementById('partnerTel').value;


    const obj = {
        partnerNo : partnerNo,
        partnerTel : partnerTel
    }
    
    // partnerNo, partnerTel 정상 반환 반환 확인
    console.log(`/manager/partner/${action}`);

    fetch(`/manager/partner/${action}`, {
        // /manager/partner/confirm || /manager/partner/refuse 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(obj)
    })
    .then(response => response.text())
    .then(result => {
        if (result == 1) { // 성공

            // 성공 시 문자 발송
            fetch

            alert(`파트너 가입 ${action === 'confirm' ? '승인' : '거절'} 처리가 완료되었습니다.\n해당 파트너에게 처리 결과가 문자 발송되었습니다.`);
            //row.remove();  // 처리된 행을 제거합니다.
            window.location.reload();

        } else { // 실패
            alert('처리 중 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        alert('처리 중 오류가 발생했습니다.');
    });
};



// 다중 항목 삭제 함수
// function handleDelete() {
//     const selectedItems = Array.from(selectItems)
//         .filter(checkbox => checkbox.checked)
//         .map(checkbox => {
//             const row = checkbox.closest('tr');
//             const partnerNo = row.querySelector('td:nth-child(2)').textContent;
//             return { checkbox, row, partnerNo };
//         });

//     if (selectedItems.length > 0) {
//         fetch(`/manager/partner/delete`, {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({ partners: selectedItems.map(item => item.partnerNo) }),
//         })
//         .then(response => response.text())
//         .then(result => {
//             if (result == 1) {
//                 alert('삭제 처리가 완료되었습니다.');
//                 selectedItems.forEach(item => item.row.remove());  // 삭제된 행을 제거합니다.
//             } else {
//                 alert('처리 중 오류가 발생했습니다.');
//             }
//         })
//         .catch(error => {
//             alert('처리 중 오류가 발생했습니다.');
//         });
//     } else {
//         alert('선택된 항목이 없습니다.');
//     }
// }