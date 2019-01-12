const THREE = require('three');

const SCREEN_WIDTH = window.innerWidth;
const SCREEN_HEIGHT = window.innerHeight;

const renderer = new THREE.WebGLRenderer();
renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
document.body.appendChild(renderer.domElement);

const scene = new THREE.Scene();

const camera = new THREE.OrthographicCamera(
  SCREEN_WIDTH / -2,
  SCREEN_WIDTH / 2,
  SCREEN_HEIGHT / 2,
  SCREEN_HEIGHT / -2,
  1,
  1000,
);

function createLine() {
  const materialLine = new THREE.LineBasicMaterial({ color: 0x0000ff });
  const geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(-20, 0, 0));
  geometry.vertices.push(new THREE.Vector3(0, 20, 0));
  geometry.vertices.push(new THREE.Vector3(20, 0, 0));
  return new THREE.Line(geometry, materialLine);
}

camera.position.set(0, -100, 0);
camera.lookAt(0, 0, 0);

const sphereStruct = {
  originX: -550,
  originZ: -300,
  lifeTime: 1700, // ms
  angle: THREE.Math.degToRad(45),
  vi: 1, // vitesse initiale
};
sphereStruct.cosAngle = Math.cos(sphereStruct.angle);
sphereStruct.sinAngle = Math.sin(sphereStruct.angle);

const G = 9 * 1 / 10000;
const g = -1 / 2 * G;


const geometry = new THREE.SphereGeometry(0, 0, 0, 200);
const material = new THREE.MeshBasicMaterial({ color: 0xffff00 });
const sphere = new THREE.Mesh(geometry, material);
sphere.position.set(sphereStruct.originX, 0, sphereStruct.originZ);
scene.add(sphere);

let d1 = Date.now();

function animate() {
  let t = Date.now() - d1;

  if (t > sphereStruct.lifeTime) {
    t = 0;
    d1 = Date.now();
  }

  requestAnimationFrame(animate);

  const x = sphereStruct.vi * sphereStruct.cosAngle * t + sphereStruct.originX;
  const z = g * t * t + sphereStruct.vi * sphereStruct.sinAngle * t + sphereStruct.originZ;

  sphere.position.set(x, 0, z);
  renderer.render(scene, camera);
}

animate();
