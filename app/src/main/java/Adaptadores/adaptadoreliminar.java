package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Global.info;
import Pojo.producto;
import com.example.a20300846_zeusvet.R; // Importar R del proyecto Zeus Vet

public class adaptadoreliminar extends RecyclerView.Adapter<adaptadoreliminar.Miniactivity> {

    private Context context;

    public adaptadoreliminar(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Miniactivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Corregido para usar R.layout.eliminar (el layout del item de checkbox)
        View view = LayoutInflater.from(context).inflate(R.layout.eliminar, parent, false);
        return new Miniactivity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Miniactivity holder, int position) {
        final producto productoActual = info.lista.get(position);

        holder.checkBox.setText(productoActual.getNombreComprador());
        holder.checkBox.setChecked(info.listabaja.contains(productoActual));

        holder.checkBox.setOnClickListener(v -> {
            CheckBox c = (CheckBox) v;
            if (c.isChecked()) {
                if (!info.listabaja.contains(productoActual))
                    info.listabaja.add(productoActual);
            } else {
                info.listabaja.remove(productoActual);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size();
    }

    public static class Miniactivity extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public Miniactivity(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.CheckBox_Eliminar);
        }
    }
}