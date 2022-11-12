package com.example.ejercicio3repasoexamenextra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ejercicio3repasoexamenextra.databinding.ActivityCrearPartidoBinding;
import com.example.ejercicio3repasoexamenextra.modelos.Constantes;
import com.example.ejercicio3repasoexamenextra.modelos.Partido;

public class CrearPartidoActivity extends AppCompatActivity {

    private ActivityCrearPartidoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearPartidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarSpinners();

        binding.btnCancelarCrearPartidoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearCrearPartidoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Partido partido;

                if ((partido = nuevoPartido()) != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.PARTIDO,partido);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    int posicion = bundle.getInt(Constantes.POSICION);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private Partido nuevoPartido() {
        if (binding.spEquipoLocalCrearPartidoActivity.getSelectedItem() != "" &&
            binding.spEquipoVisitanteCrearPartidoActivity.getSelectedItem() != "" &&
            !binding.txtGolesLocalCrearPartidoActivity.getText().toString().isEmpty() &&
            !binding.txtGolesVisitanteCrearPartidoActivity.getText().toString().isEmpty() &&
            !binding.txtResumenCrearPartidoActivity.getText().toString().isEmpty()){

            String equipoLocal = binding.spEquipoLocalCrearPartidoActivity.getSelectedItem().toString();
            String equipoVisitante = binding.spEquipoVisitanteCrearPartidoActivity.getSelectedItem().toString();
            int golesLocal = Integer.parseInt(binding.txtGolesLocalCrearPartidoActivity.getText().toString());
            int golesVisitante = Integer.parseInt(binding.txtGolesVisitanteCrearPartidoActivity.getText().toString());
            String resumen = binding.txtResumenCrearPartidoActivity.getText().toString();

            return new Partido(equipoLocal,equipoVisitante,golesLocal,golesVisitante,resumen);
        }else{
            Toast.makeText(this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void inicializarSpinners() {
        ArrayAdapter<CharSequence> adapterEquipos = ArrayAdapter.createFromResource(this,R.array.equipos, android.R.layout.simple_spinner_item);
        binding.spEquipoLocalCrearPartidoActivity.setAdapter(adapterEquipos);
        binding.spEquipoVisitanteCrearPartidoActivity.setAdapter(adapterEquipos);
    }
}