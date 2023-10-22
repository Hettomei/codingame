import * as THREE from "three";

export function build_camera_pers() {
  const camera = new THREE.PerspectiveCamera(
    75,
    window.innerWidth / window.innerHeight,
    0.1,
    1000,
  );
  camera.position.set(0, 0, 100);
  camera.lookAt(0, 0, 0);
  return camera;
}

export function build_camera_ortho() {
  const width = window.innerWidth;
  const height = window.innerHeight;
  const camera = new THREE.OrthographicCamera(
    width / -2,
    width / 2,
    height / 2,
    height / -2,
    1,
    1000,
  );
  camera.position.set(0, 0, 500);
  camera.zoom = 3;
  return camera;
}
