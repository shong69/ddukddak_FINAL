function updateStatus(checkbox) {
    const checkboxes = document.querySelectorAll('input[name="order"]');
    checkboxes.forEach(cb => cb.checked = false);
    checkbox.checked = true;
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('status', checkbox.value);
    currentUrl.searchParams.set('cp', '1'); // Reset to first page on status change
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
