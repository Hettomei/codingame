set -e

./build.sh
adb install -r build/app-signed.apk
adb shell am start -n com.equipothee.helloworld/.MainActivity

# allumer et deverouiller l ecran
adb shell input keyevent KEYCODE_WAKEUP
adb shell input keyevent KEYCODE_MENU
