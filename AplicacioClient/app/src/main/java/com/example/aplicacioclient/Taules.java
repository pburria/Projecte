 package com.example.aplicacioclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.milaifontanals.projecte.InfoTaula;
import org.milaifontanals.projecte.adapters.InfoTaulaAdapter;

import java.util.ArrayList;
import java.util.List;

 public class Taules extends AppCompatActivity {
     public static List<InfoTaula> taules;
     public static RecyclerView recycledTaules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);
        int sessionId=getIntent().getIntExtra("session_id",0);

        InfoTaula t1=new InfoTaula(1,1,true,5,4,"Pau");
        InfoTaula t2=new InfoTaula(2,2,false,7,2,"Pep");
        InfoTaula t3=new InfoTaula(3,3,true,6,6,"Pau");
        InfoTaula t4=new InfoTaula(4,4,false,7,4,"Pep");
        InfoTaula t5=new InfoTaula(5,5,true,2,1,"Pau");
        InfoTaula t6=new InfoTaula(6,-1,true,2,1,"Pau");
        InfoTaula t7=new InfoTaula(7,-1,true,2,1,"Pau");
        taules = new ArrayList<InfoTaula>();
        taules.add(t1);
        taules.add(t2);
        taules.add(t3);
        taules.add(t4);
        taules.add(t5);
        taules.add(t6);
        taules.add(t7);

        recycledTaules=findViewById(R.id.RecycledTaules);
        recycledTaules.setLayoutManager(new GridLayoutManager(this,3));
        recycledTaules.setHasFixedSize(true);

        InfoTaulaAdapter adapter=new InfoTaulaAdapter(taules);
        recycledTaules.setAdapter(adapter);
    }
}