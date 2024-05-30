const toMyHouseBoard = document.querySelector("#toMyHouseBoard");
const toTipBoard = document.querySelector("#toTipBoard");

toMyHouseBoard.addEventListener("click", () => {
    location.href="/myHouse/main";
})

toTipBoard.addEventListener("click", () => {
    location.href="/tip/main"
})