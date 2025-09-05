# Biblioteca RMI (Java + SQLite) — Guía rápida

Este README te deja el proyecto listo para **compilar y ejecutar** el servidor y el cliente con **Java + SQLite** usando el **driver JDBC**. Es la versión **sencilla** sin Maven/Gradle.

## Estructura sugerida
```
.
├─ lib/
│  └─ sqlite-jdbc-<version>.jar
├─ src/
│  └─ biblioteca/
│     ├─ BaseDatos.java
│     ├─ ServicioBiblioteca.java
│     ├─ ServicioBibliotecaImpl.java
│     ├─ ServidorPrincipal.java
│     ├─ ClientePrincipal.java
│     ├─ ResultadoConsulta.java
│     ├─ ResultadoPrestamo.java
│     └─ ResultadoDevolucion.java
├─ seed.sql
├─ biblioteca.db
├─ compile.sh
├─ run_server.sh
├─ run_client.sh
└─ README.md
```

## Requisitos
- Java 17+
- Driver SQLite JDBC en `lib/` (ej. `sqlite-jdbc-3.46.1.3.jar`)

## Base de datos (opcional)
```bash
sqlite3 biblioteca.db < seed.sql
sqlite3 biblioteca.db ".tables"
```

## Scripts
Concede permisos una vez:
```bash
chmod +x compile.sh run_server.sh run_client.sh
```

Compilar:
```bash
./compile.sh
```

Servidor:
```bash
./run_server.sh        # usa ./biblioteca.db y puerto 1099 por defecto
# ./run_server.sh ./otra.db 2001   # parámetros opcionales
```

Cliente:
```bash
./run_client.sh        # usa 127.0.0.1:1099 por defecto
# ./run_client.sh 127.0.0.1 2001   # parámetros opcionales
```

Comandos del cliente:
```
consultar <isbn>
prestar_isbn <isbn> <usuario>
prestar_titulo <titulo> <usuario>
devolver <isbn> <usuario>
salir
```

## Solución de problemas
- **No se encuentra clase main**: revisa `package biblioteca;`, compila con `-d out` y ejecuta con `-cp "out:lib/..."`.
- **No suitable driver**: falta el `.jar` en `lib/` y en el classpath.
- **Windows**: cambia `:` por `;` en el classpath.
