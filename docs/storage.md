# Local Storage

BanCuba (`cu.bpa.bancuba`) does not use SQLite databases, Room, or `SharedPreferences` in Java code.

## Storage Mechanisms

1. **Bundled Static Assets (`app/assets/`)**:
   - All reference catalogs (provinces, municipalities, BPA branches, ATMs, fixed-term deposit rates, French loan rates) are stored as static JavaScript files under `app/assets/js/`.
2. **WebView Storage**:
   - `MainActivity.onCreate()` enables WebView DOM storage (`setDomStorageEnabled(true)`), Web SQL database directory (`setDatabaseEnabled(true)` at `getDir("database", 0)`), and `CookieManager` / `AppCache` (`/data/datacu.bpa.bancuba/cache` — note the missing slash in the upstream Website 2 APK Builder template).
   - None of the bundled HTML pages persist user financial data remotely.
