package Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20300846_zeusvet.Modificar;
import com.example.a20300846_zeusvet.R;

import Global.info;
import Pojo.producto;

public class adaptadorlistadinamica extends RecyclerView.Adapter<adaptadorlistadinamica.Miniactivity> {

    private Context context;

    public adaptadorlistadinamica(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Miniactivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista, parent, false);
        return new Miniactivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Miniactivity holder, int position) {

        producto productoActual = info.lista.get(position);

        holder.NombreEq.setText(productoActual.getNombreComprador());
        holder.NombreCap.setText(productoActual.getTotalCompra());

        // CLICK -> ABRIR MODIFICAR.JAVA
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Modificar.class);
            intent.putExtra("posicion", position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size();
    }

    public static class Miniactivity extends RecyclerView.ViewHolder {

        TextView NombreEq, NombreCap;

        public Miniactivity(@NonNull View itemView) {
            super(itemView);

            NombreEq = itemView.findViewById(R.id.ListaEqui);
            NombreCap = itemView.findViewById(R.id.ListaCapi);
        }
    }
}
