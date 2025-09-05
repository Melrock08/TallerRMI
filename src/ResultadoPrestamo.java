package biblioteca;

import java.io.Serializable;

public class ResultadoPrestamo implements Serializable {
    public boolean exito;
    public String fechaDevolucion; // "2025-09-11"
    public String mensaje;

    public ResultadoPrestamo() {}

    public ResultadoPrestamo(boolean exito, String fechaDevolucion, String mensaje) {
        this.exito = exito;
        this.fechaDevolucion = fechaDevolucion;
        this.mensaje = mensaje;
    }
}

