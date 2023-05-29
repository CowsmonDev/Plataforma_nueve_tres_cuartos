package data.empresas;

import java.util.ArrayList;
import java.util.List;

public class Omnibus {

    private String id_omnibus;
    private int capacidad;
    private int velMax;
    private int ocupados;

    private List<Viaje> viajes = new ArrayList<>();
    private Empresa empresa;

    public Omnibus() {
        this.viajes = new ArrayList<>();
        this.capacidad = 0;
        this.velMax = 0;
        this.id_omnibus = "";
        this.ocupados = 0;
    }

    public Omnibus(int cap, int velMax, String nom){
        this.viajes = new ArrayList<>();
        setCapacidad(cap);
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
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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
}
