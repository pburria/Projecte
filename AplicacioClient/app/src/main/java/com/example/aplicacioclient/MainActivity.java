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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    //public static String ip="192.168.43.213";
    public static String ip="192.168.1.45";
    public static  int port=9876;
    public static Cambrer c;
    private TextView txtErrorLogin;
    private EditText edtUsuari,edtContrasenya;
    public static int sesionId;
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
                existeixUsuari(""+edtUsuari.getText(),""+edtContrasenya.getText());
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

    private void existeixUsuari(String usu,String pass){
        sesionId = -1;
        Observable.fromCallable(() -> {
            try {
                s = new Socket(ip,port);
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());

                oos.writeInt(1);

                ArrayList<String> dadesConexio = new ArrayList<String>();
                dadesConexio.add(usu);
                dadesConexio.add(pass);
                oos.writeObject(dadesConexio);
                sesionId = ois.readInt();

                if(sesionId!=-1){
                    c=(Cambrer) ois.readObject();
                }

            } catch (IOException e) {
            }finally {
                if(s!=null){
                    s.close();
                }
            }

            return false;
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    if(sesionId!=-1){
                        txtErrorLogin.setVisibility(View.INVISIBLE);
                        omplirArxiuPreferencies();
                        Intent i=new Intent(MainActivity.this,Taules.class);
                        startActivity(i);
                    }else{
                        txtErrorLogin.setVisibility(View.VISIBLE);
                        edtUsuari.setText("");
                        edtContrasenya.setText("");
                    }
                });
    }

    private void omplirArxiuPreferencies(){
        SharedPreferences login=getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=login.edit();
        edit.putString("usuari",""+edtUsuari.getText());
        edit.putString("password",""+edtContrasenya.getText());
        edit.commit();
    }
}