// Three.js 공간 생성
const canvas = document.getElementById('canvas');
const renderer = new THREE.WebGLRenderer({ canvas: canvas });
renderer.setSize(400, 400);
renderer.setPixelRatio(window.devicePixelRatio);

const scene = new THREE.Scene();

const camera = new THREE.PerspectiveCamera(60, 1, 1, 1000);
camera.position.set(0, 2, 5);
camera.lookAt(0, 0, 0);
camera.updateProjectionMatrix();

const controls = new THREE.OrbitControls(camera, canvas);
controls.target.set(0, 1, 0);

// 초기 그라데이션 배경 설정
const initialBackgroundTexture = new THREE.TextureLoader().load('/images/partner/interior/modeling/background1.JPG');
let gradientTexture = initialBackgroundTexture;
scene.background = gradientTexture;

// 초기 바닥 텍스처 로드
const initialFloorTexture = new THREE.TextureLoader().load('/images/partner/interior/modeling/floor1.jpg', function(texture) {
    texture.wrapS = THREE.RepeatWrapping;
    texture.wrapT = THREE.RepeatWrapping;
    texture.repeat.set(1, 1);
    texture.minFilter = THREE.LinearFilter;
    texture.magFilter = THREE.LinearFilter;
});

// 바닥 두께 설정
const floorThickness = 0.3;
const floorGeometry = new THREE.BoxGeometry(10, floorThickness, 10);
const floorMaterial = new THREE.MeshStandardMaterial({ map: initialFloorTexture });
const floor = new THREE.Mesh(floorGeometry, floorMaterial);
floor.position.y = -floorThickness / 2;
scene.add(floor);

// 초기 벽 텍스처 로드
const initialWallTexture = new THREE.TextureLoader().load('/images/partner/interior/modeling/wall1.jpg', function(texture) {
    texture.wrapS = THREE.RepeatWrapping;
    texture.wrapT = THREE.RepeatWrapping;
    texture.repeat.set(1, 1);
    texture.minFilter = THREE.LinearFilter;
    texture.magFilter = THREE.LinearFilter;
});

// 벽면 재질 설정
const wallMaterial = new THREE.MeshStandardMaterial({ map: initialWallTexture });

// 벽의 높이 설정
const wallHeight = 4.5; // 벽의 높이를 적절한 값으로 설정

// 벽 1 (앞쪽 벽)
const wall1Geometry = new THREE.BoxGeometry(10, wallHeight, 0.3);
const wall1 = new THREE.Mesh(wall1Geometry, wallMaterial);
wall1.name = "wall1";
wall1.position.set(0, wallHeight / 2, -5);
scene.add(wall1);

// 벽 2 (오른쪽 벽)
const wall2Geometry = new THREE.BoxGeometry(0.3, wallHeight, 10);
const wall2 = new THREE.Mesh(wall2Geometry, wallMaterial);
wall2.name = "wall2";
wall2.position.set(5, wallHeight / 2, 0);
scene.add(wall2);

// 벽 3 (뒤쪽 벽)
const wall3Geometry = new THREE.BoxGeometry(10, wallHeight, 0.3);
const wall3 = new THREE.Mesh(wall3Geometry, wallMaterial);
wall3.name = "wall3";
wall3.position.set(0, wallHeight / 2, 5);
scene.add(wall3);

// 벽 4 (왼쪽 벽)
const wall4Geometry = new THREE.BoxGeometry(0.3, wallHeight, 10);
const wall4 = new THREE.Mesh(wall4Geometry, wallMaterial);
wall4.name = "wall4";
wall4.position.set(-5, wallHeight / 2, 0);
scene.add(wall4);

// 조명 설정
const directionalLight = new THREE.DirectionalLight(0xffffff, 1);
scene.add(directionalLight);

// 주변광
const ambientLight = new THREE.AmbientLight(0xffffff, 1);
scene.add(ambientLight);

// 애니메이션 루프
function animate() {
    requestAnimationFrame(animate);
    controls.update();

    // 카메라의 위치와 방향을 기준으로 조명의 방향 업데이트
    const cameraDirection = new THREE.Vector3();
    camera.getWorldDirection(cameraDirection);
    directionalLight.position.copy(camera.position);
    directionalLight.position.add(cameraDirection.multiplyScalar(10));

    renderer.render(scene, camera);
}

function updateTextures() {
    // 새로운 배경 텍스처를 로드하여 씬 배경을 업데이트
    gradientTexture = new THREE.TextureLoader().load(backgroundNo);
    scene.background = gradientTexture;

    // 다시 렌더링
    renderer.render(scene, camera);
}
function updateFloorTextures(texturePath) {
    // 새로운 바닥 텍스처를 로드하여 바닥의 텍스처를 업데이트
    const newTexture = new THREE.TextureLoader().load(texturePath, function(texture) {
        texture.wrapS = THREE.RepeatWrapping;
        texture.wrapT = THREE.RepeatWrapping;
        texture.repeat.set(1, 1);
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;

        floor.material.map = texture;
        floor.material.needsUpdate = true;

        // 다시 렌더링
        renderer.render(scene, camera);
    });
}

function updateWallTextures(texturePath) {
    // 새로운 벽 텍스처를 로드하여 벽면의 텍스처를 업데이트
    const newTexture = new THREE.TextureLoader().load(texturePath, function(texture) {
        texture.wrapS = THREE.RepeatWrapping;
        texture.wrapT = THREE.RepeatWrapping;
        texture.repeat.set(1, 1);
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;

        wall1.material.map = texture;
        wall1.material.needsUpdate = true;
        wall2.material.map = texture;
        wall2.material.needsUpdate = true;
        wall3.material.map = texture;
        wall3.material.needsUpdate = true;
        wall4.material.map = texture;
        wall4.material.needsUpdate = true;

        // 다시 렌더링
        renderer.render(scene, camera);
    });
}

animate();





// 가구선택 소품들
const chair = document.querySelector("#chair");
const bed = document.querySelector("#bed");
const table = document.querySelector("#table");
const thing = document.querySelector("#thing");
const wall = document.querySelector("#wall");
const electronic = document.querySelector("#electronic");

// 처음의 의자/소파 선택칸만 보이게 하기
chair.style.display = 'block';
bed.style.display = 'none';
table.style.display = 'none';
thing.style.display = 'none';
wall.style.display = 'none';
electronic.style.display = 'none';

// 가구선택버튼
const addChair = document.querySelector("#add-chair");
const addBed = document.querySelector("#add-bed");
const addTable = document.querySelector("#add-table");
const addThing = document.querySelector("#add-thing");
const addWall = document.querySelector("#add-wall");
const addElectronic = document.querySelector("#add-electronic");

// 의자/소파 버튼 선택 시
addChair.addEventListener("click", () => {
    chair.style.display = 'block';
    bed.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    electronic.style.display = 'none';
});
// 침대 버튼 선택 시
addBed.addEventListener("click", () => {
    chair.style.display = 'none';
    bed.style.display = 'block';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    electronic.style.display = 'none';
});
// 테이블/책상 버튼 선택 시
addTable.addEventListener("click", () => {
    chair.style.display = 'none';
    bed.style.display = 'none';
    table.style.display = 'block';
    thing.style.display = 'none';
    wall.style.display = 'none';
    electronic.style.display = 'none';
});
// 소품 버튼 선택 시
addThing.addEventListener("click", () => {
    chair.style.display = 'none';
    bed.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'block';
    wall.style.display = 'none';
    electronic.style.display = 'none';
});
// 벽걸이 버튼 선택 시
addWall.addEventListener("click", () => {
    chair.style.display = 'none';
    bed.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'block';
    electronic.style.display = 'none';
});
// 가전제품 버튼 선택 시
addElectronic.addEventListener("click", () => {
    chair.style.display = 'none';
    bed.style.display = 'none';
    table.style.display = 'none';
    thing.style.display = 'none';
    wall.style.display = 'none';
    electronic.style.display = 'block';
});




document.addEventListener("DOMContentLoaded", function() {
    let activeTool = "move"; // 초기 값은 move로 설정

    // Move 버튼 활성화 상태에서는 드래그 앤 드롭 가능
    function enableMoveMode() {
        activeTool = "move";
        document.getElementById("moveBtn").style.backgroundColor = "#b3bcc2";
        document.getElementById("rotateBtn").style.backgroundColor = "initial";
        document.getElementById("delBtn").style.backgroundColor = "initial";

        // mainSqare 드래그 앤 드롭 기능 활성화 (mainTd 안에서만 가능)
        const mainSqares = document.querySelectorAll(".mainSqare");
        mainSqares.forEach(sqare => {
            sqare.draggable = true;
            sqare.addEventListener("dragstart", dragStart);
            sqare.addEventListener("dragend", dragEnd);
        });

        // mainTd 요소에 drop 이벤트 리스너 추가 (mainSqare을 받을 수 있음)
        const mainTds = document.getElementsByClassName("mainTd");
        for (const td of mainTds) {
            td.addEventListener("dragover", dragOver);
            td.addEventListener("drop", dropMain);
        }

        // wallSqare 드래그 앤 드롭 기능 활성화 (wallTd 안에서만 가능)
        const wallSqares = document.querySelectorAll(".wallSqare");
        wallSqares.forEach(sqare => {
            sqare.draggable = true;
            sqare.addEventListener("dragstart", dragStart);
            sqare.addEventListener("dragend", dragEnd);
        });

        // wallTd 요소에 drop 이벤트 리스너 추가 (wallSqare을 받을 수 있음)
        const wallTds = document.getElementsByClassName("wallTd");
        for (const td of wallTds) {
            td.addEventListener("dragover", dragOver);
            td.addEventListener("drop", dropWall);
        }
    }

    // mainSqare을 mainTd에 drop할 때 실행되는 함수
    function dropMain(e) {
        e.preventDefault();
        const sqareId = e.dataTransfer.getData("sqareId");
        const sqareName = e.dataTransfer.getData("sqareName");
        if(sqareName == 'mainSqare') {
            const sqare = document.getElementById(sqareId);
            const currentTd = sqare.parentNode;
            const newTd = e.target.tagName.toLowerCase() === "td" ? e.target : e.target.parentNode;
            if (currentTd !== newTd) {
                newTd.appendChild(sqare);
    
                 // 해당 sqare와 매핑된 모델 가져오기
                const model = modelsMap.get(sqareId);
                if (model) {
                    // 모델의 위치 업데이트
                    const rectX = sqare.offsetLeft;
                    const rectY = sqare.offsetTop;
                    const modelPositionX = -4 + (1.3 / 80) * (rectX - 303);
                    const modelPositionY = -4 + (1.3 / 85) * (rectY - 103);
                    model.position.set(modelPositionX, 0, modelPositionY);
                }
            }
        } else {
            alert("일반가구는 벽에 설치할 수 없습니다.");
        }
    }

    // wallSqare을 wallTd에 drop할 때 실행되는 함수
    function dropWall(e) {
        e.preventDefault();
        const sqareId = e.dataTransfer.getData("sqareId");
        const sqareName = e.dataTransfer.getData("sqareName");
        if(sqareName == 'wallSqare') {
            const sqare = document.getElementById(sqareId);
            const currentTd = sqare.parentNode;
            const newTd = e.target.tagName.toLowerCase() === "td" ? e.target : e.target.parentNode;
            if (currentTd !== newTd) {
                newTd.appendChild(sqare);

                if(newTd.classList.contains("wallTd1")) {
                    // 해당 sqare와 매핑된 모델 가져오기
                   const model = modelsMap.get(sqareId);
                   if (model) {
                       // 모델의 위치 업데이트
                       const rectX = sqare.offsetLeft;
                       const rectY = sqare.offsetTop;
                       const modelPositionX = -4 + (1.3 / 80) * (rectX - 303);
                       const modelPositionY = -4 + (1.3 / 80) * (rectY - 103);
                       model.position.set(modelPositionX, 2, modelPositionY);
                       model.rotation.y = 0;
                   }
                } else if(newTd.classList.contains("wallTd2")) {
                    // 해당 sqare와 매핑된 모델 가져오기
                   const model = modelsMap.get(sqareId);
                   if (model) {
                       // 모델의 위치 업데이트
                       const rectX = sqare.offsetLeft;
                       const rectY = sqare.offsetTop;
                       const modelPositionX = -4.8 + (1.3 / 80) * (rectX - 303);
                       const modelPositionY = -4 + (1.3 / 90) * (rectY - 103);
                       model.position.set(modelPositionX, 2, modelPositionY);

                       model.rotation.y = Math.PI/2;
                   }
                } else if(newTd.classList.contains("wallTd3")) {
                    // 해당 sqare와 매핑된 모델 가져오기
                   const model = modelsMap.get(sqareId);
                   if (model) {
                       // 모델의 위치 업데이트
                       const rectX = sqare.offsetLeft;
                       const rectY = sqare.offsetTop;
                       const modelPositionX = -4.8 + (1.3 / 80) * (rectX - 303);
                       const modelPositionY = -4 + (1.3 / 80) * (rectY - 103);
                       model.position.set(modelPositionX, 2, modelPositionY);

                       model.rotation.y = -Math.PI/2;
                    }
                } else if(newTd.classList.contains("wallTd4")) {
                    // 해당 sqare와 매핑된 모델 가져오기
                   const model = modelsMap.get(sqareId);
                   if (model) {
                       // 모델의 위치 업데이트
                       const rectX = sqare.offsetLeft;
                       const rectY = sqare.offsetTop;
                       const modelPositionX = -4 + (1.3 / 80) * (rectX - 303);
                       const modelPositionY = -5.5 + (1.3 / 80) * (rectY - 103);
                       model.position.set(modelPositionX, 2, modelPositionY);

                       model.rotation.y = Math.PI;
                    }
                }
            } else {
                alert("벽걸이 상품은 벽에만 설치해야 합니다.");
            }
        }
    }


    // Rotate 버튼 활성화 상태에서는 sqare를 클릭할 때마다 90도 회전
    function enableRotateMode() {
        activeTool = "rotate";
        document.getElementById("moveBtn").style.backgroundColor = "initial";
        document.getElementById("rotateBtn").style.backgroundColor = "#b3bcc2";
        document.getElementById("delBtn").style.backgroundColor = "initial";

        // sqare를 클릭할 때마다 90도 회전
        const sqares = document.querySelectorAll(".mainSqare");
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
            sqare.addEventListener("click", deleteSquareAndModel); // 수정된 부분
        });
    
        // td 요소에서 drop 이벤트 리스너 제거
        const tds = document.getElementsByTagName("td");
        for (const td of tds) {
            td.removeEventListener("dragover", dragOver);
            td.removeEventListener("drop", drop);
        }
    }
    
    // sqare를 클릭하면 해당 sqare와 연결된 모델을 삭제
    function deleteSquareAndModel(e) {
        const sqare = e.target;
        deleteSquare(e); // 일단 sqare 삭제
    
        // 해당 sqare와 매핑된 모델 가져오기
        const model = modelsMap.get(sqare.id);
        if (model) {
            scene.remove(model); // Three.js scene에서 모델 제거
        }
    }
    
    // sqare를 클릭하면 삭제
    function deleteSquare(e) {
        if (activeTool === "delete") {
            const sqare = e.target;
            sqare.parentNode.removeChild(sqare);
        }
    }
    

    // 초기화 및 변수 설정
    const modelsMap = new Map(); // 모델과 div 요소 간의 매핑을 위한 Map

    // 드래그 시작 이벤트
    function dragStart(e) {
        e.dataTransfer.effectAllowed = "move";
        e.dataTransfer.setData("sqareId", e.target.id);
        e.dataTransfer.setData("sqareName", e.target.getAttribute("name"));
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

    // sqare를 클릭할 때마다 90도 회전
    function rotateSquare(e) {
        if (activeTool === "rotate") {
            const sqare = e.target;
            const transform = sqare.style.transform || "rotate(0deg)";
            const currentRotation = parseInt(transform.match(/-?\d+/)[0]);
            const newRotation = currentRotation - 90;
            sqare.style.transform = `rotate(${newRotation}deg)`;

             // 해당 sqare와 매핑된 모델 가져오기
            const model = modelsMap.get(sqare.id);
            if (model) {
                // 모델도 90도 회전
                model.rotateY(Math.PI / 2); // 90도를 라디안으로 변환하여 회전
            }
        }
    }

    // sqare를 클릭하면 삭제
    function deleteSquare(e) {
        if (activeTool === "delete") {
            const sqare = e.target;
            sqare.parentNode.removeChild(sqare);
        }
    }

    const loader = new THREE.GLTFLoader();
    let rectX = 0;
    let rectY = 0;

   // 이미지 클릭 시 sqare 생성 및 glb 파일 로드
    const images = document.querySelectorAll(".mainImg");
    const td = document.getElementsByClassName("mainTd");
    let sqare = 0;
    let idNo = 0;

    images.forEach((img, index) => {
        img.addEventListener("click", e => {
            
            // 현재 td에 이미 sqare가 존재하는 경우, 추가하지 않음
            for (let i = 0; i < td.length; i++) {
                if (td[i].childNodes.length === 0) {
                    sqare = document.createElement("div");
                    sqare.classList.add("sqare");
                    sqare.classList.add("mainSqare");
                    sqare.setAttribute("name", "mainSqare");
                    sqare.id = idNo; // 각 요소에 고유한 ID 부여
                    const originalString = e.target.getAttribute("src");
                    const lengthWithoutLastFour = originalString.length - 4;
                    const newString = originalString.substring(0,lengthWithoutLastFour);
                    sqare.style.backgroundImage = `url(${newString}-top.png)`; // 배경을 설정
                    sqare.style.backgroundRepeat = 'no-repeat';
                    sqare.style.backgroundSize = 'cover';
                    sqare.style.opacity = '0.8';
                    td[i].appendChild(sqare);
                    sqare.draggable = true;
                    sqare.addEventListener("dragstart", dragStart);
                    sqare.addEventListener("dragend", dragEnd);

                    rectX = sqare.offsetLeft;
                    rectY = sqare.offsetTop;

                    break;
                }
            }

            
            const filePath = img.getAttribute("value");
            if (filePath) {
                loader.load(filePath, function (gltf) {
                    const model = gltf.scene;

                    // Compute dimensions of the loaded model's bounding box
                    const bbox = new THREE.Box3().setFromObject(model);
                    const modelSize = new THREE.Vector3();
                    bbox.getSize(modelSize);

                    // Compute scaling factor to fit within the floor plane
                    const floorSize = new THREE.Vector3(10, 0.3, 10); // Floor plane dimensions
                    let scaleFactor = 0;
                    if(filePath == '/images/partner/interior/modeling/chair/chair-5.glb') {
                        scaleFactor = Math.min(
                            floorSize.x * 3 / modelSize.x,
                            floorSize.y * 3 / modelSize.y,
                            floorSize.z * 3 / modelSize.z
                        );
                    } else if(filePath == '/images/partner/interior/modeling/chair/chair-6.glb') {
                        scaleFactor = Math.min(
                            floorSize.x * 4 / modelSize.x,
                            floorSize.y * 4 / modelSize.y,
                            floorSize.z * 4 / modelSize.z
                        );
                    } else if(filePath == '/images/partner/interior/modeling/chair/chair-7.glb') {
                        scaleFactor = Math.min(
                            floorSize.x * 4 / modelSize.x,
                            floorSize.y * 4 / modelSize.y,
                            floorSize.z * 4 / modelSize.z
                        );
                    } else {
                        scaleFactor = Math.min(
                            floorSize.x * 6 / modelSize.x,
                            floorSize.y * 6 / modelSize.y,
                            floorSize.z * 6 / modelSize.z
                        );
                    }

                    // Apply uniform scaling
                    model.scale.set(scaleFactor, scaleFactor, scaleFactor);

                    let modelPositionX = -4 + (1.3 / 80) * (rectX - 303);
                    let modelPositionY = -4 + (1.3 / 85) * (rectY - 103);

                    model.position.set(modelPositionX, 0, modelPositionY);

                    model.name = idNo;
                    idNo ++;

                    scene.add(model);

                    // 모델과 sqare의 매핑 저장
                    modelsMap.set(sqare.id, model);

                    animate();
                }, undefined, function (error) {
                    console.error(`Failed to load model from ${filePath}:`, error);
                });
            }

        });
    });

    // 이미지 클릭 시 sqare 생성 및 glb 파일 로드
    const wallImages = document.querySelectorAll(".wallImg");
    const wallTd = document.getElementsByClassName("wallTd");
    let wallSqare = 0;

    wallImages.forEach((img, index) => {
        img.addEventListener("click", e => {
            
            // 현재 td에 이미 sqare가 존재하는 경우, 추가하지 않음
            for (let i = 0; i < wallTd.length; i++) {
                if (wallTd[i].childNodes.length === 0) {
                    wallSqare = document.createElement("div");
                    wallSqare.classList.add("sqare");
                    wallSqare.classList.add("wallSqare");
                    wallSqare.setAttribute("name", "wallSqare");
                    wallSqare.setAttribute("address", e.target.getAttribute("src"));
                    wallSqare.id = idNo; // 각 요소에 고유한 ID 부여
                    const originalString = e.target.getAttribute("src");
                    const lengthWithoutLastFour = originalString.length - 4;
                    const newString = originalString.substring(0,lengthWithoutLastFour);
                    wallSqare.style.backgroundImage = `url(${newString}-top.png)`; // 배경을 설정
                    wallSqare.style.backgroundRepeat = 'no-repeat';
                    wallSqare.style.backgroundSize = 'cover';
                    wallSqare.style.opacity = '0.8';
                    wallTd[i].appendChild(wallSqare);
                    wallSqare.draggable = true;
                    wallSqare.addEventListener("dragstart", dragStart);
                    wallSqare.addEventListener("dragend", dragEnd);

                    rectX = wallSqare.offsetLeft;
                    rectY = wallSqare.offsetTop;

                    break;
                }
            }

            
            const filePath = img.getAttribute("value");
            if (filePath) {
                loader.load(filePath, function (gltf) {
                    const model = gltf.scene;

                    // Compute dimensions of the loaded model's bounding box
                    const bbox = new THREE.Box3().setFromObject(model);
                    const modelSize = new THREE.Vector3();
                    bbox.getSize(modelSize);

                    // Compute scaling factor to fit within the floor plane
                    const floorSize = new THREE.Vector3(10, 0.3, 10); // Floor plane dimensions
                    let scaleFactor = 0;
                    if(filePath == '/images/partner/interior/modeling/wall/wall-1.glb') {
                        scaleFactor = Math.min(
                            floorSize.x * 3 / modelSize.x,
                            floorSize.y * 3 / modelSize.y,
                            floorSize.z * 3 / modelSize.z
                        );
                    } else if(filePath == '/images/partner/interior/modeling/wall/wall-2.glb') {
                        scaleFactor = Math.min(
                            floorSize.x * 4 / modelSize.x,
                            floorSize.y * 4 / modelSize.y,
                            floorSize.z * 4 / modelSize.z
                        );
                    } else {
                        scaleFactor = Math.min(
                            floorSize.x * 6 / modelSize.x,
                            floorSize.y * 6 / modelSize.y,
                            floorSize.z * 6 / modelSize.z
                        );
                    }

                    // Apply uniform scaling
                    model.scale.set(scaleFactor, scaleFactor, scaleFactor);

                    let modelPositionX = -4 + (1.3 / 80) * (rectX - 303);
                    let modelPositionY = -4.8 + (1.3 /80) * (rectY - 103);
                    
                    model.position.set(modelPositionX, 2, modelPositionY);
                        
                    model.name = idNo;
                    idNo ++;

                    scene.add(model);

                    // 모델과 sqare의 매핑 저장
                    modelsMap.set(wallSqare.id, model);

                    animate();
                }, undefined, function (error) {
                    console.error(`Failed to load model from ${filePath}:`, error);
                });
            }

        });
    });

    // 초기 설정: Move 버튼 활성화
    enableMoveMode();

    // 버튼 클릭 이벤트 리스너 추가
    document.getElementById("moveBtn").addEventListener("click", enableMoveMode);
    document.getElementById("rotateBtn").addEventListener("click", enableRotateMode);
    document.getElementById("delBtn").addEventListener("click", enableDeleteMode);
});


// 배경색 가져오기
const colorBox = document.querySelectorAll(".colorBox");
let backgroundNo = "";

colorBox.forEach(element => {
    element.addEventListener("click", e => {
        // Remove border from all colorBox elements
        colorBox.forEach(box => {
            box.style.border = "none";
        });

        // Apply border to the clicked colorBox
        e.target.style.border = "1px solid #b3bcc2";

        var no = e.target.attributes[1].value;
        backgroundNo = `/images/partner/interior/modeling/background${no}.JPG`;

        // 로드된 텍스처를 업데이트
        updateTextures();
    });
});

// 바닥 텍스처 바꾸기
const floorBox = document.querySelectorAll(".floorBox");
let floorBackground = "";

floorBox.forEach(element => {
    element.addEventListener("click", e => {
        // Remove border from all floorBox elements
        floorBox.forEach(box => {
            box.style.border = "none";
        });

        // Apply border to the clicked floorBox
        e.target.style.border = "1px solid #b3bcc2";

        var no = e.target.attributes[1].value;
        floorBackground = `/images/partner/interior/modeling/floor${no}.jpg`;

        // 로드된 텍스처를 업데이트
        updateFloorTextures(floorBackground);
    });
});

// 벽 텍스처 바꾸기
const wallBox = document.querySelectorAll(".wallBox");
let wallBackground = "";

wallBox.forEach(element => {
    element.addEventListener("click", e => {
        // Remove border from all wallBox elements
        wallBox.forEach(box => {
            box.style.border = "none";
        });

        // Apply border to the clicked wallBox
        e.target.style.border = "1px solid #b3bcc2";

        var no = e.target.attributes[1].value;
        wallBackground = `/images/partner/interior/modeling/wall${no}.jpg`;

        // 로드된 텍스처를 업데이트
        updateWallTextures(wallBackground);
    });
});



// open 버튼 클릭 이벤트 핸들러
const openButton = document.getElementById('open');
let isBig = false;

openButton.addEventListener('click', function() {
    if (!isBig) {
        // viewer를 확대하여 화면 가운데로 이동
        viewer.style.transform = 'scale(2)'; // 원하는 스케일로 변경
        openButton.style.width = '200%';
        viewer.style.transition = 'transform 0.5s ease'; // transition 효과 추가
        viewer.style.position = 'absolute';
        viewer.style.top = '50%';
        viewer.style.left = '50%';
        viewer.style.transform = 'translate(-1050px, -450px)';
        
        // Three.js 카메라 및 렌더러 크기 조정
        camera.aspect = 1; // viewer 크기에 맞추어 aspect 비율 조정
        camera.updateProjectionMatrix();
        renderer.setSize(800, 800); // viewer 크기에 맞추어 renderer 크기 조정

        // Three.js 애니메이션 재시작
        animate();

        isBig = true;
    } else {
        // viewer를 원래 상태로 되돌림
        viewer.style.transform = 'scale(1)'; // 원래 크기로 변경
        openButton.style.width = '100%';
        viewer.style.transition = 'transform 0.5s ease'; // transition 효과 추가
        viewer.style.position = 'initial';
        viewer.style.top = 'initial';
        viewer.style.left = 'initial';

        // Three.js 카메라 및 렌더러 크기 원래대로 되돌리기
        camera.aspect = 1; // 원래 aspect 비율로 복구
        camera.updateProjectionMatrix();
        renderer.setSize(400, 400); // 원래 renderer 크기로 복구

        isBig = false;
    }
});

// JavaScript 코드



document.addEventListener("DOMContentLoaded", () => {
    const estimateButton = document.getElementById("estimateButton");

    function saveAs(url, fileName) {
        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    function makeDivToImageFile(captureDiv) {
        return html2canvas(captureDiv, {
            allowTaint: true,
            useCORS: true,
            width: captureDiv.offsetWidth,
            height: captureDiv.offsetHeight,
            scale: 1
        }).then(function (canvas) {
            var imageURL = canvas.toDataURL('image/jpeg');
            imageURL = imageURL.replace("data:image/jpeg;base64,", "");
            return imageURL;
        });
    }

    function saveScreenshot() {
        renderer.render(scene, camera);
        var dataURL = canvas.toDataURL('image/png');
        dataURL = dataURL.replace("data:image/png;base64,", "");
        return dataURL;
    }

    async function handleEstimate(event) {
        event.preventDefault();  // 기본 폼 제출 방지

        const captureDiv = document.getElementById('floor-table');
        const floorPlanImage = await makeDivToImageFile(captureDiv);
        const threedModelingImage = await saveScreenshot();

        // 숨겨진 입력 필드 생성 및 추가
        const floorPlanInput = document.createElement("input");
        floorPlanInput.setAttribute('name', 'floorPlan');
        floorPlanInput.value = floorPlanImage;
        floorPlanInput.type = 'hidden';

        const threedModelingInput = document.createElement("input");
        threedModelingInput.setAttribute('name', 'ThreedModeling');
        threedModelingInput.value = threedModelingImage;
        threedModelingInput.type = 'hidden';

        // 폼에 추가
        estimateButton.appendChild(floorPlanInput);
        estimateButton.appendChild(threedModelingInput);

        // 폼 직접 제출
        estimateButton.submit();
    }

    // 예상견적 조회 버튼 클릭 시 핸들러 호출
    document.getElementById('next').addEventListener('click', handleEstimate);
});

const screenShot = document.querySelector("#screenShot");

screenShot.addEventListener("click", () => {

    const captureDiv = document.getElementById('floor-table');
    
    // 버튼 등을 이용해서 적당한 때 함수를 호출한다.
    makeDivToImageFile(captureDiv);
    
    function saveAs(url, fileName) {
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
    
     
    
    function makeDivToImageFile(captureDiv) {
      html2canvas(captureDiv, {
        allowTaint: true,
        useCORS: true,
        /* 아래 3 속성이 canvas의 크기를 정해준다. */
        width: captureDiv.offsetWidth,
        height: captureDiv.offsetHeight,
        scale: 1
    
      }).then(function (canvas) {
        var imageURL = canvas.toDataURL('image/jpeg');
        console.log(imageURL);
        saveAs(imageURL, 'Floor Plan.jpg');
        estimateButton.append(input);
      }).catch(function (err) {
        console.log(err);
      });
    }


    renderer.render(scene, camera);
        var dataURL = canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = dataURL;
        link.download = '3D Modeling.png';
        link.click();

        dataURL = dataURL.replace("data:image/png;base64,", "");
        
        const input = document.createElement("input");
        input.setAttribute('name', 'ThreedModeling')
        input.value = dataURL;
        input.type = 'hidden';


        estimateButton.append(input);
})
