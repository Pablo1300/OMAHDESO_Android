package es.upsa.papps.v2021.ejemplo_login.MainCasaRural;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.MisReservas.MisReservasActivity;
import es.upsa.papps.v2021.ejemplo_login.R;
import es.upsa.papps.v2021.ejemplo_login.VistaCasaRural.VistaCasaRuralActivity;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityMainBinding;

public class MainActivityCasaRural extends AppCompatActivity {

    //Generamos el binding, viewModel y adapter del activity_main
    ActivityMainBinding viewBinding;
    MainViewModelCasaRural viewModel;
    CasasRuralesAdapter adapter;

    //Generamos el constructor
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityMainBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(MainViewModelCasaRural.class);

        //Obtenemos el intent de la actividad que la invoca
        Intent intentLogin = getIntent();

        //Asiganmos la funcion de cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener( v -> finish());

        this.adapter = new CasasRuralesAdapter();
        this.viewBinding.rvCasasRurales.setAdapter(this.adapter);

        //Proporcionamos la lista de casas rurales al adaptador para mostrarla
        this.adapter.setCasasRurales(viewModel.getCasasRurales());


        //Invocamos el método del adaptador para saber que hacer cuando pulsan en un elemento
        this.adapter.setOnItemClick( casaRural -> {
                                                    Intent intent = new Intent(Intent.ACTION_SHOW_APP_INFO,
                                                                    Uri.parse(casaRural.getId()),
                                                            this, VistaCasaRuralActivity.class);
                                                    intent.putExtra("EMAIL_USUARIO_LOGEADO", intentLogin.getExtras().getString("EMAIL_USUARIO_LOGEADO"));
                                                    intent.putExtra("ID_CASA_RURAL", casaRural.getId());


                                                    startActivity(intent);
                                                  });

        //Asiganmos la funcion de buscar casas al boton de la lupa:
        viewBinding.ivLupa.setOnClickListener( v -> {
                                                     int errorEtNumPersonas = viewModel.validarNumeroPersonas(viewBinding.etNumPersonas.getText().toString());
                                                     int errorEtNumCamas = viewModel.validarNumeroCamas(viewBinding.etNumCamas.getText().toString());
                                                     if ((errorEtNumPersonas == Constants.NO_ERROR) && (errorEtNumCamas == Constants.NO_ERROR)
                                                             && (!viewBinding.spCiudades.getSelectedItem().toString().equals("- Selecciona una ciudad -"))) {

                                                         viewModel.casasRuralesFiltradas.clear();
                                                         //Calculamos la lista de casas rurales filtradas
                                                         viewModel.casasRuralesFiltradas = viewModel.filtrarCasasRurales(viewModel.getCasasRurales(), viewBinding.spCiudades.getSelectedItem().toString(),
                                                                 viewModel.getNumPersonas(), viewModel.getNumCamas(),
                                                                 viewBinding.swPiscina.isChecked());
                                                         //Proporcionamos la lista de casas rurales filtradas al adaptador para mostrarla
                                                         this.adapter.setCasasRurales(viewModel.casasRuralesFiltradas);
                                                     } else {
                                                         if (errorEtNumPersonas != Constants.NO_ERROR) viewBinding.etNumPersonas.setError(getString(errorEtNumPersonas));
                                                         if (errorEtNumCamas != Constants.NO_ERROR) viewBinding.etNumCamas.setError(getString(errorEtNumCamas));
                                                     }

        });

        //Asignamos la funcion de incrementar y decrementar a los botones de numPersonas
        viewBinding.ivPlusPersonas.setOnClickListener( v -> incrementarNumPersonas(1));
        viewBinding.ivMinusPersonas.setOnClickListener( v -> incrementarNumPersonas(-1));

        //Asignamos la funcion de incrementar y decrementar a los botones de numCamas
        viewBinding.ivPlusCamas.setOnClickListener( v -> incrementarNumCamas(1));
        viewBinding.ivMinusCamas.setOnClickListener( v -> incrementarNumCamas(-1));

        //Asignamos una función a los editText cuando estos son editados
        viewBinding.etNumPersonas.setOnEditorActionListener((textView, actionId, keyEvent) -> onEditorAction(textView, actionId));
        viewBinding.etNumCamas.setOnEditorActionListener((textView, actionId, keyEvent) -> onEditorAction(textView, actionId));

        //Asignamos la función de mostrar la lista de casas rurales reservadas por el usuario que tiene abierta sesion
        viewBinding.btMisReservas.setOnClickListener( v -> {
                                                                Intent intentMisReservasActivity = new Intent(Intent.ACTION_ANSWER,
                                                                                                              Uri.parse(String.valueOf(v.getId())),
                                                                                               this, MisReservasActivity.class);

                                                                intentMisReservasActivity.putExtra("EMAIL_USUARIO_LOGEADO", intentLogin.getExtras().getString("EMAIL_USUARIO_LOGEADO"));

                                                                startActivity(intentMisReservasActivity);
                                                             });
        }

        public boolean onEditorAction(TextView textView, int actionId){
            if (actionId == EditorInfo.IME_ACTION_DONE){
                switch (textView.getId()){
                    case R.id.et_numPersonas:
                        intentarCambiarNumeroPersonas(textView.getText().toString());
                        break;
                    case R.id.et_numCamas:
                        intentarCambiarNumeroCamas(textView.getText().toString());
                        break;
                }
                return false;
            } else return true;
        }

        private void intentarCambiarNumeroPersonas(String numeroAsString){
            int errorId = viewModel.cambiarNumeroPersonasSiEsValido(numeroAsString);
            if (errorId == Constants.NO_ERROR){
                viewBinding.etNumPersonas.setError(null);
                shownumero(viewBinding.etNumPersonas, viewModel.getNumPersonas());
            } else {
                viewBinding.etNumPersonas.setError(getString(errorId));
            }
        }
        private void intentarCambiarNumeroCamas(String numeroAsString){
            int errorId = viewModel.cambiarNumeroCamasSiEsValido(numeroAsString);
            if (errorId == Constants.NO_ERROR){
                viewBinding.etNumCamas.setError(null);
                shownumero(viewBinding.etNumCamas, viewModel.getNumCamas());
            } else {
                viewBinding.etNumCamas.setError(getString(errorId));
            }
        }

        private void incrementarNumPersonas(int numero) {
            shownumero(viewBinding.etNumPersonas ,viewModel.incrementarNumeroPersonas(numero));
        }

        private void incrementarNumCamas(int numero) {
            shownumero(viewBinding.etNumCamas,viewModel.incrementarNumeroCamas(numero));
        }

        public void shownumero(EditText editText, int numero){
            editText.setText(String.valueOf(numero));
        }
    }

