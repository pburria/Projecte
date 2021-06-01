package com.example.aplicacioclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;
import org.milaifontanals.projecte.adapters.LiniaComandaAdapter;
import org.milaifontanals.projecte.adapters.PlatsAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Comandes extends AppCompatActivity {
    public static List<LiniaComanda> liniaComandas;
    public static List<Plat> plats;
    public static RecyclerView recycledComandes;
    public static RecyclerView recycledPlats;
    public static boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandes);

        int comanda=getIntent().getIntExtra("comanda",0);
        if(comanda==-1){
            editable=true;
        }else{
            editable=false;
        }

        LiniaComanda l1=new LiniaComanda(1,2,1,5,true);
        LiniaComanda l2=new LiniaComanda(1,3,2,1,true);
        LiniaComanda l3=new LiniaComanda(1,6,3,3,true);
        LiniaComanda l4=new LiniaComanda(1,8,4,6,true);
        LiniaComanda l5=new LiniaComanda(1,4,5,2,true);
        /*LiniaComanda l6=new LiniaComanda(1,5,6,1,true);
        LiniaComanda l7=new LiniaComanda(1,7,7,8,true);
        LiniaComanda l8=new LiniaComanda(1,1,8,8,true);*/

        liniaComandas = new ArrayList<LiniaComanda>();
        liniaComandas.add(l1);
        liniaComandas.add(l2);
        liniaComandas.add(l3);
        liniaComandas.add(l4);
        liniaComandas.add(l5);
        /*liniaComandas.add(l6);
        liniaComandas.add(l7);
        liniaComandas.add(l8);*/

        Plat p1=new Plat(1,"Entrecot",new BigDecimal(15.01),5,null);
        Plat p2=new Plat(2,"Pollastre",new BigDecimal(17.61),5,null);
        Plat p3=new Plat(3,"LLom",new BigDecimal(13.21),5,null);
        Plat p4=new Plat(4,"Cargols",new BigDecimal(24.41),5,null);
        Plat p5=new Plat(5,"LLmantol",new BigDecimal(52.51),5,null);
        Plat p6=new Plat(6,"Lokesea",new BigDecimal(34.21),5,null);
        Plat p7=new Plat(7,"Mamut",new BigDecimal(12.11),5,null);
        Plat p8=new Plat(8,"kunsito",new BigDecimal(12.51),5,null);
        Plat p9=new Plat(9,"Pit de pollastre amb llenties",new BigDecimal(16.81),5,null);

        plats = new ArrayList<Plat>();
        plats.add(p1);
        plats.add(p2);
        plats.add(p3);
        plats.add(p4);
        plats.add(p5);
        plats.add(p6);
        plats.add(p7);
        plats.add(p8);
        plats.add(p9);

        recycledComandes=findViewById(R.id.RecycledComanda);
        recycledComandes.setLayoutManager(new LinearLayoutManager(this));
        recycledComandes.setHasFixedSize(true);

        LiniaComandaAdapter adapter=new LiniaComandaAdapter(liniaComandas,plats);
        recycledComandes.setAdapter(adapter);

        recycledPlats=findViewById(R.id.RecycledPlats);
        recycledPlats.setLayoutManager(new GridLayoutManager(this,3));
        recycledPlats.setHasFixedSize(true);

        PlatsAdapter adapterPlat=new PlatsAdapter(plats);
        recycledPlats.setAdapter(adapterPlat);

    }
}