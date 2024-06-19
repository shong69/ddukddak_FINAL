
document.addEventListener('DOMContentLoaded', () => {
    const viewMoreBtn = document.querySelector("#tipBoardViewMore");
    const tipBoardMainContainers = document.querySelectorAll(".tipBoardMainContainer");

    let currentVisibleIndex = 0; // Tracks the currently visible list

    // Initially hide all project lists except the first one
    tipBoardMainContainers.forEach((list, index) => {
        if (index === currentVisibleIndex) {
            list.classList.add('active');
        }
    });

    if (viewMoreBtn != null) {
        viewMoreBtn.addEventListener("click", () => {
            if (currentVisibleIndex < tipBoardMainContainers.length - 1) {
                currentVisibleIndex++;
                tipBoardMainContainers[currentVisibleIndex].classList.add('active'); // Show the next hidden list
                
                // If all lists are shown, hide the button
                if (currentVisibleIndex >= tipBoardMainContainers.length - 1) {
                    viewMoreBtn.style.display = 'none';
                }
            }
        });
    }
});
