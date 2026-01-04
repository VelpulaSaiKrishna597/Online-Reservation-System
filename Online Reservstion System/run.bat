@echo off
echo Starting Online Reservation System...
echo.

REM Check if bin directory exists
if not exist "bin" (
    echo Error: Compiled classes not found. Please run build.bat first.
    pause
    exit /b 1
)

REM Run the application
java -cp "bin;lib/*" com.reservation.gui.LoginForm

pause

