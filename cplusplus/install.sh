set -eux

sudo add-apt-repository -y ppa:ubuntu-toolchain-r/test
sudo apt install -y g++-11
sudo apt-get install -y libsdl2-dev libsdl2-ttf-dev libsdl2-image-dev

