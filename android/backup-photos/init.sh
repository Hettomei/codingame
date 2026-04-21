set -eu

set -x
powershell.exe 'usbipd list'
set +x

read -e -r -p "taper le busiId sous la forme <x-y> : " -i "2-" busid

set -x
powershell.exe "usbipd attach --wsl --busid $busid"
set +x

echo "WAIT 5 sec"
sleep 5

adb kill-server || echo "no adb server"
sleep 1

adb devices
