# CodeSprint SQL Environment Setup

The SQL evaluators for Q9 and Q10 use a local MySQL database. The competition should not require participants to configure database environment variables manually.

## Organizer setup

Run the setup script **once on every competition PC before the competition**.

### Windows

Open PowerShell in the repository root and run:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\setup\setup-windows.ps1
```

The script:

1. Finds the local `mysql` client.
2. Asks the organizer for the MySQL `root` password.
3. Creates the `codesprint` database.
4. Creates a dedicated `codesprint` MySQL user.
5. Grants that user access only to `codesprint.*`.
6. Generates a random password for that local judge user.
7. Stores the judge configuration in `%USERPROFILE%\.codesprint\db.properties`.

The MySQL root password is **not stored in the repository**.

### Linux/macOS

```bash
chmod +x setup/setup-linux.sh
./setup/setup-linux.sh
```

The script performs the same setup and stores the local configuration at `~/.codesprint/db.properties`.

## After setup

Participants do not need to set:

- `CODESPRINT_DB_HOST`
- `CODESPRINT_DB_PORT`
- `CODESPRINT_DB_USER`
- `CODESPRINT_DB_NAME`
- `CODESPRINT_DB_PASSWORD`

The evaluator reads the local configuration automatically.

For normal use, open the question directory and run its evaluator.

### Q9

```powershell
cd Java\Q9_Easy_Latest2020Login
javac TestLatest2020Login.java
java TestLatest2020Login
```

### Q10

```powershell
cd Java\Q10_Medium_MostFrequentProduct
javac TestMostFrequentProduct.java
java TestMostFrequentProduct
```

The helper `SqlTestConfig.java` is automatically compiled by `javac` because it is in the same directory as the evaluator.

## Database design

The evaluators create their own temporary `Logins` and `Orders` tables before each test case. Therefore the organizer does **not** need to distribute a separate seed-data SQL file for Q9/Q10.

Each test starts with `DROP TABLE IF EXISTS`, then creates the required table and inserts that test's data. The participant's SQL is executed against that test data and the result is compared with the expected output.

## Manual configuration override

Environment variables are still supported for organizer debugging. If an environment variable is present, it overrides the value from `~/.codesprint/db.properties`.
