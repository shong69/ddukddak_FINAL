// 가구선택 소품들
const chair = document.querySelector("#chair");
const electronic = document.querySelector("#electronic");
const table = document.querySelector("#table");
const thing = document.querySelector("#thing");
const wall = document.querySelector("#wall");
const rug = document.querySelector("#rug");

// 처음의 의자/소파 선택칸만 보이게 하기
chair.style.display = 'block';
electronic.style.display = 'none';
table.style.display = 'none';
thing.style.display = 'none';
wall.style.display = 'none';
rug.style.display = 'none';

// 가구선택버튼
const addChair = document.querySelector("#add-chair");
const addElectronic = document.querySelector("#add-electronic");
const addTable = document.querySelector("#add-table");
const addThing = document.querySelector("#add-thing");
const addWall = document.querySelector("#add-wall");
const addRug = document.querySelector("#add-rug");

// 의자/소파 버튼 선택 시
addChair.addEventListener("click", () => {
    chair.style.display = 'block';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'none';
})
// 가전제품 버튼 선택 시
addElectronic.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'block';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'none';
})
// 테이블/책상 버튼 선택 시
addTable.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'block';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'none';
})
// 소품 버튼 선택 시
addThing.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'block';
    wall.style.display = 'none';
    rug.style.display = 'none';
})
// 벽걸이 버튼 선택 시
addWall.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'block';
    rug.style.display = 'none';
})
// 러그/매트 버튼 선택 시
addRug.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'block';
})







// 이벤트 리스너 추가
function addDragAndDropListeners(element) {
    element.setAttribute("draggable", true);
    element.addEventListener("dragstart", dragStart);
    element.addEventListener("dragend", dragEnd);
}

// 드래그 시작 이벤트
function dragStart(e) {
    e.dataTransfer.effectAllowed = "move";
    e.dataTransfer.setData("sqareId", e.target.id);
    e.target.classList.add("dragging");
}

// 드래그 종료 이벤트
function dragEnd(e) {
    e.target.classList.remove("dragging");
}

document.addEventListener("dragover", function(e) {
    e.preventDefault();
});

document.addEventListener("drop", function(e) {
    e.preventDefault();
    const sqareId = e.dataTransfer.getData("sqareId");
    const draggedElement = document.getElementById(sqareId);

    if (e.target.tagName === "TD" && e.target.childNodes.length === 0) {
        e.target.appendChild(draggedElement);

        const rect = e.target.getBoundingClientRect();
        const rectX = rect.left + window.scrollX;
        const rectY = rect.top + window.scrollY;

        // Three.js에서 cube 위치 업데이트
        var cubeDrag = scene.getObjectByName(sqareId);
        if (cubeDrag) {
            cubeDrag.position.x = rectX * 0.53 - 385;
            cubeDrag.position.y = 25;
            cubeDrag.position.z = rectY * 0.53 - 235;
        }
    }
});
const td = document.getElementsByTagName("td");

addChair.addEventListener("click", () => {
    for (let i = 0; i < td.length; i++) {
        if (td[i].childNodes.length === 0) {
            const sqare = document.createElement("div");
            sqare.classList.add("sqare");
            sqare.id = `chair-${i}`; // 각 요소에 고유한 ID 부여
            sqare.style.backgroundColor = "lightgray";
            td[i].appendChild(sqare);
            addDragAndDropListeners(sqare);

            // Three.js에서 새로운 큐브 생성
            const geometry = new THREE.BoxGeometry(1, 1, 1);
            const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
            const cube = new THREE.Mesh(geometry, material);
            cube.name = `chair-${i}`;
            scene.add(cube);

            break;
        }
    }
});
