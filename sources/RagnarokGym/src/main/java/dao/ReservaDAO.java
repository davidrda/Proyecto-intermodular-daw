package dao;

import model.Reserva;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void insertar(Reserva reserva){

        String sql = "INSERT INTO reservas (id_cliente=?, id_horario=?, fecha_reserva=?, estado=?)" +
                " VALUES(?, ?, ?, ?)";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            asignarCamposReserva(ps, reserva);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar reserva " + e.getMessage());
        }
    }

    public List<Reserva> listarTodos() {

        String sql = "SELECT * FROM reservas";

        Connection connection = DBConnection.getConnection();

        List<Reserva> listaReservas = new ArrayList<>();

        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {
                listaReservas.add(mapearReserva(resultSet));
            }

            return listaReservas;
        } catch (SQLException e) {
            System.out.println("Error al listar todas las reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Reserva buscarPorId(int id){

        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReserva(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la búsqueda de la reservas: " + e.getMessage());
        }

        return null;
    }

    public void actualizar(Reserva reserva){

        String sql =
                "UPDATE reservas SET id_cliente=?, id_horario=?, fecha_reserva=?, estado=? WHERE id_reserva=?";

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposReserva(ps, reserva);
            ps.setInt(5, reserva.getIdReserva());
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la actualización de la reserva: " + e.getMessage());
        }
    }

    public void eliminar(int id){

        String sql = "DELETE FROM reservas WHERE id_reserva=?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la eliminación de la reserva: " + e.getMessage());
        }

    }

    public List<String> listarReservasDetalladas() {
        String sql = "SELECT r.id_reserva, " +
                "c.nombre AS cliente_nombre, c.apellidos AS cliente_apellidos, " +
                "cl.nombre AS clase_nombre, " +
                "h.dia_semana, h.hora_inicio, h.hora_fin, " +
                "r.fecha_reserva, r.estado " +
                "FROM reservas r " +
                "JOIN clientes c ON r.id_cliente = c.id_cliente " +
                "JOIN horarios h ON r.id_horario = h.id_horario " +
                "JOIN clases cl ON h.id_clase = cl.id_clase";

        Connection connection = DBConnection.getConnection();
        List<String> resultado = new ArrayList<>();

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String linea = "[" + rs.getInt("id_reserva") + "] " +
                        rs.getString("cliente_nombre") + " " +
                        rs.getString("cliente_apellidos") +
                        " | " + rs.getString("clase_nombre") +
                        " | " + rs.getString("dia_semana") +
                        " " + rs.getTime("hora_inicio") +
                        "-" + rs.getTime("hora_fin") +
                        " | Fecha: " + rs.getDate("fecha_reserva").toLocalDate() +
                        " | Estado: " + rs.getString("estado");
                resultado.add(linea);
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println("Error al listar reservas detalladas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /* ========================
     MÉTODOS DE REFACTORIZACIÓN
    ======================== */

    private Reserva mapearReserva(ResultSet rs) throws SQLException {

        Reserva reserva = new Reserva(
                rs.getInt("id_cliente"),
                rs.getInt("id_reserva"),
                rs.getInt("id_horario"),
                rs.getDate("fecha_reserva").toLocalDate(),
                rs.getString("estado")
        );
        return reserva;
    }

    private void asignarCamposReserva(PreparedStatement ps, Reserva reserva) throws SQLException {
        ps.setInt(1, reserva.getIdCliente());
        ps.setInt(2, reserva.getIdHorario());
        ps.setDate(3, Date.valueOf(reserva.getFechaReserva()));
        ps.setString(4, reserva.getEstado());
    }
}
