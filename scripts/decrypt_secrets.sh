#!/bin/sh

# https://docs.github.com/ja/actions/security-guides/encrypted-secrets
# Please add decrypted files to .gitignore
gpg --quiet --batch --yes --decrypt --passphrase="$SECRET_PASSPHRASE" \
  --output "ios/DroidKaigi 2021/DroidKaigi 2021/GoogleService-Info-Production-Release.plist" "ios/DroidKaigi 2021/DroidKaigi 2021/GoogleService-Info-Production-Release.plist.gpg"
gpg --quiet --batch --yes --decrypt --passphrase="$SECRET_PASSPHRASE" \
  --output "android/src/release/google-services.json" "android/src/release/google-services.json.gpg"
gpg --quiet --batch --yes --decrypt --passphrase="$SECRET_PASSPHRASE" \
  --output "android/release.keystore" "android/release.keystore.gpg"
gpg --quiet --batch --yes --decrypt --passphrase="$SECRET_PASSPHRASE" \
  --output "android/signing.properties" "android/signing.properties.gpg"
