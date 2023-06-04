package data.empresas;

import java.util.Date;

public class Viaje {

    private String origen;
    private String destino;
    private Date fecha = new Date();
    private Omnibus omnibus;
    private float precio;

    public Viaje(String origen, String destino, float precio, Omnibus omnibus) {
        this.omnibus = omnibus;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public float getPrecio() { return precio; }
    public Omnibus getOmnibus() { return omnibus; }

    public Date getFecha() {
        return fecha;
    }

    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setOmnibus(Omnibus omnibus) { this.omnibus = omnibus; }
    public void setPrecio(float precio) { this.precio = precio; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String toString() {
        String result = ("Origen: " + this.getOrigen() + " Destino: " + this.getDestino());
        return result;
    }

}
