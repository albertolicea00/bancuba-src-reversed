# Changelog

## 2026-11-14 — cu.bpa.bancuba-v2.apk — SHA-256: `e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`

- Initialized the BanCuba (`cu.bpa.bancuba`) reverse-engineering repository structure (`app/assets/`, `app/res/`, `app/src/main/java/`, `src/`, `smali/`, `mappings/evidence/`, `scripts/tests/`, `docs/`, `.workspace/bin/`, `.workspace/archives/`) mirroring `transfermovil-src-reversed` prior to decompilation.
- Verified target APK metadata (`cu.bpa.bancuba` v`1.3`, code `2`, Min SDK `14`, Target SDK `27`, Compile SDK `23`) and documented extraction in `docs/target-metadata.md`.
- Added `Makefile` and 7 analysis toolchain scripts (`verify-apk.sh`, `decompile.sh`, `search-strings.sh`, `mapping-stats.sh`, `reconstruction-inventory.py`, `update-progress.py`, `compile-reconstruction.py`).
- Created full 12-document template set in `docs/` (`target-metadata.md`, `versioning.md`, `architecture.md`, `calculators.md`, `networking.md`, `security.md`, `storage.md`, `domain-model.md`, `reconstruction-inventory.md`, `unmapped-source-review.md`, `java-compilation.md`, `runtime-validation.md`).
- Updated `README.md`, `AGENTS.md`, and `TODO.md` with documentation links and reference links (`bpa.cu`, `APK_Bancuba` on EcuRed, Apklis).
