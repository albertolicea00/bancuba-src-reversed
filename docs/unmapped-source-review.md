# Unlinked main-package source review

Target: `cu.bpa.bancuba-v2.apk`, SHA-256
`e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`.

## Purpose and limits

This ledger tracks Java source files in the main application package (`cu.bpa.bancuba`) that lack exact clean-source links in `app/src/main/java/`. The ledger is an audit queue, not a decompilation-success metric. JADX decompilation success and source-to-clean class identity links are separate measures, and a link does not prove behavioral equivalence.

Classification categories:
- `application behavior`: UI, routing, financial calculators, directory lookup, persistence, or security logic.
- `application support`: shared helpers, base classes, adapters, or internal utilities supporting app behavior.
- `generated`: resource/build output, synthetic bridges, binding code, or compiler-generated machinery (`BuildConfig`, `R`).
- `third-party/library`: vendored or dependency implementation rather than BanCuba-specific behavior.

## Current status

Pending initial decompilation (`make decompile`). Populate package-by-package review tables after generating the baseline inventory with `make inventory`.
