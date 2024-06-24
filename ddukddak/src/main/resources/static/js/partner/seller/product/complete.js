console.log("complete js");

/* 전체선택버튼 */
const selectAll = document.querySelector('#selectAllCheckBox');
const checkboxes = document.getElementsByName('selectProduct');

selectAll.addEventListener('change', () => {
    checkboxes.forEach(elements => {
        elements.checked = selectAll.checked;
    });
});

function updateStatus(checkbox) {
    const checkboxes = document.querySelectorAll('input[name="order"]');
    checkboxes.forEach(cb => cb.checked = false);
    checkbox.checked = true;
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('status', checkbox.value);
    currentUrl.searchParams.set('cp', '1');
    window.location.href = currentUrl.toString();
}

function getStatusFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('status');
}

document.addEventListener('DOMContentLoaded', function () {
    const status = getStatusFromUrl();
    if (status) {
        const checkbox = document.querySelector(`input[name="order"][value="${status}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    } else {
        const checkbox = document.querySelector('input[name="order"][value="A"]');
        if (checkbox) {
            checkbox.checked = true;
        }
    }
});
