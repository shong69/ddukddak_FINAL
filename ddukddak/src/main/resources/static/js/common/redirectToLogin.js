function redirectToLogin() {
    const currentUrl = window.location.href;
    
    window.location.href = `/member/login?returnUrl=${encodeURIComponent(currentUrl)}`;
    
}

document.addEventListener('DOMContentLoaded', () => {
    const returnUrlField = document.getElementById('returnUrl');
    const returnUrl = new URLSearchParams(window.location.search).get('returnUrl');
    if (returnUrlField && returnUrl) {
        returnUrlField.value = returnUrl;
    }
});