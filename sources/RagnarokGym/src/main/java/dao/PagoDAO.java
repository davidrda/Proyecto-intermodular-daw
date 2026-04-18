package dao;

import model.Pago;
import util.DBConnection;
import util.SchemDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private Connection connection;

    public PagoDAO() {
        connection = DBConnection.getConnection();
    }

    public int insertar(Pago pago) {
        String sql = "INSERT INTO pagos (id_servicio, id_cliente, metodo_pago, fecha_pago, importe, estado)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposPago(ps, pago);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public List<Pago> listarTodos() {
        String sql = "SELECT * FROM pagos";
        List<Pago> listaPagos = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                listaPagos.add(mapearPago(rs));
            }
            return listaPagos;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Pago buscarPorId(int id) {
        String sql = "SELECT * FROM pagos WHERE id_pago = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPago(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int actualizar(Pago pago) {
        String sql = "UPDATE pagos SET id_servicio=?, id_cliente=?, metodo_pago=?, fecha_pago=?, importe=?, estado=?" +
                " WHERE id_pago=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            asignarCamposPago(ps, pago);
            ps.setInt(7, pago.getIdPago());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id_pago = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    // MÉTODOS AUXILIARES
    private Pago mapearPago(ResultSet rs) throws SQLException {
        Pago pago = new Pago(
                rs.getInt(SchemDB.PAG_ID),
                rs.getInt(SchemDB.PAG_ID_SERVICIO),
                rs.getInt(SchemDB.PAG_ID_CLIENTE),
                rs.getString(SchemDB.PAG_METODO_PAGO),
                rs.getDate(SchemDB.PAG_FECHA_PAGO).toLocalDate(),
                rs.getDouble(SchemDB.PAG_IMPORTE),
                rs.getString(SchemDB.PAG_ESTADO)
        );
        return pago;
    }

    private void asignarCamposPago(PreparedStatement ps, Pago pago) throws SQLException {
        ps.setInt(1, pago.getIdServicio());
        ps.setInt(2, pago.getIdCliente());
        ps.setString(3, pago.getMetodoPago());
        ps.setDate(4, Date.valueOf(pago.getFechaPago()));
        ps.setDouble(5, pago.getImporte());
        ps.setString(6, pago.getEstado());
    }
}
