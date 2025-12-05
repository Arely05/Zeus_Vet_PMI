package com.example.a20300846_zeusvet;

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

public class adaptadoreliminar extends RecyclerView.Adapter<adaptadoreliminar.Miniactivity> {

    private Context context;

    public adaptadoreliminar(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Miniactivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eli, parent, false);
        return new Miniactivity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Miniactivity holder, int position) {
        final producto productoActual = info.lista.get(position); // Usa 'producto'

        holder.checkBox.setText(productoActual.getNomProd());
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

    // Estructura de ViewHolder verificada
    public static class Miniactivity extends RecyclerView.ViewHolder {
        CheckBox checkBox; // Declaración de la variable miembro (campo de clase)

        public Miniactivity(@NonNull View itemView) {
            super(itemView);
            // Inicialización (donde te da error)
            checkBox = itemView.findViewById(R.id.CheckBox_Eliminar);
        }
    }
}