# Architecture Overview

Static architectural analysis of **BanCuba** (`cu.bpa.bancuba`, version `1.3`, `versionCode` `2`), an offline-first banking guide and financial calculator published by **Banco Popular de Ahorro (BPA)** in Granma, Cuba.

## Application Wrapper

Rather than a native multi-screen Android application, BanCuba is built using the **Website 2 APK Builder Pro** hybrid wrapper template (`MainActivity.java` + `MainActivity$a`), bundling an HTML5/CSS3/Bootstrap/jQuery portal inside `assets/`.

| Attribute | Value |
|---|---|
| Package | `cu.bpa.bancuba` |
| Version | `1.3` (`versionCode` `2`) |
| Min / Target / Compile SDK | `14` / `27` / `23` |
| DEX Classes | 17 (`MainActivity`, inner classes, and `R` subclasses) |
| Launcher Activity | `cu.bpa.bancuba.MainActivity` (portrait, `adjustResize`) |
| Entry URL | `file:///android_asset/index.html` |
| Splash Duration | `1500` ms (`R.integer.duration`) |

## Manifest Components & Permissions

- **Activities**: Single exported launcher activity `cu.bpa.bancuba.MainActivity` (`android.intent.action.MAIN` + `LAUNCHER`).
- **Services / Receivers / Providers**: None.
- **Permissions**:
  - `android.permission.INTERNET`
  - `android.permission.ACCESS_NETWORK_STATE`
- **Runtime permission requests**: On API > 23, `MainActivity.requestRuntimePermissions()` requests `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `RECORD_AUDIO`, and `CAMERA` (request code `200`). Because none of those permissions are declared in `AndroidManifest.xml`, Android automatically denies them without prompting.

## Bundled Web Assets (`app/assets/`)

The UI and business logic live in local web assets:

- **13 HTML pages**: `index.html`, `prestamo_frances.html`, `plazo_fijo.html`, `directorio_sucursal.html`, `directorio_atm.html`, `belect.html`, `transfer.html`, `doc_cred.html`, `manual_atm.html`, `prod_serv.html`, `contacto.html`, `404r.html` (12 top-level pages).
- **21 JS files**: `jquery.js`, `bootstrap.min.js`, `obj_plazos_tasa_prestamofrances.js`, `obj_plazos_tasa_plazofijo.js`, `obj_prov.js`, `obj_mun.js`, `obj_suc.js`, `nsuc.js`, `fmtIt.js`, `val_num.js`, `validaciones.js`, `menu.js`, `arriba.js`, plus backup copies (`--obj_prov_orig.js`, `--obj_suc_orig.js`, `mun - copia.js`, `nsuc - copia.js`, `obj_mun_orig.js`).
- **8 CSS files**: Bootstrap 3, `estilos2.css`, Font Awesome, and IcoMoon (`fonts/style.css`).
