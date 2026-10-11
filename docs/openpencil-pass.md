# OpenPencil pass — couture-dz

## Design (Shared/OpenPencil/couture-dz/)
- `design.html` (search + order cards with balances, DZ tokens) → `design.fig`
- `lint --preset accessibility`: 0 errors, 0 warnings

## Code review (no rewrite needed)
`MainActivity.kt` already meets the 2026 standard: dynamic color, edge-to-edge, adaptive
rail (≥840dp) / bottom bar, labeled nav (glyph + string resources), search field, heading
semantics, offline note. No code change — forcing edits would only risk a green build.

## APKs
`build-debug.yml` adds `assembleRelease` (unsigned) + `app-release` artifact + a versioned
`release-v*-b*` GitHub Release for the release APK (debug flow unchanged).
