package com.example.a20300846_zeusvet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import necesario

import android.os.Bundle;
import android.view.View;
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
    Toolbar toolbar; // Variable declarada

    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        // --- CORRECCIÓN 1: INICIALIZAR TOOLBAR ---
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modificar Venta");
        }

        posicion = getIntent().getIntExtra("posicion", 0);

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

        // --- CORRECCIÓN 2: SPINNER IGUAL A MAIN ACTIVITY ---
        String[] tipos = {"Shampoo", "Acondicionador", "Crema para peinar"};
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        Mod_TipoProducto.setAdapter(adap);

        cargarDatos();

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
        if (info.lista.isEmpty()) return; // Evitar crash si la lista está vacía

        producto p = info.lista.get(posicion);

        Mod_NombreComprador.setText(p.getNombreComprador());
        Mod_Telefono.setText(p.getTelefonoComprador()); // Uso del getter correcto
        Mod_Correo.setText(p.getCorreoComprador()); // Uso del getter correcto
        Mod_NombreProducto.setText(p.getNombreProducto());
        Mod_Cantidad.setText(p.getCantidadComprada()); // Uso del getter correcto
        Mod_TotalCompra.setText(p.getTotalCompra());

        ArrayAdapter adapter = (ArrayAdapter) Mod_TipoProducto.getAdapter();
        int pos = adapter.getPosition(p.getTipoProducto());
        if (pos >= 0) Mod_TipoProducto.setSelection(pos);
    }

    private void guardar() {
        if (Mod_NombreComprador.getText().toString().isEmpty() ||
                Mod_TotalCompra.getText().toString().isEmpty()) {
            Toast.makeText(this, "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        producto p = info.lista.get(posicion);

        p.setNombreComprador(Mod_NombreComprador.getText().toString());
        p.setTelefonoComprador(Mod_Telefono.getText().toString()); // Setter correcto
        p.setCorreoComprador(Mod_Correo.getText().toString()); // Setter correcto
        p.setNombreProducto(Mod_NombreProducto.getText().toString());
        p.setCantidadComprada(Mod_Cantidad.getText().toString()); // Setter correcto
        p.setTotalCompra(Mod_TotalCompra.getText().toString());
        p.setTipoProducto(Mod_TipoProducto.getSelectedItem().toString());

        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }
}