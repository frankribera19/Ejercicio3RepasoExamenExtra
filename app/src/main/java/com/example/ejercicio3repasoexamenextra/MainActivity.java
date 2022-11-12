package com.example.ejercicio3repasoexamenextra;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.example.ejercicio3repasoexamenextra.adapters.PartidosAdapter;
import com.example.ejercicio3repasoexamenextra.databinding.ActivityMainBinding;
import com.example.ejercicio3repasoexamenextra.modelos.Constantes;
import com.example.ejercicio3repasoexamenextra.modelos.Partido;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int posicion;

    private ArrayList<Partido> listaDePartidos;

    private ActivityResultLauncher<Intent> crearPartido;


    private PartidosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        iniciarListaAdapteryLayout();

        inicializarLaunchers();

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPartido.launch(new Intent(MainActivity.this, CrearPartidoActivity.class));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("PARTIDOS",listaDePartidos);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Partido> temp = (ArrayList<Partido>) savedInstanceState.getSerializable("PARTIDOS");
        listaDePartidos.addAll(temp);
        adapter.notifyItemRangeInserted(0, listaDePartidos.size());
    }

    public void verResumenPartido(){
        Partido partido = listaDePartidos.get(posicion);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constantes.PARTIDO, partido);
        Intent intent = new Intent(MainActivity.this, VerDatosPartidoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void inicializarLaunchers() {
        crearPartido = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null && result.getData().getExtras() != null){
                                Partido partido = (Partido) result.getData().getExtras().getSerializable(Constantes.PARTIDO);
                                posicion = result.getData().getExtras().getInt(Constantes.POSICION);
                                listaDePartidos.add(posicion,partido);
                                //Toast.makeText(MainActivity.this,partido.toString(), Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void iniciarListaAdapteryLayout() {
        int columnas;
        columnas =getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
        listaDePartidos = new ArrayList<>();
        adapter = new PartidosAdapter(this,listaDePartidos,R.layout.partido_view_holder);
        layoutManager = new GridLayoutManager(this, columnas);
    }
}