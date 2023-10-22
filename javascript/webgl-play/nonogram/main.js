import * as THREE from "three";
import { ArcballControls } from "three/addons/controls/ArcballControls.js";

import { set_renderer } from "./renderer";
import { build_camera_ortho } from "./camera";

function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
  // material.uniforms.u_time.value = clock.getElapsedTime();
}

function create_cube(size) {
  const geometry = new THREE.BoxGeometry(size, size, size);
  const material = new THREE.MeshBasicMaterial({ color: 0xffffff });
  return new THREE.Mesh(geometry, material);
}

function build_nonograme(scene, number_cases_x, number_cases_y) {
  const cube_size = 10;
  const espace = 2;
  for (let j = 0; j < number_cases_y; j++) {
    for (let i = 0; i < number_cases_x; i++) {
      const cube = create_cube(cube_size);
      cube.position.x = i * cube_size + espace * i;
      cube.position.y = j * cube_size + espace * j;
      scene.add(cube);
    }
  }
}
function build_scene(scene) {
  build_nonograme(scene, 30, 30);
}

// const clock = new THREE.Clock();
const renderer = set_renderer();
const scene = new THREE.Scene();
const camera = build_camera_ortho(scene);

const controls = new ArcballControls(camera, renderer.domElement, scene);
console.log(controls.dampingFactor);
controls.dampingFactor = 1000;
controls.enableRotate = false;
controls.setGizmosVisible(false);
// controls.maxZoom = false;
// controls.minZoom = false;

controls.addEventListener("change", function () {
  renderer.render(scene, camera);
  console.log(controls.zoom);
});
controls.update();

build_scene(scene);

animate();
