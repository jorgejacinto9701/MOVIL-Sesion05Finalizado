package com.cibertec.semana04.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {

    public static final String URL = "https://api-cibertec-moviles.herokuapp.com/servicio/";
    public static final String URL_PAIS = "https://restcountries.com/v3.1/";

    public static Retrofit retrofit = null;
    public static Retrofit retrofitPais = null;
    public static Retrofit getConnecion(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getConnecionRestPais(){
        if (retrofitPais == null){
            retrofitPais = new Retrofit.Builder().baseUrl(URL_PAIS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitPais;
    }

}
