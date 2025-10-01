@echo off
REM Convenient script to publish individual modules to Maven Central
REM Usage: publish.bat <module-name>
REM Example: publish.bat analytiks-appsflyer

setlocal

if "%~1"=="" (
    echo.
    echo ╔════════════════════════════════════════════════════════════════╗
    echo ║  Analytiks Module Publisher                                   ║
    echo ╚════════════════════════════════════════════════════════════════╝
    echo.
    echo Usage: publish.bat ^<module-name^>
    echo.
    echo Available modules:
    echo   Core modules:
    echo     - analytiks-core
    echo     - analytiks
    echo.
    echo   Addon modules:
    echo     - analytiks-appsflyer
    echo     - analytiks-amplitude
    echo     - analytiks-appvisor
    echo     - analytiks-azureinsight
    echo     - analytiks-googleanalytics
    echo     - analytiks-mixpanel
    echo     - analytiks-segment
    echo     - analytiks-timber
    echo.
    echo Special commands:
    echo   - all-addons  : Publish all addon modules
    echo   - core        : Publish core modules
    echo   - list        : List all available modules
    echo   - release     : Close and release staging repositories
    echo.
    echo Options:
    echo   --release     : Publish and automatically release to Maven Central
    echo.
    echo Examples:
    echo   publish.bat analytiks-appsflyer
    echo   publish.bat analytiks-appsflyer --release
    echo   publish.bat all-addons
    echo   publish.bat core
    echo   publish.bat release
    echo.
    exit /b 1
)

set MODULE_NAME=%~1
set RELEASE_FLAG=%~2

if "%MODULE_NAME%"=="list" (
    call gradlew.bat listModules
    exit /b %ERRORLEVEL%
)

if "%MODULE_NAME%"=="all-addons" (
    call gradlew.bat publishAllAddons
    exit /b %ERRORLEVEL%
)

if "%MODULE_NAME%"=="core" (
    call gradlew.bat publishCore
    exit /b %ERRORLEVEL%
)

if "%MODULE_NAME%"=="release" (
    echo.
    echo ╔════════════════════════════════════════════════════════════════╗
    echo ║  Closing and Releasing Staging Repositories                   ║
    echo ╚════════════════════════════════════════════════════════════════╝
    echo.
    call gradlew.bat closeAndReleaseStagingRepositories
    exit /b %ERRORLEVEL%
)

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║  Publishing: %MODULE_NAME%
echo ╚════════════════════════════════════════════════════════════════╝
echo.

if "%RELEASE_FLAG%"=="--release" (
    call gradlew.bat publishAndReleaseModule -PmoduleName=%MODULE_NAME%
) else (
    call gradlew.bat publishModule -PmoduleName=%MODULE_NAME%
)

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ╔════════════════════════════════════════════════════════════════╗
    echo ║  ✅ Successfully published %MODULE_NAME%
    echo ╚════════════════════════════════════════════════════════════════╝
    echo.
) else (
    echo.
    echo ╔════════════════════════════════════════════════════════════════╗
    echo ║  ❌ Failed to publish %MODULE_NAME%
    echo ╚════════════════════════════════════════════════════════════════╝
    echo.
    exit /b %ERRORLEVEL%
)

endlocal

