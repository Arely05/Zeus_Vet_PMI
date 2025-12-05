package com.example.a20300846_zeusvet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adaptadores.adaptadoreliminar;
import Pojo.global.info;
import Pojo.producto;

public class Eliminar extends AppCompatActivity {

    RecyclerView rueliminar;
    Button elim;
    adaptadoreliminar adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);

        rueliminar = findViewById(R.id.Eliminar);
        elim = findViewById(R.id.Button_Eliminar); // <--- Inicialización del botón

        // Si rueliminar o elim son null, la app crasheará. Asumimos el layout ya tiene los IDs.

        adaptador = new adaptadoreliminar(this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rueliminar.setAdapter(adaptador);
        rueliminar.setLayoutManager(llm);

        // LÓGICA DE ELIMINACIÓN
        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.listabaja.isEmpty()) {
                    Toast.makeText(Eliminar.this, "No hay productos seleccionados para eliminar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                info.lista.removeAll(info.listabaja);
                info.listabaja.clear();
                adaptador.notifyDataSetChanged();

                Toast.makeText(Eliminar.this, "Productos eliminados correctamente.", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adaptador != null) {
            adaptador.notifyDataSetChanged();
        }
    }
}