package com.example.a20300846_zeusvet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Pojo.producto;
import Global.info;
import Adaptadores.adaptadoreliminar;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables como campos de la clase, actualizados
    EditText NomComprador, TelComprador, Correo, NomProd, CantidadComp, TotalComp;
    Spinner TipoProd; // El antiguo TiempoIns, ahora es un Spinner
    Button button, ButtonVer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialización de Vistas (Actualizada y corregida)
        NomComprador = findViewById(R.id.NombreComprador); // Nombre del comprador
        TelComprador = findViewById(R.id.Telefono); // Telefono
        Correo = findViewById(R.id.Correo); // Correo
        NomProd = findViewById(R.id.NombreProducto); // Nombre del producto
        TipoProd = findViewById(R.id.TipoProducto); // Tipo de producto (Spinner)
        CantidadComp = findViewById(R.id.CantidadComprada); // Cantidad de productos comprados
        TotalComp = findViewById(R.id.TotalCompra); // Total de compra (Nuevo ID)

        button = findViewById(R.id.button);
        ButtonVer = findViewById(R.id.ButtonVer);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar el Spinner para Tipo de Producto
        String[] tiposProducto = {"Shampoo", "Acondicionador", "Crema para peinar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tiposProducto);
        TipoProd.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica de guardado con los nuevos campos
                producto nuevoProducto = new producto();
                nuevoProducto.setNombreComprador(NomComprador.getText().toString());
                nuevoProducto.setTelefonoComprador(TelComprador.getText().toString());
                nuevoProducto.setCorreoComprador(Correo.getText().toString());
                nuevoProducto.setNombreProducto(NomProd.getText().toString());
                nuevoProducto.setTipoProducto(TipoProd.getSelectedItem().toString()); // Valor del Spinner
                nuevoProducto.setCantidadComprada(CantidadComp.getText().toString());
                nuevoProducto.setTotalCompra(TotalComp.getText().toString()); // Nuevo campo

                info.lista.add(nuevoProducto);
                Toast.makeText(MainActivity.this, "Venta registrada: " + nuevoProducto.getNombreComprador(), Toast.LENGTH_SHORT).show();

                // Opcional: limpiar campos
                NomComprador.setText("");
                TelComprador.setText("");
                Correo.setText("");
                NomProd.setText("");
                CantidadComp.setText("");
                TotalComp.setText("");
                TipoProd.setSelection(0);
            }
        });

        ButtonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se navega a Lista_Dinamica.class, que es el visor de la lista.
                Intent verActivity = new Intent(MainActivity.this, Lista_Dinamica.class);
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