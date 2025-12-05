package com.example.a20300846_zeusvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Global.info;
import Pojo.producto;

public class MainActivity extends AppCompatActivity {

    EditText NomComprador, TelComprador, Correo, CantidadComp, TotalComp, NomProd;
    Spinner TipoProd;
    Button button, ButtonVer;
    Toolbar toolbar;
    SharedPreferences archivo;

    ArrayList<String> listaNombresTipos = new ArrayList<>();
    ArrayList<Integer> listaIdTipos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        archivo = getSharedPreferences("Sesion", Context.MODE_PRIVATE);

        NomComprador = findViewById(R.id.NombreComprador);
        TelComprador = findViewById(R.id.Telefono);
        Correo = findViewById(R.id.Correo);
        TipoProd = findViewById(R.id.TipoProducto);
        CantidadComp = findViewById(R.id.CantidadComprada);
        TotalComp = findViewById(R.id.TotalCompra);
        NomProd = findViewById(R.id.NombreProducto);

        button = findViewById(R.id.button);
        ButtonVer = findViewById(R.id.ButtonVer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargarTiposProducto();

        button.setOnClickListener(v -> registrarVenta());

        ButtonVer.setOnClickListener(v -> {
            Intent verActivity = new Intent(MainActivity.this, Lista_Dinamica.class);
            startActivity(verActivity);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargarTiposProducto() {
        String url = "http://10.0.2.2/bd/obtener_tipos.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaNombresTipos.clear();
                    listaIdTipos.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            listaIdTipos.add(obj.getInt("id"));
                            listaNombresTipos.add(obj.getString("nombre"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, listaNombresTipos);
                        TipoProd.setAdapter(adapter);
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Toast.makeText(MainActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void registrarVenta() {
        String url = "http://10.0.2.2/bd/insertar.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    guardarLocalmente();
                    limpiarCampos();
                },
                error -> Toast.makeText(MainActivity.this, "Error en registro", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                int idUsuario = archivo.getInt("id_usuario", -1);
                params.put("id_cuenta", String.valueOf(idUsuario));

                int posSpinner = TipoProd.getSelectedItemPosition();
                if (posSpinner >= 0 && posSpinner < listaIdTipos.size()) {
                    params.put("id_tipo_producto", String.valueOf(listaIdTipos.get(posSpinner)));
                } else {
                    params.put("id_tipo_producto", "1");
                }

                params.put("nombre_producto", NomProd.getText().toString());
                params.put("nombre", NomComprador.getText().toString());
                params.put("telefono", TelComprador.getText().toString());
                params.put("correo", Correo.getText().toString());
                params.put("cantidad", CantidadComp.getText().toString());
                params.put("total", TotalComp.getText().toString());
                params.put("fecha", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void guardarLocalmente() {
        producto p = new producto();
        p.setIdentificador(NomComprador.getText().toString());
        p.setNombreProducto(NomProd.getText().toString());
        p.setTelefono(TelComprador.getText().toString());
        p.setCorreo(Correo.getText().toString());
        p.setCantidad(CantidadComp.getText().toString());
        p.setTotal_final(TotalComp.getText().toString());
        if (TipoProd.getSelectedItem() != null) {
            p.setNombre_producto_tipo(TipoProd.getSelectedItem().toString());
        }
        info.lista.add(p);
    }

    private void limpiarCampos() {
        NomComprador.setText("");
        NomProd.setText("");
        TelComprador.setText("");
        Correo.setText("");
        CantidadComp.setText("");
        TotalComp.setText("");
        TipoProd.setSelection(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_registro) return true;
        if (id == R.id.menu_lista) {
            startActivity(new Intent(this, Lista_Dinamica.class));
            return true;
        }
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