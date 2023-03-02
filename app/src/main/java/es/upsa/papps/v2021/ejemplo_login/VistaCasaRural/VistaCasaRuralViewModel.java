package es.upsa.papps.v2021.ejemplo_login.VistaCasaRural;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;

public class VistaCasaRuralViewModel extends AndroidViewModel {
    Repository repository;
    int posicionCarrusel = 0;

    public VistaCasaRuralViewModel(@NonNull Application application) {
        super(application);
        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        repository = omahdesoApplication.getRepository();
    }

    public CasaRural findCasaRuralById(String id){
        return repository.getCasaRuralById(id);
    }

    public int getPosicion(){
        return posicionCarrusel;
    }

    public void setPosicion(int num){
        this.posicionCarrusel = num;
    }

    public void sumarPosicion(){
        posicionCarrusel++;
    }

    public void restarPosicion(){
        posicionCarrusel--;
    }
}
