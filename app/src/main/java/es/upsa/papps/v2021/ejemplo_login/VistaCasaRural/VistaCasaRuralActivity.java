package es.upsa.papps.v2021.ejemplo_login.VistaCasaRural;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import es.upsa.papps.v2021.ejemplo_login.MisReservas.MisReservasActivity;
import es.upsa.papps.v2021.ejemplo_login.Reserve.ReserveActivity;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityVistacasaruralBinding;

public class VistaCasaRuralActivity extends AppCompatActivity {

    ActivityVistacasaruralBinding viewBinding;
    VistaCasaRuralViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding = ActivityVistacasaruralBinding.inflate(getLayoutInflater());
        setContentView(this.viewBinding.getRoot());

        viewModel = new ViewModelProvider(this).get(VistaCasaRuralViewModel.class);

        //Asiganmos la funcion de ir cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener(v -> finish());

        //Obtenemos el intent de la actividad que la invoca
        Intent intent = getIntent();

        //Obtenemos la casa rural sobre la que se ha hecho click
        CasaRural casaRural = viewModel.findCasaRuralById(intent.getDataString());
        showCasaRural(casaRural);

        viewBinding.btReservar.setOnClickListener( v -> {
            Intent intentMisReservasActivity = new Intent(Intent.ACTION_SHOW_APP_INFO,
                    Uri.parse(String.valueOf(v.getId())),
                    this, ReserveActivity.class);

            intentMisReservasActivity.putExtra("EMAIL_USUARIO_LOGEADO", intent.getExtras().getString("EMAIL_USUARIO_LOGEADO"));
            intentMisReservasActivity.putExtra("ID_CASA_RURAL", intent.getExtras().getString("ID_CASA_RURAL"));
            startActivity(intentMisReservasActivity);
        });

        viewBinding.btIzquierda.setOnClickListener( v -> {
                                                            if (viewModel.getPosicion() == 0){
                                                                viewModel.setPosicion(casaRural.getNombreFotos().length - 1);
                                                            } else {
                                                                viewModel.restarPosicion();
                                                            }
                                                            showFotosCasaRural(casaRural.getNombreFotos(), viewModel.getPosicion());
                                                        });

        viewBinding.btDerecha.setOnClickListener( v -> {
                                                            if (viewModel.getPosicion() == (casaRural.getNombreFotos().length - 1)){
                                                                viewModel.setPosicion(0);
                                                            } else {
                                                                viewModel.sumarPosicion();
                                                            }
                                                            showFotosCasaRural(casaRural.getNombreFotos(), viewModel.getPosicion());
        });
    }

    void showFotosCasaRural(String[] fotos, int posicion){
        Glide.with(this).load(fotos[posicion]).into(viewBinding.ivPortada);
    }

    void showCasaRural(CasaRural casaRural){
        viewBinding.tvNombreCasaRural.setText(casaRural.getNombre());
        viewBinding.tvUbicacion.setText(casaRural.getProvincia());
        viewBinding.tvDormitoriosNumero.setText(String.valueOf(casaRural.getNumCamas()));
        viewBinding.tvCapacidadNumero.setText(String.valueOf(casaRural.getNumMaxPersonas()));
        viewBinding.tvValoracionMedia.setText(String.valueOf(casaRural.getValoracion()));
        viewBinding.ratingBar.setRating(casaRural.getValoracion());
        viewBinding.tvIsPiscina.setText(casaRural.isPiscinaText());
        Glide.with(this).load(casaRural.getNombreFoto1()).into(viewBinding.ivPortada);
    }


}