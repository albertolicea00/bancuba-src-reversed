# Runtime Validation

Scope and status of dynamic/runtime validation for **BanCuba** (`cu.bpa.bancuba` v1.3, `versionCode` `2`).

## Static vs. Dynamic Scope

- **Static verification completed**:
  - `make verify` confirmed package `cu.bpa.bancuba`, `versionName` `1.3`, `versionCode` `2`, `minSdkVersion` `14`, `targetSdkVersion` `27`, `compileSdkVersion` `23`, and APK SHA-256 `e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`.
  - JADX 1.5.6 decompiled 100% of DEX classes (0 errors).
  - Apktool 3.0.3 decoded `AndroidManifest.xml`, XML resources, smali, and all HTML/JS/CSS assets cleanly.
- **Outstanding runtime checks**:
  - Emulator/device execution of `prestamo_frances.html` and `plazo_fijo.html` to confirm WebView rendering and verify the `Website 2 APK Builder Pro` 30-day installer-package check (`verifyBuilderLicenseOrRedirect()`).
