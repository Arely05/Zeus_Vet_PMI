package com.example.torneo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Pojo.producto;
import Pojo.global.info;
import adaptadores.adaptadoreliminar;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables como campos de la clase
    EditText NomEqui, NomCap, Tel , HoraIns, TiempoIns, Pago;
    Button button, ButtonVer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Debe usar el layout corregido que tiene todos los campos
        setContentView(R.layout.activity_main);

        // Inicialización de Vistas (ya no debe marcar error)
        NomEqui = findViewById(R.id.NombreEquipo);
        NomCap = findViewById(R.id.NombreCapitan);
        Tel = findViewById(R.id.Telefono);
        HoraIns = findViewById(R.id.HoraInscripcion);
        TiempoIns = findViewById(R.id.TiempoInscripcion);
        Pago = findViewById(R.id.Pago);
        button = findViewById(R.id.button);
        ButtonVer = findViewById(R.id.ButtonVer);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica de guardado con los nuevos campos
                producto nuevoProducto = new producto();
                nuevoProducto.setNomProd(NomEqui.getText().toString());        // Nombre del Producto
                nuevoProducto.setDescripcion(NomCap.getText().toString());     // Descripción
                nuevoProducto.setPrecio(Tel.getText().toString());             // Precio
                nuevoProducto.setStock(HoraIns.getText().toString());          // Stock
                nuevoProducto.setTipoProducto(TiempoIns.getText().toString()); // Tipo de Producto
                nuevoProducto.setCantProducto(Pago.getText().toString());      // Cantidad de Producto (Volumen/Peso)

                info.lista.add(nuevoProducto);
                Toast.makeText(MainActivity.this, "Producto registrado: " + nuevoProducto.getNomProd(), Toast.LENGTH_SHORT).show();


            }
        });

        ButtonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verActivity = new Intent(MainActivity.this, ver.class);
                startActivity(verActivity);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.opc1){
            Intent eliminar=new Intent(this, Eliminar.class);
            startActivity(eliminar);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}