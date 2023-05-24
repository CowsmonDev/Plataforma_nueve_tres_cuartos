package empresas;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String id_empresa;
    private String nombre;
    private List<Omnibus> omnibus = new ArrayList<>();

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public List<Omnibus> getOmnibus() {
        return omnibus;
    }

    public void addOmnibus(Omnibus omnibus){
        this.omnibus.add(omnibus);
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
