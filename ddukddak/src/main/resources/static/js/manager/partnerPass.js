document.addEventListener('DOMContentLoaded', () => {
    const selectAllCheckbox = document.querySelector('thead input[type="checkbox"]');
    const selectItemCheckboxes = document.querySelectorAll('tbody input[type="checkbox"]');
    const deleteBtn = document.querySelector('.deleteBtn');
    const confirmButtons = document.querySelectorAll('.confirmBtn');
    const refuseButtons = document.querySelectorAll('.refuseBtn');

    // 전체 선택 체크박스 클릭 이벤트
    selectAllCheckbox.addEventListener('click', () => {
        selectItemCheckboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });

    // 개별 선택 체크박스 클릭 이벤트
    selectItemCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('click', () => {
            if (!checkbox.checked) {
                selectAllCheckbox.checked = false;
            } else {
                const allChecked = Array.from(selectItemCheckboxes).every(item => item.checked);
                selectAllCheckbox.checked = allChecked;
            }
        });
    });

    // 승인 버튼 클릭 이벤트
    confirmButtons.forEach(button => {
        button.addEventListener('click', () => {
            handleApproval('approve', button);
        });
    });

    // 거절 버튼 클릭 이벤트
    refuseButtons.forEach(button => {
        button.addEventListener('click', () => {
            handleApproval('reject', button);
        });
    });

    function handleApproval(action, button) {
        const selectedItems = [];
        selectItemCheckboxes.forEach(checkbox => {
            if (checkbox.checked) {
                const row = checkbox.closest('tr');
                const partnerNo = row.querySelector('td:nth-child(2)').textContent;
                selectedItems.push(partnerNo);
            }
        });

        if (selectedItems.length > 0) {
            // 서버에 승인/거절 요청 보내기
            fetch(`/api/partner/${action}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ partners: selectedItems }),
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(`${action === 'approve' ? '승인' : '거절'} 처리가 완료되었습니다.`);
                    // 필요한 경우 페이지 새로고침 또는 행 업데이트
                    window.location.reload();
                } else {
                    alert('처리 중 오류가 발생했습니다.');
                }
            });
        } else {
            alert('선택된 항목이 없습니다.');
        }
    }
});