package es.upsa.papps.v2021.ejemplo_login.clases;

import java.util.Objects;

public class CasaRural {
    private String id;
    private String nombre;
    private int numMaxPersonas;
    private int numMinPersonas;
    private int numCamas;
    private int numBannos;
    private double precioNoche;
    private boolean piscina;
    private final String[] nombreFotos;
    private String provincia;
    private float valoracion;

    public CasaRural(String id, String nombre, int numMaxPersonas, int numMinPersonas, int numCamas,
                     int numBannos, double precioNoche, boolean piscina, String[] nombreFotos,
                     String provincia, float valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.numMaxPersonas = numMaxPersonas;
        this.numMinPersonas = numMinPersonas;
        this.numCamas = numCamas;
        this.numBannos = numBannos;
        this.precioNoche = precioNoche;
        this.piscina = piscina;
        this.nombreFotos = nombreFotos;
        this.provincia = provincia;
        this.valoracion = valoracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumMaxPersonas() {
        return numMaxPersonas;
    }

    public void setNumMaxPersonas(int numMaxPersonas) {
        this.numMaxPersonas = numMaxPersonas;
    }

    public int getNumMinPersonas() {
        return numMinPersonas;
    }

    public void setNumMinPersonas(int numMinPersonas) {
        this.numMinPersonas = numMinPersonas;
    }

    public int getNumCamas() {
        return numCamas;
    }

    public void setNumCamas(int numCamas) {
        this.numCamas = numCamas;
    }

    public int getNumBannos() {
        return numBannos;
    }

    public void setNumBannos(int numBannos) {
        this.numBannos = numBannos;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }

    public String getNombreFoto1() {
        return nombreFotos[0];
    }

    public String[] getNombreFotos() {
        return nombreFotos;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public String isPiscinaText(){
        if (piscina) return "SI";
        else return "NO";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaRural casaRural = (CasaRural) o;
        return Objects.equals(id, casaRural.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static enum Provincia
    {
        A_CORUNNA("A Coruña"), ALAVA("Álava"), ALBACETE("Albacete"), ALICANTE("Alicante"),
        ALMERIA("Almería"), ASTURIAS("Asturias"), AVILA("Ávila"), BADAJOZ("Badajoz"),
        BALEARES("Baleares"), BARCELONA("Barcelona"), BURGOS("Burgos"), CACERES("Cáceres"),
        CANTABRIA("Cantabria"), CASTELLON("Castellón"), CIUDAD_REAL("Ciudad Real"),
        CORDOBA("Córdoba"), CUENCA("Cuenca"), GIRONA("Girona"), GRANADA("Granada"),
        GUADALAJARA("Guadalajara"), GIPUZKOA("Gipuzkoa"), HUELVA("Huelva"), HUESCA("Huesca"),
        JAEN("Jaén"), LA_RIOJA("La Rioja"), LAS_PALMAS("Las Palmas"), LEON("León"),
        LERIDA("Lérida"), LUGO("Lugo"), MADRID("Madrid"), MALAGA("Málaga"), MURCIA("Murcia"),
        NAVARRA("Navarra"), OURENSE("Ourense"), PALENCIA("Palencia"), PONTEVEDRA("Pontevedra"),
        SALAMANCA("Salamanca"), SEGOVIA("Segovia"), SEVILLA("Sevilla"), SORIA("Soria"),
        TARRAGONA("Tarragona"), SANTA_CRUZ("Santa Cruz de Tenerife"), TERUEL("Teruel"),
        TOLEDO("Toledo"), VALENCIA("Valencia"), VALLADOLID("Valladolid"), VIZCAYA("Vizcaya"),
        ZAMORA("Zamora"), ZARAGOZA("Zaragoza");

        private final String provincia;

        Provincia(String provincia) {
            this.provincia = provincia;
        }

        public String getProvincia() {
            return this.provincia;
        }
    }
}
