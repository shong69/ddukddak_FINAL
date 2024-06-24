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
});
// 가전제품 버튼 선택 시
addElectronic.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'block';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'none';
});
// 테이블/책상 버튼 선택 시
addTable.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'block';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'none';
});
// 소품 버튼 선택 시
addThing.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'block';
    wall.style.display = 'none';
    rug.style.display = 'none';
});
// 벽걸이 버튼 선택 시
addWall.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'block';
    rug.style.display = 'none';
});
// 러그/매트 버튼 선택 시
addRug.addEventListener("click", () => {
    chair.style.display = 'none';
    electronic.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    rug.style.display = 'block';
});




document.addEventListener("DOMContentLoaded", function() {
    let activeTool = "move"; // 초기 값은 move로 설정

    // Move 버튼 활성화 상태에서는 드래그 앤 드롭 가능
    function enableMoveMode() {
        activeTool = "move";
        document.getElementById("moveBtn").style.backgroundColor = "#b3bcc2";
        document.getElementById("rotateBtn").style.backgroundColor = "initial";
        document.getElementById("delBtn").style.backgroundColor = "initial";

        // sqare 드래그 앤 드롭 기능 활성화
        const sqares = document.querySelectorAll(".sqare");
        sqares.forEach(sqare => {
            sqare.draggable = true;
            sqare.addEventListener("dragstart", dragStart);
            sqare.addEventListener("dragend", dragEnd);
        });

        // td 요소에 drop 이벤트 리스너 추가
        const tds = document.getElementsByTagName("td");
        for (const td of tds) {
            td.addEventListener("dragover", dragOver);
            td.addEventListener("drop", drop);
        }
    }

    // Rotate 버튼 활성화 상태에서는 sqare를 클릭할 때마다 90도 회전
    function enableRotateMode() {
        activeTool = "rotate";
        document.getElementById("moveBtn").style.backgroundColor = "initial";
        document.getElementById("rotateBtn").style.backgroundColor = "#b3bcc2";
        document.getElementById("delBtn").style.backgroundColor = "initial";

        // sqare를 클릭할 때마다 90도 회전
        const sqares = document.querySelectorAll(".sqare");
        sqares.forEach(sqare => {
            sqare.removeEventListener("dragstart", dragStart);
            sqare.removeEventListener("dragend", dragEnd);
            sqare.removeEventListener("click", deleteSquare); // 삭제 이벤트 리스너 제거
            sqare.addEventListener("click", rotateSquare);
        });

        // td 요소에서 drop 이벤트 리스너 제거
        const tds = document.getElementsByTagName("td");
        for (const td of tds) {
            td.removeEventListener("dragover", dragOver);
            td.removeEventListener("drop", drop);
        }
    }

    // Delete 버튼 활성화 상태에서는 sqare를 클릭하면 삭제
    function enableDeleteMode() {
        activeTool = "delete";
        document.getElementById("moveBtn").style.backgroundColor = "initial";
        document.getElementById("rotateBtn").style.backgroundColor = "initial";
        document.getElementById("delBtn").style.backgroundColor = "#b3bcc2";

        // sqare를 클릭하면 삭제
        const sqares = document.querySelectorAll(".sqare");
        sqares.forEach(sqare => {
            sqare.removeEventListener("dragstart", dragStart);
            sqare.removeEventListener("dragend", dragEnd);
            sqare.removeEventListener("click", rotateSquare); // 회전 이벤트 리스너 제거
            sqare.addEventListener("click", deleteSquare);
        });

        // td 요소에서 drop 이벤트 리스너 제거
        const tds = document.getElementsByTagName("td");
        for (const td of tds) {
            td.removeEventListener("dragover", dragOver);
            td.removeEventListener("drop", drop);
        }
    }

    // 드래그 시작 이벤트
    function dragStart(e) {
        e.dataTransfer.effectAllowed = "move";
        e.dataTransfer.setData("sqareId", e.target.id);
        e.target.classList.add("dragging");

        // 드래그 중에 투명도 조정
        e.target.style.opacity = "0.5";
    }

    // 드래그 종료 이벤트
    function dragEnd(e) {
        e.target.classList.remove("dragging");

        // 드래그 종료 시 원래 투명도로 복구
        e.target.style.opacity = "1";
    }

    // 드래그 오버 이벤트 (드롭을 허용)
    function dragOver(e) {
        e.preventDefault();
    }

    // 드롭 이벤트 (sqare를 td로 옮기기)
    function drop(e) {
        e.preventDefault();
        const sqareId = e.dataTransfer.getData("sqareId");
        const sqare = document.getElementById(sqareId);
        const currentTd = sqare.parentNode;
        const newTd = e.target.tagName.toLowerCase() === "td" ? e.target : e.target.parentNode;

        if (currentTd !== newTd) {
            newTd.appendChild(sqare);
        }
    }

    // sqare를 클릭할 때마다 90도 회전
    function rotateSquare(e) {
        if (activeTool === "rotate") {
            const sqare = e.target;
            const transform = sqare.style.transform || "rotate(0deg)";
            const currentRotation = parseInt(transform.match(/-?\d+/)[0]);
            const newRotation = currentRotation + 90;
            sqare.style.transform = `rotate(${newRotation}deg)`;
        }
    }

    // sqare를 클릭하면 삭제
    function deleteSquare(e) {
        if (activeTool === "delete") {
            const sqare = e.target;
            sqare.parentNode.removeChild(sqare);
        }
    }

    // 이미지 클릭 시 sqare 생성
    const images = document.querySelectorAll(".item-box img");
    const td = document.getElementsByTagName("td");

    images.forEach((img, index) => {
        img.addEventListener("click", () => {
            for (let i = 0; i < td.length; i++) {
                if (td[i].childNodes.length === 0) {
                    const sqare = document.createElement("div");
                    sqare.classList.add("sqare");
                    sqare.id = `sqare-${index}-${i}`; // 각 요소에 고유한 ID 부여
                    sqare.style.backgroundColor = getColorByIndex(index); // 색상을 설정
                    td[i].appendChild(sqare);
                    sqare.draggable = true;
                    sqare.addEventListener("dragstart", dragStart);
                    sqare.addEventListener("dragend", dragEnd);
                    break;
                }
            }
        });
    });

    // 색상 설정 함수
    function getColorByIndex(index) {
        const colors = [
            "lightgray", "lightblue", "lightgreen", "lightcoral", "lightpink", 
            "lightyellow", "lightgoldenrodyellow", "lightcyan", "lightsteelblue", "lightseagreen"
        ];
        return colors[index % colors.length];
    }

    // 드래그 앤 드롭 리스너 추가 함수
    function addDragAndDropListeners(sqare) {
        sqare.addEventListener("dragstart", dragStart);
        sqare.addEventListener("dragend", dragEnd);
    }

    // 초기 설정: Move 버튼 활성화
    enableMoveMode();

    // 버튼 클릭 이벤트 리스너 추가
    document.getElementById("moveBtn").addEventListener("click", enableMoveMode);
    document.getElementById("rotateBtn").addEventListener("click", enableRotateMode);
    document.getElementById("delBtn").addEventListener("click", enableDeleteMode);
});




// Three.js 공간 생성
const canvas = document.getElementById('canvas');
const renderer = new THREE.WebGLRenderer({ canvas });
renderer.setSize(400, 400);

const scene = new THREE.Scene();

const camera = new THREE.PerspectiveCamera(45, 1, 1, 1000);
camera.position.set(0, 10, 20);
camera.lookAt(0, 0, 0);

const controls = new THREE.OrbitControls(camera, canvas);
controls.target.set(0, 0, 0);

const floorGeometry = new THREE.PlaneGeometry(10, 10, 10, 10);
const floorMaterial = new THREE.MeshBasicMaterial({ color: 0xffffff }); // White color, no wireframe
const floor = new THREE.Mesh(floorGeometry, floorMaterial);
floor.rotation.x = -Math.PI / 2;
scene.add(floor);

// Lighting
const light = new THREE.DirectionalLight(0xffffff, 1);
light.position.set(5, 5, 5).normalize();
scene.add(light);


// GLTFLoader to load the model
const loader = new THREE.GLTFLoader();
loader.load('/images/partner/interior/modeling/OlosChair.glb', function (gltf) {
    scene.add(gltf.scene);
    animate();
}, undefined, function (error) {
    console.error(error);
});


function animate() {
    requestAnimationFrame(animate);
    controls.update();
    renderer.render(scene, camera);
}

animate();
