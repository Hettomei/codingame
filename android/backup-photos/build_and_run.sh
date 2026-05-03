set -e

./build.sh


APK=app/build/outputs/apk/debug/app-debug.apk
adb install -r $APK
echo "✓ APK installé sur le device"

adb shell am start -n com.equipothee.helloworld/.MainActivity

# allumer et deverouiller l ecran
adb shell input keyevent KEYCODE_WAKEUP
adb shell input keyevent KEYCODE_MENU
