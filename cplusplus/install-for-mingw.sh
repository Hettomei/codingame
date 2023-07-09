set -eux

echo "If it automatically close, restart this script"
sleep 3

pacman -Syu
pacman -S --noconfirm base-devel gcc vim cmake git
pacman -S --noconfirm --needed mingw-w64-ucrt-x86_64-toolchain
pacman -S --noconfirm mingw64/mingw-w64-x86_64-cmake
# pacman -S --noconfirm mingw64/mingw-w64-ucrt-x86_64-cmake # inexistant
pacman -S --noconfirm clang # to have clang-format

echo "Test de G++ :"
g++  --version
