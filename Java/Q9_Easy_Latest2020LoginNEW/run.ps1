$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

Write-Host "Compiling Q9 evaluator..."
javac TestLatest2020Login.java
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "Running Q9 evaluator..."
java TestLatest2020Login
exit $LASTEXITCODE
