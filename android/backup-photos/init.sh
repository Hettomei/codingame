set -eu

powershell.exe 'usbipd list'

read -r -p "taper le busiId sous la forme 2-8" busid

powershell.exe "usbipd attach --wsl --busid $busid"
