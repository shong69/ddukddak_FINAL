document.addEventListener("DOMContentLoaded", () => {
    const downloadBtn = document.getElementById("downloadBtn");

    downloadBtn.addEventListener("click", () => {
        // 두 개의 이미지 요소를 찾습니다.
        const floorPlanImg = document.getElementById("floorPlanImg");
        const threedModelingImg = document.getElementById("threedModelingImg");

        // 각 이미지를 다운로드하는 함수입니다.
        function downloadImage(imageElement, fileName) {
            const link = document.createElement('a');
            link.href = imageElement.src;
            link.download = fileName;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }

        // 두 개의 이미지를 다운로드합니다.
        downloadImage(floorPlanImg, 'FloorPlan.jpg');
        downloadImage(threedModelingImg, '3DModeling.png');
    });
});
