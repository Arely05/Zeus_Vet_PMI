package com.example.a20300846_zeusvet;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adaptadores.adaptadorlistadinamica; // Importa el adaptador corregido

public class Lista_Dinamica extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    adaptadorlistadinamica adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar EdgeToEdge si es necesario
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_dinamica);

        // 1. Configurar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lista de Ventas");
        }

        // 2. Inicializar RecyclerView usando el ID del layout: @+id/Rec
        recyclerView = findViewById(R.id.Rec);

        // 3. Configurar LayoutManager
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        // 4. Instanciar y configurar el Adaptador
        // El constructor del adaptador recibe el Context (this)
        adaptador = new adaptadorlistadinamica(this);
        recyclerView.setAdapter(adaptador);

        // 5. Insets (mantenemos la compatibilidad con EdgeToEdge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Esto asegura que la lista se actualice cuando volvemos de Modificar o Eliminar
        if (adaptador != null) {
            adaptador.notifyDataSetChanged();
        }
    }
}