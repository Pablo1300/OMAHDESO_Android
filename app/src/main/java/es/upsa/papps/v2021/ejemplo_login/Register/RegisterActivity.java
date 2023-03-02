package es.upsa.papps.v2021.ejemplo_login.Register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.MainCasaRural.MainActivityCasaRural;
import es.upsa.papps.v2021.ejemplo_login.R;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding viewBinding;
    RegisterViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityRegisterBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        //Obtenemos el intent de la actividad que la invoca
        Intent intent = getIntent();

        //Asiganmos la funcion de cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener( v -> finish());

        //Agregamos la función correspondiente al botón de register
        viewBinding.btRegistrarse.setOnClickListener(v -> {
                                                            //Obtenemos los valores de los TextEdit
                                                            String email = viewBinding.etEmail.getText().toString();
                                                            String contrasenna = viewBinding.etContrasenna.getText().toString();
                                                            String repContrasenna = viewBinding.etRepContrasenna.getText().toString();

                                                            //Validamos con las funciones del ViewModel
                                                            int errorEmail = viewModel.isEmailValid(email);
                                                            int errorContrasenna = viewModel.isContrasennaValid(contrasenna);
                                                            int errorRepContrasenna = viewModel.isRepContrasennaValid(contrasenna, repContrasenna);

                                                            //Comprobamos errores y los incluimos
                                                            if (errorEmail != Constants.NO_ERROR)
                                                                viewBinding.etEmail.setError(getString(errorEmail));
                                                            else viewBinding.etEmail.setError(null);
                                                            if (errorContrasenna != Constants.NO_ERROR)
                                                                viewBinding.etContrasenna.setError(getString(errorContrasenna));
                                                            else viewBinding.etContrasenna.setError(null);
                                                            if (errorRepContrasenna != Constants.NO_ERROR)
                                                                viewBinding.etRepContrasenna.setError(getString(errorRepContrasenna));
                                                            else viewBinding.etRepContrasenna.setError(null);

                                                            //Si no tiene errores comprobamos si el usuario no está registrado, lo registramos y abrimos el siguiente activity
                                                            if ((errorEmail == Constants.NO_ERROR) && (errorContrasenna == Constants.NO_ERROR) && (errorRepContrasenna == Constants.NO_ERROR)) {
                                                                if (!viewModel.comprobarUsuarioRegistrado(email, contrasenna)) {
                                                                    viewModel.registrarUsuario(email, contrasenna);

                                                                    Intent intentMainActivityCasaRural = new Intent(Intent.ACTION_USER_UNLOCKED,
                                                                            Uri.parse(String.valueOf(v.getId())),
                                                                            this, MainActivityCasaRural.class);
                                                                    intentMainActivityCasaRural.putExtra("EMAIL_USUARIO_LOGEADO", email);

                                                                    startActivity(intentMainActivityCasaRural);
                                                                //Si tiene errores mostramos mensaje de error y eliminamos lo que esta escrito en los TextEdit
                                                                } else {
                                                                    viewBinding.tvErrorRegistrarse.setText(R.string.string_errorRegistrarse);
                                                                    viewBinding.etEmail.setText("");
                                                                    viewBinding.etContrasenna.setText("");
                                                                    viewBinding.etRepContrasenna.setText("");
                                                                }
                                                            }
                                                        });
    }
}
