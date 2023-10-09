import * as THREE from "three";

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  1000,
);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

const geometry = new THREE.BoxGeometry(15, 15, 15);
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
const cube = new THREE.Line(geometry, material);
scene.add(cube);

camera.position.set(0, 0, 100);
camera.lookAt(0, 0, 0);

const line_material = new THREE.LineBasicMaterial({ color: 0xffffff });

const points = [
  new THREE.Vector3(-15, 0, 0),
  new THREE.Vector3(0, 15, 0),
  new THREE.Vector3(15, 0, 0),
  new THREE.Vector3(15, 0, 20),
];

const buffer_geometry = new THREE.BufferGeometry().setFromPoints(points);

const line = new THREE.Line(buffer_geometry, line_material);

scene.add(line);

function animate() {
  requestAnimationFrame(animate);

  // cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;

  // line.rotation.x += 0.01;
  line.rotation.y += 0.05;

  renderer.render(scene, camera);
}

animate();
