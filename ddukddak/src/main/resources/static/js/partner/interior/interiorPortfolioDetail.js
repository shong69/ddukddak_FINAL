

const portDetailTop = document.querySelector("#portDetailTop");

if (portDetailTop != null ) {
    portDetailTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    });
}

