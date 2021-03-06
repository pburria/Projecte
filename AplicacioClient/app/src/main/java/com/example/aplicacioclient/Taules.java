 package com.example.aplicacioclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.milaifontanals.projecte.InfoTaula;
import org.milaifontanals.projecte.adapters.InfoTaulaAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

 public class Taules extends AppCompatActivity {
     private List<InfoTaula> taules;
     private RecyclerView recycledTaules;
     Socket s;
     ObjectOutputStream oos;
     ObjectInputStream ois;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);
        getTaules(MainActivity.sesionId);

        Timer timer = new Timer();
        class actualitzarTaules extends TimerTask {
            public void run() {
                getTaules(MainActivity.sesionId);
            }
        }
        TimerTask actualitzarTaules = new actualitzarTaules();
        timer.scheduleAtFixedRate(actualitzarTaules, 10000, 10000);
    }

    private void getTaules(int sessio){
        Observable.fromCallable(() -> {
            try {
                s = new Socket(MainActivity .ip,MainActivity.port);

                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());

                oos.writeInt(2);
                oos.flush();
                oos.writeInt(sessio);
                oos.flush();

                int numTaules = ois.readInt();

                if(numTaules!=-1){
                    taules = new ArrayList<InfoTaula>();
                    taules= (List<InfoTaula>) ois.readObject();
                }

            } catch (IOException e) {
                Log.e("ERROR", "Error connectant" , e);
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

                    recycledTaules=findViewById(R.id.RecycledTaules);
                    recycledTaules.setLayoutManager(new GridLayoutManager(this,3));
                    recycledTaules.setHasFixedSize(true);
                    CrearAdapter();
                });

    }

      private void CrearAdapter(){
        InfoTaulaAdapter adapter=new InfoTaulaAdapter(taules);
        recycledTaules.setAdapter(adapter);
    }
}