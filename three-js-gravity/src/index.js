const THREE = require('three');
require('./OrbitControls');

const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

camera.position.set(0, 0, 100);
camera.lookAt(0, 0, 0);

renderer.shadowMap.enabled = true;
renderer.shadowMap.type = THREE.PCFSoftShadowMap;
renderer.gammaInput = true;
renderer.gammaOutput = true;

function createCube() {
  const geometryBox = new THREE.BoxGeometry(20, 20, 20);
  const materialMesh = new THREE.MeshLambertMaterial({ color: 0x0ff00f });
  return new THREE.Mesh(geometryBox, materialMesh);
}

function createLine() {
  const materialLine = new THREE.LineBasicMaterial({ color: 0x0000ff });
  const geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(-20, 0, 0));
  geometry.vertices.push(new THREE.Vector3(0, 20, 0));
  geometry.vertices.push(new THREE.Vector3(20, 0, 0));
  return new THREE.Line(geometry, materialLine);
}

function createSphere() {
  const geometry = new THREE.SphereGeometry(20, 20, 20, 20);
  const material = new THREE.MeshLambertMaterial({ color: 0xffff00 });
  return new THREE.Mesh(geometry, material);
}

const cube = createCube();
const line = createLine();
const sphere = createSphere();

sphere.position.set(-70, 0, 0);

// var directionalLight = new THREE.DirectionalLight( 0xffffff, 0 );
// scene.add( directionalLight );
const controls = new THREE.OrbitControls(camera);


const scene = new THREE.Scene();
const ambient = new THREE.AmbientLight(0xffffff, 0.2);
scene.add(ambient);
const spotLight = new THREE.SpotLight(0xffffff, 1);
spotLight.position.set(15, 40, 35);
spotLight.angle = Math.PI / 4;
spotLight.penumbra = 0.05;
spotLight.decay = 2;
spotLight.distance = 200;
spotLight.castShadow = true;
spotLight.shadow.mapSize.width = 1024;
spotLight.shadow.mapSize.height = 1024;
spotLight.shadow.camera.near = 10;
spotLight.shadow.camera.far = 200;
scene.add(spotLight);

const lightHelper = new THREE.SpotLightHelper(spotLight);
scene.add(lightHelper);
const shadowCameraHelper = new THREE.CameraHelper(spotLight.shadow.camera);
scene.add(shadowCameraHelper);


scene.add(cube);
scene.add(line);
scene.add(sphere);

const geometry = new THREE.PlaneGeometry(100, 20, 3);
const material = new THREE.MeshBasicMaterial({ color: 0x0e0e0e, side: THREE.DoubleSide });
const plane = new THREE.Mesh(geometry, material);
scene.add(plane);

function animate() {
  requestAnimationFrame(animate);
  controls.update();

  cube.rotation.x += 0.005;
  cube.rotation.y += 0.005;

  line.rotation.x += 0.05;
  line.rotation.y += 0.001;

  const orbitRadius = 100; // for example
  const date = Date.now() * 0.001;

  sphere.position.set(
    Math.cos(date) * orbitRadius,
    0,
    Math.sin(date) * orbitRadius,
  );

  renderer.render(scene, camera);
}

animate();
