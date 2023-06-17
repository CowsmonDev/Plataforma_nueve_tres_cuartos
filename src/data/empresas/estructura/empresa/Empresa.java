package data.empresas.estructura.empresa;

import data.db.cvs.CSVTransfrom;
import data.empresas.estructura.omnibus.Omnibus;

import java.util.ArrayList;
import java.util.List;

public class Empresa implements CSVTransfrom<Empresa> {

    private String id_empresa;
    private String nombre;
    private List<Omnibus> omnibus = new ArrayList<>();

    public Empresa() {
        this("", "");
    }
    public Empresa(String id_empresa, String nombre) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public String getIdEmpresa() { return id_empresa; }
    public List<Omnibus> getOmnibus() { return omnibus; }

    public void setOmnibus(List<Omnibus> omnibus) { this.omnibus = omnibus; }

    @Override
    public Empresa transformFromCSV(String[] data) {
        return new Empresa(data[0], data[1]);
    }

    @Override
    public String transformToCSV() {
        return id_empresa + ";" + nombre;
    }
}
