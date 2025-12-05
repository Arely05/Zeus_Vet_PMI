package Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torneo.R;
import com.example.torneo.modificar;

import Pojo.global.info;
import Pojo.producto;


public class adaptadorlistadinamica extends RecyclerView.Adapter<adaptadorlistadinamica.Miniactivity> {
    public Context context;

    public adaptadorlistadinamica(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Miniactivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.milista, parent, false);
        Miniactivity obj = new Miniactivity(v);
        return (obj);
    }

    @Override
    public void onBindViewHolder(@NonNull Miniactivity miact, int i) {
        final int pos=i;
        producto productoActual = info.lista.get(i);

        miact.NombreEq.setText(productoActual.getNomProd());
        miact.NombreCap.setText(productoActual.getDescripcion());

        miact.NombreCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CORRECCIÓN: Navegar a modificar.class y pasar la posición
                Intent modificar = new Intent(context, modificar.class);
                modificar.putExtra("posicion",pos);
                context.startActivity(modificar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size();
    }

    public class Miniactivity extends RecyclerView.ViewHolder{
        TextView NombreEq,NombreCap;
        public Miniactivity(@NonNull View itemView) {
            super(itemView);
            NombreEq=itemView.findViewById(R.id.ListaEqui);
            NombreCap=itemView.findViewById(R.id.ListaCapi);

        }
    }
}