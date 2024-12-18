package com.mycompany.oscarssoftware.modelos;

import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anibal
 */
public class Pedido extends conexion implements sentencias{
    private int idpedido;
    private Date fecha_pedido;
    private int idEmpleado;
    private int idCliente;
    private String nombreCliente;
    private String nombreEmpleado;
    private boolean saved;

    public Pedido() {
    }
    
    

    public Pedido(int idpedido, Date fecha_pedido, int idEmpleado, int idcliente, String cliente, String empleado) {
        this.idpedido = idpedido;
        this.fecha_pedido = fecha_pedido;
        this.idEmpleado = idEmpleado;
        this.idCliente = idcliente;
        this.nombreCliente = cliente;
        this.nombreEmpleado = empleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }


    @Override
    public ArrayList consulta() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.idpedido, p.idcliente, c.nombre AS nombrecliente, p.idempleado, e.nombre AS "
                + "nombreempleado , p.fecha_pedido "
                + "FROM pedido p "
                + "JOIN cliente c ON p.idcliente = c.id "
                + "JOIN empleado e ON p.idempleado = e.idempleado "
                + "WHERE estado = true";
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("idpedido");
                int idcliente = rs.getInt("idcliente");
                int idempleado = rs.getInt("idempleado");
                Date fecha = rs.getDate("fecha_pedido");
                String cliente = rs.getString("nombrecliente");
                String empleado = rs.getString("nombreempleado");
                        
                Pedido p = new Pedido(id, fecha, idempleado, idcliente, cliente, empleado);
                pedidos.add(p);
            }
        
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return pedidos;
        
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT into pedido (idempleado, idcliente, fecha_pedido) VALUES (?, ?, ?)";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idEmpleado);
            stm.setInt(2, this.idCliente);
            stm.setDate(3, this.fecha_pedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean borrar() {
        if (eliminarDetallePedidos()) {
            String sql = "DELETE FROM pedido WHERE idpedido = ?";
            try (Connection con = getCon();
                 PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setInt(1, this.idpedido);
                stm.executeUpdate();
                return true;
            } catch (SQLException e) {
                Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE pedido SET idcliente = ?, idempleado = ?, fecha_pedido = ? where idpedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idCliente);                  
            stm.setInt(2, this.idEmpleado);
            stm.setDate(3, this.fecha_pedido);
            stm.setInt(4, this.idpedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public double consultaTotal(){
        String sql = "SELECT SUM(d.cantidad * pr.precio) AS total "
                + "FROM pedido p "
                + "JOIN detalle_pedido d ON d.idPedido = p.idpedido "
                + "JOIN producto pr ON pr.idproducto = d.idProducto "
                + "WHERE p.idpedido = ?";
        double total = 0;
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idpedido);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                total = rs.getDouble("total");
            }
          
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return total;
    }
    
    public int obtenerID(){
        String sql = "SELECT MAX(idpedido) as ultimo_id FROM pedido";
        int id = 0;
        try {
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("ultimo_id");
            }
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return id;
    }
    
    public ArrayList<DetallePedido> consultaDetalles() {
        ArrayList<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetallePedido WHERE idPedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idpedido);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdPedido(resultSet.getInt("idPedido"));
                detalle.setIdProducto(resultSet.getInt("idProducto"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
                detalle.setPrecioUnit(resultSet.getDouble("precioUnit"));
                detalle.setPrecioTotal(resultSet.getDouble("precioTotal"));

                detalles.add(detalle);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return detalles;
    }
    
    public boolean eliminarDetallePedidos() {
        String sql = "DELETE FROM detalle_pedido WHERE idpedido = ?";
        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idpedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean modificarEstado(){
        String sql = "UPDATE pedido SET estado = 0 WHERE idpedido = ?";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idpedido);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    
public double ingresosTotales() {
    String sql = "SELECT SUM(d.cantidad * pr.precio) - SUM(d.cantidad * pr.costo) AS total " +
                 "FROM pedido p " +
                 "JOIN detalle_pedido d ON d.idPedido = p.idpedido " +
                 "JOIN producto pr ON pr.idproducto = d.idProducto " +
                 "WHERE p.estado = ?";
    try {
        Connection con = getCon();
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setBoolean(1, false);  // Asegúrate de que "estado" es un booleano.
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getDouble("total");
        }
    } catch (SQLException e) {
        Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
    }
    return 0;
}

    public int pedidosPendientes(){
        String sql = "SELECT count(*) from pedido where estado = ?";
        int cantidad = 0;
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setBoolean(1, true);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
        }
        return cantidad;
        
    }
    
    public boolean clienteExiste(int idcliente) {
        String sql = "SELECT  * from pedido where idcliente = ? limit 1";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idcliente);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean existeEmpleado(int idempleado) {
            String sql = "SELECT  * from pedido where idEmpleado = ? limit 1";
        try {
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idempleado);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    
}
