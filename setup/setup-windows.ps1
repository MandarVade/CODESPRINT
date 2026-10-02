$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "==============================================="
Write-Host " CodeSprint SQL Environment Setup (Windows)"
Write-Host "==============================================="
Write-Host ""

function Find-MySqlClient {
    $command = Get-Command mysql -ErrorAction SilentlyContinue
    if ($command) {
        return $command.Source
    }

    $candidates = @(
        "$env:ProgramFiles\MySQL\MySQL Server 8.0\bin\mysql.exe",
        "$env:ProgramFiles\MySQL\MySQL Server 8.4\bin\mysql.exe",
        "$env:ProgramFiles\MySQL\MySQL Server 9.0\bin\mysql.exe",
        "$env:ProgramFiles\MariaDB 11.0\bin\mysql.exe",
        "C:\xampp\mysql\bin\mysql.exe"
    )

    foreach ($path in $candidates) {
        if (Test-Path $path) {
            return $path
        }
    }

    return $null
}

$mysql = Find-MySqlClient
if (-not $mysql) {
    Write-Host "ERROR: MySQL client (mysql.exe) was not found." -ForegroundColor Red
    Write-Host "Install the organizer-provided MySQL environment or add mysql.exe to PATH."
    exit 1
}

Write-Host "MySQL client: $mysql"
Write-Host ""

$secureRootPassword = Read-Host "Enter the MySQL root password" -AsSecureString
$rootPassword = [Runtime.InteropServices.Marshal]::PtrToStringBSTR(
    [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secureRootPassword)
)

try {
    $bytes = New-Object byte[] 24
    [Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
    $appPassword = -join ($bytes | ForEach-Object { $_.ToString("x2") })

    $sql = @"
CREATE DATABASE IF NOT EXISTS codesprint;
CREATE USER IF NOT EXISTS 'codesprint'@'localhost' IDENTIFIED BY '$appPassword';
ALTER USER 'codesprint'@'localhost' IDENTIFIED BY '$appPassword';
CREATE USER IF NOT EXISTS 'codesprint'@'127.0.0.1' IDENTIFIED BY '$appPassword';
ALTER USER 'codesprint'@'127.0.0.1' IDENTIFIED BY '$appPassword';
GRANT ALL PRIVILEGES ON codesprint.* TO 'codesprint'@'localhost';
GRANT ALL PRIVILEGES ON codesprint.* TO 'codesprint'@'127.0.0.1';
FLUSH PRIVILEGES;
"@

    $env:MYSQL_PWD = $rootPassword
    $sql | & $mysql --protocol=TCP -h 127.0.0.1 -P 3306 -u root --batch --skip-column-names
    if ($LASTEXITCODE -ne 0) {
        throw "MySQL setup failed. Check the root password and make sure the MySQL server is running."
    }
}
finally {
    Remove-Item Env:MYSQL_PWD -ErrorAction SilentlyContinue
    $rootPassword = $null
}

$configDirectory = Join-Path $HOME ".codesprint"
$configPath = Join-Path $configDirectory "db.properties"
New-Item -ItemType Directory -Path $configDirectory -Force | Out-Null

@"
host=127.0.0.1
port=3306
user=codesprint
database=codesprint
password=$appPassword
mysql-client=$($mysql.Replace("\", "/"))
"@ | Set-Content -Path $configPath -Encoding UTF8

Write-Host ""
Write-Host "Database setup completed successfully." -ForegroundColor Green
Write-Host "Local CodeSprint database: codesprint"
Write-Host "Local judge configuration: $configPath"
Write-Host ""
Write-Host "Participants do NOT need to configure environment variables."
Write-Host "Run the SQL evaluator normally from its question folder."
Write-Host ""
