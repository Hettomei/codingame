const DEBUG = false;

export function debug(...a) {
  DEBUG && console.log("debug", ...a);
}
