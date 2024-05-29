const chair = document.querySelector("#add-chair");
const table = document.querySelector("#add-table");
const sofa = document.querySelector("#add-sofa");
const bed = document.querySelector("#add-bed");
const td = document.getElementsByTagName("td");
const openButton = document.querySelector("#open");

const nameString = ["chair", "table", "sofa", "bed"];


let rectX = 0;
let rectY = 0;
let isViewerExpanded = false;


// 이벤트 리스너 추가
function addDragAndDropListeners(element) {
    element.setAttribute("draggable", true);
    element.addEventListener("dragstart", dragStart);
    element.addEventListener("dragend", dragEnd);
    element.addEventListener("dragover", dragOver);
    element.addEventListener("drop", drop);
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

// 드래그 오버 이벤트
function dragOver(e) {
    e.preventDefault();
}

// 드롭 이벤트
function drop(e) {
    e.preventDefault();
    const sqareId = e.dataTransfer.getData("sqareId");
    const draggedElement = document.getElementById(sqareId);
    if (e.target.tagName === "TD" && e.target.childNodes.length === 0) {
        e.target.appendChild(draggedElement);

        rectX = draggedElement.offsetLeft;
        rectY = draggedElement.offsetTop;


        var cubeDrag = scene.getObjectByName(sqareId);
        cubeDrag.position.x = rectX*0.53 - 198;
        cubeDrag.position.y = 25;
        cubeDrag.position.z = rectY*0.53 - 90;
    }
}

// 의자 추가
chair.addEventListener("click", () => {
    for (let i = 0; i < td.length; i++) {
        if (td[i].childNodes.length === 0) {
            const sqare = document.createElement("div");
            sqare.classList.add("sqare");
            sqare.id = `chair-${i}`; // 각 요소에 고유한 ID 부여
            sqare.innerText = nameString[0];
            sqare.style.backgroundColor = "lightgray";
            td[i].append(sqare);
            addDragAndDropListeners(sqare);

            rectX = sqare.offsetLeft;
            rectY = sqare.offsetTop;


            const loader = new THREE.GLTFLoader();
            loader.load('/images/gamingChair.glb', function (gltf) {
                const model = gltf.scene;
                model.position.set(rectX * 0.53 - 198, 25, rectY * 0.53 - 90);
                model.scale.set(0.1,0.1,0.1);

                model.name = `chair-${i}`;
                scene.add(model);
            }, undefined, function (error) {
                console.error(error);
            });

            break;
        }
    }
});

// 테이블 추가
table.addEventListener("click", () => {
    for (let i = 0; i < td.length; i++) {
        if (td[i].childNodes.length === 0) {
            const sqare = document.createElement("div");
            sqare.classList.add("sqare");
            sqare.id = `table-${i}`; // 각 요소에 고유한 ID 부여
            sqare.innerText = nameString[1];
            sqare.style.backgroundColor = "yellow";
            td[i].append(sqare);
            addDragAndDropListeners(sqare);

            rectX = sqare.offsetLeft;
            rectY = sqare.offsetTop;


            const cubeGeometry = new THREE.BoxGeometry(38, 38, 38);
            const cubeMaterial = new THREE.MeshBasicMaterial({ color: 0xffff00 });
            const cube = new THREE.Mesh(cubeGeometry, cubeMaterial);

            // 바닥 평면의 중심에 정육면체 추가
            cube.position.x = rectX*0.53 - 198;
            cube.position.y = 18.5;
            cube.position.z = rectY*0.53 - 90;
            cube.name = `table-${i}`;

            scene.add(cube);


            break;
        }
    }
});

// 소파 추가
sofa.addEventListener("click", () => {
    for (let i = 0; i < td.length; i++) {
        if (td[i].childNodes.length === 0) {
            const sqare = document.createElement("div");
            sqare.classList.add("sqare");
            sqare.id = `sofa-${i}`; // 각 요소에 고유한 ID 부여
            sqare.innerText = nameString[2];
            sqare.style.backgroundColor = "pink";
            td[i].append(sqare);
            addDragAndDropListeners(sqare);

            rectX = sqare.offsetLeft;
            rectY = sqare.offsetTop;

            const cubeGeometry = new THREE.BoxGeometry(38, 38, 38);
            const cubeMaterial = new THREE.MeshBasicMaterial({ color: 0xffc1cc });
            const cube = new THREE.Mesh(cubeGeometry, cubeMaterial);

            // 바닥 평면의 중심에 정육면체 추가
            cube.position.x = rectX*0.53 - 198;
            cube.position.y = 18.5;
            cube.position.z = rectY*0.53 - 90;
            cube.name = `sofa-${i}`;

            scene.add(cube);


            break;
        }
    }
});

// 침대 추가
bed.addEventListener("click", () => {
    for (let i = 0; i < td.length; i++) {
        if (td[i].childNodes.length === 0) {
            const sqare = document.createElement("div");
            sqare.classList.add("sqare");
            sqare.id = `bed-${i}`; // 각 요소에 고유한 ID 부여
            sqare.innerText = nameString[3];
            sqare.style.backgroundColor = "lightblue";
            td[i].append(sqare);
            addDragAndDropListeners(sqare);

            rectX = sqare.offsetLeft;
            rectY = sqare.offsetTop;

            const cubeGeometry = new THREE.BoxGeometry(38, 38, 38);
            const cubeMaterial = new THREE.MeshBasicMaterial({ color: 0x6497b1 });
            const cube = new THREE.Mesh(cubeGeometry, cubeMaterial);

            // 바닥 평면의 중심에 정육면체 추가
            cube.position.x = rectX*0.53 - 198;
            cube.position.y = 18.5;
            cube.position.z = rectY*0.53 - 90;
            cube.name = `bed-${i}`;

            scene.add(cube);


            break;
        }
    }
});

// 모든 td 요소에 드래그 오버 및 드롭 이벤트 추가
for (let i = 0; i < td.length; i++) {
    td[i].addEventListener("dragover", dragOver);
    td[i].addEventListener("drop", drop);
}

// Three.js 설정
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, 400 / 400, 0.1, 1000);
const renderer = new THREE.WebGLRenderer();
renderer.setSize(400, 400);
document.getElementById("viewer").appendChild(renderer.domElement);

// 바닥 평면 추가 (파란색)
const geometry = new THREE.PlaneGeometry(200, 200);
const material = new THREE.MeshBasicMaterial({ color: 0x808080, side: THREE.DoubleSide });
const plane = new THREE.Mesh(geometry, material);
plane.rotation.x = -Math.PI / 2; // 바닥 평면을 수평으로 회전
scene.add(plane);

// 좌표축 추가
/*
const axesHelper = new THREE.AxesHelper(100); // 길이가 100인 좌표축
scene.add(axesHelper);

// X축 좌표 숫자 표시
for (let i = -100; i <= 100; i += 50) {
    createText(i.toString(), 0xff0000, i, 0, 0);
}

// Y축 좌표 숫자 표시
for (let i = -100; i <= 100; i += 50) {
    createText(i.toString(), 0x00ff00, 0, i, 0);
}

// Z축 좌표 숫자 표시
for (let i = -100; i <= 100; i += 50) {
    createText(i.toString(), 0x0000ff, 0, 0, i);
}

function createText(text, color, x, y, z) {
    const loader = new THREE.FontLoader();
    loader.load('https://threejs.org/examples/fonts/helvetiker_regular.typeface.json', function(font) {
        const geometry = new THREE.TextGeometry(text, {
            font: font,
            size: 5, // 텍스트 크기 조정
            height: 1
        });
        const material = new THREE.MeshBasicMaterial({ color: color });
        const textMesh = new THREE.Mesh(geometry, material);
        textMesh.position.set(x, y, z); // 텍스트 메시 위치 설정
        scene.add(textMesh); // 씬에 텍스트 메시 추가
    });
}
*/

camera.position.set(130, 130, 130);
camera.lookAt(0, 0, 0);

function animate() {
    requestAnimationFrame(animate);
    renderer.render(scene, camera);
}

animate();


camera.position.set(150, 150, 150);
camera.lookAt(0, 0, 0);

function animate() {
    requestAnimationFrame(animate);
    renderer.render(scene, camera);
}

animate();

// orbitControls 추가
const controls = new THREE.OrbitControls(camera, renderer.domElement);
controls.enableDamping = true; // 감속 효과 활성화
controls.dampingFactor = 0.25; // 감속 정도 설정
controls.screenSpacePanning = false; // 수평 및 수직 회전 제한
controls.minDistance = 100; // 최소 줌 거리 설정
controls.maxDistance = 500; // 최대 줌 거리 설정
controls.maxPolarAngle = Math.PI / 2; // 최대 회전 각도 설정



// viewer 클릭 이벤트 추가
openButton.addEventListener("click", () => {
    if (isViewerExpanded) {
        viewer.style.position = "block";
        viewer.style.transform = "translate(0, 0)";
        viewer.style.position = "";
        viewer.style.width = "400px";
        viewer.style.height = "400px";
        openButton.innerText = "[  ]";
        isViewerExpanded = false;
    } else {
        viewer.style.position = "absolute";
        viewer.style.top = "50%";
        viewer.style.left = "50%";
        viewer.style.transform = "translate(-50%, -50%)";
        viewer.style.width = "800px";
        viewer.style.height = "800px";
        openButton.innerText = "X";
        isViewerExpanded = true;
    }
    renderer.setSize(parseInt(viewer.style.width), parseInt(viewer.style.height));
    camera.aspect = parseInt(viewer.style.width) / parseInt(viewer.style.height);
    camera.updateProjectionMatrix();
});
