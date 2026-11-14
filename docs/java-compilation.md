# Hybrid Java compiler audit

Target: `cu.bpa.bancuba-v2.apk`, SHA-256
`e694d0f58f8ad3e6f964d76069b1371c5ee89fb5230276f151d7e95359062de3`.

`scripts/compile-reconstruction.py` compiles every Java source under
`app/src/main/java` against a **local APK-derived bytecode jar** and a **local
Android platform jar**. This is a dependency-backed compiler audit: it detects
unresolved symbols, invalid signatures, type mismatches, and other Java errors
in the reconstructed tree while retaining the APK's raw dependencies.
It is not a standalone Android build, an APK produced from clean source, a
resource-packaging test, behavioral equivalence, or runtime verification.

## Local prerequisites and provenance

- Python 3 and a JDK with `javac` on `PATH`.
- The target APK remains local in `.workspace/bin/cu.bpa.bancuba-v2.apk`.
- Pin dex2jar to **2.4** under `.workspace/tools/dex-tools-v2.4/`.
- Convert the target APK bytecode to `.workspace/tools/target-bytecode.jar`:

```sh
bash .workspace/tools/dex-tools-v2.4/d2j-dex2jar.sh \
  -f -o .workspace/tools/target-bytecode.jar \
  .workspace/bin/cu.bpa.bancuba-v2.apk
```

## Run the full audit

Once decompilation (`src/cu/bpa/bancuba/R.java`) and clean Java files exist under `app/src/main/java/`:

```sh
make compile-audit ANDROID_JAR="$HOME/Library/Android/sdk/platforms/android-36/android.jar"
```

The script writes `.workspace/compile-reconstruction/report.json` and `.workspace/compile-reconstruction/javac.log`.
