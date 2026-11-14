# Local Android runtime validation

Target: `cu.bpa.bancuba-v2.apk`, SHA-256
`e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`.

## Scope and safety rules

When performing dynamic or hybrid-APK validation in an Android emulator:

1. Use an isolated Android Virtual Device (AVD) dedicated to research.
2. Disable external network connectivity during emulator inspection.
3. Keep all modified/hybrid APKs, UI hierarchy dumps, screenshots, and logcat captures inside `.workspace/` (excluded from Git).

## Observed results

Pending decompilation and initial static analysis. Record emulator environment, API level, startup behavior, and screen hierarchy comparisons here once dynamic checks are performed.
