# BanCuba (BPA) Reverse Engineering — Task List

## Extraction & Baseline Setup

- [x] Verify APK identity (SHA-256, package `cu.bpa.bancuba`, version `1.3`, code `2`, SDK levels)
- [x] Initialize repository directory structure, documentation templates, and toolchain scripts
- [x] Run `make decompile` on `.workspace/bin/cu.bpa.bancuba-v2.apk` to extract JADX Java (`src/`), Apktool disassembly (`smali/`), resources (`app/res/`), assets (`app/assets/`), and `app/AndroidManifest.xml`
- [x] Create local smali archive `.workspace/archives/smali-1.3.2.zip` and record its SHA-256 in `docs/versioning.md`
- [x] Run `make inventory` to generate initial `.workspace/reconstruction-inventory.json` and record class counts in `README.md` and `docs/reconstruction-inventory.md`

## Architecture & Manifest Analysis

- [x] Decode and analyze `app/AndroidManifest.xml` (launch activity `cu.bpa.bancuba.MainActivity`, activities, fragments, permissions, and exported components)
- [x] Determine whether UI and business logic are native Android Java or hybrid/WebView-backed (`app/assets/`)
- [x] Inventory bundled assets under `app/assets/` and resources under `app/res/`
- [x] Document findings in `docs/architecture.md`

## Financial Calculators & Simulators

- [x] Trace fixed-term deposit (plazo fijo) interest calculation formulas, interest rates, and term tiers
- [x] Trace personal loan / credit simulator formulas (interest rates, monthly amortization schedule, and borrowing capacity calculation)
- [x] Document all financial formulas, rounding rules, and rate tables in `docs/calculators.md`

## Institutional Directories & Banking Reference Data

- [x] Extract and document provincial BPA branch/office directories (addresses, phone numbers, codes)
- [x] Extract and document provincial ATM (cajeros automáticos) directories and locations
- [x] Document credit application requirements, banking service catalogs, ATM usage guides, and electronic/USSD banking reference info

## Local Storage, Networking & Security

- [x] Inspect local data persistence (bundled SQLite/JSON/XML/HTML datasets, `SharedPreferences`) and document in `docs/storage.md`
- [x] Inspect any network endpoints or external URLs referenced via `INTERNET` / `ACCESS_NETWORK_STATE` permissions (`docs/networking.md`)
- [x] Audit application security settings (`allowBackup`, cleartext traffic, WebView settings if applicable, exported components) in `docs/security.md`

## Domain Model & Clean Java Reconstruction

- [x] Catalog domain entities and view/controller classes in `docs/domain-model.md`
- [x] Classify unlinked main-package sources in `docs/unmapped-source-review.md`
- [x] Populate `mappings/classes.json` with verified obfuscated-to-clean class mappings
- [x] Reconstruct clean Java classes under `app/src/main/java/` with `@obfuscated_class` annotations
- [x] Set up local `target-bytecode.jar` with dex2jar and run `make compile-audit` (`docs/java-compilation.md`)

## Dynamic Validation (When Emulator Available)

- [ ] Install APK on isolated Android emulator and capture initial launch logcat and UI hierarchy
- [ ] Compare runtime screens and calculator outputs against static code map in `docs/runtime-validation.md`
