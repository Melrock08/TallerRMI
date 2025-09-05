package biblioteca;

import java.io.Serializable;

public class ResultadoDevolucion implements Serializable {
    public boolean exito;
    public String mensaje;

    public ResultadoDevolucion() {}
    public ResultadoDevolucion(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }
}
