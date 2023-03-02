package es.upsa.papps.v2021.ejemplo_login.MainCasaRural;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.R;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;

public class MainViewModelCasaRural extends AndroidViewModel {
    Repository repository;
    List<CasaRural> casasRuralesFiltradas = new ArrayList<>();
    int numPersonas = 0, numCamas = 0;

    public MainViewModelCasaRural(@NonNull Application application) {
        super(application);

        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        this.repository =  omahdesoApplication.getRepository();
    }

    public List<CasaRural> getCasasRurales()
    {
        return repository.getCasasRurales();
    }

    public List<CasaRural> filtrarCasasRurales(List<CasaRural> casasRurales, String ciudad,
                                               int numPersonas, int numCamas, boolean isPiscina){
        for (CasaRural cr : casasRurales) {
            if ((cr.getProvincia().equals(ciudad)) && (cr.getNumMaxPersonas() >= numPersonas) &&
                    (cr.getNumMinPersonas() <= numPersonas) && (cr.getNumCamas() == numCamas) &&
                    (cr.isPiscina() == isPiscina)) {
                casasRuralesFiltradas.add(cr);
            }
        }
        return casasRuralesFiltradas;
    }

    public int getNumPersonas(){
        return numPersonas;
    }

    public int getNumCamas(){
        return numCamas;
    }

    public void setNumeroPersonas(int numero){
        numPersonas = numero;
    }

    public void setNumeroCamas(int numero){
        numCamas = numero;
    }


    public int incrementarNumeroPersonas(int numero){
        return numPersonas += numero;
    }

    public int incrementarNumeroCamas(int numero){
        return numCamas += numero;
    }

    public int cambiarNumeroPersonasSiEsValido(String numeroAsString){
        int id = validarNumeroPersonas(numeroAsString);
        if (id == Constants.NO_ERROR){
            setNumeroPersonas(Integer.parseInt(numeroAsString));
        }
        return id;
    }

    public int validarNumeroPersonas(String numeroAsString){
        if (numeroAsString.equals("")) return Constants.ERROR_NONUM;
        int numero = Integer.parseInt(numeroAsString) ;
        if (numero > 70) return Constants.ERROR_MAYOR70;
        if (numero < 1) return Constants.ERROR_MENOR1;
        return Constants.NO_ERROR;
    }

    public int cambiarNumeroCamasSiEsValido(String numeroAsString){
            int id = validarNumeroCamas(numeroAsString);
            if (id == Constants.NO_ERROR){
                setNumeroCamas(Integer.parseInt(numeroAsString));
            }
            return id;
        }

    public int validarNumeroCamas(String numeroAsString){
        if (numeroAsString.equals("")) return Constants.ERROR_NONUM;
        int numero = Integer.parseInt(numeroAsString) ;
        if (numero > 30) return Constants.ERROR_MAYOR30;
        if (numero < 1) return Constants.ERROR_MENOR1;
        return Constants.NO_ERROR;
    }
}
