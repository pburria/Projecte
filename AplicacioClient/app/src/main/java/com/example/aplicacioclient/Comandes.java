package com.example.aplicacioclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.InfoTaula;
import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;
import org.milaifontanals.projecte.adapters.InfoTaulaAdapter;
import org.milaifontanals.projecte.adapters.LiniaComandaAdapter;
import org.milaifontanals.projecte.adapters.PlatsAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Comandes extends AppCompatActivity {
    private  List<LiniaComanda> liniaComandas;
    private List<Plat> plats;
    private List<Categoria> categories;
    private  RecyclerView recycledComandes;
    private  RecyclerView recycledPlats;
    public static boolean editable;
    private int comanda;
    private int taula;
    Socket s,s1,s2;
    ObjectOutputStream oos,oos1,oos2;
    ObjectInputStream ois,ois1,ois2;
    Button btnConfirm;
    LiniaComandaAdapter liniaComandaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandes);

        comanda=getIntent().getIntExtra("comanda",0);
        taula=getIntent().getIntExtra("taula",0);
        if(comanda==-1){
            editable=true;
        }else{
            editable=false;
        }
        getCartaIComanda();
        btnConfirm=findViewById(R.id.btnGardarComanda);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editable==true){
                    crearComanda();
                }
            }
        });
    }

    private void getCartaIComanda(){
        Observable.fromCallable(() -> {
            try {
                categories=new ArrayList<>();
                plats=new ArrayList<>();
                liniaComandas=new ArrayList<>();

                s = new Socket(MainActivity .ip,MainActivity.port);
                s1 = new Socket(MainActivity .ip,MainActivity.port);

                oos = new ObjectOutputStream(s.getOutputStream());
                oos1 = new ObjectOutputStream(s1.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
                ois1 = new ObjectInputStream(s1.getInputStream());

                oos.writeInt(3);
                oos.flush();
                oos.writeInt(MainActivity.sesionId);
                oos.flush();

                int nCategoria = ois.readInt();
                if(nCategoria!=-1){
                    categories= (List<Categoria>) ois.readObject();
                    int nPlats=ois.readInt();
                    plats= (List<Plat>) ois.readObject();
                }

                oos1.writeInt(4);
                oos1.flush();
                oos1.writeInt(MainActivity.sesionId);
                oos1.flush();
                oos1.writeInt(comanda);
                oos1.flush();

                int nLiniaComanda = ois1.readInt();
                if(nLiniaComanda!=-1){
                    liniaComandas= (List<LiniaComanda>) ois1.readObject();
                }

            } catch (IOException e) {
                Log.e("ERROR", "Error connectant" , e);
            }finally {
                if(s!=null){
                    s.close();
                }
                if(s1!=null){
                    s1.close();
                }
            }

            return false;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    recycledPlats=findViewById(R.id.RecycledPlats);
                    recycledPlats.setLayoutManager(new GridLayoutManager(this,3));
                    recycledPlats.setHasFixedSize(true);

                    PlatsAdapter adapterPlat=new PlatsAdapter(plats,liniaComandas,this);
                    recycledPlats.setAdapter(adapterPlat);
                    gestioCategories();

                    recycledComandes=findViewById(R.id.RecycledComanda);
                    recycledComandes.setLayoutManager(new LinearLayoutManager(this));
                    recycledComandes.setHasFixedSize(true);

                    liniaComandaAdapter=new LiniaComandaAdapter(liniaComandas,plats);
                    recycledComandes.setAdapter(liniaComandaAdapter);

                    TextView total=findViewById(R.id.txtTotalComanda);
                    BigDecimal totall=new BigDecimal(0);
                    DecimalFormat format = new DecimalFormat();
                    format.setMaximumFractionDigits(2);

                    for(int i=0;i<liniaComandas.size();i++){
                        BigDecimal preu=new BigDecimal(0);
                        for(int j=0;j<plats.size();j++){
                            if(liniaComandas.get(i).getPlat()==plats.get(j).getCodi()){
                                preu=plats.get(j).getPreu();
                            }
                        }
                        int qtat=liniaComandas.get(i).getQtat();
                        totall=BigDecimal.valueOf(qtat).multiply(preu).add(totall);
                    }
                    total.setText("TOTAL: "+format.format(totall)+"€");

                });
    }

    private void crearComanda(){
        Observable.fromCallable(() -> {
            try {
                s2 = new Socket(MainActivity .ip,MainActivity.port);

                oos2 = new ObjectOutputStream(s2.getOutputStream());
                ois2 = new ObjectInputStream(s2.getInputStream());

                oos2.writeInt(5);
                oos2.flush();
                oos2.writeInt(MainActivity.sesionId);
                oos2.flush();
                oos2.writeInt(taula);
                oos2.flush();
                oos2.writeInt(liniaComandas.size());
                oos2.flush();
                oos2.writeObject(liniaComandas);
                oos2.flush();
                oos2.writeObject(MainActivity.c);
                oos2.flush();
                int nComanda=ois2.readInt();

            } catch (IOException e) {
                Log.e("ERROR", "Error connectant" , e);
            }finally {
                if(s2!=null){
                    s2.close();
                }
            }

            return false;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    finish();
                });
    }

    public void afegirPlat(int index){
        liniaComandaAdapter.notifyItemInserted(index);
    }
    public void actualitzarPlat(int index){
        liniaComandaAdapter.notifyItemChanged(index);
    }
    public void eliminarPlat(int index){
        liniaComandaAdapter.notifyItemRemoved(index);
    }

    private void gestioCategories(){
        Spinner s=findViewById(R.id.spinner);
        ArrayAdapter<Categoria> adapterCat=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        s.setAdapter(adapterCat);

    }

}