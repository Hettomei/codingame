set -e

./build.sh
adb install -r build/app-signed.apk
adb shell am start -n com.equipothee.helloworld/.MainActivity
