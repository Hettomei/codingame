import * as THREE from "three";

const clock = new THREE.Clock();

const uniforms = {
  colorA: { type: "vec3", value: new THREE.Color(0xffffff) },
  colorB: { type: "vec3", value: new THREE.Color(0xff0000) },
  u_time: { type: "f", value: 0.5 },
};

const material = new THREE.ShaderMaterial({
  uniforms,
  vertexShader: vertexShader(),
  fragmentShader: fragmentShader(),
});

function vertexShader() {
  return `
    varying vec3 vUv;

    void main() {
      vUv = position;

      vec4 modelViewPosition = modelViewMatrix * vec4(position.x, position.y, position.z, 1.);
      gl_Position = projectionMatrix * modelViewPosition ;
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
        gl_FragColor = vec4(mix(colorA, colorB, sin(vUv.y * vUv.x * sin(u_time)) ), 1);
      }
  `;
}

function update_time() {
  material.uniforms.u_time.value = clock.getElapsedTime();
}

export { material, update_time };
