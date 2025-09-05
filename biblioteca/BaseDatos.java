package biblioteca;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class BaseDatos {
    private final String url; // jdbc:sqlite:biblioteca.db

    public BaseDatos(String rutaDb) {
        this.url = "jdbc:sqlite:" + rutaDb;
    }

    private Connection conexion() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        c.setAutoCommit(false);
        return c;
    }

    public Optional<FilaLibro> buscarPorIsbn(Connection conn, String isbn) throws SQLException {
        String sql = "SELECT isbn, title, author, total_copies, borrowed_copies FROM books WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new FilaLibro(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("total_copies"),
                        rs.getInt("borrowed_copies")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<FilaLibro> buscarPorTitulo(Connection conn, String titulo) throws SQLException {
        String sql = "SELECT isbn, title, author, total_copies, borrowed_copies FROM books WHERE title = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new FilaLibro(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("total_copies"),
                        rs.getInt("borrowed_copies")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public ResultadoPrestamo intentarPrestamoPorIsbn(String isbn, String usuarioId) {
        try (Connection conn = conexion()) {
            Optional<FilaLibro> ob = buscarPorIsbn(conn, isbn);
            if (!ob.isPresent()) { conn.rollback(); return new ResultadoPrestamo(false, null, "No existe ISBN"); }
            FilaLibro libro = ob.get();
            int disponibles = libro.totalCopias - libro.copiasPrestadas;
            if (disponibles <= 0) { conn.rollback(); return new ResultadoPrestamo(false, null, "No hay ejemplares disponibles"); }
            String update = "UPDATE books SET borrowed_copies = borrowed_copies + 1 WHERE isbn = ?";
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                ps.setString(1, isbn);
                ps.executeUpdate();
            }
            conn.commit();
            String fecha = LocalDate.now().plusDays(7).toString();
            return new ResultadoPrestamo(true, fecha, "Préstamo realizado. Fecha de devolución: " + fecha);
        } catch (SQLException e) {
            // System.err.println("Error DB: " + e.getMessage());
            return new ResultadoPrestamo(false, null, "Error DB: " + e.getMessage());
        }
    }

    public ResultadoConsulta consultarPorIsbn(String isbn) {
        try (Connection conn = conexion()) {
            Optional<FilaLibro> ob = buscarPorIsbn(conn, isbn);
            conn.rollback();
            if (!ob.isPresent()) return new ResultadoConsulta(false, 0, null, "No existe libro");
            FilaLibro l = ob.get();
            int disp = l.totalCopias - l.copiasPrestadas;
            return new ResultadoConsulta(true, disp, l.titulo, "OK");
        } catch (SQLException e) {
            // System.err.println("Error DB: " + e.getMessage());
            return new ResultadoConsulta(false, 0, null, "Error DB: " + e.getMessage());
        }
    }

    public ResultadoDevolucion devolverPorIsbn(String isbn, String usuarioId) {
        try (Connection conn = conexion()) {
            Optional<FilaLibro> ob = buscarPorIsbn(conn, isbn);
            if (!ob.isPresent()) { conn.rollback(); return new ResultadoDevolucion(false, "No existe libro"); }
            FilaLibro l = ob.get();
            if (l.copiasPrestadas <= 0) { conn.rollback(); return new ResultadoDevolucion(false, "No hay préstamos registrados"); }
            String update = "UPDATE books SET borrowed_copies = borrowed_copies - 1 WHERE isbn = ?";
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                ps.setString(1, isbn);
                ps.executeUpdate();
            }
            conn.commit();
            return new ResultadoDevolucion(true, "Devolución registrada");
        } catch (SQLException e) {
            // System.err.println("Error DB: " + e.getMessage());
            return new ResultadoDevolucion(false, "Error DB: " + e.getMessage());
        }
    }

    public static class FilaLibro {
        public final String isbn, titulo, autor;
        public final int totalCopias, copiasPrestadas;
        public FilaLibro(String isbn, String titulo, String autor, int total, int prestadas) {
            this.isbn = isbn; this.titulo = titulo; this.autor = autor;
            this.totalCopias = total; this.copiasPrestadas = prestadas;
        }
    }
}
