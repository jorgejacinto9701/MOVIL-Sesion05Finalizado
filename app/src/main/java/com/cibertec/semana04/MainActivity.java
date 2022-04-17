package com.cibertec.semana04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.semana04.entity.Editorial;
import com.cibertec.semana04.service.ServiceEditorial;
import com.cibertec.semana04.util.ConnectionRest;
import com.cibertec.semana04.util.FunctionUtil;
import com.cibertec.semana04.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtDireccion, txtFechaCreacion;
    Spinner spnPais;
    Button btnEnviar;
    ServiceEditorial service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtRegEdiNombre);
        txtDireccion = findViewById(R.id.txtRegEdiDirecccion);
        spnPais = findViewById(R.id.spnRegEdiPais);
        txtFechaCreacion = findViewById(R.id.txtRegEdiFechaCreacion);
        btnEnviar = findViewById(R.id.btnRegEdiEnviar);

        service = ConnectionRest.getConnecion().create(ServiceEditorial.class);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String pais = spnPais.getSelectedItem().toString();
                String fec =  txtFechaCreacion.getText().toString();

                if (!nom.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("Nombre es de 3 a 30");
                } else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("Dirección es de 3 a 30");
                } else if ( spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Seleccione un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha es yyyy-MM-dd");
                }else{
                    Editorial obj = new Editorial();
                    obj.setNombre(nom);
                    obj.setDireccion(dir);
                    obj.setPais(pais);
                    obj.setFechaCreacion(fec);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(1);

                    registraEditorial(obj);
                }


            }
        });
    }

    public void registraEditorial(Editorial obj){
        Call<Editorial> call =  service.insertaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                    if (response.isSuccessful()){
                        Editorial objSalida = response.body();
                        mensajeAlert("Registro Exitoso " + objSalida.getIdEditorial());
                    }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error " + t.getMessage());
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