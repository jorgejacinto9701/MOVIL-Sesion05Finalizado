package com.cibertec.semana04.service;

import com.cibertec.semana04.entity.Editorial;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceEditorial {

    @POST("editorial")
    public Call<Editorial> insertaEditorial(@Body Editorial obj);


}
