#!/bin/bash
set -e

ANDROID_JAR=$ANDROID_SDK_ROOT/platforms/android-34/android.jar
BUILD_TOOLS=$ANDROID_SDK_ROOT/build-tools/34.0.0
PKG=com/equipothee/helloworld

# Nettoyage
rm -rf build
mkdir -p build/gen build/obj build/dex build/apk_unsigned build/apk_aligned

# 1. Compiler les ressources
aapt2 compile res/layout/activity_main.xml -o build/

aapt2 link build/layout_activity_main.xml.flat \
    -I $ANDROID_JAR \
    --manifest AndroidManifest.xml \
    --min-sdk-version 24 \
    --target-sdk-version 34 \
    --java build/gen \
    -o build/apk_unsigned/app.apk

# 2. Compiler le Java
javac -classpath $ANDROID_JAR \
      -sourcepath src:build/gen \
      -d build/obj \
      --release 11 \
      src/$PKG/MainActivity.java build/gen/$PKG/R.java

# 3. Convertir en Dalvik (dex)
$BUILD_TOOLS/d8 build/obj/$PKG/*.class \
    --lib $ANDROID_JAR \
    --output build/dex/

# 4. Ajouter le dex dans le apk
cp build/dex/classes.dex build/apk_unsigned/
cd build/apk_unsigned && zip app.apk classes.dex && cd ../..

# 5. Aligner
zipalign -v 4 build/apk_unsigned/app.apk build/apk_aligned/app.apk

# 6. Générer une clé de signature (une seule fois)
if [ ! -f mykey.jks ]; then
    keytool -genkey -v -keystore mykey.jks -alias mykey \
        -keyalg RSA -keysize 2048 -validity 10000 \
        -dname "CN=Dev, OU=Dev, O=Dev, L=City, S=State, C=FR" \
        -storepass android -keypass android
fi

# 7. Signer
apksigner sign --ks mykey.jks --ks-pass pass:android \
    --out build/app-signed.apk build/apk_aligned/app.apk

echo "✓ APK prêt : build/app-signed.apk"
