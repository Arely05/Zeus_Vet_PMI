package com.example.a20300846_zeusvet;

import android.annotation.SuppressLint;
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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import Adaptadores.adaptadoreliminar;
import Global.info;
import Pojo.producto;

public class Eliminar extends AppCompatActivity {

    RecyclerView rueliminar;
    Button elim;
    adaptadoreliminar adaptador;
    Toolbar toolbar;
    SharedPreferences archivo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rueliminar = findViewById(R.id.Eliminar);
        elim = findViewById(R.id.Button_Eliminar);

        archivo = this.getSharedPreferences("Sesion", Context.MODE_PRIVATE);

        adaptador = new adaptadoreliminar(this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rueliminar.setAdapter(adaptador);
        rueliminar.setLayoutManager(llm);

        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.listabaja.isEmpty()) {
                    Toast.makeText(Eliminar.this, "No hay ventas seleccionadas para eliminar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (producto p : info.listabaja) {
                    eliminarDeBD(p.getId_venta());
                }

                info.lista.removeAll(info.listabaja);
                info.listabaja.clear();
                adaptador.notifyDataSetChanged();

                Toast.makeText(Eliminar.this, "Procesando eliminación...", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void eliminarDeBD(int idVenta) {
        String url = "http://10.0.2.2/bd/eliminar.php?id=" + idVenta;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                },
                error -> {
                    Toast.makeText(Eliminar.this, "Error al eliminar venta ID: " + idVenta, Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adaptador != null) {
            adaptador.notifyDataSetChanged();
        }
    }

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
            if (info.lista.isEmpty()) {
                Toast.makeText(this, "No hay ventas registradas para modificar.", Toast.LENGTH_SHORT).show();
                return true;
            }
            intent = new Intent(this, Modificar.class);
        } else if (id == R.id.opc1) {
            return true;
        } else if (id == R.id.menu_cerrar_sesion) {
            SharedPreferences.Editor editor = archivo.edit();
            editor.clear();
            editor.apply();
            intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return false;
    }
}