package com.example.a20300846_zeusvet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Global.info;
import Pojo.producto;

public class Card extends AppCompatActivity {

    TextView tvNombre, tvTelefono, tvCorreo, tvNombreProducto, tvTipoProducto, tvCantidad, tvTotal;
    Button btnLlamar;
    Toolbar toolbar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() != null) getSupportActionBar().setTitle("Detalles del Pedido");
        }

        tvNombre = findViewById(R.id.card_nombrecomprador);
        tvTelefono = findViewById(R.id.card_telefono);
        tvCorreo = findViewById(R.id.card_correo);
        tvNombreProducto = findViewById(R.id.card_nombreproducto);
        tvTipoProducto = findViewById(R.id.card_producto);
        tvCantidad = findViewById(R.id.card_cantidad);
        tvTotal = findViewById(R.id.card_total);
        btnLlamar = findViewById(R.id.b_llamar);

        position = getIntent().getIntExtra("posicion", -1);

        if (position != -1 && position < info.lista.size()) {
            producto p = info.lista.get(position);

            tvNombre.setText(p.getIdentificador());
            tvTelefono.setText(p.getTelefono());
            tvCorreo.setText(p.getCorreo());
            tvNombreProducto.setText(p.getNombreProducto());
            tvTipoProducto.setText(p.getNombre_producto_tipo());
            tvCantidad.setText(p.getCantidad());
            tvTotal.setText("$" + p.getTotal_final());


            btnLlamar.setOnClickListener(v -> realizarLlamada(p.getTelefono()));

            tvTelefono.setOnClickListener(v -> realizarLlamada(p.getTelefono()));

        } else {
            Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void realizarLlamada(String numero) {
        if (numero != null && !numero.isEmpty()) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + numero));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Número no disponible", Toast.LENGTH_SHORT).show();
        }
    }
}