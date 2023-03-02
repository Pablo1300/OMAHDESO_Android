package es.upsa.papps.v2021.ejemplo_login.MisReservas;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;
import es.upsa.papps.v2021.ejemplo_login.databinding.ItemReservasBinding;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.MisReservasViewHolder> {
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    //Creo la interfaz para la funcion OnItemClick
    public static interface OnItemClick {
        public void onItemClick(Reserva reserva);
    }

    private List<Reserva> reservas;
    private MisReservasAdapter.OnItemClick onItemClick;

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
        notifyDataSetChanged();
    }

    public void setOnItemClick(MisReservasAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    //Crea el viewHolder a partir de la clase que hemos creado de MisReservasViewHolder
    public MisReservasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReservasBinding itemViewBindind = ItemReservasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MisReservasViewHolder(itemViewBindind);
    }

    //Proporciona la vista al ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MisReservasViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);
        holder.bind(reserva);
    }

    //Proporciona el número de items que tendrá que tener el ReciclerView
    @Override
    public int getItemCount() {
        return (reservas == null) ? 0 : reservas.size();
    }

    public class MisReservasViewHolder extends RecyclerView.ViewHolder {
        private ItemReservasBinding itemViewBinding;
        private Reserva reserva;

        public MisReservasViewHolder(ItemReservasBinding itemViewBindind) {
            //Pasandole el itemViewBinding con su root, conseguimos que al constructor del
            //ViewHolder le pase la vista que se va a utilizar para mostrar cada item
            super(itemViewBindind.getRoot());
            this.itemViewBinding = itemViewBindind;

            //Accedo al root del ViewBinding y le proporciono el método setOnClickListener con lo que
            // tiene que obtener
            this.itemViewBinding.getRoot().setOnClickListener(v -> {
                        if (onItemClick != null) {
                            //Obtenemos la vista sobre la que se está pulsando
                            onItemClick.onItemClick(reserva);
                        }
                    }
            );
        }

        public MisReservasViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        //Mostrar en el ViewHolder
        public void bind(Reserva reserva) {
            itemViewBinding.tvIdReserva.setText(reserva.getId());
            itemViewBinding.tvNombreCasaRural.setText(reserva.getCasaRural().getNombre());
            itemViewBinding.tvUbicacion.setText(reserva.getCasaRural().getProvincia());
            itemViewBinding.tvFechaEn.setText(formatoFecha.format(reserva.getFechaEntrada()));
            itemViewBinding.tvFechaSal.setText(formatoFecha.format(reserva.getFechaSalida()));
            Glide.with(this.itemView.getContext()).load(reserva.getCasaRural().getNombreFoto1()).into(itemViewBinding.imageCasa);
            this.reserva = reserva;
        }
    }
}
