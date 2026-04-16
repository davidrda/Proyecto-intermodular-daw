package dao;

import model.Cliente;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void insertar(Cliente cliente){

        String sql = "INSERT INTO clientes (nombre, apellidos, dni, email, telefono, fecha_alta, fecha_baja)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setDate(6, Date.valueOf(cliente.getFechaAlta()));

            if (cliente.getFechaBaja() != null){
                ps.setDate(7, Date.valueOf(cliente.getFechaBaja()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente " + e.getMessage());
        }
    }

    public List<Cliente> listarTodos() {

        String sql = "SELECT * FROM clientes";

        Connection connection = DBConnection.getConnection();

        List<Cliente> listaClientes = new ArrayList<>();

        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {

                Date fechaBajaSql = resultSet.getDate("fecha_baja");
                LocalDate fechaBaja = (fechaBajaSql != null) ? fechaBajaSql.toLocalDate() : null;

                Cliente cliente = new Cliente(
                        resultSet.getInt("id_cliente"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("dni"),
                        resultSet.getString("email"),
                        resultSet.getString("telefono"),
                        resultSet.getDate("fecha_alta").toLocalDate(),
                        fechaBaja
                );

                listaClientes.add(cliente);

            }

            return listaClientes;
        } catch (SQLException e) {
            System.out.println("Error al listar todos los clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Cliente buscarPorId(int id){

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {

                if (resultSet.next()) {

                    Date fechaBajaSql = resultSet.getDate("fecha_baja");
                    LocalDate fechaBaja = (fechaBajaSql != null) ? fechaBajaSql.toLocalDate() : null;

                    return new Cliente(
                            resultSet.getInt("id_cliente"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellidos"),
                            resultSet.getString("dni"),
                            resultSet.getString("email"),
                            resultSet.getString("telefono"),
                            resultSet.getDate("fecha_alta").toLocalDate(),
                            fechaBaja
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la búsqueda del cliente: " + e.getMessage());
        }

        return null;
    }

    public void actualizar(Cliente cliente){

        String sql =
                "UPDATE clientes SET nombre=?, apellidos=?, dni=?, email=?, telefono=?, fecha_alta=?, fecha_baja=? WHERE id_cliente=?";

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setDate(6, Date.valueOf(cliente.getFechaAlta()));

            if (cliente.getFechaBaja() != null){
                ps.setDate(7, Date.valueOf(cliente.getFechaBaja()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setInt(8, cliente.getIdCliente());

            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la actualización del cliente: " + e.getMessage());
        }
    }

    public void eliminar(int id){

        String sql = "DELETE FROM clientes WHERE id_cliente=?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error en la eliminación del cliente: " + e.getMessage());
        }

    }



}
