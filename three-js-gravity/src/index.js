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

// function createLine() {
//   const materialLine = new THREE.LineBasicMaterial({ color: 0x0000ff });
//   const geometry = new THREE.Geometry();
//   geometry.vertices.push(new THREE.Vector3(-20, 0, 0));
//   geometry.vertices.push(new THREE.Vector3(0, 20, 0));
//   geometry.vertices.push(new THREE.Vector3(20, 0, 0));
//   return new THREE.Line(geometry, materialLine);
// }

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

camera.position.set(0, -100, 0);
camera.lookAt(0, 0, 0);

const G = 9 * 1 / 10000;
const g = -1 / 2 * G;

const masterSphere = createSphereStruct(60);

const hgeometry = new THREE.SphereGeometry(10, 10, 10, 200);
const hmaterial = new THREE.MeshBasicMaterial({ color: 0x005fff });
const home = new THREE.Mesh(hgeometry, hmaterial);
home.position.set(masterSphere.originX, 0, masterSphere.originZ);
scene.add(home);

const geometry = new THREE.SphereGeometry(4, 4, 4, 200);
const material = new THREE.MeshBasicMaterial({ color: 0xffff00 });
const masterProjectile = new THREE.Mesh(geometry, material);
masterProjectile.position.set(masterSphere.originX, 0, masterSphere.originZ);

let throwProjectiles = [];

function getRandomInclusive(min, max) {
  return Math.random() * (max - min + 1) + min;
}

function generateI() {
  let i = 0;
  let direction = 1; // 1 up

  function inc() {
    if (direction) {
      i += 1;
      if (i > 90) {
        direction = 0;
      }
      return;
    }

    i -= 1;
    if (i < 40) {
      direction = 1;
    }
  }

  function getAngle() {
    const a = i % 105;
    return [a, a + 4];
  }

  return {
    inc,
    getAngle,
  };
}

const genI = generateI();

function createProjectile() {
  const d1 = Date.now();
  const projectile = masterProjectile.clone();
  projectile.position.set(masterSphere.originX, 0, masterSphere.originZ);

  const angle = getRandomInclusive(...genI.getAngle());
  const sphereStruct = createSphereStruct(angle);

  throwProjectiles.push([projectile, sphereStruct, d1]);
  scene.add(projectile);
}

function moveProjectile([projectile, sphereStruct, d1]) {
  const t = Date.now() - d1;
  const x = sphereStruct.vi * sphereStruct.cosAngle * t + sphereStruct.originX;
  const z = g * t * t + sphereStruct.vi * sphereStruct.sinAngle * t + sphereStruct.originZ;
  projectile.position.set(x, 0, z);
}

function removeOldProjectile([projectile, sphereStruct]) {
  const a = projectile.position.z > sphereStruct.originZ - 100;
  if (!a) {
    scene.remove(projectile);
  }
  return a;
}

function animate() {
  requestAnimationFrame(animate);

  throwProjectiles.forEach(moveProjectile);

  renderer.render(scene, camera);

  throwProjectiles = throwProjectiles.filter(removeOldProjectile);

  genI.inc();
  createProjectile();
}

animate();
