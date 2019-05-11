set -e

CLJ_PATH=/home/tgauthier/programmes/codingame/xmas-2018-12-08
WIN_PATH=/mnt/c/Users/tim/programmes/

echo "(ns Player" > $CLJ_PATH/windows_build.clj
tail -n +2 "$CLJ_PATH/src/xmas/core.clj" >> $CLJ_PATH/windows_build.clj

cp -v -u -R $CLJ_PATH $WIN_PATH
echo 'ok'
