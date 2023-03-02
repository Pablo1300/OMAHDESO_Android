package es.upsa.papps.v2021.ejemplo_login.MainCasaRural;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.databinding.ItemCasaruralBinding;

public class CasasRuralesAdapter extends RecyclerView.Adapter<CasasRuralesAdapter.CasaRuralViewHolder>{


    //Creo la interfaz para la funcion OnItemClick
    public static interface OnItemClick{
        public void onItemClick(CasaRural casaRural);
    }

    private List<CasaRural> casasRurales;
    private OnItemClick onItemClick;

    public void setCasasRurales(List<CasaRural> casasRurales){
        this.casasRurales = casasRurales;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    //Implementamos los metodos de la clase de la que extiende
    //Crea el viewHolder
    @NonNull
    @Override
    //Crea el viewHolder a partir de la clase que hemos creado de CasaRuralViewHolder
    public CasaRuralViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creamos un LayoutInflater a partir del context al que pertenece el parent
        ItemCasaruralBinding itemViewBindind = ItemCasaruralBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CasaRuralViewHolder(itemViewBindind);
    }

    //Proporciona la vista al ViewHolder
    @Override
    public void onBindViewHolder(@NonNull CasaRuralViewHolder holder, int position) {
        CasaRural casaRural = casasRurales.get(position);
        holder.bind(casaRural);
    }

    //Proporciona el número de items que tendrá que tener el ReciclerView
    @Override
    public int getItemCount() {
        return (casasRurales == null)?0 : casasRurales.size();
    }



    //Creo una clase para el ViewHolder
    public class CasaRuralViewHolder extends RecyclerView.ViewHolder{
        private ItemCasaruralBinding itemViewBinding;
        private CasaRural casaRural;

        public CasaRuralViewHolder(ItemCasaruralBinding itemViewBindind) {
            //Pasandole el itemViewBinding con su root, conseguimos que al constructor del
            //ViewHolder le pase la vista que se va a utilizar para mostrar cada item
            super(itemViewBindind.getRoot());
            this.itemViewBinding = itemViewBindind;

            //Accedo al root del ViewBinding y le proporciono el método setOnClickListener con lo que
            // tiene que obtener
            this.itemViewBinding.getRoot().setOnClickListener( v -> {
                                                                        if (onItemClick != null) {
                                                                            //Obtenemos la vista sobre la que se está pulsando
                                                                            onItemClick.onItemClick(casaRural);
                                                                        }
                                                                    }

                                                             );
        }

        //Mostrar en el ViewHolder
        public void bind(CasaRural casaRural){
            itemViewBinding.tvNombreCasaRural.setText(casaRural.getNombre());
            itemViewBinding.tvCapacidadNumero.setText(casaRural.getNumMinPersonas() + "-" + casaRural.getNumMaxPersonas());
            itemViewBinding.tvNumeroCamas.setText(String.valueOf(casaRural.getNumCamas()));
            itemViewBinding.tvBaOsNumero.setText(String.valueOf(casaRural.getNumBannos()));
            itemViewBinding.tvPrecio.setText(String.valueOf(casaRural.getPrecioNoche()));
            itemViewBinding.tvUbicacion.setText(casaRural.getProvincia());
            itemViewBinding.tvCalificacion.setText(String.valueOf(casaRural.getValoracion()));
            Glide.with(this.itemView.getContext()).load(casaRural.getNombreFoto1()).into(itemViewBinding.ivImageCasa);
            this.casaRural = casaRural;
        }
    }
}
