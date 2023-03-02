package es.upsa.papps.v2021.ejemplo_login.Register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.OmahdesoApplication;
import es.upsa.papps.v2021.ejemplo_login.Repository;
import es.upsa.papps.v2021.ejemplo_login.clases.Usuario;

public class RegisterViewModel extends AndroidViewModel {
    Repository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        OmahdesoApplication omahdesoApplication = (OmahdesoApplication) getApplication();
        repository = omahdesoApplication.getRepository();
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
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return Constants.NO_ERROR;
        return Constants.ERROR_EMAIL;
    }

    public int isContrasennaValid(String contrasenna){
        if(contrasenna.length() >= 8) return Constants.NO_ERROR;
        return Constants.ERROR_CONTRASENNA;
    }

    public int isRepContrasennaValid(String contrasenna, String repContrasenna){
        if(repContrasenna.equals(contrasenna)) return Constants.NO_ERROR;
        return Constants.ERROR_REPCONTRASENNA;
    }

    public void registrarUsuario(String email, String contrasenna){
        repository.addUsuariosRegistrados(email, contrasenna);
    }
}
