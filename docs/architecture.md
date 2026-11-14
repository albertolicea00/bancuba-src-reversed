# Architecture Overview

Target: `cu.bpa.bancuba` v1.3 (versionCode `2`), SHA-256 `e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`.

> [!NOTE]
> **Status:** Pre-decompilation baseline. Only APK badging metadata (`aapt2 dump badging`) has been extracted so far. Run `make decompile` to populate `src/`, `smali/`, `app/res/`, `app/assets/`, and `app/AndroidManifest.xml`.

## Build & Package Properties

| Property | Value |
| :--- | :--- |
| **Package Name** | `cu.bpa.bancuba` |
| **Version Name** | `1.3` |
| **Version Code** | `2` |
| **Compile SDK** | `23` (Android 6.0) |
| **Min SDK** | `14` (Android 4.0) |
| **Target SDK** | `27` (Android 8.1) |
| **APK Size** | ~6.5 MB |

## Declared Permissions (from `aapt2 dump badging`)

- Network: `INTERNET`, `ACCESS_NETWORK_STATE`

## Pending Analysis Sections (Post-Decompilation)

- **Manifest Components:** Activities, fragments, services, permissions, and exported flags.
- **Native Java vs. Hybrid/WebView Architecture:** Determine whether screens and calculators are implemented in native Java or bundled HTML/JS assets in `app/assets/`.
- **Bundled Assets & Directories:** Inspect `app/assets/` and `app/res/`.
