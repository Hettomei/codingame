import * as THREE from "three";
import { ArcballControls } from "three/addons/controls/ArcballControls.js";

import { set_renderer } from "./renderer";
import { build_camera } from "./camera";

let material;

function vertexShader() {
  return `
    varying vec3 vUv;

    void main() {
      vUv = position;

      vec4 modelViewPosition = modelViewMatrix * vec4(position.x, position.y, position.z, 1.0);
      gl_Position = projectionMatrix * modelViewPosition;
    }
  `;
}
function fragmentShader() {
  return `
      uniform vec3 colorA;
      uniform vec3 colorB;
      uniform float u_time;
      varying vec3 vUv;

      void main() {
        gl_FragColor = vec4(mix(colorA, colorB, sin(vUv.x + u_time * 10.) ), 1.0);
      }
  `;
}

function addExperimentalCube() {
  let uniforms = {
    colorB: { type: "vec3", value: new THREE.Color(0x00ff00) },
    colorA: { type: "vec3", value: new THREE.Color(0xffffff) },
    u_time: { type: "f", value: 0.5 },
  };

  let geometry = new THREE.BoxGeometry(25, 25, 25);
  let _material = new THREE.ShaderMaterial({
    uniforms,
    fragmentShader: fragmentShader(),
    vertexShader: vertexShader(),
  });

  let mesh = new THREE.Mesh(geometry, _material);
  mesh.position.x = 0;
  scene.add(mesh);
  return _material;
}

function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
  material.uniforms.u_time.value = clock.getElapsedTime();
}

const clock = new THREE.Clock();
const renderer = set_renderer();
const scene = new THREE.Scene();
const camera = build_camera();

const controls = new ArcballControls(camera, renderer.domElement, scene);

controls.addEventListener("change", function () {
  renderer.render(scene, camera);
});
controls.update();

material = addExperimentalCube();
animate();
