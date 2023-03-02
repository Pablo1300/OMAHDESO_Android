package es.upsa.papps.v2021.ejemplo_login.clases;

import java.util.Objects;

public class Usuario {
    private String email;
    private String contrasenna;

    public Usuario(String email, String contrasenna) {
        this.email = email;
        this.contrasenna = contrasenna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public boolean login(String contrasenna){
        return this.contrasenna.equals(contrasenna);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
