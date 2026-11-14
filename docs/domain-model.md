# Domain Model

BanCuba (`cu.bpa.bancuba`) has no Java domain entity classes; all domain models live in static JavaScript object literals under `app/assets/js/`.

## 1. Loan Term Tier (`obj_plazos_tasa_prestamofrances.js`)

```json
{ "id": 1, "plazo": 3, "tasa": 2.25 }
```

| Field | Type | Description |
|---|---|---|
| `id` | Number | Sequential tier identifier (`1`–`12`) |
| `plazo` | Number | Loan duration in months (`3` to `120`) |
| `tasa` | Number | Annual interest rate percentage |

## 2. Fixed-Term Deposit Tier (`obj_plazos_tasa_plazofijo.js`)

```json
{ "id": 1, "plazo": 90, "tasa": 2, "mes": 3 }
```

| Field | Type | Description |
|---|---|---|
| `id` | Number | Tier identifier |
| `plazo` | Number | Term in days (`90`–`2190`) |
| `tasa` | Number | Annual interest rate percentage (varies across `plazos_tasa_cup`, `plazos_tasa_cuc`, `plazos_tasa_mlc`) |
| `mes` | Number | Equivalent term in months (`3`–`72`) |

## 3. Branch & Commercial Office Record (`obj_suc.js`)

Grouped by province and municipality index (`obj_prov_<p>_mun_<m>`):

```json
{
  "suc": "1012",
  "tipo": "Suc.",
  "desc": "Sandino",
  "dir": "Zona G No. lA",
  "tlf": " 2525 2541 3535"
}
```

| Field | Type | Description |
|---|---|---|
| `suc` | String | BPA branch code |
| `tipo` | String | Office type (e.g., `"Suc."`, `"Ofic. Comerc. "`) |
| `desc` | String | Branch or office name |
| `dir` | String | Street address |
| `tlf` | String | Telephone number(s) |
