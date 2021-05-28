package com.example.aplicacioclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtErrorLogin;
    private EditText edtUsuari,edtContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AplicacioClient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuari=findViewById(R.id.Usuari);
        edtContrasenya=findViewById(R.id.Contrasenya);
        txtErrorLogin=findViewById(R.id.ErrorLogin);

        caregarPreferencies();

        Button btnLogin=findViewById(R.id.Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcte=existeixUsuari(""+edtUsuari.getText(),""+edtContrasenya.getText());
                edtUsuari.setText("");
                edtContrasenya.setText("");
                Intent i=new Intent(MainActivity.this,Taules.class);
                i.putExtra("session_id",12345);
                 startActivity(i);
            }
        });
    }

    private void caregarPreferencies(){
        SharedPreferences loginGuardat=getSharedPreferences("login", Context.MODE_PRIVATE);
        if(loginGuardat!=null){
            edtUsuari.setText(loginGuardat.getString("usuari",""));
            edtContrasenya.setText(loginGuardat.getString("password",""));
        }

    }

    private boolean existeixUsuari(String usu,String pass){
        if(usu.equals("plopez") && pass.equals("plopez")){
            txtErrorLogin.setVisibility(View.INVISIBLE);
            omplirArxiuPreferencies();
            return true;
        }else{
            txtErrorLogin.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private void omplirArxiuPreferencies(){
        SharedPreferences login=getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=login.edit();
        edit.putString("usuari",""+edtUsuari.getText());
        edit.putString("password",""+edtContrasenya.getText());
        edit.commit();
    }
}