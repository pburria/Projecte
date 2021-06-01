package org.milaifontanals.projecte.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicacioclient.R;
import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class LiniaComandaAdapter extends RecyclerView.Adapter<LiniaComandaAdapter.ViewHolder> {
    private List<LiniaComanda> listLiniaComanda;
    private List<Plat> listPlat;

    public LiniaComandaAdapter(List<LiniaComanda> llistatLiniesComanda,List<Plat> llistaPlat){
        listLiniaComanda=llistatLiniesComanda;
        listPlat=llistaPlat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View filaView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fila_linea_comanda,viewGroup,false);
        return new LiniaComandaAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiniaComanda inf=listLiniaComanda.get(position);
        Plat plat=listPlat.get(inf.getPlat());

        int qtat=inf.getQtat();
        BigDecimal preu=plat.getPreu();
        BigDecimal total=BigDecimal.valueOf(qtat).multiply(preu);
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);

        holder.txtQtat.setText(""+qtat);
        holder.txtNomPlat.setText(""+plat.getNom());
        holder.txtPreu.setText(""+format.format(preu)+"€");
        holder.txtTotalLinea.setText((""+format.format(total))+"€");
    }

    @Override
    public int getItemCount() {
        return listLiniaComanda.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtQtat,txtNomPlat,txtPreu,txtTotalLinea;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txtQtat=fila.findViewById(R.id.txtQtat);
            txtNomPlat=fila.findViewById(R.id.txtNomPlat);
            txtPreu=fila.findViewById(R.id.txtPreu);
            txtTotalLinea=fila.findViewById(R.id.txtTotalLinea);
        }
    }
}
