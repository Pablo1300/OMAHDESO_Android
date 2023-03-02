package es.upsa.papps.v2021.ejemplo_login.MisReservas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import es.upsa.papps.v2021.ejemplo_login.VistaReserva.VistaReservaActivity;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityMisreservasBinding;

public class MisReservasActivity extends AppCompatActivity {
    ActivityMisreservasBinding viewBinding;
    MisReservasViewModel viewModel;
    MisReservasAdapter adapter;


    ActivityResultLauncher<Reserva> deleteAsignaturaActivityLauncher = registerForActivityResult(new ActivityResultContract<Reserva, Reserva>() {
                                                                                                @NonNull
                                                                                                @Override
                                                                                                public Intent createIntent(@NonNull Context context, Reserva reserva) {
                                                                                                    return new Intent(Intent.ACTION_DELETE,
                                                                                                                        Uri.parse(reserva.getCodReserva()),
                                                                                                                        context, VistaReservaActivity.class);
                                                                                                }

                                                                                                @Override
                                                                                                public Reserva parseResult(int resultCode, @Nullable Intent intent) {
                                                                                                    if (resultCode == Activity.RESULT_OK)
                                                                                                    {
                                                                                                        return viewModel.findReservalByCod(intent.getDataString());
                                                                                                    }
                                                                                                    return null;
                                                                                                }
                                                                                            },
                                                                                            reserva -> {
                                                                                                if (reserva != null) {
                                                                                                    //Obtenemos el intent de la actividad que la invoca
                                                                                                    Intent intentMisReservas = getIntent();
                                                                                                    String emailUsuarioLogeado = intentMisReservas.getExtras().getString("EMAIL_USUARIO_LOGEADO");

                                                                                                    List<Reserva> reservasActualizada = viewModel.filtrarReservasUsuario(viewModel.cancelarReserva(reserva.getCodReserva()), emailUsuarioLogeado);
                                                                                                    adapter.setReservas(reservasActualizada);
                                                                                                }
                                                                                            });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityMisreservasBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(MisReservasViewModel.class);

        //Obtenemos el intent de la actividad que la invoca
        Intent intentMisReservas = getIntent();
        String emailUsuarioLogeado = intentMisReservas.getExtras().getString("EMAIL_USUARIO_LOGEADO");

        //Asiganmos la funcion de cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener( v -> finish());

        this.adapter = new MisReservasAdapter();
        this.viewBinding.recyclerViewMisReservas.setAdapter(this.adapter);

        //Proporcionamos la lista de reservas al adaptador para mostrarla
        this.adapter.setReservas(viewModel.filtrarReservasUsuario(viewModel.getReservas(), emailUsuarioLogeado));

        //Invocamos el método del adaptador para saber que hacer cuando pulsan en un elemento
        this.adapter.setOnItemClick( reserva -> {
                                                    Intent intent = new Intent(Intent.ACTION_SHOW_APP_INFO,
                                                    Uri.parse(reserva.getId()),
                                                    this, VistaReservaActivity.class);

                                                    deleteAsignaturaActivityLauncher.launch(reserva);
                                                });

    }
}
