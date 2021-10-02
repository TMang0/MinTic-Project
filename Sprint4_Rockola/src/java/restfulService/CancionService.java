
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
        String sql = "SELECT * FROM cancion";

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
        String Sql = "SELECT * FROM cancion WHERE codigo_can = ?";

        try {

            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                cancion.setCodigo_can(rs.getInt("id_cliente"));
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
        String Sql = "INSERT INTO cancion(codigo_can,nombre_can,nombre_art,nombre_gen)";
        Sql = Sql + "values (?,?,?,?)";

        try {
            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, cancion.getCodigo_can());
            pstm.setString(2, cancion.getNombre_can());
            pstm.setString(3, cancion.getNombre_art());
            pstm.setString(4, cancion.getNombre_gen());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return cancion;
    }

    public CancionModel updateCancion(CancionModel cancion) {
        Conexion conn = new Conexion();
        String sql = "UPDATE cancion SET nombre_can=?,nombre_art=?,nombre_gen=? WHERE codigo_can= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setString(1, cancion.getNombre_can());
            pstm.setString(2, cancion.getNombre_art());
            pstm.setString(3, cancion.getNombre_gen());
            pstm.setInt(4, cancion.getCodigo_can());
            pstm.executeUpdate(); 
        } catch (SQLException excepcion) { 
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return null;
        }
        return cancion;
    }

    public String delaCancion(int id) {
        Conexion conn = new Conexion();

        String sql = "DELETE FROM cancion WHERE codigo_can= ?";
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
    
}
