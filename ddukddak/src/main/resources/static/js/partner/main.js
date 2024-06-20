const seller = document.querySelector('#sellerBtn');
const interior = document.querySelector('#interiorBtn');

if(seller != null) {
    seller.addEventListener('click', () => {
        seller.closest('form').submit();
    })
}

if(interior != null) {
    interior.addEventListener('click', () => {
        interior.closest('form').submit();
    })
}
