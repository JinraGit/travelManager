@echo off
echo *** Wechsel in Backend-Verzeichnis ***
cd .\backend\
echo *** Führe Gradle Build (ohne Tests) aus ***
call .\gradlew clean build -x test
if %errorlevel% neq 0 (
    echo Fehler beim Gradle Build!
    exit /b %errorlevel%
)

cd ..
echo *** Wechsel in Frontend-Verzeichnis ***
cd .\frontend\
rmdir .\node_modules /s /q
echo *** Führe npm install aus ***
call npm install
if %errorlevel% neq 0 (
    echo Fehler bei npm install!
    exit /b %errorlevel%
)

cd ..

echo *** Baue Docker-Image für Datenbank ***
docker build -t travelmanager-db:latest ./db
if %errorlevel% neq 0 (
    echo Fehler beim Docker Build für DB!
    exit /b %errorlevel%
)

echo *** Baue Docker-Image für Backend ***
docker build -t travelmanager-backend:latest ./backend
if %errorlevel% neq 0 (
    echo Fehler beim Docker Build für Backend!
    exit /b %errorlevel%
)

echo *** Baue Docker-Image für Frontend ***
docker build -t travelmanager-frontend:latest ./frontend
if %errorlevel% neq 0 (
    echo Fehler beim Docker Build für Frontend!
    exit /b %errorlevel%
)

echo *** Stoppe und lösche bestehende Docker-Container und Volumes ***
docker compose down -v

echo *** Baue und starte Docker-Container ***
docker compose up --build -d

echo *** Fertig! ***
