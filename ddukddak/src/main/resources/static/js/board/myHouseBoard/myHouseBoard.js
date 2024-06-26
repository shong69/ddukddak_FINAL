// const myHouseDetail = document.querySelector("#myHouseDetail");
const boardCreateBtn = document.querySelector("#boardCreateBtn");


// if (myHouseDetail != null) {
//     myHouseDetail.addEventListener("click", () => {
    
//         location.href="/myHouse/detail";
    
//     });
// }

if (boardCreateBtn != null) {
    boardCreateBtn.addEventListener("click", () => {
    
        location.href="registMyHouse";
    
    });
}

// ===============================================================================


const sortMethod = document.querySelector("#sortingSelect");

if(sortMethod != null) {

    sortMethod.addEventListener("change", function () {
    
        const selectedOption = this.value;
        console.log(selectedOption);
        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('sort', selectedOption);
        window.location.href = currentUrl;
    
    });
    
    var currentUrl = window.location.href
    
    var queryString = currentUrl.split('?')[1];
    console.log(queryString);
    if (queryString) {
    
        var queryParams = queryString.split('&');
    
    
        for (var i = 0; i < queryParams.length; i++) {
            if (queryParams[i].indexOf('sort=') !== -1) {
    
                var sortValue = queryParams[i].split('=')[1];
    
                sortMethod.value = sortValue;
    
            }
        }
    } else {
        console.log("쿼리 파라미터가 없습니다.");
    }


};

