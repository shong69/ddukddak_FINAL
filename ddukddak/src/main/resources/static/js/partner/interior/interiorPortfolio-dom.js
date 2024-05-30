document.addEventListener('DOMContentLoaded', (event) => {
    const viewMoreBtn = document.querySelector("#interiorProjectViewMore");
    const portBotContainer = document.querySelector(".botContainer");
    const projectLists = document.querySelectorAll(".portListMainContainer");
    
    let currentVisibleIndex = 0; // Tracks the currently visible list

    // Initially hide all project lists except the first one
    projectLists.forEach((list, index) => {
        if (index > currentVisibleIndex) {
            list.style.display = 'none';
        }
    });

    if (viewMoreBtn != null) {
        viewMoreBtn.addEventListener("click", () => {
            if (currentVisibleIndex < projectLists.length - 1) {
                currentVisibleIndex++;
                projectLists[currentVisibleIndex].style.display = 'flex'; // Show the next hidden list
                
                // If all lists are shown, hide the button
                if (currentVisibleIndex >= projectLists.length - 1) {
                    viewMoreBtn.style.display = 'none';
                }
            }
        });
    }
});
