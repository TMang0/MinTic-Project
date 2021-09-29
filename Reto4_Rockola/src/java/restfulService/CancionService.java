/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restfulService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import restfulModel.CancionModel;
import restfulModel.Conexion;

public class CancionService {
    public ArrayList<CancionModel> getCanciones() {
        ArrayList<CancionModel> lista = new ArrayList<>();
        Conexion conn = new Conexion();
       
        String sql = "select c.codigo_can, c.nombre_can, a.nombre_art, g.nombre_gen\n" +
                     "from cancion c\n" +
                     "inner join can_gen cg on c.codigo_can = cg.codigo_can\n" +
                     "inner join genero g on cg.codigo_gen = g.codigo_gen\n" +
                     "inner join art_can ac on c.codigo_can = ac.codigo_can\n" +
                     "inner join artista a on ac.codigo_art = a.codigo_art";

        try {
            Statement stm = conn.getCon().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                CancionModel cancion = new CancionModel();
                cancion.setCodigo_can(rs.getInt("codigo_can"));
                cancion.setNombre_can(rs.getString("nombre_can"));
                cancion.setNombre_art(rs.getString("nombre_art"));
                cancion.setNombre_gen(rs.getString("nombre_gen"));
                lista.add(cancion);
            }
        } catch (SQLException e) {
        }

        return lista;
    }
    
    public CancionModel getCancion(int id) {
        CancionModel cancion = new CancionModel();
        Conexion conex = new Conexion();
        String Sql = "select c.codigo_can, c.nombre_can, a.nombre_art, g.nombre_gen\n" +
                    "from cancion c\n" +
                    "inner join can_gen cg on c.codigo_can = cg.codigo_can\n" +
                    "inner join genero g on cg.codigo_gen = g.codigo_gen\n" +
                    "inner join art_can ac on c.codigo_can = ac.codigo_can\n" +
                    "inner join artista a on ac.codigo_art = a.codigo_art\n" +
                    "where  c.codigo_can=?";

        try {

            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                cancion.setCodigo_can(rs.getInt("codigo_can"));
                cancion.setNombre_can(rs.getString("nombre_can"));
                cancion.setNombre_art(rs.getString("nombre_art"));
                cancion.setNombre_gen(rs.getString("nombre_gen"));
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return cancion;
    }
    
    public CancionModel addCancion(CancionModel cancion) {
        Conexion conex = new Conexion();
        String Sql = "insert into cancion(codigo_can,nombre_can)";
        Sql = Sql + "values (?,?)";

        try {
            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, cancion.getCodigo_can());
            pstm.setString(2, cancion.getNombre_can());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return cancion;
    }
    
    public String delaCancion(int id) {
        Conexion conn = new Conexion();

        String sql = "delete from cancion where codigo_can= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return "{\"Accion\":\"Error\"}";
        }
        return "{\"Accion\":\"Registro Borrado\"}";
    }
    
    public CancionModel updateCancion(CancionModel cancion) {
        Conexion conn = new Conexion();
        String sql = "update cancion set nombre_can=?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setString(1, cancion.getNombre_can());
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return null;
        }
        return cancion; 
    }
}
