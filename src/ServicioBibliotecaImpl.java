package biblioteca;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

public class ServicioBibliotecaImpl extends UnicastRemoteObject implements ServicioBiblioteca {
    private final BaseDatos db;

    protected ServicioBibliotecaImpl(String rutaDb) throws RemoteException {
        super();
        this.db = new BaseDatos(rutaDb);
    }

    @Override
    public ResultadoPrestamo prestarPorIsbn(String isbn, String usuarioId) throws RemoteException {
        return db.intentarPrestamoPorIsbn(isbn, usuarioId);
    }

    @Override
    public ResultadoPrestamo prestarPorTitulo(String titulo, String usuarioId) throws RemoteException {
        try {
            Optional<BaseDatos.FilaLibro> ob;
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:biblioteca.db")) {
                conn.setAutoCommit(false);
                ob = db.buscarPorTitulo(conn, titulo);
                conn.rollback();
            }
            if (!ob.isPresent()) return new ResultadoPrestamo(false, null, "No existe título");
            return db.intentarPrestamoPorIsbn(ob.get().isbn, usuarioId);
        } catch (java.sql.SQLException e) {
            // Log the exception or handle it appropriately
            return new ResultadoPrestamo(false, null, "Error de base de datos: " + e.getMessage());
        }
    }

    @Override
    public ResultadoConsulta consultarPorIsbn(String isbn) throws RemoteException {
        return db.consultarPorIsbn(isbn);
    }

    @Override
    public ResultadoDevolucion devolverPorIsbn(String isbn, String usuarioId) throws RemoteException {
        return db.devolverPorIsbn(isbn, usuarioId);
    }
}
