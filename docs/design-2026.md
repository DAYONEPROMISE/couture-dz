# CoutureDZ — 2026 UI/UX Design Spec (Penpot-ready)

Open-source design: Penpot (https://penpot.app). No Figma. One shared direction across all 20 DZ apps; this file is the per-app instantiation.

## 1. Design tokens
| Token | Light | Dark | Notes |
|---|---|---|---|
| Primary | dynamic (Android 12+ `dynamicLightColorScheme`) | dynamic (`dynamicDarkColorScheme`) | fallback M3 baseline purple/green when dynamic unavailable |
| Surface / on-surface | M3 default | M3 default | never hardcode hex in Compose |
| Shape | 12dp cards, 28dp search field | same | `CardDefaults`, `SearchBarDefaults.InputField` |
| Type | M3 `headlineSmall` section titles (heading semantics), `bodyMedium` content | same | FR default, AR mirrored automatically |
| Touch target | ≥ 48dp (M3 NavigationBar/Rail defaults) | same | accessibility scanner clean |

## 2. Components (Penpot library → Compose mapping)
- TopBar: Penpot `TopBar/Center` → `CenterAlignedTopAppBar` (pinned scroll). Only on compact width; wide screens omit top bar (rail carries identity).
- Navigation: Penpot `Nav/Destinations×3` → `NavigationBar` (compact) / `NavigationRail` (≥840dp). Icons: People, ReceiptLong, Straighten with text labels, always labelled (a11y).
- Search: Penpot `Search/Field` → `SearchBarDefaults.InputField`, max width 640dp, hint localized.
- Order card: Penpot `Card/Order` → M3 `Card` (1dp elevation), title `titleMedium` + amounts line `Price · Deposit · Due` formatted via `Dzd.format` (e.g. `45 000 DZD`).
- Adaptive rule: Penpot boards `Compact 360×800` and `Expanded 1280×800`; Compose `BoxWithConstraints`: `<840dp` single-pane + bottom nav, `≥840dp` rail + row of cards.

## 3. Screens (3 destinations, offline)
1. **Clients** — search + list (name/phone/notes), FAB-add (roadmap; Room DAO ready: `clients()`).
2. **Commandes** — search + cards (fabric/model/amounts/deadline), demo data now, Room `orders()` next.
3. **Mesures** — per-client sheet (chest/waist/hips/shoulder/sleeve/length cm), Room `measurements(cid)` next.

## 4. FR/AR RTL rules
- `supportsRtl=true`, no forced `LayoutDirection`; system locale drives mirroring.
- `values/` (EN fallback), `values-fr`, `values-ar` all carry the same keys; Arabic strings verified RTL (e.g. `الباقي = السعر − العربون`).
- Numbers stay LTR within RTL text (DZD amounts use spaces, no locale-breaking commas).

## 5. Dark mode
- `isSystemInDarkTheme()` + dynamic schemes on API 31+, static M3 fallback below. Tested via CI lint + manual theme toggle.

## 6. Accessibility checklist
- Section titles carry `heading()` semantics; nav icons decorative (`contentDescription=null`) with text labels.
- Contrast from M3 dynamic pairs; no text below `bodySmall`; cards ≥1dp elevation, not color-alone.

## 7. Penpot reproduction (5 min)
1. New file `CoutureDZ-2026`, page per width (360 / 1280).
2. Create color libraries `light`/`dark` from a Pixel screenshot (dynamic626 extract) or M3 baseline.
3. Draw the 3 boards above using components in §2; export PNGs to `docs/` (optional).
4. Handoff: this file + `MainActivity.kt` is the source of truth — implementation matches boards 1:1.

## 8. Limitations / next
- Lists currently demo data; Room wiring + add/edit sheets + deadline reminders are next iterations (no scope change in this task).
- No new backend, no cost ($0, GitHub Actions free tier).
