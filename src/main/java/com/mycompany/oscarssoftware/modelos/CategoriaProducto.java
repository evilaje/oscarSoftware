package com.mycompany.oscarssoftware.modelos;

import com.mycompany.oscarssoftware.clases.conexion;
import com.mycompany.oscarssoftware.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaProducto extends conexion implements sentencias {

    // Atributos
    private int idCategoria;
    private String nombreCategoria;

    // Constructores
    public CategoriaProducto(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaProducto() {
        // Inicialización por defecto
    }

    // Getters y setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    // Implementación del método para consultar todas las categorías
    @Override
    public ArrayList<CategoriaProducto> consulta() {
        ArrayList<CategoriaProducto> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_producto";
        try (
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {
            
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nombreCategoria = rs.getString("nombre_categoria");
                CategoriaProducto categoria = new CategoriaProducto(idCategoria, nombreCategoria);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    // Implementación del método para insertar una nueva categoría
    @Override
    public boolean insertar() {
        String sql = "INSERT INTO categoria_producto (idCategoria, nombre_categoria) VALUES (?, ?)";
        try (Connection con = getCon(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            this.idCategoria = obtenerUltimoId() + 1; // Asignar el nuevo ID
            ps.setInt(1, this.idCategoria);
            ps.setString(2, this.nombreCategoria);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Implementación del método para borrar una categoría
    @Override
    public boolean borrar() {
        String sql = "DELETE FROM categoria_producto WHERE idCategoria = ?";
        try (Connection con = getCon(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, this.idCategoria);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Implementación del método para modificar una categoría
    @Override
    public boolean modificar() {
        String sql = "UPDATE categoria_producto SET nombre_categoria = ? WHERE idcategoria = ?";
        try (Connection con = getCon(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, this.nombreCategoria);
            ps.setInt(2, this.idCategoria);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el último ID de la tabla categoria_producto
    public int obtenerUltimoId() {
        String sql = "SELECT MAX(idCategoria) AS ultimo_id FROM categoria_producto";
        try (Connection con = getCon(); 
             Statement stm = con.createStatement(); 
             ResultSet rs = stm.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("ultimo_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna 0 si no se encuentra ningún ID, indicando que la tabla está vacía
    }
}
