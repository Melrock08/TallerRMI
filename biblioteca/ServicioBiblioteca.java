package biblioteca;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioBiblioteca extends Remote {
    ResultadoPrestamo prestarPorIsbn(String isbn, String usuarioId) throws RemoteException;
    ResultadoPrestamo prestarPorTitulo(String titulo, String usuarioId) throws RemoteException;
    ResultadoConsulta consultarPorIsbn(String isbn) throws RemoteException;
    ResultadoDevolucion devolverPorIsbn(String isbn, String usuarioId) throws RemoteException;
}
