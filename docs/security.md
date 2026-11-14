# Security Analysis

Security assessment of **BanCuba** (`cu.bpa.bancuba` v1.3, `versionCode` `2`).

## 1. Manifest & Component Security

- **Exported components**: Only `cu.bpa.bancuba.MainActivity` is exported (via `MAIN`/`LAUNCHER` intent filter). There are no exported services, broadcast receivers, or content providers.
- **Backup flag**: `android:allowBackup="true"` is enabled in `AndroidManifest.xml`.
- **Cleartext traffic**: `R.bool.usesCleartextTraffic` is `true`, though the app itself loads local `file:///android_asset/` pages.
- **Debuggability**: `R.bool.isDebugMode` is `false`, so `WebView.setWebContentsDebuggingEnabled(false)` is set on API >= 19.
- **Screenshot protection**: `R.bool.flag_secure` is `false`.

## 2. WebView & JavaScript Bridge (`Website2APK`)

Because `R.bool.enableJsApi` is `true` and `settings.setJavaScriptEnabled(true)` is enabled:
- `MainActivity` exposes `window.Website2APK` (`MainActivity.Website2ApkJavascriptBridge`) to loaded pages, offering methods such as `getUniqueDeviceID()` (`Settings.Secure.ANDROID_ID`), `openExternal(url)`, `printPage()`, `askEnableGPS()`, and `exitApp()`.
- `R.bool.allow_file_access` is `false`, while `R.bool.allow_x_origin_from_file` is `true` (enabling `setAllowFileAccessFromFileURLs(true)` and `setAllowUniversalAccessFromFileURLs(true)` on API >= 16).
- Because `R.bool.extlink` is `true`, `WebViewClient.shouldOverrideUrlLoading` routes external `http`/`https` hosts out to the system browser when they differ from `targetHost`, limiting untrusted remote pages from running inside the privileged `file://` WebView context.

## 3. Website 2 APK Builder Pro License Check

`MainActivity` embeds the wrapper's built-in MD5 checksum check (`md5WithSalt(input)` = `MD5(input + "00110011")`):
- `R.string.csum` = `95D6E56DF9DF383FA7EB693C4A912815`
  - First 16 chars (`95D6E56DF9DF383F`) match `MD5("cu.bpa.bancuba00110011")[0..16]`.
  - Last 16 chars (`A7EB693C4A912815`) do **not** match `MD5(R.string.devid + "cu.bpa.bancuba00110011")[16..32]` (`4AEA01C4CEBA5F57`).
- As a result, in `verifyBuilderLicenseOrRedirect()`, if the app was installed via an installer package other than `null` or `com.google.android.packageinstaller` and >30 days have elapsed since `classes.dex` build time (`2019-08-29`), `targetUrl` is overwritten with `https://websitetoapk.com/apptest/testp.html`.

## 4. Cryptography & Sensitive Data

- No banking credentials, PINs, cards, or user accounts are stored or processed by BanCuba.
- The only cryptographic primitive in `classes.dex` is `MessageDigest.getInstance("MD5")` for the wrapper license check.
