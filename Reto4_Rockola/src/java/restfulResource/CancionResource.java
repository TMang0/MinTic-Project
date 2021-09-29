/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restfulResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restfulModel.CancionModel;
import restfulService.CancionService;

@Path("cancion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CancionResource {
    CancionService servicio = new CancionService();

    @GET
    public ArrayList<CancionModel> getCanciones() {

        return servicio.getCanciones();
    }
    
    @Path("/{cancionId}")
    @GET
    public CancionModel getCancion(@PathParam("cancionId") int id) {

        return servicio.getCancion(id);
    }
    
    @POST
    public CancionModel addCancion(String JSON) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        CancionModel cancion = gson.fromJson(JSON, CancionModel.class);
        return servicio.addCancion(cancion);
    }
    
    @DELETE
    @Path("/{CancionId}")
    public String delaCancion(@PathParam("CancionId") int id) {

        return servicio.delaCancion(id);

    }
    
    @PUT
    public CancionModel updateCancion(String JSON) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        CancionModel cancion = gson.fromJson(JSON, CancionModel.class);
        return servicio.updateCancion(cancion);
    }
}
