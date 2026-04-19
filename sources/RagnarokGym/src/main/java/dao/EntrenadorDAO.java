package dao;

import model.Entrenador;
import database.DBConnection;
import database.SchemDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    private Connection connection;

    public EntrenadorDAO() {
        connection = DBConnection.getConnection();
    }

    public int insertar(Entrenador entrenador) {
        String sql =
                "INSERT INTO entrenadores (nombre, apellidos, dni, email, telefono, especialidad, fecha_contratacion, fecha_baja)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposEntrenador(ps, entrenador);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public List<Entrenador> listarTodos() {
        String sql = "SELECT * FROM entrenadores";
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
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Entrenador buscarPorId(int id) {
        String sql = "SELECT * FROM entrenadores WHERE id_entrenador=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mapearEntrenador(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


    public int actualizar(Entrenador entrenador) {
        String sql =
                "UPDATE entrenadores SET nombre=?, apellidos=?, dni=?, email=?, telefono=?, especialidad=?, fecha_contratacion=?, fecha_baja=? " +
                        "WHERE id_entrenador=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposEntrenador(ps, entrenador);
            ps.setInt(9, entrenador.getIdEntrenador());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM entrenadores WHERE id_entrenador=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;

    }

    // MÉTODOS
    private Entrenador mapearEntrenador(ResultSet resultSet) throws SQLException {
        Date fechaBajaSql = resultSet.getDate(SchemDB.ENT_FECHA_BAJA);
        LocalDate fechaBaja = (fechaBajaSql != null) ? fechaBajaSql.toLocalDate() : null;

        Entrenador entrenador = new Entrenador(
                resultSet.getInt(SchemDB.ENT_ID),
                resultSet.getString(SchemDB.ENT_NOMBRE),
                resultSet.getString(SchemDB.ENT_APELLIDOS),
                resultSet.getString(SchemDB.ENT_DNI),
                resultSet.getString(SchemDB.ENT_EMAIL),
                resultSet.getString(SchemDB.ENT_TELEFONO),
                resultSet.getString(SchemDB.ENT_ESPECIALIDAD),
                resultSet.getDate(SchemDB.ENT_FECHA_CONTRATACION).toLocalDate(),
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
