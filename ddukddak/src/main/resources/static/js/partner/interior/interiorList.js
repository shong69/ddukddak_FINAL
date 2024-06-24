document.addEventListener('DOMContentLoaded', function() {
    const interiorDetailViewBtns = document.querySelectorAll('.interiorDetailViewBtn');
    
    if (interiorDetailViewBtns) {
        interiorDetailViewBtns.forEach(item => {
            item.addEventListener('click', function() {
                const portfolioNo = this.getAttribute('data-portfolio-no');
                
                if (portfolioNo != 0) {
                    const url = `/interior/interiorPortfolio/${portfolioNo}`;
                    window.location.href = url;
                } else {
                    alert("해당 시공사의 포트폴리오가 존재하지 않습니다.");
                    console.error('Portfolio number not found');
                    return;
                }
            });
        });
    }
});
