set -eux

echo "If it automatically close, restart this script"
sleep 3

pacman -Syu
pacman -S base-devel gcc vim cmake git
pacman -S --needed mingw-w64-x86_64-toolchain
pacman -S mingw64/mingw-w64-x86_64-cmake
pacman -S clang # to have clang-format
g++  --version
