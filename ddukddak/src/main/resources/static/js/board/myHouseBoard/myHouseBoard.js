const myHouseDetail = document.querySelector("#myHouseDetail");
const boardCreateBtn = document.querySelector("#boardCreateBtn");


myHouseDetail.addEventListener("click", () => {

    location.href="/myHouse/detail";

});

boardCreateBtn.addEventListener("click", () => {

    location.href="/myHouse/registMyHouse";

})