package dao;

import model.Reserva;
import util.DBConnection;
import util.SchemDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private Connection connection;

    public ReservaDAO() {
        connection = DBConnection.getConnection();
    }

    public int insertar(Reserva reserva){

        String sql = "INSERT INTO reservas (id_cliente=?, id_horario=?, fecha_reserva=?, estado=?)" +
                " VALUES(?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            asignarCamposReserva(ps, reserva);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public List<Reserva> listarTodos() {

        String sql = "SELECT * FROM reservas";

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
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Reserva buscarPorId(int id){

        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReserva(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int actualizar(Reserva reserva){

        String sql =
                "UPDATE reservas SET id_cliente=?, id_horario=?, fecha_reserva=?, estado=? WHERE id_reserva=?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposReserva(ps, reserva);
            ps.setInt(5, reserva.getIdReserva());
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public int eliminar(int id){

        String sql = "DELETE FROM reservas WHERE id_reserva=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;

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

        List<String> resultado = new ArrayList<>();

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String linea = "[" + rs.getInt(SchemDB.RES_ID) + "] " +
                        rs.getString(SchemDB.CLI_NOMBRE) + " " +
                        rs.getString(SchemDB.CLI_APELLIDOS) +
                        " | " + rs.getString(SchemDB.CLA_NOMBRE) +
                        " | " + rs.getString(SchemDB.HOR_DIA_SEMANA) +
                        " " + rs.getTime(SchemDB.HOR_HORA_INICIO) +
                        "-" + rs.getTime(SchemDB.HOR_HORA_FIN) +
                        " | Fecha: " + rs.getDate(SchemDB.RES_FECHA_RESERVA).toLocalDate() +
                        " | Estado: " + rs.getString(SchemDB.RES_ESTADO);
                resultado.add(linea);
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // MÉTODOS
    private Reserva mapearReserva(ResultSet rs) throws SQLException {

        Reserva reserva = new Reserva(
                rs.getInt(SchemDB.RES_ID_CLIENTE),
                rs.getInt(SchemDB.RES_ID),
                rs.getInt(SchemDB.RES_ID_HORARIO),
                rs.getDate(SchemDB.RES_FECHA_RESERVA).toLocalDate(),
                rs.getString(SchemDB.RES_ESTADO)
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
