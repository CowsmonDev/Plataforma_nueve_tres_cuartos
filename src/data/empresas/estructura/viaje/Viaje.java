package data.empresas.estructura.viaje;

import data.db.cvs.CSVTransfrom;
import data.empresas.estructura.omnibus.Omnibus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Viaje implements CSVTransfrom<Viaje> {

    private String origen;
    private String destino;
    private Date fecha = new Date();
    private Omnibus omnibus;
    private String idOmnibus;
    private float precio;

    public Viaje(){
        this("","",0, "");
        this.idOmnibus = "";
    }


    public Viaje(String origen, String destino, float precio, Omnibus omnibus) {
        this.omnibus = omnibus;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.idOmnibus = this.omnibus.getIdOmnibus();
    }
    public Viaje(String origen, String destino, float precio, String idOmnibus) {
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.idOmnibus = idOmnibus;
        this.omnibus = null;
    }

    public String toString() {
        return ("Origen: " + this.getOrigen() + " Destino: " + this.getDestino());
    }


    // Getters
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public float getPrecio() { return precio; }
    public Omnibus getOmnibus() { return omnibus; }
    public Date getFecha() { return fecha; }
    public String getIdOmnibus() { return idOmnibus; }

    // Setters
    public void setOmnibus(Omnibus omnibus) {
        this.omnibus = omnibus;
        this.idOmnibus = omnibus.getIdOmnibus();
    }
    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setPrecio(float precio) { this.precio = precio; }
    public void setFecha(Date fecha) { this.fecha = fecha; }


    // CSVTransfrom methods
    @Override
    public Viaje transformFromCSV(String[] line) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        try {
            data = dateFormat.parse(line[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Viaje v = new Viaje(
                line[0],
                line[1],
                Float.parseFloat(line[3]),
                line[4]
        );
        v.setFecha(data);
        return v;
    }

    @Override
    public String transformToCSV() {
        return this.getOrigen() + ";" + this.getDestino() + ";" + this.getFecha() + ";" + this.getPrecio() + ";" + this.getIdOmnibus();
    }
}
