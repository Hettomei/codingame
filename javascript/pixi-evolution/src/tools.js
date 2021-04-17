const DEBUG = true;

export function debug(...a) {
  DEBUG && console.log("debug", ...a);
}
