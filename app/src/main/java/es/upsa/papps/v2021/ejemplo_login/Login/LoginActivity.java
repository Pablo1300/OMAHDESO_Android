package es.upsa.papps.v2021.ejemplo_login.Login;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.MainCasaRural.MainActivityCasaRural;
import es.upsa.papps.v2021.ejemplo_login.MainCasaRural.MainViewModelCasaRural;
import es.upsa.papps.v2021.ejemplo_login.R;
import es.upsa.papps.v2021.ejemplo_login.Register.RegisterActivity;
import es.upsa.papps.v2021.ejemplo_login.VistaCasaRural.VistaCasaRuralActivity;
import es.upsa.papps.v2021.ejemplo_login.clases.Usuario;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityLoginBinding;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    //Generamos el binding y viewModel del activity_main
    ActivityLoginBinding viewBinding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //Agregamos la función correspondiente al botón de login
        viewBinding.btLogin.setOnClickListener(v -> {
                                                        //Obtenemos los valores de los Textedit
                                                        String email = viewBinding.username.getText().toString();
                                                        String contrasenna = viewBinding.password.getText().toString();

                                                        //Validamos con las funciones del ViewModel
                                                        int errorEmail = viewModel.isEmailValid(email);
                                                        int errorContrasenna = viewModel.isContrasennaValid(contrasenna);

                                                        //Comprobamos errores y los incluimos
                                                        if (errorEmail != Constants.NO_ERROR)
                                                            viewBinding.username.setError(getString(errorEmail));
                                                        else viewBinding.username.setError(null);
                                                        if (errorContrasenna != Constants.NO_ERROR)
                                                            viewBinding.password.setError(getString(errorContrasenna));
                                                        else viewBinding.password.setError(null);

                                                        //Si no tiene errores comprobamos si el usuario está registrado y abrimos el siguiente activity
                                                        if ((errorEmail == Constants.NO_ERROR) && (errorContrasenna == Constants.NO_ERROR)) {
                                                            if (viewModel.comprobarUsuarioRegistrado(email, contrasenna)) {
                                                                Intent intentMainActivityCasaRural = new Intent(Intent.ACTION_USER_UNLOCKED,
                                                                        Uri.parse(String.valueOf(v.getId())),
                                                                        this, MainActivityCasaRural.class);
                                                                intentMainActivityCasaRural.putExtra("EMAIL_USUARIO_LOGEADO", email);

                                                                startActivity(intentMainActivityCasaRural);
                                                                //Si tiene errors mostramos mensaje de error y eliminamos lo que esta escrito en los TextEdit
                                                            } else {
                                                                viewBinding.tvErrorIniciarSesion.setText(R.string.string_errorIniciarSesion);
                                                                viewBinding.username.setText("");
                                                                viewBinding.password.setText("");
                                                            }
                                                        }
                                                    });

        //Obtenemos la string que contiene la parte que queremos clickar
        String stringNoRegistrado = (String) viewBinding.tvNoRegistrado.getText();
        //Creamos una variable string que pueda tener span
        SpannableString ss = new SpannableString(stringNoRegistrado);

        //Creamos una variable ClickableSpan con las funciones que necesita
        ClickableSpan clickableSpan = new ClickableSpan() {
                                                            //Programamos la función onClick para que muestre el activity cuando se pulse en ese span
                                                            @Override
                                                            public void onClick(View textView) {
                                                                Intent intentMainActivityCasaRural = new Intent(Intent.ACTION_USER_UNLOCKED,
                                                                        Uri.parse(String.valueOf(textView.getId())),
                                                                        LoginActivity.this, RegisterActivity.class);

                                                                startActivity(intentMainActivityCasaRural);
                                                            }
                                                            //Función para dar formato a la parte clickable
                                                            @Override
                                                            public void updateDrawState(TextPaint ds) {
                                                                super.updateDrawState(ds);
                                                                ds.setUnderlineText(false);
                                                            }
                                                           };

        //Agregamos las coordenadas de la parte clickable y obtenemos la posición del caracter '?'
        // para saber donde tiene que empezar la parte clickable dependiendo del idioma
            {
                ss.setSpan(clickableSpan, (viewModel.posicionCaracter(stringNoRegistrado, '?') + 1), stringNoRegistrado.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                //Le agregamos al TextView el contenido, el span, que pueda se clickable y el color
                TextView textView = (TextView) findViewById(R.id.tv_noRegistrado);
                textView.setText(ss);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setHighlightColor(Color.TRANSPARENT);
            }
        }
}

