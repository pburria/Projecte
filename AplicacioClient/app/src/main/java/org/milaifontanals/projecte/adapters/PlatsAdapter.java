package org.milaifontanals.projecte.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private List<LiniaComanda> listLineaComanda;
    private Comandes activityComanda;

    public PlatsAdapter(List<Plat> llistaPlat,List<LiniaComanda> llistaLineaComanda,Comandes c){
        listPlat=llistaPlat;
        listLineaComanda=llistaLineaComanda;
        this.activityComanda=c;
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

        Bitmap img=BitmapFactory.decodeByteArray(plat.getFoto(), 0, plat.getMidaFoto());

        holder.imgPlat.setImageBitmap(img);
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
        public ImageView imgPlat;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txtNomPlat2=fila.findViewById(R.id.txtNomPlat2);
            txtPreuPlat=fila.findViewById(R.id.txtPreuPlat);
            btnAfegir=fila.findViewById(R.id.btnAfegir);
            btnCancelar=fila.findViewById(R.id.btnCancelar);
            imgPlat=fila.findViewById(R.id.imgPlat);

            btnAfegir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Comandes.editable==true){
                        Plat plat;
                        plat= listPlat.get(getAdapterPosition());
                        boolean trobat=false;
                        int i;

                        for(i = 0; i<listLineaComanda.size(); i++){
                            if(plat.getCodi()==listLineaComanda.get(i).getPlat()){
                                listLineaComanda.get(i).setQtat(listLineaComanda.get(i).getQtat()+1);
                                LiniaComanda linia=listLineaComanda.get(i);
                                String nom=plat.getNom();
                                int codi =plat.getCodi();
                                int codiPlat=linia.getPlat();
                                int qtat=linia.getQtat();
                                trobat=true;
                                break;
                            }
                        }
                        if(trobat==false){
                            LiniaComanda c=new LiniaComanda(0,plat.getCodi(),listLineaComanda.size()+1,1,false);
                            listLineaComanda.add(c);
                            activityComanda.afegirPlat(listLineaComanda.size()-1);
                        }else{
                            activityComanda.actualitzarPlat(i);
                        }
                    }
                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Comandes.editable==true){
                        Plat plat;
                        plat= listPlat.get(getAdapterPosition());

                        for(int i = 0; i<listLineaComanda.size(); i++){
                            if(plat.getCodi()==listLineaComanda.get(i).getPlat()){
                                listLineaComanda.remove(listLineaComanda.get(i));
                                activityComanda.eliminarPlat(i);
                                break;
                            }

                        }


                    }
                }
            });
        }
    }
}
