set -eu

current_path=$(pwd)
cd $(mktemp -d)

echo "Installation de SDL"

pwd

curl -OL https://github.com/libsdl-org/SDL/releases/download/release-2.24.0/SDL2-devel-2.24.0-mingw.tar.gz

ls -al
tar -xzf SDL2-devel-2.24.0-mingw.tar.gz
cp -vr SDL2-2.24.0/x86_64-w64-mingw32 $current_path


echo
echo
echo "Installation de SDL ttif"
echo


curl -OL 'https://github.com/libsdl-org/SDL_ttf/releases/download/release-2.20.1/SDL2_ttf-devel-2.20.1-mingw.tar.gz'
ls -al
tar -xzf SDL2_ttf-devel-2.20.1-mingw.tar.gz
ls -al
cd SDL2_ttf-2.20.1/x86_64-w64-mingw32
ls -al
cp -v bin/* $current_path/x86_64-w64-mingw32/bin
cp -v include/SDL2/* $current_path/x86_64-w64-mingw32/include/SDL2
cp -v lib/libSDL2* $current_path/x86_64-w64-mingw32/lib
