// 약관 전체 동의 기초 로직
const agreeAllBtn = document.getElementById('agreeAllBtn');
const agreeButtons = document.querySelectorAll('.agreeBtn');

agreeAllBtn.addEventListener('change', function() {
    if (this.checked) {
        agreeButtons.forEach(function(button) {
            button.checked = true;
        });
    } else {
        agreeButtons.forEach(function(button) {
            button.checked = false;
        });
    }
});

agreeButtons.forEach(function(button) {
    button.addEventListener('change', function() {
        if (document.querySelectorAll('.agreeBtn:checked').length === agreeButtons.length) {
            agreeAllBtn.checked = true;
        } else {
            agreeAllBtn.checked = false;
        }
    });
});