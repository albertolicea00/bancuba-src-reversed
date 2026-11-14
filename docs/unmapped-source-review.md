# Unmapped Source Review

Classification ledger for files in `src/` not mapped to `app/src/main/java/`.

| Source File | Classification | Role | Evidence |
|---|---|---|---|
| `src/cu/bpa/bancuba/R.java` | Generated Android Resource Index | Compiler-generated `R` class holding resource IDs (`bool`, `drawable`, `id`, `integer`, `layout`, `menu`, `string`, `style`) | Standard `aapt2` generated `R` class with inner static ID classes (`R$bool`, `R$string`, etc.) |
