@echo off
rem -------------------------------------------------------------------------
rem fUML Runtime Environment Script for Win32
rem -------------------------------------------------------------------------

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=fuml.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

pushd %DIRNAME%..
set FUML_HOME=%CD%
popd

set FUML_LIB_DIR=../lib

if not "%JAVA_HOME%" == "" goto ADD_TOOLS

set JAVA=java

echo JAVA_HOME is not set.  Unexpected results may occur.
echo Set JAVA_HOME to the directory of your local JDK to avoid this message.
goto SKIP_TOOLS

:ADD_TOOLS

set JAVA=%JAVA_HOME%\bin\java

rem A full JDK with tools.jar is not required.
if not exist "%JAVA_HOME%\lib\tools.jar" goto SKIP_TOOLS

rem If exists, point to the JDK javac compiler.
set JAVAC_JAR=%JAVA_HOME%\lib\tools.jar

:SKIP_TOOLS

set FUML_CLASSPATH=%FUML_LIB_DIR%/fuml.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/activation.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/commons-logging.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/log4j.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/junit.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/jaxb-api-2.1-EA2.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/jaxb-impl-2.1-EA2.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/stax-api.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/stax-utils.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/xalan.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/xerces.jar
set FUML_CLASSPATH=%FUML_CLASSPATH%;%FUML_LIB_DIR%/jdom.jar

rem Setup FUML specific properties
set JAVA_OPTS=%JAVA_OPTS% -Dprogram.name=%PROGNAME%

rem Add -server to the JVM options, if supported
"%JAVA%" -version 2>&1 | findstr /I hotspot > nul
if not errorlevel == 1 (set JAVA_OPTS=%JAVA_OPTS% -server)

rem JVM memory allocation pool parameters. Modify as appropriate.
set JAVA_OPTS=%JAVA_OPTS% -Xms128m -Xmx512m

rem With Sun JVMs reduce the RMI GCs to once per hour
set JAVA_OPTS=%JAVA_OPTS% -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000

rem JPDA options. Uncomment and modify as appropriate to enable remote debugging.
rem set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y %JAVA_OPTS%

rem Setup the java endorsed dirs
set FUML_ENDORSED_DIRS=%FUML_HOME%\lib\endorsed

echo ===============================================================================
echo.
echo   fUML Runtime Environment
echo.
echo   FUML_HOME: %FUML_HOME%
echo.
echo   JAVA: %JAVA%
echo.
echo   JAVA_OPTS: %JAVA_OPTS%
echo.
echo   CLASSPATH: %FUML_CLASSPATH%
echo.
echo ===============================================================================
echo.

:RESTART
"%JAVA%" %JAVA_OPTS% "-Djava.endorsed.dirs=%FUML_ENDORSED_DIRS%" -classpath "%FUML_CLASSPATH%" -Dlog4j.debug=false -Dlog4j.configuration=file:log4j.properties org.modeldriven.fuml.Fuml %*
if ERRORLEVEL 10 goto RESTART

:END
rem if "%NOPAUSE%" == "" pause

:END_NO_PAUSE
