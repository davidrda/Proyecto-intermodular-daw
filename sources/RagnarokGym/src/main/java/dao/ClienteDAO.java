package dao;

import model.Cliente;
import database.DBConnection;
import database.SchemDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection connection;

    public ClienteDAO() {
        connection = DBConnection.getConnection();
    }

    public int insertar(Cliente cliente){

        String sql = "INSERT INTO clientes (nombre, apellidos, dni, email, telefono, fecha_alta, fecha_baja)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            asignarCamposCliente(ps, cliente);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public List<Cliente> listarTodos() {

        String sql = "SELECT * FROM clientes";

        List<Cliente> listaClientes = new ArrayList<>();

        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {
                listaClientes.add(mapearCliente(resultSet));
            }

            return listaClientes;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Cliente buscarPorId(int id){

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int actualizar(Cliente cliente){

        String sql =
                "UPDATE clientes SET nombre=?, apellidos=?, dni=?, email=?, telefono=?, fecha_alta=?, fecha_baja=? WHERE id_cliente=?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposCliente(ps, cliente);
            ps.setInt(8, cliente.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public int eliminar(int id){

        String sql = "DELETE FROM clientes WHERE id_cliente=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    // MÉTODOS
    private Cliente mapearCliente(ResultSet resultSet) throws SQLException {
        Date fechaBajaSql = resultSet.getDate(SchemDB.CLI_FECHA_BAJA);
        LocalDate fechaBaja = (fechaBajaSql != null) ? fechaBajaSql.toLocalDate() : null;

        Cliente cliente = new Cliente(
                resultSet.getInt(SchemDB.CLI_ID),
                resultSet.getString(SchemDB.CLI_NOMBRE),
                resultSet.getString(SchemDB.CLI_APELLIDOS),
                resultSet.getString(SchemDB.CLI_DNI),
                resultSet.getString(SchemDB.CLI_EMAIL),
                resultSet.getString(SchemDB.CLI_TELEFONO),
                resultSet.getDate(SchemDB.CLI_FECHA_ALTA).toLocalDate(),
                fechaBaja
        );
        return cliente;
    }

    private void asignarCamposCliente(PreparedStatement ps, Cliente cliente) throws SQLException {
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
    }


}
