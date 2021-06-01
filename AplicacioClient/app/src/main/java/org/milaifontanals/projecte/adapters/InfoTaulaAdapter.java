package org.milaifontanals.projecte.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacioclient.Comandes;
import com.example.aplicacioclient.MainActivity;
import com.example.aplicacioclient.R;
import com.example.aplicacioclient.Taules;

import org.milaifontanals.projecte.InfoTaula;

import java.util.List;

public class InfoTaulaAdapter extends RecyclerView.Adapter<InfoTaulaAdapter.ViewHolder> {

    private List<InfoTaula> listInfoTaules;
    public InfoTaulaAdapter(List<InfoTaula> llistaInfoTaules){
        listInfoTaules=llistaInfoTaules;
    }

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
                    taulaSelec= Taules.taules.get(getAdapterPosition());
                    Intent intent =  new Intent(v.getContext(), Comandes.class);
                    intent.putExtra("comanda",taulaSelec.getCodi_comanda());
                    v.getContext().startActivity(intent);
                }
            });

            fila.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    InfoTaula taulaSelec;
                    taulaSelec= Taules.taules.get(getAdapterPosition());
                    if(taulaSelec.getCodi_comanda()!=-1){
                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Buidar taula");
                        builder.setMessage("Estas segur que vols buidar la taula?");
                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Taules.taules.remove(taulaSelec);
                                InfoTaulaAdapter adapter=new InfoTaulaAdapter(Taules.taules);
                                Taules.recycledTaules.setAdapter(adapter);
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
    }
}
