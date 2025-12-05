package com.example.a20300846_zeusvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Adaptadores.adaptadorlistadinamica;
import Global.info;
import Pojo.producto;

public class Lista_Dinamica extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    adaptadorlistadinamica adaptador;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_dinamica);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lista de Ventas");
        }

        archivo = getSharedPreferences("Sesion", Context.MODE_PRIVATE);

        recyclerView = findViewById(R.id.Rec);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        adaptador = new adaptadorlistadinamica(this);
        recyclerView.setAdapter(adaptador);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosDeBD();
    }

    private void cargarDatosDeBD() {
        int idUsuario = archivo.getInt("id_usuario", -1);
        String url = "http://10.0.2.2/bd/obtener_ventas.php?id_cuenta=" + idUsuario;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    info.lista.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            producto p = new producto();

                            p.setId_venta(obj.getInt("id"));
                            p.setId_cuenta(obj.getInt("id_cuenta"));
                            p.setId_tipo_producto(obj.getInt("id_tipo_producto"));
                            p.setNombreProducto(obj.optString("nombre_producto", ""));
                            p.setIdentificador(obj.optString("nombre_comprador", "Sin Nombre"));
                            p.setTotal_final(obj.optString("total", "0"));
                            p.setTelefono(obj.optString("telefono", ""));
                            p.setCorreo(obj.optString("correo", ""));
                            p.setCantidad(obj.optString("cantidad", "0"));
                            p.setFecha(obj.optString("fecha", ""));
                            p.setNombre_producto_tipo(obj.optString("nombre_tipo", "Producto")); // Join

                            info.lista.add(p);
                        }
                        adaptador.notifyDataSetChanged();
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_registro) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.menu_lista) return true;
        if (id == R.id.menu_modificar_nav) {
            if (info.lista.isEmpty()) Toast.makeText(this, "Lista vacía", Toast.LENGTH_SHORT).show();
            else startActivity(new Intent(this, Modificar.class));
            return true;
        }
        if (id == R.id.opc1) {
            startActivity(new Intent(this, Eliminar.class));
            return true;
        }
        if (id == R.id.menu_cerrar_sesion) {
            archivo.edit().clear().apply();
            Intent intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}