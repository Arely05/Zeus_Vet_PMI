package com.example.a20300846_zeusvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class Modificar extends AppCompatActivity {

    EditText Mod_NombreComprador, Mod_Telefono, Mod_Correo, Mod_NombreProducto, Mod_Cantidad, Mod_TotalCompra;
    Spinner Mod_TipoProducto;
    Button btnAnterior, btnGuardar, btnSiguiente;
    Toolbar toolbar;
    SharedPreferences archivo;

    ArrayList<String> listaNombresTipos = new ArrayList<>();
    ArrayList<Integer> listaIdTipos = new ArrayList<>();

    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        archivo = getSharedPreferences("Sesion", Context.MODE_PRIVATE);

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        posicion = getIntent().getIntExtra("posicion", 0);

        cargarTiposProducto();

        btnSiguiente.setOnClickListener(v -> {
            if (posicion < info.lista.size() - 1) {
                posicion++;
                cargarDatosEnPantalla();
            } else {
                Toast.makeText(this, "Último registro", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnterior.setOnClickListener(v -> {
            if (posicion > 0) {
                posicion--;
                cargarDatosEnPantalla();
            } else {
                Toast.makeText(this, "Primer registro", Toast.LENGTH_SHORT).show();
            }
        });

        btnGuardar.setOnClickListener(v -> guardarCambiosBD());
    }

    private void cargarTiposProducto() {
        String url = "http://10.0.2.2/bd/obtener_tipos.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaNombresTipos.clear();
                    listaIdTipos.clear();
                    try {
                        for(int i=0; i<response.length(); i++){
                            JSONObject obj = response.getJSONObject(i);
                            listaIdTipos.add(obj.getInt("id"));
                            listaNombresTipos.add(obj.getString("nombre"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaNombresTipos);
                        Mod_TipoProducto.setAdapter(adapter);

                        cargarDatosEnPantalla();
                    } catch (JSONException e) { e.printStackTrace(); }
                }, error -> {});
        Volley.newRequestQueue(this).add(request);
    }

    private void cargarDatosEnPantalla() {
        if(info.lista.isEmpty()) return;

        producto p = info.lista.get(posicion);

        Mod_NombreComprador.setText(p.getIdentificador());
        Mod_NombreProducto.setText(p.getNombreProducto());
        Mod_Telefono.setText(p.getTelefono());
        Mod_Correo.setText(p.getCorreo());
        Mod_Cantidad.setText(p.getCantidad());
        Mod_TotalCompra.setText(p.getTotal_final());

        if(p.getNombre_producto_tipo() != null) {
            ArrayAdapter adap = (ArrayAdapter) Mod_TipoProducto.getAdapter();
            if (adap != null) {
                int pos = adap.getPosition(p.getNombre_producto_tipo());
                if(pos >= 0) Mod_TipoProducto.setSelection(pos);
            }
        }
    }

    private void guardarCambiosBD() {
        String url = "http://10.0.2.2/bd/modificar.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(Modificar.this, response, Toast.LENGTH_SHORT).show();
                    actualizarObjetoLocal();
                },
                error -> Toast.makeText(Modificar.this, "Error al modificar", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                producto p = info.lista.get(posicion);

                params.put("id", String.valueOf(p.getId_venta()));

                int idUsuario = archivo.getInt("id_usuario", -1);
                params.put("id_cuenta", String.valueOf(idUsuario));

                int posSpinner = Mod_TipoProducto.getSelectedItemPosition();
                if (!listaIdTipos.isEmpty() && posSpinner >= 0) {
                    params.put("id_tipo_producto", String.valueOf(listaIdTipos.get(posSpinner)));
                } else {
                    params.put("id_tipo_producto", String.valueOf(p.getId_tipo_producto()));
                }

                params.put("nombre_producto", Mod_NombreProducto.getText().toString());
                params.put("nombre", Mod_NombreComprador.getText().toString());
                params.put("telefono", Mod_Telefono.getText().toString());
                params.put("correo", Mod_Correo.getText().toString());
                params.put("cantidad", Mod_Cantidad.getText().toString());
                params.put("total", Mod_TotalCompra.getText().toString());

                params.put("fecha", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void actualizarObjetoLocal(){
        producto p = info.lista.get(posicion);
        p.setIdentificador(Mod_NombreComprador.getText().toString());
        p.setNombreProducto(Mod_NombreProducto.getText().toString());
        p.setTelefono(Mod_Telefono.getText().toString());
        p.setCorreo(Mod_Correo.getText().toString());
        p.setCantidad(Mod_Cantidad.getText().toString());
        p.setTotal_final(Mod_TotalCompra.getText().toString());
        if (Mod_TipoProducto.getSelectedItem() != null) {
            p.setNombre_producto_tipo(Mod_TipoProducto.getSelectedItem().toString());
        }
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
        if (id == R.id.menu_lista) {
            startActivity(new Intent(this, Lista_Dinamica.class));
            return true;
        }
        if (id == R.id.menu_modificar_nav) return true;
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