package es.upsa.papps.v2021.ejemplo_login.VistaReserva;

import static java.time.temporal.ChronoUnit.DAYS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import es.upsa.papps.v2021.ejemplo_login.MisReservas.MisReservasViewModel;
import es.upsa.papps.v2021.ejemplo_login.VistaCasaRural.VistaCasaRuralViewModel;
import es.upsa.papps.v2021.ejemplo_login.clases.CasaRural;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityMisreservasBinding;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityVistareservaBinding;

public class VistaReservaActivity extends AppCompatActivity {
    ActivityVistareservaBinding viewBinding;
    VistaReservaViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityVistareservaBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(VistaReservaViewModel.class);

        //Obtenemos el intent de la actividad que la invoca
        Intent intentMisReservas = getIntent();

        //Asiganmos la funcion de cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener( v -> finish());

        Reserva reserva = viewModel.findReservalByCod(intentMisReservas.getDataString());
        System.out.println(reserva.getCasaRural().getNombre());
        showReserva(reserva);

        viewBinding.btEliminar.setOnClickListener( v -> {

            Intent intentCancelar = new Intent();
            intentCancelar.setAction( getIntent().getAction() );
            intentCancelar.setData(Uri.parse(reserva.getCodReserva()));

            setResult(Activity.RESULT_OK, intentCancelar);
            finish();
        });
    }

    void showReserva(Reserva reserva){
        viewBinding.tvNombreCasaRural.setText(reserva.getCasaRural().getNombre());
        viewBinding.tvNumCodReserva.setText(reserva.getCodReserva());
        viewBinding.tvNombre.setText(reserva.getNombre());
        viewBinding.tvApellidos.setText(reserva.getApellidos());
        viewBinding.tvDNI.setText(reserva.getDni());
        viewBinding.tvTelefono.setText(reserva.getTelefono());

        Date fechaEntrada = reserva.getFechaEntrada();
        Date fechaSalida = reserva.getFechaSalida();
        viewBinding.tvEstancia.setText(viewModel.getFormat().format(fechaEntrada) + " - " + viewModel.getFormat().format(fechaSalida));

        int dias = (int) ((fechaSalida.getTime()-fechaEntrada.getTime())/86400000);
        int totalPagar = (int) (dias * reserva.getCasaRural().getPrecioNoche() * reserva.getCasaRural().getNumMaxPersonas());
        viewBinding.tvTotalPagarNum.setText(String.valueOf(totalPagar));
    }
}
