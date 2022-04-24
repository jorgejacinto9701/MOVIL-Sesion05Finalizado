package com.cibertec.semana04;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.semana04.entity.Editorial;
import com.cibertec.semana04.entity.Pais;
import com.cibertec.semana04.service.EditorialService;
import com.cibertec.semana04.service.PaisService;
import com.cibertec.semana04.util.ConnectionRest;
import com.cibertec.semana04.util.FunctionUtil;
import com.cibertec.semana04.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtDireccion, txtFechaCreacion;
    Button btnEnviar;
    EditorialService service;
    PaisService paiService;


    Spinner spnPais;
    ArrayAdapter<String> adapatador;
    ArrayList<String> datos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtRegEdiNombre);
        txtDireccion = findViewById(R.id.txtRegEdiDirecccion);
        txtFechaCreacion = findViewById(R.id.txtRegEdiFechaCreacion);
        spnPais = findViewById(R.id.spnRegEdiPais);
        btnEnviar = findViewById(R.id.btnRegEdiEnviar);
        service = ConnectionRest.getConnecion().create(EditorialService.class);
        paiService = ConnectionRest.getConnecionRestPais().create(PaisService.class);

        adapatador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, datos);
        spnPais.setAdapter(adapatador);

        paiService.listaPaises().enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                 mensajeAlert("onResponse >> " + response.isSuccessful());
                  if (response.isSuccessful()){
                         List<Pais> lista = response.body();

                         ArrayList<String> aux = new ArrayList<String>();
                         for(Pais x:lista){
                             aux.add(x.getName().getCommon());
                         }
                         //Ordena la data de paises
                         Collections.sort(aux);

                         //Se agrega el priemr elemento
                         datos.add("[ Seleccione País ]");
                         //Se agrega todos los paises ya ordenados
                         datos.addAll(aux);
                         adapatador.notifyDataSetChanged();
                  }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error >> " + t.getMessage());
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String fec = txtFechaCreacion.getText().toString();
                String pais = spnPais.getSelectedItem().toString();

                if (!nom.matches(ValidacionUtil.NOMBRE)) {
                    mensajeAlert("El nombre es entre 3 y 30 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)) {
                    mensajeAlert("La dirección es entre 3 y 30 caracteres");
                }else if (!fec.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La fecha tiene formato yyyy-MM-dd");
                }else if ( spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Seleccione un país");
                }else{
                    Editorial obj = new Editorial();
                    obj.setNombre(nom);
                    obj.setDireccion(dir);
                    obj.setFechaCreacion(fec);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(1);
                    insertado(obj);
                }

            }
        });
    }

    private  void insertado(Editorial obj){
        Call<Editorial> call = service.insertaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                    if (response.isSuccessful()){
                        Editorial objSalida = response.body();
                        mensajeAlert("Se registro al Editorial >> " + objSalida.getIdEditorial() + " - " + objSalida.getNombre());
                    }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                    mensajeAlert("Error >> " + t.getMessage());
            }
        });

    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


}