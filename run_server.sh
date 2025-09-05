#!/usr/bin/env bash
set -euo pipefail

JAR="lib/sqlite-jdbc-3.46.1.3.jar"
OUT_DIR="out"
DB="${1:-./biblioteca.db}"
PORT="${2:-1099}"

if [[ ! -f "$JAR" ]]; then
  echo "❌ No se encontró $JAR"
  echo "➡️  Descarga el driver JDBC de SQLite (org.xerial) y colócalo en lib/"
  exit 1
fi

if [[ ! -d "$OUT_DIR" ]]; then
  echo "❌ No existe $OUT_DIR. Compila primero: ./compile.sh"
  exit 1
fi

echo "🚀 Iniciando servidor RMI (puerto=$PORT, db=$DB) ..."
exec java -cp "$OUT_DIR:$JAR" biblioteca.ServidorPrincipal --db="$DB" --port="$PORT"
