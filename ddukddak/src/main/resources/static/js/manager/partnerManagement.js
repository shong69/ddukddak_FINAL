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

// **************** 단일 회원 처리 ****************

const delBtn = document.querySelectorAll('.delBtn');

// 탈퇴 버튼 클릭 시 for문
delBtn.forEach(button => {
    button.addEventListener('click', e => handleApproval('delete', e));
});

// deleteBtn.addEventListener('click', handleDelete);


// 서버로 값 전달 함수
async function handleApproval(action, e) {
    const selectedItems = Array.from(selectItems).filter(checkbox => checkbox.checked);

    if (selectedItems.length > 0) {
        alert('선택된 항목이 존재합니다. 체크 해제 후 시도해 주세요.');
        
        // 체크된 항목 모두 해제
        Array.from(selectItems).forEach(checkbox => {
            checkbox.checked = false;
        });

        // 전체 항목 체크 풀기
        if(selectAll.checked) selectAll.checked = false;

        return; // 함수 종료
    }

    const button = e.currentTarget;
    const row = button.closest('tr');
    const partnerNo = row.querySelector('td:nth-child(2)').textContent;
    const partnerTel = row.querySelector('td:nth-child(4)').textContent;
    console.log(memberNo);

    try {

        // 1. 파트너 탈퇴 처리
        const updateResponse = await fetch(`/manager/partner/member/${action}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: memberNo
        });
        const updateResult = await updateResponse.text();

        // 2. 파트너 탈퇴 시 문자 발송
        if (updateResult == 1) { // 파트너 DEL 'W' -> 'Y' OR 'N' 성공

            const smsResponse = await fetch(`/sms/sendOne/partner/${action}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: partnerTel
            });
            const smsResult = await smsResponse.text();

            if (smsResult == 1) { // 문자 발송 성공
                alert(`파트너 탈퇴 처리가 완료되었습니다.\n해당 파트너에게 결과가 문자로 발송되었습니다.`);
                window.location.reload();
            } else {
                alert("금일 발송량 초과 혹은 서버 문제로 인해 인증 번호 발송에 실패했습니다.");   
            }
        } else {
            alert('파트너 탈퇴 처리 중 오류가 발생했습니다.');
        }
    } catch (error) {
        alert('처리 중 오류가 발생했습니다: ' + error.message);
    }
}

// -----------------------------------------------------------------------

// **************** 다중 파트너 처리 ****************

// '선택 처리' 버튼 클릭 시 
document.querySelector('#multiPassBtn').addEventListener('click', () => {

    // 1. 미체크 처리
    const selectedItems = Array.from(selectItems).filter(checkbox => checkbox.checked);

    if (selectedItems.length === 0) {
        alert('선택된 항목이 없습니다.');
        return;
    }

    if(confirm("선택한 항목에 대해 탈퇴처리를 진행하겠습니까?")) {
        handleMultiApproval("delete");
    } else {
        alert("작업이 취소되었습니다. 선택한 항목이 해제됩니다.");
         // 체크된 항목 모두 해제
         Array.from(selectItems).forEach(checkbox => {
            checkbox.checked = false;
        });

        // 전체 항목 체크 풀기
        if(selectAll.checked) selectAll.checked = false;

        return;
    }


});


// 다중 항목 업데이트 및 SMS 발송 로직 함수
async function handleMultiApproval(actionForBackend) {
    
    // 1. 체크된 항목의 파트너 번호, 핸드폰 번호 얻어오기
    const selectedItems = Array.from(selectItems)
        .filter(checkbox => checkbox.checked)
        .map(checkbox => {
            const row = checkbox.closest('tr');
            const partnerNo = row.querySelector('td:nth-child(2)').textContent;
            const partnerTel = row.querySelector('td:nth-child(4)').textContent;
            return { partnerNo, partnerTel };
        });
    
    // 체크 된 애들 개수(알림에 띄울) 얻어오기
    const selectedCount = selectedItems.length;

    //console.log("actionForBackend : " + actionForBackend);
    //console.log("selectedItems: ", JSON.stringify(selectedItems));
    
    // 2. 값을 잘 넘겨 받아 왔다면 업데이트부터 시작
    if (selectedItems.length > 0) {
        try {
            const updateResponse  = await fetch(`/manager/partner/management/multi/${actionForBackend}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ partners : selectedItems }),
            });
            const updateResult = await updateResponse .text();
            
            //console.log("업데이트 결과 개수 : " + updateResult)

            // 업데이트 성공 시 SMS 발송 시작
            if (updateResult > 0) {
                try {
                    const smsResponse = await fetch(`/sms/sendMany/partner/mulit/${actionForBackend}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ partners: selectedItems }),
                    });
                    const smsResult = await smsResponse.text();
                    
                    //console.log("문자 결과 개수 : " + smsResult);
    
                    alert(` 선택하신 항목에 대한 '파트너 탈퇴' 처리 결과입니다.
                        \n 업데이트 성공 결과 : ${updateResult} / ${selectedCount}
                        \n SMS 발송 성공 결과 : ${smsResult} / ${selectedCount}`);
    
                    window.location.reload();
                } catch(smsError) {
                    alert('SMS 발송 중 오류가 발생했습니다: ' + smsError.message);
                }
                
            } else {
                alert('파트너 탈퇴 처리 중 오류가 발생했습니다.');
                window.location.reload();
            }
        } catch (updateError) {
            alert('파트너 탈퇴 중 오류가 발생했습니다 : ' + updateError.message);
        }
    } else {
        alert('선택된 항목이 없습니다.');
    }
}