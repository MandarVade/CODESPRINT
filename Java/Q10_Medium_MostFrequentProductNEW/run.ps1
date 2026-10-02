$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

Write-Host "Compiling Q10 evaluator..."
javac TestMostFrequentProduct.java
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "Running Q10 evaluator..."
java TestMostFrequentProduct
exit $LASTEXITCODE
