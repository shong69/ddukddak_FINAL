document.addEventListener('DOMContentLoaded', function() {
    const interiorDetailViewBtns = document.querySelectorAll('.interiorDetailViewBtn');
    
    if (interiorDetailViewBtns) {
        interiorDetailViewBtns.forEach(item => {
            item.addEventListener('click', function() {
                const portfolioNo = this.getAttribute('data-portfolio-no');
                
                if (portfolioNo) {
                    const url = `/interior/interiorPortfolio/${portfolioNo}`;
                    window.location.href = url;
                } else {
                    console.error('Portfolio number not found');
                }
            });
        });
    }
});
