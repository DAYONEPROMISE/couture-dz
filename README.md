# CoutureDZ — carnet de mesures offline (MIT)

Offline-first Android app (Kotlin, Jetpack Compose Material 3, Room) for Algerian tailors.
Clean-room code, no account, no network. French + Arabic RTL.

## Features
- Clients: name, phone, notes (Room)
- Measurements (taille): chest/waist/hips/shoulder/sleeve/length in cm
- Orders: fabric (tissu), model (modele), deadline, price + deposit in DZD, balance = price - deposit, status

## Build
```
./gradlew :app:testDebugUnitTest :app:lint :app:assembleDebug
```

## Tests
`DzdTest` — 8 unit tests: DZD balance/format, name/measurement/money validation.

## License
MIT
