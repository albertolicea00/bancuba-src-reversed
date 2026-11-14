# Java Compilation Audit

Verification of `app/src/main/java/cu/bpa/bancuba/MainActivity.java` against the installed Android SDK (`javac 17.0.15`, `-source 8 -target 8`).

## Observed Results

- **Target file**: `app/src/main/java/cu/bpa/bancuba/MainActivity.java` (1,106 lines) + `src/cu/bpa/bancuba/R.java`
- **Classpath tested**: `/Users/albertolicea00/Library/Android/sdk/platforms/android-34/android.jar`
- **Outcome**: All syntax, types, and Android API references resolve cleanly except for **2 removed legacy `WebSettings` methods** that existed in the APK's original `compileSdkVersion 23` (Android 6.0) and were removed in Android 13 (API 33):
  - `WebSettings.setAppCacheEnabled(boolean)` (line 703)
  - `WebSettings.setAppCachePath(String)` (line 715)
- **Notes**: Compiling against an Android 6.0–12 (`android-23` through `android-32`) `android.jar` resolves those two legacy `WebSettings` methods as well.
