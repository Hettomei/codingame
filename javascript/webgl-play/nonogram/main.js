import * as THREE from "three";
import { ArcballControls } from "three/addons/controls/ArcballControls.js";
import Stats from "three/addons/libs/stats.module.js";

import { set_renderer } from "./renderer";
import { build_camera_ortho } from "./camera";

let stats;
performance.mark("start");
function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
  // material.uniforms.u_time.value = clock.getElapsedTime();
  stats.update();
}

function build_nonograme(scene, number_cases_x, number_cases_y) {
  const cube_size = 10;
  const espace = 2;
  const init_x = (number_cases_x * cube_size + espace * number_cases_x) / 2;
  const init_y = (number_cases_y * cube_size + espace * number_cases_y) / 2;

  const material = new THREE.MeshBasicMaterial({ color: 0xffffff });
  const geometry = new THREE.BoxGeometry(cube_size, cube_size, cube_size);
  let cube;

  for (let j = 0; j < number_cases_y; j++) {
    for (let i = 0; i < number_cases_x; i++) {
      cube = new THREE.Mesh(geometry, material);
      cube.position.x = i * cube_size + espace * i - init_x;
      cube.position.y = j * cube_size + espace * j - init_y;
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

stats = new Stats();
document.body.appendChild(stats.dom);

const controls = new ArcballControls(camera, renderer.domElement, scene);
console.log(controls.dampingFactor);
controls.dampingFactor = 1000;
controls.enableRotate = false;
controls.setGizmosVisible(false);
controls.minZoom = 1.5;
controls.maxZoom = 9.0;

controls.addEventListener("change", function () {
  renderer.render(scene, camera);
  // console.log(camera.position);
});
controls.update();

build_scene(scene);

animate();
performance.mark("end");
console.log(performance.measure("Measurement", "start", "end"));
