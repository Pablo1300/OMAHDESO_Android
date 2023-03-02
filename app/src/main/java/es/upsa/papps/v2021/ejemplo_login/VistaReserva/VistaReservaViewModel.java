package es.upsa.papps.v2021.ejemplo_login.VistaReserva;

import android.app.Application;
import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Date;

import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;

public class VistaReservaViewModel extends AndroidViewModel {
    Repository repository;

    public VistaReservaViewModel(@NonNull Application application) {
        super(application);

        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        this.repository =  omahdesoApplication.getRepository();
    }

    public SimpleDateFormat getFormat (){
        return repository.getFormatoFecha();
    }

    public Reserva findReservalByCod(String cod){
        return repository.getReservaByCod(cod);
    }


}
