set -eu

powershell.exe 'usbipd list'

read -r -p "taper le busiId sous la forme '2-8' => " busid

powershell.exe "usbipd attach --wsl --busid $busid"

echo "WAIT 5 sec"
sleep 5

adb kill-server || echo "no adb server"
sleep 1

adb devices
