package es.upsa.papps.v2021.ejemplo_login.clases;

import java.util.Date;
import java.util.Objects;

public class Reserva {
    private String codReserva;
    private CasaRural casaRural;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private Date fechaEntrada;
    private Date fechaSalida;
    private String email;

    public Reserva(String codReserva, CasaRural casaRural, String nombre, String apellidos, String dni,
                   String telefono, Date fechaEntrada, Date fechaSalida, String email) {
        this.codReserva = codReserva;
        this.casaRural = casaRural;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.email = email;
    }

    public String getId() {
        return codReserva;
    }

    public void setCodReserva(String codReserva) {
        this.codReserva = codReserva;
    }

    public CasaRural getCasaRural() {
        return casaRural;
    }

    public void setCasaRural(CasaRural casaRural) {
        this.casaRural = casaRural;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String  getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodReserva() {
        return codReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(codReserva, reserva.codReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codReserva);
    }
}
