✅ Build & Run

1. Container starten mit Volume löschen
   ```bash
   docker compose down -v
   docker compose up --build
   ```

2. Container starten ohne Volume löschen
   ```bash
   docker compose down
   docker compose up --build
   ```
