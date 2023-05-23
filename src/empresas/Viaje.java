package empresas;

import java.time.LocalDate;
import java.util.Date;

public class Viaje {

    private String origen;
    private String destino;
    private Date fecha = new Date();

    private float precio;

    public Viaje(String origen, String destino, float precio) {
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public float getPrecio() { return precio; }

    public Date getFecha() {
        return fecha;
    }

    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setPrecio(float precio) { this.precio = precio; }

}
