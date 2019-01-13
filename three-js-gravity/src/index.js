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

function createSphereStruct(angle) {
  const angleRad = THREE.Math.degToRad(angle);

  return {
    originX: -550,
    originZ: -300,
    lifeTime: 1700, // ms
    angle,
    vi: 1, // vitesse initiale
    cosAngle: Math.cos(angleRad),
    sinAngle: Math.sin(angleRad),
  };
}

let sphereStruct = createSphereStruct(60);

const G = 9 * 1 / 10000;
const g = -1 / 2 * G;


const hgeometry = new THREE.SphereGeometry(10, 10, 10, 200);
const hmaterial = new THREE.MeshBasicMaterial({ color: 0x005fff });
const home = new THREE.Mesh(hgeometry, hmaterial);
home.position.set(sphereStruct.originX, 0, sphereStruct.originZ);
scene.add(home);

const geometry = new THREE.SphereGeometry(4, 4, 4, 200);
const material = new THREE.MeshBasicMaterial({ color: 0xffff00 });
let projectile = new THREE.Mesh(geometry, material);
projectile.position.set(sphereStruct.originX, 0, sphereStruct.originZ);
scene.add(projectile);

let d1 = Date.now();

function getRandomInclusive(min, max) {
  return Math.random() * (max - min + 1) + min;
}

function animate() {
  requestAnimationFrame(animate);

  const t = Date.now() - d1;
  const x = sphereStruct.vi * sphereStruct.cosAngle * t + sphereStruct.originX;
  const z = g * t * t + sphereStruct.vi * sphereStruct.sinAngle * t + sphereStruct.originZ;

  projectile.position.set(x, 0, z);
  renderer.render(scene, camera);

  if (projectile.position.z < sphereStruct.originZ) {
    d1 = Date.now();
    projectile = projectile.clone();
    projectile.position.set(sphereStruct.originX, 0, sphereStruct.originZ);
    const angle = getRandomInclusive(10, 80);
    console.log(angle);
    sphereStruct = createSphereStruct(angle);
    scene.add(projectile);
  }
}

animate();
