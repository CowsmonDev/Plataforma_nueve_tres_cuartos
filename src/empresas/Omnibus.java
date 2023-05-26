package empresas;

import java.util.ArrayList;
import java.util.List;

public class Omnibus {

    private List<Viaje> viajes = new ArrayList<>();
    private int capacidad;
    private int velMax;
    private String nombre;

    public Omnibus() {
        this.viajes = new ArrayList<>();
        this.capacidad = 0;
        this.velMax = 0;
        this.nombre = "";
    }

    public Omnibus(int cap, int velMax, String nom){
        this.viajes = new ArrayList<>();
        setCapacidad(cap);
        setNombre(nom);
        setVelMax(velMax);
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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

}
