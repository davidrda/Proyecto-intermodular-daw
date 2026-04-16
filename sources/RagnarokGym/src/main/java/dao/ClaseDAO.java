package dao;


import model.Clase;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {

    public void insertar(Clase clase) {
        String sql =
                "INSERT INTO clases (id_entrenador, id_sala, nombre, nivel, duracion_minutos) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposClase(ps, clase);
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la inserción de clases: " + e.getMessage());
        }
    }

    public List<Clase> listarTodos(){
        String sql = "SELECT * FROM clases";
        Connection connection = DBConnection.getConnection();
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
            System.out.println("Error en listar todas las clases: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Clase buscarPorId (int id){
        String sql = "SELECT * FROM clases where id_clase = ?";
        Connection connection = DBConnection.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearClase(rs);
                }
            }
        } catch (SQLException e){
            System.out.println("Error en buscar por id de clase: " + e.getMessage());
        }
        return null;
    }

    public void actualizar(Clase clase){
        String sql = "UPDATE clases SET id_entrenador=?, id_sala=?, nombre=?, nivel=?, duracion_minutos=? WHERE id_clase=?";
        Connection connection = DBConnection.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposClase(ps, clase);
            ps.setInt(6, clase.getIdClase());
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en actualizar la clase: " +e.getMessage());
        }
    }

    public void eliminar(int id){
        String sql = "DELETE FROM clases WHERE id_clase = ?";
        Connection connection = DBConnection.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la eliminación de la clase: " + e.getMessage());
        }
    }

    // MÉTODOS AUXILIARES
    private Clase mapearClase(ResultSet rs) throws SQLException {
        Clase clase = new Clase(
                rs.getInt("id_clase"),
                rs.getInt("id_entrenador"),
                rs.getInt("id_sala"),
                rs.getString("nombre"),
                rs.getString("nivel"),
                rs.getInt("duracion_minutos")
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
