# CodeSprint SQL Competition

# Coordinator Copy-Paste Setup Guide

This guide is for **CodeSprint coordinators** who need to prepare a Windows computer for the SQL questions.

There are **two ways** to run the SQL questions:

### Method 1 — NEW Setup ⭐ Recommended

Uses:

* `setup/setup-windows.ps1`
* `SqlTestConfig.java`
* `run.ps1`

Participants do **not** need to configure environment variables.

### Method 2 — OLD Setup 🔄 Fallback

If the new setup does not work on a competition computer, use the previously working Q9/Q10 files and configure the required environment variables manually before running the evaluator.

---

# PART 1 — NEW SETUP ⭐ RECOMMENDED

## Step 1 — Open the CodeSprint repository

Open PowerShell.

Go to the CodeSprint repository.

Example:

```powershell
cd C:\NEAL\CODESPRINT\CodeSprint
```

You should be inside:

```text
C:\NEAL\CODESPRINT\CodeSprint
```

---

# Step 2 — Check Java

Run:

```powershell
java -version
```

Java should be installed.

Example:

```text
java version "25.0.1"
```

If Java is not recognized, stop and fix Java before continuing.

---

# Step 3 — Check MySQL

Run:

```powershell
mysql --version
```

Example:

```text
mysql.exe Ver 8.0.x
```

If MySQL is recognized, continue.

If you get:

```text
'mysql' is not recognized...
```

the MySQL client is either not installed or is not available through PATH.

The new setup script can detect MySQL from several common installation locations, but MySQL itself must be installed.

---

# Step 4 — Check the NEW setup files

Run:

```powershell
dir .\setup
```

You should see:

```text
README.md
setup-windows.ps1
setup-linux.sh
```

You should also have the NEW SQL question folders:

```text
Java\
├── Q9_Easy_Latest2020LoginNEW\
└── Q10_Medium_MostFrequentProductNEW\
```

The exact folder capitalization/name may differ depending on how the files were copied.

---

# Step 5 — PowerShell execution policy

Some Windows computers may block the setup script.

If you run:

```powershell
.\setup\setup-windows.ps1
```

and receive an error saying:

```text
The file is not digitally signed
```

run:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

Then run the setup script again:

```powershell
.\setup\setup-windows.ps1
```

### Important

The `-Scope Process` option means this is temporary.

It applies only to the current PowerShell window.

It does NOT permanently change the computer's PowerShell execution policy.

---

# Step 6 — Run the NEW database setup

Run:

```powershell
.\setup\setup-windows.ps1
```

You will see:

```text
CodeSprint SQL Environment Setup (Windows)
```

The script will detect MySQL.

Then it will ask:

```text
Enter the MySQL root password:
```

Enter the MySQL root password.

The password will not be displayed while typing.

---

# Step 7 — Wait for successful setup

You should see something similar to:

```text
Database setup completed successfully.

Local CodeSprint database: codesprint

Local judge configuration:
C:\Users\<YOUR_USERNAME>\.codesprint\db.properties

Participants do NOT need to configure environment variables.
Run the SQL evaluator normally from its question folder.
```

If you see this, the database setup is complete.

---

# Step 8 — Verify the generated configuration

Run:

```powershell
Get-Content "$HOME\.codesprint\db.properties"
```

You should see:

```text
host=127.0.0.1
port=3306
user=codesprint
database=codesprint
password=********
mysql-client=...
```

### IMPORTANT

Do NOT share the password.

Do NOT commit this file to GitHub.

Do NOT copy this password into the competition repository.

The file is local machine configuration.

---

# Step 9 — Test NEW Q9

Go to the NEW Q9 folder.

For example:

```powershell
cd .\Java\Q9_Easy_Latest2020LoginNEW\
```

Check the files:

```powershell
dir
```

You should have:

```text
Latest2020Login.sql
SqlTestConfig.java
TestLatest2020Login.java
problem.md
run.ps1
run.sh
```

---

# Step 10 — Put the participant SQL into the SQL file

The participant writes their answer inside:

```text
Latest2020Login.sql
```

For testing the setup, use the known correct query:

```sql
SELECT
    user_id,
    MAX(time_stamp) AS time_stamp
FROM Logins
WHERE time_stamp >= '2020-01-01 00:00:00'
  AND time_stamp < '2021-01-01 00:00:00'
GROUP BY user_id
ORDER BY user_id;
```

Save the file.

---

# Step 11 — Run NEW Q9 evaluator

Run:

```powershell
.\run.ps1
```

A successful test should end with:

```text
Score: 10/10
Marks: 100/100
```

All 10 test cases should show:

```text
PASS
```

---

# Step 12 — Test NEW Q10

Go back to the Java folder:

```powershell
cd ..
```

Then:

```powershell
cd .\Q10_Medium_MostFrequentProductNEW\
```

Check:

```powershell
dir
```

The folder should contain:

```text
MostFrequentProduct.sql
SqlTestConfig.java
TestMostFrequentProduct.java
problem.md
run.ps1
run.sh
```

---

# Step 13 — Test Q10

Put the correct Q10 SQL into:

```text
MostFrequentProduct.sql
```

Then run:

```powershell
.\run.ps1
```

A successful result should end with:

```text
Score: 10/10
Marks: 100/100
```

---

# Step 14 — NEW setup is successful

If both questions produce:

```text
Q9  → 10/10 → 100/100
Q10 → 10/10 → 100/100
```

the computer is ready to run the NEW SQL evaluators.

The participant does NOT need to configure:

* MySQL username
* MySQL password
* database name
* environment variables

The NEW `SqlTestConfig.java` reads the local configuration automatically.

---

# PART 2 — IF THE NEW SETUP DOES NOT WORK

If the NEW setup fails on a competition computer and there is not enough time to troubleshoot it, use the **previously working Q9/Q10 files**.

The OLD setup uses environment variables.

This is the fallback method.

---

# PART 3 — OLD SETUP / ENVIRONMENT VARIABLE METHOD

## Step 1 — Use the previous Q9/Q10 folders

Use the previously working Q9 and Q10 folders that were already tested and committed.

Do NOT mix the NEW evaluator files with the OLD configuration method.

The OLD evaluator expects its database connection information to come from environment variables.

---

# Step 2 — Make sure MySQL is running

Check:

```powershell
mysql --version
```

Then make sure your MySQL server is running.

---

# Step 3 — Create/use the CodeSprint database

The OLD evaluator requires the database to exist.

Use the database configuration expected by the OLD evaluator.

The usual configuration is:

```text
Host: 127.0.0.1
Port: 3306
Database: codesprint
Username: codesprint
Password: <your configured password>
```

Use the actual credentials configured for the competition computer.

---

# Step 4 — Configure environment variables

The OLD evaluator reads the database configuration from environment variables.

Set the required variables in PowerShell.

Example:

```powershell
$env:CODESPRINT_DB_HOST="127.0.0.1"
$env:CODESPRINT_DB_PORT="3306"
$env:CODESPRINT_DB_USER="codesprint"
$env:CODESPRINT_DB_NAME="codesprint"
$env:CODESPRINT_DB_PASSWORD="YOUR_PASSWORD"
```

Replace:

```text
YOUR_PASSWORD
```

with the actual password for the CodeSprint database user.

---

# Step 5 — Configure MySQL client

If the OLD evaluator requires the MySQL client path, configure:

```powershell
$env:MYSQL_CLIENT="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
```

If MySQL is available through PATH, the evaluator may be able to use:

```powershell
$env:MYSQL_CLIENT="mysql"
```

Use the configuration that matches the OLD evaluator.

---

# Step 6 — Verify environment variables

Run:

```powershell
$env:CODESPRINT_DB_HOST
```

Expected:

```text
127.0.0.1
```

Run:

```powershell
$env:CODESPRINT_DB_PORT
```

Expected:

```text
3306
```

Run:

```powershell
$env:CODESPRINT_DB_USER
```

Expected:

```text
codesprint
```

Run:

```powershell
$env:CODESPRINT_DB_NAME
```

Expected:

```text
codesprint
```

Do NOT print the password publicly.

---

# Step 7 — Run OLD Q9 evaluator

Go to the OLD Q9 folder.

Example:

```powershell
cd C:\NEAL\CODESPRINT\CodeSprint\Java\Q9_Easy_Latest2020Login
```

Run the evaluator according to the files in that folder.

For example:

```powershell
javac TestLatest2020Login.java
java TestLatest2020Login
```

If that folder already has a `run.ps1`, use:

```powershell
.\run.ps1
```

---

# Step 8 — Run OLD Q10 evaluator

Go to:

```powershell
cd ..\Q10_Medium_MostFrequentProduct
```

Then run the evaluator according to the existing files.

For example:

```powershell
javac TestMostFrequentProduct.java
java TestMostFrequentProduct
```

or:

```powershell
.\run.ps1
```

if the OLD folder contains the script.

---

# PART 4 — IMPORTANT: NEW vs OLD

Do not mix the two methods.

## NEW method

```text
setup-windows.ps1
        |
        v
~/.codesprint/db.properties
        |
        v
SqlTestConfig.java
        |
        v
run.ps1
        |
        v
Evaluator
```

Environment variables are **not required**.

---

## OLD method

```text
Manually configure environment variables
        |
        v
Evaluator
        |
        v
MySQL
```

The OLD method is the fallback if the NEW infrastructure cannot be used.

---

# PART 5 — Quick Competition-Day Procedure

## TRY THIS FIRST — NEW SETUP

Run:

```powershell
cd C:\path\to\CodeSprint
```

Then:

```powershell
java -version
```

Then:

```powershell
mysql --version
```

If PowerShell blocks the setup script:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

Then:

```powershell
.\setup\setup-windows.ps1
```

Enter the MySQL root password.

Verify:

```powershell
Get-Content "$HOME\.codesprint\db.properties"
```

Test Q9:

```powershell
cd .\Java\Q9_Easy_Latest2020LoginNEW\
.\run.ps1
```

Test Q10:

```powershell
cd ..\Q10_Medium_MostFrequentProductNEW\
.\run.ps1
```

If both work:

```text
Q9  → 10/10
Q10 → 10/10
```

**DONE.**

---

# PART 6 — FALLBACK IF NEW SETUP FAILS

If the NEW setup cannot be made to work:

1. Stop using the NEW Q9/Q10 evaluator.
2. Use the previously working Q9/Q10 folders.
3. Configure the required environment variables.
4. Verify the variables.
5. Run the OLD evaluators.
6. Confirm the evaluator can connect to MySQL.
7. Test Q9.
8. Test Q10.

The OLD setup is the backup plan.

---

# FINAL RULE FOR COORDINATORS

### Do this first:

```text
NEW setup
    ↓
setup-windows.ps1
    ↓
db.properties
    ↓
NEW Q9 evaluator
    ↓
NEW Q10 evaluator
```

### Only if that fails:

```text
OLD Q9/Q10
    ↓
Set environment variables
    ↓
Run old evaluators
```

Do not mix NEW and OLD evaluator/configuration files during the competition.

Keep database passwords private and never commit them to the repository.
