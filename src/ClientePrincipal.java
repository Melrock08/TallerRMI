package biblioteca;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientePrincipal {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java biblioteca.ClientePrincipal <ip-servidor>");
            System.exit(1);
        }
        String host = args[0];
        try {
            Registry registro = LocateRegistry.getRegistry(host, 1099);
            ServicioBiblioteca servicio = (ServicioBiblioteca) registro.lookup("ServicioBiblioteca");
            System.out.println("Conectado a servidor " + host);
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Comandos: consultar <isbn> | prestar_isbn <isbn> <usuario> | prestar_titulo <titulo> <usuario> | devolver <isbn> <usuario> | salir");
                while (true) {
                    System.out.print("> ");
                    String linea = sc.nextLine().trim();
                    if (linea.isEmpty()) continue;
                    if (linea.equalsIgnoreCase("salir")) break;
                    String[] partes = linea.split(" ", 3);
                    String cmd = partes[0];
                    try {
                        if (cmd.equalsIgnoreCase("consultar") && partes.length >= 2) {
                            ResultadoConsulta r = servicio.consultarPorIsbn(partes[1]);
                            System.out.println("existe=" + r.existe + " titulo=" + r.titulo + " disponibles=" + r.disponibles + " msg=" + r.mensaje);
                        } else if (cmd.equalsIgnoreCase("prestar_isbn") && partes.length >= 3) {
                            ResultadoPrestamo r = servicio.prestarPorIsbn(partes[1], partes[2]);
                            System.out.println("exito=" + r.exito + " devolucion=" + r.fechaDevolucion + " msg=" + r.mensaje);
                        } else if (cmd.equalsIgnoreCase("prestar_titulo") && partes.length >= 3) {
                            ResultadoPrestamo r = servicio.prestarPorTitulo(partes[1], partes[2]);
                            System.out.println("exito=" + r.exito + " devolucion=" + r.fechaDevolucion + " msg=" + r.mensaje);
                        } else if (cmd.equalsIgnoreCase("devolver") && partes.length >= 3) {
                            ResultadoDevolucion r = servicio.devolverPorIsbn(partes[1], partes[2]);
                            System.out.println("exito=" + r.exito + " msg=" + r.mensaje);
                        } else {
                            System.out.println("Comando inválido.");
                        }
                    } catch (java.rmi.RemoteException ex) {
                        System.out.println("Error de comunicación con el servidor: " + ex.getMessage());
                    } catch (Exception ex) {
                        System.out.println("Error inesperado: " + ex.getMessage());
                    }
                }
            }
        } catch (java.rmi.NotBoundException ex) {
            System.out.println("El servicio no está registrado en el servidor: " + ex.getMessage());
        } catch (java.rmi.RemoteException ex) {
            System.out.println("No se pudo conectar al servidor: " + ex.getMessage());
        }
    }
}
