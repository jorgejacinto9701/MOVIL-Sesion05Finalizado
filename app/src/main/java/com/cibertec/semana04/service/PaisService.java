package com.cibertec.semana04.service;

import com.cibertec.semana04.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisService {

    @GET("all")
    public Call<List<Pais>> listaPaises();

}
