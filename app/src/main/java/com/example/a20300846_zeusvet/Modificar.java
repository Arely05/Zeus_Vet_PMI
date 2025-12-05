package com.example.a20300846_zeusvet;

import androidx.appcompat.app.AppCompatActivity;

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

    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        // ---------------------------
        // 1. RECIBIR LA POSICIÓN
        // ---------------------------
        posicion = getIntent().getIntExtra("posicion", 0);

        // ---------------------------
        // 2. REFERENCIAS XML
        // ---------------------------
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

        // ---------------------------
        // 3. SPINNER (asumiendo tipos)
        // ---------------------------
        String[] tipos = {"Pelo", "Vacuna", "Servicio", "Producto General"};
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        Mod_TipoProducto.setAdapter(adap);

        // ---------------------------
        // 4. CARGAR DATOS DEL PRODUCTO
        // ---------------------------
        cargarDatos();

        // ---------------------------
        // 5. BOTÓN GUARDAR
        // ---------------------------
        btnGuardar.setOnClickListener(v -> guardar());

        // ---------------------------
        // 6. BOTÓN SIGUIENTE
        // ---------------------------
        btnSiguiente.setOnClickListener(v -> {
            if (posicion < info.lista.size() - 1) {
                posicion++;
                cargarDatos();
            } else {
                Toast.makeText(this, "Último registro", Toast.LENGTH_SHORT).show();
            }
        });

        // ---------------------------
        // 7. BOTÓN ANTERIOR
        // ---------------------------
        btnAnterior.setOnClickListener(v -> {
            if (posicion > 0) {
                posicion--;
                cargarDatos();
            } else {
                Toast.makeText(this, "Primer registro", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // ====================================================
    //      CARGAR INFORMACIÓN DEL REGISTRO
    // ====================================================
    private void cargarDatos() {
        producto p = info.lista.get(posicion);

        Mod_NombreComprador.setText(p.getNombreComprador());
        Mod_Telefono.setText(p.getTelefono());
        Mod_Correo.setText(p.getCorreo());
        Mod_NombreProducto.setText(p.getNombreProducto());
        Mod_Cantidad.setText(p.getCantidad());
        Mod_TotalCompra.setText(p.getTotalCompra());

        // Seleccionar el spinner según el tipo guardado
        ArrayAdapter adapter = (ArrayAdapter) Mod_TipoProducto.getAdapter();
        int pos = adapter.getPosition(p.getTipoProducto());
        if (pos >= 0) Mod_TipoProducto.setSelection(pos);
    }

    // ====================================================
    //                  GUARDAR CAMBIOS
    // ====================================================
    private void guardar() {

        if (Mod_NombreComprador.getText().toString().isEmpty() ||
                Mod_NombreProducto.getText().toString().isEmpty() ||
                Mod_Cantidad.getText().toString().isEmpty() ||
                Mod_TotalCompra.getText().toString().isEmpty()) {

            Toast.makeText(this, "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        producto p = info.lista.get(posicion);

        p.setNombreComprador(Mod_NombreComprador.getText().toString());
        p.setTelefono(Mod_Telefono.getText().toString());
        p.setCorreo(Mod_Correo.getText().toString());
        p.setNombreProducto(Mod_NombreProducto.getText().toString());
        p.setCantidad(Mod_Cantidad.getText().toString());
        p.setTotalCompra(Mod_TotalCompra.getText().toString());
        p.setTipoProducto(Mod_TipoProducto.getSelectedItem().toString());

        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();

        // Opcional: refrescar la lista al regresar
        setResult(RESULT_OK);
    }
}
