package com.example.ejercicio3repasoexamenextra.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio3repasoexamenextra.MainActivity;
import com.example.ejercicio3repasoexamenextra.R;
import com.example.ejercicio3repasoexamenextra.modelos.Partido;

import java.util.ArrayList;

public class PartidosAdapter extends RecyclerView.Adapter<PartidosAdapter.PartidoVH> {

    private Context context;
    private ArrayList<Partido> objects;
    protected int resource;

    public PartidosAdapter(Context context, ArrayList<Partido> objects, int resource) {
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public PartidoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View partidoView = LayoutInflater.from(context).inflate(resource,null);
        ViewGroup.LayoutParams layoutParams =new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        partidoView.setLayoutParams(layoutParams);
        return new PartidoVH(partidoView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartidoVH holder, int position) {
        Partido partido = objects.get(position);
        holder.lblNPartido.setText("PARTIDO "+ (position+1));
        holder.lblEquipoLocal.setText(partido.getEquipoLocal());
        holder.lblEquipoVisitnate.setText(partido.getEquipoVisitante());
        holder.lblGolesLocal.setText(String.valueOf(partido.getGolesLocal()));
        holder.lblGolesVisitante.setText(String.valueOf(partido.getGolesVisitante()));
        
        holder.btnResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarResultado(partido, holder.getAdapterPosition()).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = (MainActivity) context;
                main.verResumenPartido();
            }
        });
    }

    private AlertDialog mostrarResultado(Partido partido, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("RESULTADO");
        builder.setCancelable(false);

        TextView resultado = new TextView(context);

        int golesELocal = partido.getGolesLocal();
        int golesEVisitante = partido.getGolesVisitante();

        if (golesELocal > golesEVisitante){
            resultado.setText("GANADOR EL EQUIPO: " + partido.getEquipoLocal());
        }else{
            resultado.setText("GANADOR EL EQUIPO: " + partido.getEquipoVisitante());
        }
        resultado.setTextSize(24);
        resultado.setTextColor(Color.GREEN);
        resultado.setPadding(100,100,100,100);

        builder.setView(resultado);

        builder.setPositiveButton("ACEPTAR",null);

        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class PartidoVH extends RecyclerView.ViewHolder{

        TextView lblEquipoLocal, lblEquipoVisitnate, lblGolesLocal, lblGolesVisitante, lblNPartido;
        Button btnResultado;

        public PartidoVH(@NonNull View itemView) {
            super(itemView);

            lblNPartido = itemView.findViewById(R.id.lblNPartidoPartidoViewHolder);
            lblEquipoLocal = itemView.findViewById(R.id.lblNombreELocalPartidoViewHolder);
            lblEquipoVisitnate = itemView.findViewById(R.id.lblNombreEVisitantePartidoViewHolder);
            lblGolesLocal = itemView.findViewById(R.id.lblGolesELocalPartidoViewHolder);
            lblGolesVisitante = itemView.findViewById(R.id.lblGolesEVisitantePartidoViewHolder);
            btnResultado = itemView.findViewById(R.id.btnResultadoPartidoViewHolder);

        }
    }
}
