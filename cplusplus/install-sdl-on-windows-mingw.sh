set -eu

current_path=$(pwd)


function  install_sdl {
cd $(mktemp -d)

echo "Installation de SDL"
pwd
echo

curl -OL https://github.com/libsdl-org/SDL/releases/download/release-2.24.0/SDL2-devel-2.24.0-mingw.tar.gz

ls -al
tar -xzf SDL2-devel-2.24.0-mingw.tar.gz
cp -r SDL2-2.24.0/x86_64-w64-mingw32 $current_path
}

function install_sdl_ttf {
  cd $(mktemp -d)

  echo "Installation de SDL ttif"
  pwd
  echo
  
  curl -OL 'https://github.com/libsdl-org/SDL_ttf/releases/download/release-2.20.1/SDL2_ttf-devel-2.20.1-mingw.tar.gz'
  tar -xzf SDL2_ttf-devel-2.20.1-mingw.tar.gz
  cd SDL2_ttf-2.20.1/x86_64-w64-mingw32
  ls -al
  cp bin/* $current_path/x86_64-w64-mingw32/bin
  cp include/SDL2/* $current_path/x86_64-w64-mingw32/include/SDL2
  cp lib/libSDL2* $current_path/x86_64-w64-mingw32/lib
}

function install_sdl_image {
  cd $(mktemp -d)

  echo "Installation de SDL image"
  pwd
  echo
  
  curl -OL 'https://github.com/libsdl-org/SDL_image/releases/download/release-2.6.2/SDL2_image-devel-2.6.2-mingw.tar.gz'
  tar -xzf 'SDL2_image-devel-2.6.2-mingw.tar.gz'

  cd SDL2_image-2.6.2/x86_64-w64-mingw32

  ls -al
  cp bin/* $current_path/x86_64-w64-mingw32/bin
  cp include/SDL2/* $current_path/x86_64-w64-mingw32/include/SDL2
  cp lib/libSDL2* $current_path/x86_64-w64-mingw32/lib
}

function install_sdl_gfx {
cd $(mktemp -d)

echo "installation de sdl gfx"
pwd

curl -OL 'http://www.ferzkopp.net/Software/SDL2_gfx/SDL2_gfx-1.0.4.tar.gz'
ls -al
tar -xzf SDL2_gfx-1.0.4.tar.gz
cd SDL2_gfx-1.0.4
./autogen.sh
./configure
make
pwd
#cp -r SDL2_gfx-1.0.4 $current_path 
}

install_sdl
install_sdl_ttf
install_sdl_image
#install_sdl_gfx
