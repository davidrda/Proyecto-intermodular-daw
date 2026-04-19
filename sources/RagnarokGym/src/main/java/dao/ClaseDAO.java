package dao;


import model.Clase;
import database.DBConnection;
import database.SchemDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {
    private Connection connection;

    public ClaseDAO() {
        connection = DBConnection.getConnection();
    }

    public int insertar(Clase clase) {
        String sql =
                "INSERT INTO clases (id_entrenador, id_sala, nombre, nivel, duracion_minutos) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposClase(ps, clase);
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public List<Clase> listarTodos(){
        String sql = "SELECT * FROM clases";
        List<Clase> listaClases = new ArrayList<>();
        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()){
                listaClases.add(mapearClase(rs));
            }
            return listaClases;
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Clase buscarPorId (int id){
        String sql = "SELECT * FROM clases where id_clase = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearClase(rs);
                }
            }
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int actualizar(Clase clase){
        String sql = "UPDATE clases SET id_entrenador=?, id_sala=?, nombre=?, nivel=?, duracion_minutos=? WHERE id_clase=?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposClase(ps, clase);
            ps.setInt(6, clase.getIdClase());
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " +e.getMessage());
        }
        return -1;
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM clases WHERE id_clase = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                throw new IllegalArgumentException("No se puede eliminar la clase porque tiene datos asociados");
            }
            throw new IllegalArgumentException("Error al eliminar la clase: " + e.getMessage());
        }
    }

    // MÉTODOS AUXILIARES
    private Clase mapearClase(ResultSet rs) throws SQLException {
        Clase clase = new Clase(
                rs.getInt(SchemDB.CLA_ID),
                rs.getInt(SchemDB.CLA_ID_ENTRENADOR),
                rs.getInt(SchemDB.CLA_ID_SALA),
                rs.getString(SchemDB.CLA_NOMBRE),
                rs.getString(SchemDB.CLA_NIVEL),
                rs.getInt(SchemDB.CLA_DURACION)
        );
        return clase;
    }

    private void asignarCamposClase(PreparedStatement ps, Clase clase) throws SQLException {
        ps.setInt(1, clase.getIdEntrenador());
        ps.setInt(2, clase.getIdSala());
        ps.setString(3, clase.getNombre());
        ps.setString(4, clase.getNivel());
        ps.setInt(5, clase.getDuracionMinutos());
    }
}
