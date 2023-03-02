package es.upsa.papps.v2021.ejemplo_login.Reserve;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.UUID;

import es.upsa.papps.v2021.ejemplo_login.Constants;
import es.upsa.papps.v2021.ejemplo_login.R;
import es.upsa.papps.v2021.ejemplo_login.clases.Reserva;
import es.upsa.papps.v2021.ejemplo_login.databinding.ActivityReserveBinding;

public class ReserveActivity extends AppCompatActivity {
    ActivityReserveBinding viewBinding;
    ReserveViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Otorgamos al viewBinding creado las variables del layout
        this.viewBinding = ActivityReserveBinding.inflate(getLayoutInflater());

        //Le asignamos una vista
        setContentView(this.viewBinding.getRoot());

        //Relacionamos con el viewModel para poder mostrar los datos generados en este
        viewModel = new ViewModelProvider(this).get(ReserveViewModel.class);

        //Obtenemos el intent de la actividad que la invoca
        Intent intentReserveActivity = getIntent();

        //Asiganmos la funcion de cerrar el activity al boton de ir atras
        viewBinding.btAtras.setOnClickListener( v -> finish());

        //Agregamos la función de desplegar el calendario para seleccionar una fecha al editText de fecha de entrada
        viewBinding.etfechEntrada.setOnClickListener( v ->  showDatePickerDialog(v.getId()));

        //Agregamos la función de desplegar el calendario para seleccionar una fecha al editText de fecha de salida
        viewBinding.etfechSalida.setOnClickListener( v ->  showDatePickerDialog(v.getId()));


        //Agregamos la función correspondiente al botón de reservar
        viewBinding.btReservar.setOnClickListener( v -> {
            //Obtenemos los valores de los TextEdit
            String nombre = viewBinding.etNombre.getText().toString();
            String apellidos = viewBinding.etApellidos.getText().toString();
            String dni = viewBinding.etDNI.getText().toString();
            String telefono = viewBinding.etTelefono.getText().toString();
            String fechEntrada = viewBinding.etfechEntrada.getText().toString();
            String fechSalida = viewBinding.etfechSalida.getText().toString();
            boolean isMayor18 = viewBinding.swMayor18.isChecked();
            boolean isCondiciones = viewBinding.swCondiciones.isChecked();

            //Validamos con las funciones del ViewModel
            int errorNombre = viewModel.isNombreValid(nombre);
            int errorApellidos = viewModel.isApellidosValid(apellidos);
            int errorDNI = viewModel.isDNIValid(dni);
            int errorTelefono = viewModel.isTelefonoValid(telefono);
            int errorFechEntrada = viewModel.isFechEntradaValid(fechEntrada);
            int errorFechSalida = viewModel.isFechSalidaValid(fechSalida, fechEntrada);

            //Comprobamos errores y los incluimos
            if (errorNombre != Constants.NO_ERROR) viewBinding.etNombre.setError(getString(errorNombre));
            else viewBinding.etNombre.setError(null);
            if (errorApellidos != Constants.NO_ERROR) viewBinding.etApellidos.setError(getString(errorApellidos));
            else viewBinding.etApellidos.setError(null);
            if (errorDNI != Constants.NO_ERROR) viewBinding.etDNI.setError(getString(errorDNI));
            else viewBinding.etDNI.setError(null);
            if (errorTelefono != Constants.NO_ERROR) viewBinding.etTelefono.setError(getString(errorTelefono));
            else viewBinding.etTelefono.setError(null);
            if (errorFechEntrada != Constants.NO_ERROR) viewBinding.etfechEntrada.setError(getString(errorFechEntrada));
            else viewBinding.etfechEntrada.setError(null);
            if (errorFechSalida != Constants.NO_ERROR) viewBinding.etfechSalida.setError(getString(errorFechSalida));
            else viewBinding.etfechSalida.setError(null);
            if (isMayor18) viewBinding.swMayor18.setError(null);
            else viewBinding.swMayor18.setError(getString(Constants.ERROR_ISMAYOR18));
            if (isCondiciones) viewBinding.swCondiciones.setError(null);
            else viewBinding.swCondiciones.setError(getString(Constants.ERROR_ISCONDICIONES));


            //Si no tiene errores añadimos la casa a la lista de reservas del usuario
            if ((errorNombre == Constants.NO_ERROR) && (errorApellidos == Constants.NO_ERROR) &&
                (errorDNI == Constants.NO_ERROR) && (errorTelefono == Constants.NO_ERROR) &&
                (errorFechEntrada == Constants.NO_ERROR) && (errorFechSalida == Constants.NO_ERROR) &&
                isMayor18 && isCondiciones) {

                    String emailUsuarioLogeado = intentReserveActivity.getExtras().getString("EMAIL_USUARIO_LOGEADO");
                    String idCasaRuralReservas = intentReserveActivity.getExtras().getString("ID_CASA_RURAL");

                    viewModel.setReserva(new Reserva(UUID.randomUUID().toString(),
                    viewModel.getCasaRuralById(idCasaRuralReservas), nombre, apellidos, dni,
                    telefono, viewModel.convertirDate(fechEntrada),
                    viewModel.convertirDate(fechSalida), emailUsuarioLogeado));

                Toast.makeText(viewBinding.getRoot().getContext(),"Reserva completada", Toast.LENGTH_LONG).show();

                finish();
            }

        });
    }

    //FUncion que muestra el calendario y recoge la fecha seleccionada para mostrarla en el editText correspondiente
    private void showDatePickerDialog(int id) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anno, int mes, int dia) {

                final String selectedDate = dia + "/" + (mes+1) + "/" + anno;

                switch (id){
                    case (R.id.etfechEntrada):
                        viewBinding.etfechEntrada.setText(selectedDate);
                        break;
                    case (R.id.etfechSalida):
                        viewBinding.etfechSalida.setText(selectedDate);
                        break;
                }
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    //Creamos una clase que sirva para recoger los valores de la fecha y agregar las funciones para que aparezca el calendario
    public static class DatePickerFragment extends DialogFragment{
        private DatePickerDialog.OnDateSetListener listener;

        //Creamos el fragment del calendario y le agregamos el listener
        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Obtenemos la fecha actual para que se desplegue con esta seleccionada
            final Calendar c = Calendar.getInstance();
            int anno = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            // Creamos un nuevo DatePickerDialog que muestra el calendario con la fecha actual
            return new DatePickerDialog(getActivity(), listener, anno, mes, dia);
        }
    }
}
