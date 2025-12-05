package com.example.a20300846_zeusvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adaptadores.adaptadoreliminar;
import Global.info;
import Pojo.producto;

public class Eliminar extends AppCompatActivity {

    RecyclerView rueliminar;
    Button elim;
    adaptadoreliminar adaptador;
    Toolbar toolbar;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);

        // 1. Configurar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Inicializar Vistas
        rueliminar = findViewById(R.id.Eliminar);
        elim = findViewById(R.id.Button_Eliminar);

        // 3. Inicializar Sesión (para el menú de cerrar sesión)
        archivo = this.getSharedPreferences("Sesion", Context.MODE_PRIVATE);

        // 4. Configurar RecyclerView
        adaptador = new adaptadoreliminar(this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rueliminar.setAdapter(adaptador);
        rueliminar.setLayoutManager(llm);

        // 5. Lógica de Eliminación
        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.listabaja.isEmpty()) {
                    Toast.makeText(Eliminar.this, "No hay ventas seleccionadas para eliminar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Eliminación local de las listas en memoria
                info.lista.removeAll(info.listabaja);
                info.listabaja.clear();
                adaptador.notifyDataSetChanged();

                // Nota: Si deseas eliminar también de la BD remota, aquí deberías agregar la petición Volley
                // similar a como se hizo en Modificar.java, iterando sobre info.listabaja antes de borrarla.

                Toast.makeText(Eliminar.this, "Ventas eliminadas correctamente.", Toast.LENGTH_SHORT).show();
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

    // --- LÓGICA DE MENÚ UNIFICADO ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (handleMenuNavigation(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean handleMenuNavigation(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.menu_registro) {
            intent = new Intent(this, MainActivity.class);
        } else if (id == R.id.menu_lista) {
            intent = new Intent(this, Lista_Dinamica.class);
        } else if (id == R.id.menu_modificar_nav) {
            intent = new Intent(this, Modificar.class);
        } else if (id == R.id.opc1) {
            // Ya estamos en Eliminar
            return true;
        } else if (id == R.id.menu_cerrar_sesion) {
            SharedPreferences.Editor editor = archivo.edit();
            editor.clear();
            editor.apply();
            intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        return false;
    }
}