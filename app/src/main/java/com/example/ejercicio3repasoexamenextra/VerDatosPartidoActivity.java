package com.example.ejercicio3repasoexamenextra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ejercicio3repasoexamenextra.databinding.ActivityVerDatosPartidoBinding;
import com.example.ejercicio3repasoexamenextra.modelos.Constantes;
import com.example.ejercicio3repasoexamenextra.modelos.Partido;

public class VerDatosPartidoActivity extends AppCompatActivity {

    private ActivityVerDatosPartidoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerDatosPartidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Partido partido = (Partido) bundle.getSerializable(Constantes.PARTIDO);

        binding.lblNombreELocalVerDatosActivity.setText(partido.getEquipoLocal());
        binding.lblNombreEVisitanteVerDatosActivity.setText(partido.getEquipoVisitante());
        binding.lblGolesELocalVerDatosActivity.setText(String.valueOf(partido.getGolesLocal()));
        binding.lblGolesEVisitanteVerDatosActivity.setText(String.valueOf(partido.getGolesVisitante()));
        binding.lblResumenVerDatosActivity.setText(partido.getResumen());
    }
}