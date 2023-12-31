import * as THREE from "three";
import { GLTFLoader } from "three/addons/loaders/GLTFLoader.js";

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  3000,
  window.innerWidth / window.innerHeight,
  0.1,
  1000,
);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);

document.body.appendChild(renderer.domElement);

const geometry = new THREE.BoxGeometry(1, 1, 1);
const material = new THREE.MeshBasicMaterial({ color: 0x00ffff });
const cube = new THREE.Mesh(geometry, material);
scene.add(cube);

let ambient = new THREE.AmbientLight(0xaf7ede, 0.8);

scene.add(ambient);

camera.position.z = 5;

const loader = new GLTFLoader();
let aaa;

loader.load(
  "./zelda.glb",
  function (gltf) {
    aaa = gltf.scene;
    scene.add(aaa);

    animate();
  },
  undefined,
  function (error) {
    console.error(error);
  },
);

function animate() {
  requestAnimationFrame(animate);

  cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;

  aaa.rotation.x += 0.01;
  aaa.rotation.y += 0.01;

  renderer.render(scene, camera);
}
