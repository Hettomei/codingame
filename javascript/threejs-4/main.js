import * as THREE from "three";

import { set_renderer } from "./renderer";
import { build_camera } from "./camera";

const renderer = set_renderer();
const scene = new THREE.Scene();
const camera = build_camera();

// function create_cube(x, y, z) {
//   const geometry = new THREE.BoxGeometry(x, y, z);
//   const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
//   return new THREE.Line(geometry, material);
// }

function create_line(vectors) {
  const points = vectors.map(([x, y, z]) => new THREE.Vector3(x, y, z));
  const buffer_geometry = new THREE.BufferGeometry().setFromPoints(points);
  const line_material = new THREE.LineBasicMaterial({ color: 0xffffff });
  return new THREE.Line(buffer_geometry, line_material);
}

function build_scene() {
  const line_x = create_line([
    [-300, 0, 0],
    [300, 0, 0],
  ]);
  const line_y = create_line([
    [0, -300, 0],
    [0, 300, 0],
  ]);
  scene.add(line_x);
  scene.add(line_y);

  const vertices_sin = [];
  const vertices_cos = [];

  for (let i = -50; i < 50; i += 0.01) {
    vertices_sin.push(i * 10, Math.sin(i) * 20, 0);
    vertices_cos.push(i * 10, Math.cos(i) * 20, 0);
  }

  const geometry = new THREE.BufferGeometry();
  geometry.setAttribute(
    "position",
    new THREE.Float32BufferAttribute(vertices_sin.concat(vertices_cos), 3),
  );
  const material = new THREE.PointsMaterial({ color: 0xff00ff });
  const points = new THREE.Points(geometry, material);
  scene.add(points);
}

function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
}
build_scene();
animate();
