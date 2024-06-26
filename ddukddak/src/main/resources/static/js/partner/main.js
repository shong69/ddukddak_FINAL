const sellerBtn = document.querySelector('#sellerBtn');
const interiorBtn = document.querySelector('#interiorBtn');

sellerBtn.addEventListener("click", () => {
    location.href = "/partner/seller/product/create";
});

interiorBtn.addEventListener("click", () => {
    location.href = "/partner/interiorPortfolioEditMain";
})