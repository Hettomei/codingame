import * as THREE from "three";
import Stats from "three/addons/libs/stats.module.js";

import { setControls } from "./my_controls";
import { set_renderer } from "./renderer";
import { build_camera_ortho } from "./camera";
// import { material, update_time } from "./my_materials/red_sin";

performance.mark("start");

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
  build_nonograme(scene, 20, 20);
}

function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
  // update_time();
  stats.update();
}

const renderer = set_renderer();
const scene = new THREE.Scene();
const camera = build_camera_ortho(scene);

const stats = new Stats();
document.body.appendChild(stats.dom);

setControls(camera, renderer, scene);

build_scene(scene);

animate();

performance.mark("end");

console.log(performance.measure("Measurement", "start", "end"));
