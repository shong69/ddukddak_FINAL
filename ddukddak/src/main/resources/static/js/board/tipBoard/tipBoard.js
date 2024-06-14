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



document.addEventListener('DOMContentLoaded', function () {
    const tipBoardImg = document.querySelectorAll('.tipBoardImg');
    if (tipBoardImg != null) {
        tipBoardImg.forEach(item => {
            item.addEventListener('click', function () {
                const boardNo = this.getAttribute('data-board-no');
                console.log(boardNo);
                const url = `/tip/detail/${boardNo}`;
                window.location.href = url;
            });
        });
    }

    const likeIcons = document.querySelectorAll('.click-heart');
    if (likeIcons != null) {
        likeIcons.forEach(item => {
            item.addEventListener('click', function (event) {
                event.preventDefault(); // Prevent the default action of the anchor tag
                // if (loginMemberNo == null) {
                //     alert("로그인 후 이용해주세요.");
                //     return;
                // }
                const boardNo = this.closest('.tipBoardItem').querySelector('.tipBoardImg').getAttribute('data-board-no');
                if (boardNo) {
                    const obj = { 
                        "boardNo" : boardNo,
                        // "memberNo" : loginMemberNo,
                        "likeCheck" : likeCheck
                    };

                    fetch("/tip/like", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(obj)
                    }).then(resp => resp.text()).then(count => {

                        if (count == -1) {
                            console.log("Like error");
                            return;
                        }

                        likeCheck = likeCheck == 0 ? 1 : 0;

                        // Toggle the likeCheck status and icon class
                        this.querySelector('.fa-heart').classList.toggle("fa-regular");
                        this.querySelector('.fa-heart').classList.toggle("fa-solid");

                        // Update the like count displayed next to the icon
                        this.querySelector('h4').innerText = count;
                    }).catch(error => {
                        console.error('Error:', error);
                    });
                }
            });
        });
    }
});