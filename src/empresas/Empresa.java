package empresas;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre;
    private List<Omnibus> viajes = new ArrayList<>();

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public List<Omnibus> getViajes() {
        return viajes;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
