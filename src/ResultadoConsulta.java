package biblioteca;

import java.io.Serializable;

public class ResultadoConsulta implements Serializable {
    public boolean existe;
    public int disponibles;
    public String titulo;
    public String mensaje;

    public ResultadoConsulta() {}

    public ResultadoConsulta(boolean existe, int disponibles, String titulo, String mensaje) {
        this.existe = existe;
        this.disponibles = disponibles;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }
}
