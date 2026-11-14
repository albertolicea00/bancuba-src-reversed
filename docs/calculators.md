# Financial Calculators & Offline Utilities

All interactive calculations and directory lookups in BanCuba (`cu.bpa.bancuba`) run client-side in JavaScript inside the `WebView` without any server calls.

## 1. French Amortization Loan Calculator (`prestamo_frances.html`)

- **Data source**: `assets/js/obj_plazos_tasa_prestamofrances.js` defines `plazos_tasa.obj` with 12 term/rate tiers from **3 months (2.25%)** up to **120 months (7.75%)**:
  - `3m`: `2.25%`, `6m`: `2.75%`, `12m`: `4.25%`, `24m`: `5.25%`, `36m`: `6.25%`, `48m`: `6.50%`, `60m`: `6.75%`, `72m`: `7.25%`, `84m`: `7.35%`, `96m`: `7.45%`, `108m`: `7.60%`, `120m`: `7.75%`.
- **Payment capacity & schedule**:
  - Computes net monthly payment capacity (`capacpago = Math.floor(sumax - restax)`) from user-entered household income/expenses, formatted via `fmtIt(capacpago)`.
  - Estimates repayment duration (`Math.ceil(monto / capacpago)`) and iterates through `plazos_tasa.obj` to render feasible monthly installment options and total interest in `#tabla_result`.

## 2. Fixed-Term Deposit Calculator (`plazo_fijo.html`)

- **Data source**: `assets/js/obj_plazos_tasa_plazofijo.js` defines three currency-specific rate tables:
  - `plazos_tasa_cup` (CUP): `90d / 3m` (`2%`), `180d / 6m` (`2.5%`), `365d / 12m` (`4%`), `730d / 24m` (`5%`), `1095d / 36m` (`6%`), `1825d / 60m` (`6.50%`), `2190d / 72m` (`7%`).
  - `plazos_tasa_cuc` (CUC, legacy 2019): `90d` (`1.5%`) to `1825d` (`4.25%`).
  - `plazos_tasa_mlc` (MLC): `90d` (`0.5%`) to `1825d` (`1.9%`).
- Note: Several entries in `obj_plazos_tasa_plazofijo.js` contain duplicate `"id": 5` keys from manual editing by the BPA author.

## 3. Number Formatting & Validation (`fmtIt.js`, `val_num.js`, `validaciones.js`)

- `fmtIt(fracc)` rounds and formats floating-point currency amounts to two decimal places using string manipulation and carry propagation (`fmtIt1()`).
- `val_num.js` and `validaciones.js` enforce numeric input constraints on HTML form fields.

## 4. Branch & ATM Directory (`directorio_sucursal.html`, `directorio_atm.html`)

- `obj_prov.js`, `obj_mun.js`, `obj_suc.js`, and `nsuc.js` provide offline cascading province → municipality → branch/ATM lookups across Cuba (using variables such as `obj_prov_<p>_mun_<m>` containing branch code `suc`, `tipo`, `desc`, `dir`, and `tlf`).
