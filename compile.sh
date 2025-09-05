#!/usr/bin/env bash
set -euo pipefail

JAR="lib/sqlite-jdbc-3.46.1.3.jar"
SRC_DIR="src/biblioteca"
OUT_DIR="out"

if [[ ! -f "$JAR" ]]; then
  echo "❌ No se encontró $JAR"
  echo "➡️  Descarga el driver JDBC de SQLite (org.xerial) y colócalo en lib/"
  echo "    Ejemplo: lib/sqlite-jdbc-3.46.1.3.jar"
  exit 1
fi

if [[ ! -d "$SRC_DIR" ]]; then
  echo "❌ No existe el directorio de fuentes: $SRC_DIR"
  echo "   Ajusta SRC_DIR en compile.sh o reubica tus .java."
  exit 1
fi

mkdir -p "$OUT_DIR"
echo "🧱 Compilando fuentes de $SRC_DIR a $OUT_DIR ..."
javac -encoding UTF-8 -d "$OUT_DIR" -cp "$JAR" "$SRC_DIR"/*.java
echo "✅ Compilación OK"
