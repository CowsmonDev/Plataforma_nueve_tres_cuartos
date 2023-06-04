package data.empresas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Omnibus {

    private String id_omnibus;
    private int velMax;
    private int ocupados;

    private ArrayList<Asiento> asientos;
    private List<Viaje> viajes;
    private Empresa empresa;

    public Omnibus(String id_omnibus, Empresa e) {
        this(id_omnibus, 0, 0, e);
    }

    public Omnibus(String id_omnibus, int velMax, int asientos, Empresa empresa) {
        this.id_omnibus = id_omnibus;
        this.velMax = velMax;
        this.ocupados = 0;
        this.asientos = new ArrayList<>(asientos);
        for (int i = 0; i < asientos; i++)
            this.asientos.add(new Asiento(i + 1));
        this.viajes = new ArrayList<>();
        this.empresa = empresa;
    }

    public int getOcupados() {
        this.ocupados = 0;
        for(Asiento a1: asientos){
            if(a1.getOcupacion()){
                this.ocupados = this.ocupados + 1;
            }
        }
        return ocupados;
    }

    public int getCapacidad() {
        return asientos.size();
    }

    public int getVelMax() {
        return velMax;
    }

    public String getIdOmnibus() {
        return id_omnibus;
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

    public List<Viaje> getViajes() { return viajes; }

    public void setViajes(List<Viaje> viajes) { this.viajes = viajes; }

    public Empresa getEmpresa() { return empresa; }

    public String ocuparAsiento(int a1){
        String s1;
        if(!this.asientos.get(a1).getOcupacion()){   // true asiento ocupado / false asiento desocupado
            this.asientos.get(a1).setOcupacion(true);
            s1 = "Fue seleccionado con exito";
            return s1;
        }else{
            s1 = "Este asiento estaba ocupado";
            return s1;
        }
    }

    public String toString(){
        String s1 = " IdOmnibus: " + this.getIdOmnibus() + 
                    " Velocidad Maxima: " + this.getVelMax() + " Capacidad: " + this.getCapacidad();
        return s1;
    }

    public void esquemaAsiento(){
        System.out.println("Esquema de asientos del ómnibus:");
        for (int i = 0; i < asientos.size(); i++) { //recorro la list de asientos
            System.out.printf("%3d", i + 1); // Número de asiento
            if (asientos.get(i).getOcupacion()) {
                System.out.print("[X] "); // Asiento ocupado
            } else {
                System.out.print("[ ] "); // Asiento desocupado
            }


            if (((i + 1) % 2 == 0)&&((i + 1) % 4 != 0)) {
                System.out.print(" | "); // Pasillo
            }


            if ((i + 1) % 4 == 0) {
                System.out.println(); // Salto de línea cada 4 asientos
            }
        }
    }
}
