@echo off
setlocal enabledelayedexpansion

:: Versuche .env Datei zu laden
if not exist .env (
    echo Fehler: .env Datei nicht gefunden!
    exit /b 1
)

:: Lese Repository-Namen aus der .env Datei
set "REPO_NAME="
for /f "usebackq tokens=1,* delims==" %%A in (".env") do (
    if /i "%%A"=="REPO_NAME" (
        set "REPO_NAME=%%B"
    )
)

:: Prüfe ob Variable gesetzt ist
if "%REPO_NAME%"=="" (
    echo Fehler: REPO_NAME nicht in .env Datei definiert!
    exit /b 1
)

echo *** Pushing Images to Docker Hub! ***

echo *** Push Docker-Image für Datenbank zur Registry ***
docker tag leo-green-db:latest %REPO_NAME%/leo-green-db:latest
docker push %REPO_NAME%/leo-green-db:latest
if %errorlevel% neq 0 (
    echo Fehler beim Docker Push!
    exit /b %errorlevel%
)

echo *** Push Docker-Image für Backend zur Registry ***
docker tag leo-green-backend:latest %REPO_NAME%/leo-green-backend:latest
docker push %REPO_NAME%/leo-green-backend:latest
if %errorlevel% neq 0 (
    echo Fehler beim Docker Push!
    exit /b %errorlevel%
)

echo *** Push Docker-Image für Frontend zur Registry ***
docker tag leo-green-frontend:latest %REPO_NAME%/leo-green-frontend:latest
docker push %REPO_NAME%/leo-green-frontend:latest
if %errorlevel% neq 0 (
    echo Fehler beim Docker Push!
    exit /b %errorlevel%
)

echo *** Fertig! ***
