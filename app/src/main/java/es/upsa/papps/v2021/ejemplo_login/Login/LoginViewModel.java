package es.upsa.papps.v2021.ejemplo_login.Login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.clases.Usuario;

public class LoginViewModel extends AndroidViewModel {
    Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) application;
        this.repository =  omahdesoApplication.getRepository();
    }

    public Usuario findUsuarioByEmail(String email){
        return repository.getUsuarioByEmail(email);
    }

    public boolean comprobarUsuarioRegistrado(String email, String contrasenna) {
        Usuario usuario = findUsuarioByEmail(email);
        if ( usuario != null) return usuario.login(contrasenna);
        else return false;
    }

    public int isEmailValid(String email){
         if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return Constants.NO_ERROR;
         }
         return Constants.ERROR_EMAIL;
    }

    public int isContrasennaValid(String contrasenna){
        if(contrasenna.length() >= 8){
            return Constants.NO_ERROR;
        }
        return Constants.ERROR_CONTRASENNA;
    }

    public int posicionCaracter(String stringContar, Character caracterBuscar) {
        int i;

        for (i = 0; i <= stringContar.length(); i++) {
            if (Character.compare(stringContar.charAt(i), caracterBuscar) == 0) {
                break;
            }
        }
        return i;
    }
}


