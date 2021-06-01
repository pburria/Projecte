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

import org.milaifontanals.projecte.Cambrer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView txtErrorLogin;
    private EditText edtUsuari,edtContrasenya;
    private int sesionId;
    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;
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
                if(correcte){
                    Intent i=new Intent(MainActivity.this,Taules.class);
                    i.putExtra("session_id",12345);
                    startActivity(i);
                }
                edtUsuari.setText("");
                edtContrasenya.setText("");

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
        Observable.fromCallable(() -> {
            try {
                s = new Socket("192.168.1.45", 9876);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeInt(1);
                ArrayList<String> dadesConexio = new ArrayList<String>();
                dadesConexio.add("" + edtUsuari.getText());
                dadesConexio.add("" + edtContrasenya.getText());
                oos.writeObject(dadesConexio);
                ois = new ObjectInputStream(s.getInputStream());
                Log.d("SESSIONN",""+sesionId);
                sesionId = ois.readInt();
                if(sesionId!=-1){
                    Cambrer c=(Cambrer) ois.readObject();
                    Log.d("Cambrer",c.toString());
                }

            } catch (IOException e) {
                Log.d("ERROR", "" + e);
            }
            s.close();
            return false;
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {

                });
        Log.d("SESSION",""+sesionId);
        if(sesionId!=-1){
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