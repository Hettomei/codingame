const THREE = require('three');

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

camera.position.set(0, 0, 100);
camera.lookAt(0, 0, 0);

function createCube() {
  const geometryBox = new THREE.BoxGeometry(5, 5, 5);
  const materialMesh = new THREE.MeshBasicMaterial({ color: 0x0ffff0 });
  return new THREE.Mesh(geometryBox, materialMesh);
}

function createLine() {
  const materialLine = new THREE.LineBasicMaterial({ color: 0x0000ff });
  const geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(-10, 0, 0));
  geometry.vertices.push(new THREE.Vector3(0, 10, 0));
  geometry.vertices.push(new THREE.Vector3(10, 0, 0));
  return new THREE.Line(geometry, materialLine);
}

const cube = createCube();
const line = createLine();

scene.add(cube);
scene.add(line);

function animate() {
  requestAnimationFrame(animate);
  cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;

  line.rotation.x += 0.1;
  line.rotation.y += 0.001;
  renderer.render(scene, camera);
}

animate();
