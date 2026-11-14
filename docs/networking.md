# Networking & External Endpoints

BanCuba (`cu.bpa.bancuba`) operates almost entirely offline, loading `file:///android_asset/index.html` into the system `WebView`.

## Manifest & WebView Configuration

- **Declared permissions**: `INTERNET` and `ACCESS_NETWORK_STATE`.
- **Cleartext traffic**: `android:usesCleartextTraffic="@bool/usesCleartextTraffic"` evaluates to `true` (`res/values/bools.xml`).
- **SSL handling**: `R.bool.ignoreSsl` is `false`; `WebViewClient.onReceivedSslError` invokes `handler.cancel()` when `ignoreSsl` is false.

## External URLs & URI Schemes

`MainActivity.handleExternalSchemeUrl(String)` (obfuscated `b(String)`) intercepts non-local links and dispatches system `Intent`s (`tel:`, `mailto:`, `sms:`, `market://`, `whatsapp:`, `skype:`, `geo:0,0?q=`, `maps:`, `intent://...scheme=http`, `exit:`, and URLs containing `#___external`).

Observed external references in `app/assets/` and `MainActivity.java`:

| Reference | Context |
|---|---|
| `https://bancaremota.bpa.cu` | Referenced in `belect.html` (BPA Remote Banking portal) |
| `https://bancaremota.bpa.cu/?page=herramientas` | Referenced in `belect.html` |
| `mailto:sandy@a7472.gr.bpa.cu` | Author/developer contact link in `index.html` |
| `https://mobile.facebook.com/bancuba/` | Social share link in `index.html` |
| `https://twitter.com/search?q=bancuba&src=typed_query` | Social link in `index.html` |
| `https://websitetoapk.com/apptest/testp.html` | Fallback redirect target in `MainActivity.verifyBuilderLicenseOrRedirect()` |

There are **no** REST APIs, Retrofit clients, sockets, or background sync services in the APK.
