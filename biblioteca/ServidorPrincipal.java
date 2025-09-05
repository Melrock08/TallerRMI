package biblioteca;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorPrincipal {
    public static void main(String[] args) {
        String rutaDb = "biblioteca.db";
        int puerto = 1099;
        try {
            Registry registro = LocateRegistry.createRegistry(puerto);
            ServicioBibliotecaImpl impl = new ServicioBibliotecaImpl(rutaDb);
            registro.rebind("ServicioBiblioteca", impl);
            System.out.println("Servidor RMI activo en puerto " + puerto + " con BD: " + rutaDb);
        } catch (java.rmi.RemoteException e) {
            System.err.println("RemoteException iniciando servidor: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error iniciando servidor: " + e.getMessage());
        }
    }
}

