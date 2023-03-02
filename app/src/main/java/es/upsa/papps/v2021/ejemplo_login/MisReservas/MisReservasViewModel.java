package es.upsa.papps.v2021.ejemplo_login.MisReservas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;

public class MisReservasViewModel extends AndroidViewModel {
    Repository repository;

    public MisReservasViewModel(@NonNull Application application) {
        super(application);

        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        this.repository =  omahdesoApplication.getRepository();
    }

    public List<Reserva> getReservas(){
        return repository.getReservas();
    }

    public List<Reserva> filtrarReservasUsuario(List<Reserva> reservas, String email){
        List<Reserva> reservasUsuario = new ArrayList<>();

        for (Reserva rs : reservas) {
            if (rs.getEmail().equals(email)) {
                reservasUsuario.add(rs);
            }
        }
        return reservasUsuario;
    }

    public Reserva findReservalByCod(String cod){
        return repository.getReservaByCod(cod);
    }

    public List<Reserva> cancelarReserva(String codReserva){
        repository.deleteReserva(repository.getReservaByCod(codReserva));
        return repository.getReservas();
    }
}
