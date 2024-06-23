// Scene setup
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

camera.position.set(0, 10, 20);
camera.lookAt(0, 0, 0);

const controls = new THREE.OrbitControls(camera, canvas);
controls.target.set(0, 0, 0);

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

// Camera positioning
camera.position.z = 5;

// Animation loop
function animate() {
    requestAnimationFrame(animate);
    renderer.render(scene, camera);
}