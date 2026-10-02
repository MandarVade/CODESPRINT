#!/usr/bin/env bash
set -euo pipefail

printf '\n===============================================\n'
printf ' CodeSprint SQL Environment Setup (Linux/macOS)\n'
printf '===============================================\n\n'

MYSQL_BIN="$(command -v mysql || true)"
if [[ -z "$MYSQL_BIN" ]]; then
  echo "ERROR: mysql client was not found in PATH."
  echo "Install the organizer-provided MySQL environment first."
  exit 1
fi

echo "MySQL client: $MYSQL_BIN"
echo

read -r -s -p "Enter the MySQL root password: " ROOT_PASSWORD
echo

APP_PASSWORD="$(openssl rand -hex 24 2>/dev/null || true)"
if [[ -z "$APP_PASSWORD" ]]; then
  APP_PASSWORD="$(python3 -c 'import secrets; print(secrets.token_hex(24))')"
fi

export MYSQL_PWD="$ROOT_PASSWORD"
trap 'unset MYSQL_PWD ROOT_PASSWORD' EXIT

"$MYSQL_BIN" --protocol=TCP -h 127.0.0.1 -P 3306 -u root --batch --skip-column-names <<SQL
CREATE DATABASE IF NOT EXISTS codesprint;
CREATE USER IF NOT EXISTS 'codesprint'@'localhost' IDENTIFIED BY '$APP_PASSWORD';
ALTER USER 'codesprint'@'localhost' IDENTIFIED BY '$APP_PASSWORD';
CREATE USER IF NOT EXISTS 'codesprint'@'127.0.0.1' IDENTIFIED BY '$APP_PASSWORD';
ALTER USER 'codesprint'@'127.0.0.1' IDENTIFIED BY '$APP_PASSWORD';
GRANT ALL PRIVILEGES ON codesprint.* TO 'codesprint'@'localhost';
GRANT ALL PRIVILEGES ON codesprint.* TO 'codesprint'@'127.0.0.1';
FLUSH PRIVILEGES;
SQL

CONFIG_DIR="$HOME/.codesprint"
CONFIG_PATH="$CONFIG_DIR/db.properties"
mkdir -p "$CONFIG_DIR"
cat > "$CONFIG_PATH" <<EOF_CONFIG
host=127.0.0.1
port=3306
user=codesprint
database=codesprint
password=$APP_PASSWORD
mysql-client=$MYSQL_BIN
EOF_CONFIG
chmod 600 "$CONFIG_PATH"

printf '\nDatabase setup completed successfully.\n'
printf 'Local CodeSprint database: codesprint\n'
printf 'Local judge configuration: %s\n' "$CONFIG_PATH"
printf '\nParticipants do NOT need to configure environment variables.\n'
printf 'Run the SQL evaluator normally from its question folder.\n\n'
