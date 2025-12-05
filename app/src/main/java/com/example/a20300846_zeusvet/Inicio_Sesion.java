package com.example.a20300846_zeusvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Inicio_Sesion extends AppCompatActivity {

    EditText Usuario, Contrasena;
    Button ButtonInicio;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion);

        Usuario = findViewById(R.id.Mod_Usuario);
        Contrasena = findViewById(R.id.Mod_Contraseña);
        ButtonInicio = findViewById(R.id.Button_Inicio);

        archivo = getSharedPreferences("Sesion", Context.MODE_PRIVATE);

        ButtonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://10.0.2.2/bd/ingreso.php?usr=";
                url = url + Usuario.getText().toString();
                url = url + "&pass=" + Contrasena.getText().toString();

                JsonObjectRequest pet = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("usr") != -1) {
                                        Intent principal = new Intent(Inicio_Sesion.this, MainActivity.class);
                                        SharedPreferences.Editor editor = archivo.edit();
                                        editor.putInt("id_usuario", response.getInt("usr"));
                                        editor.apply();
                                        startActivity(principal);
                                        finish();
                                    } else {
                                        Usuario.setText("");
                                        Contrasena.setText("");
                                        Toast.makeText(Inicio_Sesion.this,
                                                "Usuario o Contraseña Incorrectos",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                Toast.makeText(Inicio_Sesion.this,
                                        response.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("yo", error.getMessage());
                            }
                        }
                );

                RequestQueue lanzarPeticion = Volley.newRequestQueue(Inicio_Sesion.this);
                lanzarPeticion.add(pet);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}