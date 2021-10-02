
package restfulModel;

import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.xml.bind.annotation.XmlRootElement;

public class CancionModel {
    
    private int codigo_can;
    private String nombre_can;
    private String nombre_art;
    private String nombre_gen;
    
    public CancionModel(){ 
        
    }

    public CancionModel(int codigo_can, String nombre_can, String nombre_art, String nombre_gen) {
        this.codigo_can = codigo_can;
        this.nombre_can = nombre_can;
        this.nombre_art = nombre_art;
        this.nombre_gen = nombre_gen;
    }

    public int getCodigo_can() {
        return codigo_can;
    }

    public void setCodigo_can(int codigo_can) {
        this.codigo_can = codigo_can;
    }

    public String getNombre_can() {
        return nombre_can;
    }

    public void setNombre_can(String nombre_can) {
        this.nombre_can = nombre_can;
    }

    public String getNombre_art() {
        return nombre_art;
    }

    public void setNombre_art(String nombre_art) {
        this.nombre_art = nombre_art;
    }

    public String getNombre_gen() {
        return nombre_gen;
    }

    public void setNombre_gen(String nombre_gen) {
        this.nombre_gen = nombre_gen;
    }
    
    
    
}
