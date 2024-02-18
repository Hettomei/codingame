import { ArcballControls } from "three/addons/controls/ArcballControls.js";

function setControls(camera, renderer, scene) {
  const controls = new ArcballControls(camera, renderer.domElement, scene);
  controls.enableRotate = false;
  controls.setGizmosVisible(false);
  // controls.minZoom = 1.5;
  // controls.maxZoom = 9.0;

  controls.addEventListener("change", function () {
    renderer.render(scene, camera);
    // console.log(camera.position);
  });
  controls.update();
}

export { setControls };
