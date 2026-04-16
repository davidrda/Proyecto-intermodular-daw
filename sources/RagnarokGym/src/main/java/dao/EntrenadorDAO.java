package dao;

import model.Entrenador;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    public void insertar(Entrenador entrenador) {
        String sql =
                "INSERT INTO entrenadores (nombre, apellidos, dni, email, telefono, especialidad, fecha_contratacion, fecha_baja)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposEntrenador(ps, entrenador);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en la inserción de datos de entrenador: " + e.getMessage());
        }
    }

    public List<Entrenador> listarTodos() {
        String sql = "SELECT * FROM entrenadores";

        Connection connection = DBConnection.getConnection();
        List<Entrenador> listaEntrenadores = new ArrayList<>();

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                listaEntrenadores.add(mapearEntrenador(rs));
            }
            return listaEntrenadores;
        } catch (SQLException e) {
            System.out.println("Error al listar todos los entrenadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Entrenador buscarPorId(int id) {
        String sql = "SELECT * FROM entrenadores WHERE id_entrenador=?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mapearEntrenador(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la búsqueda del entrenador: " + e.getMessage());
        }
        return null;
    }


    public void actualizar(Entrenador entrenador) {
        String sql =
                "UPDATE entrenadores SET nombre=?, apellidos=?, dni=?, email=?, telefono=?, especialidad=?, fecha_contratacion=?, fecha_baja=? " +
                        "WHERE " +
                        "id_entrenador=?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposEntrenador(ps, entrenador);
            ps.setInt(9, entrenador.getIdEntrenador());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en la actualización de entrenador: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM entrenadores WHERE id_entrenador=?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en la eliminación de entrenador: " + e.getMessage());
        }

    }

    /* =============
    MÉTODOS
    ============= */
    private Entrenador mapearEntrenador(ResultSet resultSet) throws SQLException {
        Date fechaBajaSql = resultSet.getDate("fecha_baja");
        LocalDate fechaBaja = (fechaBajaSql != null) ? fechaBajaSql.toLocalDate() : null;

        Entrenador entrenador = new Entrenador(
                resultSet.getInt("id_entrenador"),
                resultSet.getString("nombre"),
                resultSet.getString("apellidos"),
                resultSet.getString("dni"),
                resultSet.getString("email"),
                resultSet.getString("telefono"),
                resultSet.getString("especialidad"),
                resultSet.getDate("fecha_contratacion").toLocalDate(),
                fechaBaja
        );
        return entrenador;
    }

    private void asignarCamposEntrenador(PreparedStatement ps, Entrenador entrenador) throws SQLException {
        ps.setString(1, entrenador.getNombre());
        ps.setString(2, entrenador.getApellidos());
        ps.setString(3, entrenador.getDni());
        ps.setString(4, entrenador.getEmail());
        ps.setString(5, entrenador.getTelefono());
        ps.setString(6, entrenador.getEspecialidad());
        ps.setDate(7, Date.valueOf(entrenador.getFechaContratacion()));

        if (entrenador.getFechaBaja() != null) {
            ps.setDate(8, Date.valueOf(entrenador.getFechaBaja()));
        } else {
            ps.setNull(8, Types.DATE);
        }
    }
}
