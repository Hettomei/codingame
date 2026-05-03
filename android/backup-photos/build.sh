#!/bin/bash
set -e

# Générer la clé si elle n'existe pas
if [ ! -f mykey.jks ]; then
    keytool -genkey -v -keystore mykey.jks -alias mykey \
        -keyalg RSA -keysize 2048 -validity 10000 \
        -dname "CN=Dev, OU=Dev, O=Dev, L=City, S=State, C=FR" \
        -storepass android -keypass android
fi

./gradlew assembleDebug

APK=app/build/outputs/apk/debug/app-debug.apk
echo "✓ APK prêt : $APK"
