import * as THREE from "three";

import { set_renderer } from "./renderer";
import { build_camera } from "./camera";

const renderer = set_renderer();
const scene = new THREE.Scene();
const camera = build_camera();
let cube;
let line;
let line22;

function create_cube(x, y, z) {
  const geometry = new THREE.BoxGeometry(x, y, z);
  const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
  return new THREE.Line(geometry, material);
}

function create_line(vectors) {
  const points = vectors.map(([x, y, z]) => new THREE.Vector3(x, y, z));
  const buffer_geometry = new THREE.BufferGeometry().setFromPoints(points);
  const line_material = new THREE.LineBasicMaterial({ color: 0xffffff });
  return new THREE.Line(buffer_geometry, line_material);
}

function build_scene() {
  cube = create_cube(5, 5, 5);
  scene.add(create_cube(15, 15, 15));
  scene.add(cube);

  line = create_line([
    [-15, 0, 0],
    [0, 15, 0],
    [15, 0, 0],
    [0, 0, 15],
    [-15, 0, 0],
  ]);
  scene.add(line);

  const line2 = [];

  for (let i = 0; i < 100; i++) {
    line2.push([Math.sin(i) * 20, Math.cos(i) * 20, i / 10]);
  }
  line22 = create_line(line2);
  scene.add(line22);
}

function animate() {
  requestAnimationFrame(animate);
  // cube.position.x += 0.03;
  cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;

  // line.rotation.x += 0.002;
  // line.rotation.y += 0.02;
  // line22.rotation.x += 0.01;
  line22.rotation.y += 0.01;

  renderer.render(scene, camera);
}
build_scene();
animate();
