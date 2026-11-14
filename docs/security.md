# Security Assessment

Target: `cu.bpa.bancuba` v1.3 (versionCode `2`).

> [!NOTE]
> **Status:** Structural template (pending decompilation). Populate with concrete evidence from `app/AndroidManifest.xml`, `src/`, and `smali/` after decompiling.

## Audit Checklist

1. **Manifest Security Flags:** `debuggable`, `allowBackup`, `usesCleartextTraffic`, and exported components.
2. **WebView Security (if applicable):** `setJavaScriptEnabled`, `addJavascriptInterface`, file access flags (`setAllowFileAccess`, `setAllowUniversalAccessFromFileURLs`).
3. **Local Data & Bundled Assets:** Check whether any sensitive internal endpoints or credentials are embedded in `app/assets/` or `src/`.
