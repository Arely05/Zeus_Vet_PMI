package com.example.a20300846_zeusvet;

import androidx.annotation.NonNull; // Import necesario para el menú
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context; // Necesario para SharedPreferences
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import Global.info;
import Pojo.producto;

public class Modificar extends AppCompatActivity {

    EditText Mod_NombreComprador, Mod_Telefono, Mod_Correo, Mod_NombreProducto, Mod_Cantidad, Mod_TotalCompra;
    Spinner Mod_TipoProducto;
    Button btnAnterior, btnGuardar, btnSiguiente;
    Toolbar toolbar;
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        // 1. Configurar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modificar Venta");
        }

        // 2. Recibir posición
        posicion = getIntent().getIntExtra("posicion", 0);

        // 3. Vincular Vistas
        Mod_NombreComprador = findViewById(R.id.Mod_NombreComprador);
        Mod_Telefono = findViewById(R.id.Mod_Telefono);
        Mod_Correo = findViewById(R.id.Mod_Correo);
        Mod_NombreProducto = findViewById(R.id.Mod_NombreProducto);
        Mod_Cantidad = findViewById(R.id.Mod_Cantidad);
        Mod_TotalCompra = findViewById(R.id.Mod_TotalCompra);
        Mod_TipoProducto = findViewById(R.id.Mod_TiempoInscripcion);

        btnAnterior = findViewById(R.id.Button_Anterior);
        btnGuardar = findViewById(R.id.Button_Guardar);
        btnSiguiente = findViewById(R.id.Button_Siguiente);

        // 4. Configurar Spinner (Mismos datos que MainActivity)
        String[] tipos = {"Shampoo", "Acondicionador", "Crema para peinar"};
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        Mod_TipoProducto.setAdapter(adap);

        // 5. Cargar Datos iniciales
        cargarDatos();

        // 6. Botones
        btnGuardar.setOnClickListener(v -> guardar());

        btnSiguiente.setOnClickListener(v -> {
            if (posicion < info.lista.size() - 1) {
                posicion++;
                cargarDatos();
            } else {
                Toast.makeText(this, "Último registro", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnterior.setOnClickListener(v -> {
            if (posicion > 0) {
                posicion--;
                cargarDatos();
            } else {
                Toast.makeText(this, "Primer registro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatos() {
        // SEGURIDAD: Si no hay datos, cerramos la actividad para evitar errores
        if (info.lista.isEmpty()) {
            Toast.makeText(this, "No hay registros para modificar", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Validación de rango por si la posición recibida es inválida
        if (posicion >= info.lista.size()) posicion = info.lista.size() - 1;
        if (posicion < 0) posicion = 0;

        producto p = info.lista.get(posicion);

        Mod_NombreComprador.setText(p.getNombreComprador());
        Mod_Telefono.setText(p.getTelefonoComprador());
        Mod_Correo.setText(p.getCorreoComprador());
        Mod_NombreProducto.setText(p.getNombreProducto());
        Mod_Cantidad.setText(p.getCantidadComprada());
        Mod_TotalCompra.setText(p.getTotalCompra());

        ArrayAdapter adapter = (ArrayAdapter) Mod_TipoProducto.getAdapter();
        int pos = adapter.getPosition(p.getTipoProducto());
        if (pos >= 0) Mod_TipoProducto.setSelection(pos);
    }

    private void guardar() {
        if (info.lista.isEmpty()) return;

        if (Mod_NombreComprador.getText().toString().isEmpty() ||
                Mod_TotalCompra.getText().toString().isEmpty()) {
            Toast.makeText(this, "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        producto p = info.lista.get(posicion);

        p.setNombreComprador(Mod_NombreComprador.getText().toString());
        p.setTelefonoComprador(Mod_Telefono.getText().toString());
        p.setCorreoComprador(Mod_Correo.getText().toString());
        p.setNombreProducto(Mod_NombreProducto.getText().toString());
        p.setCantidadComprada(Mod_Cantidad.getText().toString());
        p.setTotalCompra(Mod_TotalCompra.getText().toString());
        p.setTipoProducto(Mod_TipoProducto.getSelectedItem().toString());

        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();

        // Opcional: Cerrar al guardar para volver a la lista
        // finish();
    }

    // ==========================================
    //   AGREGADO: LÓGICA DEL MENÚ (TOOLBAR)
    // ==========================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_registro) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_lista) {
            Intent intent = new Intent(this, Lista_Dinamica.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.opc1) { // Eliminar
            Intent intent = new Intent(this, Eliminar.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_modificar_nav) {
            // Ya estamos aquí
            return true;
        } else if (id == R.id.menu_cerrar_sesion) {
            getSharedPreferences("Sesion", Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}