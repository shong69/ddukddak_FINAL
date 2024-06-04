document.addEventListener('DOMContentLoaded', function() {
    
    const ellipsedContent = document.querySelector('.ellipsed-content');
    const originalText = ellipsedContent.textContent;

    function addEllipsis() {
        let text = originalText;
        ellipsedContent.textContent = text;

        while (ellipsedContent.scrollHeight > ellipsedContent.clientHeight) {
            text = text.slice(0, -1);
            ellipsedContent.textContent = text + '...';
        }
    }

    addEllipsis();
    window.addEventListener('resize', addEllipsis);
});

const tipBoardContainers = document.querySelectorAll('.tipBoardContainer');

if (tipBoardContainers != null) {
    if (tipBoardContainers.length > 0) {
        tipBoardContainers.forEach(container => {
            container.addEventListener('click', () => {
                location.href = '/tip/detail';
            });
        });
    }
}
