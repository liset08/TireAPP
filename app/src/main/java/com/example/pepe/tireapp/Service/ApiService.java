package com.example.pepe.tireapp.Service;



import com.example.pepe.tireapp.ResponseMessage;
import com.example.pepe.tireapp.model.Camion;
import com.example.pepe.tireapp.model.Camion_neumaticos;
import com.example.pepe.tireapp.model.Grupoempresa;
import com.example.pepe.tireapp.model.Lectura;
import com.example.pepe.tireapp.model.Login;
import com.example.pepe.tireapp.model.TipoNeumatico;
import com.example.pepe.tireapp.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LISET on 22/05/2018.
 */

public interface ApiService {

    String API_BASE_URL = "http://tireapp.somee.com";

    @GET("/api/product/findall")
    Call<List<Grupoempresa>> getGemp();

    @POST("/api/product/finda/{id}")
    Call<Void> finda(@Query("id") int id);

    @POST("/api/product/create")
    Call<Void> create(@Body Grupoempresa grupoempresa);


    @PUT("/api/product/update")
    Call<Void> update(@Body Grupoempresa garupoempresa);


    //Mantenimiento de neumaticos

    @GET("/api/neu/find")
    Call<List<TipoNeumatico>> getNeum();

    @POST("/api/neu/create")
    Call<Void> createNeumatico(@Body TipoNeumatico tipoNeumatico);

    @PUT("/api/neu/update")
    Call<Void> update(@Body TipoNeumatico tipoNeumatico);
    @DELETE("/api/neu/delete/{id}")
    Call<Void> delete(@Path("id") int id);


    //
    @POST("/api/lectura/create")
    Call<Void> createLectur(@Body Lectura lectura);


    //Mantenimiento de camion
    @POST("/api/camion/create")
    Call<Void> create(@Body Camion camion);

    @GET("/api/camion/getByPlaca/{placa}")
    Call<Camion> findCamion(@Path("placa") String placa);


    @GET("/api/camion/getAll")
    Call<List<Camion>> getCamiones();


    @POST("/api/usuario/login")
    Call<List<Usuario>> login(@Body Login login);

    @POST("/api/camion/asignNeumatic")
    Call<Void> asignarNeumatico(@Body Camion_neumaticos camion_neumaticos);

    @DELETE("/api/CamNeu/delete/{id}")
    Call<Void> deleteCamNeu(@Path("id") int id);
}
