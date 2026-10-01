#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
javac TestLatest2020Login.java
java TestLatest2020Login
