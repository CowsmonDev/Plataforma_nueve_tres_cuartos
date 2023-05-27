package data.empresas;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String id_empresa;
    private String nombre;
    private List<Omnibus> omnibus = new ArrayList<>();

    public Empresa(String id_empresa, String nombre) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public String getIdEmpresa() { return id_empresa; }
    public List<Omnibus> getOmnibus() { return omnibus; }
}
