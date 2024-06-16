/* 탑 버튼 */

const portTopButton = document.querySelector("#portTop");

if (portTopButton != null ) {
    portTopButton.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    });
}

/* 프로젝트 더보기 */
const viewMoreBtn = document.querySelector("#interiorProjectViewMore");
const portBotContainer = document.querySelector(".botContainer");
const portListMainContainer = document.querySelector(".portListMainContainer");


let currentVisibleIndex = 0; // Keeps track of currently visible containers

if (viewMoreBtn != null) {
    viewMoreBtn.addEventListener("click", () => {
        if (currentVisibleIndex < portListMainContainer.length) {
            portListMainContainer[currentVisibleIndex].style.display = 'block'; // Show the next hidden container
            currentVisibleIndex++;
            if (currentVisibleIndex >= portListMainContainer.length) {
                viewMoreBtn.style.display = 'none'; // Hide the button if all containers are shown
            }
        }
    });
}

// if(portListMainContainer != null) {

//     // Initially hide all portListMainContainer elements
//     portListMainContainer.forEach((container, index) => {
//         if (index >= currentVisibleIndex) {
//             container.style.display = 'none';
//         }
//     });
// }

document.querySelector("#insertBtn").addEventListener("click", () => {
    location.href = "/partner/registProject";
});