package es.upsa.papps.v2021.ejemplo_login.Reserve;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;

public class ReserveViewModel extends AndroidViewModel {
    Repository repository;

    public ReserveViewModel(@NonNull Application application) {
        super(application);

        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        repository = omahdesoApplication.getRepository();
    }

    public int isNombreValid(String nombre){
        if(nombre.length() != 0){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_NOMBRE;
    }

    public int isApellidosValid(String apellidos){
        if(apellidos.length() != 0){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_APELLIDOS;
    }

    public int isDNIValid(String dni){
        if(dni.length() == 9 && Character.isLetter(dni.charAt(8))) {
            for (int i = 0; i < (dni.length() - 1); i++){
                if (Character.getNumericValue(dni.charAt(i)) >= 0 &&
                        Character.getNumericValue(dni.charAt(i)) <= 9){
                    continue;
                } else {
                    return Constants.ERROR_DNI;
                }
            }
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_DNI;
    }

    public int isTelefonoValid(String telefono){
        if(telefono.length() == 9 &&
                Integer.parseInt(telefono) <= 999999999 && Integer.parseInt(telefono) >= 600000000){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_TELEFONO;
    }

    public int isFechEntradaValid(String fechEntradaString){
        Date fechEntrada;
        try {
            fechEntrada = repository.getFormatoFecha().parse(fechEntradaString);
        } catch (ParseException e) {
            return Constants.ERROR_FORMATOFECH;
        }
        Calendar calendar = Calendar.getInstance();
        Date fechHoy = calendar.getTime();
        String fechHoyFormateada = repository.getFormatoFecha().format(fechHoy);
        try {
            fechHoy = repository.getFormatoFecha().parse(fechHoyFormateada);
        } catch (ParseException e) {
            return -1;
        }

        if(fechEntrada.after(fechHoy) ){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_FECHENTRADA;
    }

    public int isFechSalidaValid(String fechSalidaString, String fechEntradaString){

        Date fechEntrada, fechSalida;

        try {
            fechEntrada = repository.getFormatoFecha().parse(fechEntradaString);
        } catch (ParseException e) {
            return Constants.ERROR_FORMATOFECH;
        }

        try {
            fechSalida = repository.getFormatoFecha().parse(fechSalidaString);
        } catch (ParseException e) {
            return Constants.ERROR_FORMATOFECH;
        }

        Calendar calendar = Calendar.getInstance();
        Date fechHoy = calendar.getTime();
        String fechHoyFormateada = repository.getFormatoFecha().format(fechHoy);
        try {
            fechHoy = repository.getFormatoFecha().parse(fechHoyFormateada);
        } catch (ParseException e) {
            return -1;
        }

        if(fechSalida.after(fechEntrada) && fechSalida.after(fechHoy)){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_FECHSALIDA;
    }

    public Date convertirDate (String fechaString){
        return repository.convertirDate(fechaString);
    }

    public void setReserva(Reserva reserva){
        repository.addReservas(reserva);
    }

    public CasaRural getCasaRuralById(String id){
        return repository.getCasaRuralById(id);
    }
}
