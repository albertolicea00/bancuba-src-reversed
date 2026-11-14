# Changelog

## 2026-11-14 — cu.bpa.bancuba-v2.apk — SHA-256: `e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`

- Initialized the BanCuba (`cu.bpa.bancuba`) reverse-engineering repository structure (`app/assets/`, `app/res/`, `app/src/main/java/`, `src/`, `smali/`, `mappings/evidence/`, `scripts/tests/`, `docs/`, `.workspace/bin/`, `.workspace/archives/`) mirroring `transfermovil-src-reversed` prior to decompilation.
- Verified target APK metadata (`cu.bpa.bancuba` v`1.3`, code `2`, Min SDK `14`, Target SDK `27`, Compile SDK `23`) and documented extraction in `docs/target-metadata.md`.
- Added `Makefile` and 7 analysis toolchain scripts (`verify-apk.sh`, `decompile.sh`, `search-strings.sh`, `mapping-stats.sh`, `reconstruction-inventory.py`, `update-progress.py`, `compile-reconstruction.py`).
- Created full 12-document template set in `docs/` (`target-metadata.md`, `versioning.md`, `architecture.md`, `calculators.md`, `networking.md`, `security.md`, `storage.md`, `domain-model.md`, `reconstruction-inventory.md`, `unmapped-source-review.md`, `java-compilation.md`, `runtime-validation.md`).
- Updated `README.md`, `AGENTS.md`, and `TODO.md` with documentation links and reference links (`bpa.cu`, `APK_Bancuba` on EcuRed, Apklis).
- Added a `Related Repositories` cross-reference table in `README.md` linking all six Cuban Android reverse-engineering repositories.
- Decompiled `cu.bpa.bancuba-v2.apk` with JADX 1.5.6 and Apktool 3.0.3 into `src/`, `app/AndroidManifest.xml`, `app/res/`, and text-based `app/assets/` (HTML/CSS/JS), and archived local `smali/` to `.workspace/archives/smali-1.3.2.zip` (SHA-256: `255276ef162f35bc4928034ce703f2762d44c2423c0b77c8e1f1a09672f0e8bd`).
- Reconstructed clean `app/src/main/java/cu/bpa/bancuba/MainActivity.java` (1,106 lines, including `Website2ApkJavascriptBridge`) and mapped `MainActivity` and `MainActivity$a` in `mappings/classes.json`.
- Completed static analysis across `docs/architecture.md`, `docs/calculators.md`, `docs/domain-model.md`, `docs/networking.md`, `docs/security.md`, `docs/storage.md`, `docs/unmapped-source-review.md`, `docs/java-compilation.md`, `docs/runtime-validation.md`, and `docs/versioning.md`.
- Prepared release baseline `1.3.2-v1.0`.
- Updated `scripts/update-progress.py` and `README.md` to reflect real decompilation and reconstruction progress (`23/25` TODO tasks = `92%`, `1/2` exact source links = `50%`, overall `71%`), noting the core UI/logic is bundled HTML/JS in `app/assets/`.
