package org.milaifontanals.projecte.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacioclient.Comandes;
import com.example.aplicacioclient.R;

import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class PlatsAdapter extends RecyclerView.Adapter<PlatsAdapter.ViewHolder> {
    private List<Plat> listPlat;

    public PlatsAdapter(List<Plat> llistaPlat){
        listPlat=llistaPlat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View filaView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fila_plat,viewGroup,false);
        return new PlatsAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plat plat=listPlat.get(position);

        BigDecimal preu=plat.getPreu();
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);

        holder.LinearPlat.setBackgroundResource(R.drawable.bg);
        holder.txtPreuPlat.setText(""+format.format(preu)+"€");
        holder.txtNomPlat2.setText(""+plat.getNom());
    }

    @Override
    public int getItemCount() {
        return listPlat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomPlat2,txtPreuPlat;
        public Button btnAfegir,btnCancelar;
        public LinearLayout LinearPlat;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txtNomPlat2=fila.findViewById(R.id.txtNomPlat2);
            txtPreuPlat=fila.findViewById(R.id.txtPreuPlat);
            btnAfegir=fila.findViewById(R.id.btnAfegir);
            btnCancelar=fila.findViewById(R.id.btnCancelar);
            LinearPlat=fila.findViewById(R.id.LinearPlat);

            btnAfegir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Comandes.editable==true){
                        Plat plat;
                        plat= listPlat.get(getAdapterPosition());
                        boolean trobat=false;

                        for(int i = 0; i<Comandes.liniaComandas.size(); i++){
                            if(plat.getCodi()==Comandes.liniaComandas.get(i).getPlat()){
                                Comandes.liniaComandas.get(i).setQtat(Comandes.liniaComandas.get(i).getQtat()+1);
                                LiniaComanda linia=Comandes.liniaComandas.get(i);
                                String nom=plat.getNom();
                                int codi =plat.getCodi();
                                int codiPlat=linia.getPlat();
                                int qtat=linia.getQtat();
                                trobat=true;
                                break;
                            }
                        }
                        if(trobat==false){
                            LiniaComanda c=new LiniaComanda(0,plat.getCodi(),Comandes.liniaComandas.size()+1,1,false);
                            Comandes.liniaComandas.add(c);
                        }
                        LiniaComandaAdapter adapter=new LiniaComandaAdapter(Comandes.liniaComandas,Comandes.plats);
                        Comandes.recycledComandes.setAdapter(adapter);
                    }
                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Comandes.editable==true){
                        Plat plat;
                        plat= listPlat.get(getAdapterPosition());

                        for(int i = 0; i<Comandes.liniaComandas.size(); i++){
                            if(plat.getCodi()==Comandes.liniaComandas.get(i).getPlat()){
                                Comandes.liniaComandas.remove(Comandes.liniaComandas.get(i));
                                break;
                            }
                        }
                        LiniaComandaAdapter adapter=new LiniaComandaAdapter(Comandes.liniaComandas,Comandes.plats);
                        Comandes.recycledComandes.setAdapter(adapter);
                    }
                }
            });
        }
    }
}
