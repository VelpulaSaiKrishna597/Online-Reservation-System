@echo off
echo Building Online Reservation System...
echo.

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

REM Compile Java files
echo Compiling Java source files...
javac -d bin -cp "lib/*" src/com/reservation/**/*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful!
    echo.
    echo To run the application, use:
    echo java -cp "bin;lib/*" com.reservation.gui.LoginForm
) else (
    echo.
    echo Build failed! Please check the errors above.
)

pause

