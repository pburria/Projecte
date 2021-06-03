package org.milaifontanals.projecte.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacioclient.Comandes;
import com.example.aplicacioclient.MainActivity;
import com.example.aplicacioclient.R;
import com.example.aplicacioclient.Taules;

import org.milaifontanals.projecte.InfoTaula;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InfoTaulaAdapter extends RecyclerView.Adapter<InfoTaulaAdapter.ViewHolder> {

    private List<InfoTaula> listInfoTaules;
    public InfoTaulaAdapter(List<InfoTaula> llistaInfoTaules){
        listInfoTaules=llistaInfoTaules;
    }

    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View filaView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fila_taula,viewGroup,false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoTaula inf=listInfoTaules.get(position);
        holder.txtNumTaula.setText(""+inf.getNum());

        if(inf.isEsMeva()==false){
            holder.txtNomCambrer.setText(inf.getNomCambrer());
            holder.itemView.setBackgroundColor(Color.GRAY);
            holder.progressBar.setVisibility(View.INVISIBLE);
        }else{
            holder.itemView.setBackgroundColor(Color.GREEN);
            holder.progressBar.setMax(inf.getPlatsTotals());
            holder.progressBar.setProgress(inf.getPlatsPreparats());
        }

        if(inf.getCodi_comanda()==-1){
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.txtNomCambrer.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return listInfoTaules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNumTaula,txtNomCambrer;
        public ProgressBar progressBar;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txtNumTaula=fila.findViewById(R.id.txtNumTaula);
            txtNomCambrer=fila.findViewById(R.id.txtNomCambrer);
            progressBar=fila.findViewById(R.id.progressBar);

            fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InfoTaula taulaSelec;
                    taulaSelec= listInfoTaules.get(getAdapterPosition());
                    Intent intent =  new Intent(v.getContext(), Comandes.class);
                    intent.putExtra("comanda",taulaSelec.getCodi_comanda());
                    intent.putExtra("taula",taulaSelec.getNum());
                    v.getContext().startActivity(intent);
                }
            });

            fila.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    InfoTaula taulaSelec;
                    taulaSelec= listInfoTaules.get(getAdapterPosition());
                    if(taulaSelec.getCodi_comanda()!=-1){
                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Buidar taula");
                        builder.setMessage("Estas segur que vols buidar la taula?");
                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buidarTaula();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                    return false;
                }
            });
        }
        private void buidarTaula(){
            Observable.fromCallable(() -> {
                try {
                    s = new Socket(MainActivity .ip,MainActivity.port);

                    oos = new ObjectOutputStream(s.getOutputStream());
                    ois = new ObjectInputStream(s.getInputStream());

                    oos.writeInt(6);
                    oos.flush();
                    oos.writeInt(MainActivity.sesionId);
                    oos.flush();

                    InfoTaula taulaSelec;
                    taulaSelec= listInfoTaules.get(getAdapterPosition());
                    taulaSelec.setCodi_comanda(-1);

                    oos.writeInt(taulaSelec.getNum());
                    oos.flush();

                    int correcte = ois.readInt();

                } catch (IOException e) {
                    Log.e("ERROR", "Error connectant" , e);
                }finally {
                    if(s!=null){
                        s.close();
                    }
                }

                return getAdapterPosition();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> {
                        InfoTaulaAdapter.this.notifyItemChanged(result);

                    });
        }
    }
}
