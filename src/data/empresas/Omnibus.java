package data.empresas;

import java.util.ArrayList;
import java.util.List;

public class Omnibus {

    private String id_omnibus;
    private int velMax;
    private int ocupados;

    private Boolean[] asientos;
    private List<Viaje> viajes;
    private Empresa empresa;

    public Omnibus() {
        this.viajes = new ArrayList<>();
        this.asientos = new Boolean[30];
        this.velMax = 0;
        this.id_omnibus = "";
        this.ocupados = 0;
    }

    public Omnibus(int velMax, String nom, int cap){
        this.viajes = new ArrayList<>();
        this.asientos = new Boolean[cap];
        setNombre(nom);
        setVelMax(velMax);
        this.ocupados = 0;
    }

    public int getOcupados() {
        return ocupados;
    }

    public void setOcupados(int ocupados) {
        this.ocupados = ocupados;
    }

    public int getCapacidad() {
        return asientos.length;
    }

    public int getVelMax() {
        return velMax;
    }

    public void setVelMax(int velMax) {
        this.velMax = velMax;
    }

    public String getNombre() {
        return id_omnibus;
    }

    public void setNombre(String id_omnibus) {
        this.id_omnibus = id_omnibus;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public String ocuparAsiento(int a1){
        String s1;
        if(this.asientos[a1] == false){
            this.asientos[a1] = true;
            s1 = "Fue seleccionado con exito";
            return s1;
        }else{
            s1 = "Este asiento estaba ocupado";
            return s1;
        }
    }

}
