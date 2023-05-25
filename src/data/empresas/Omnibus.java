package data.empresas;

public class Omnibus {

    private String id_omnibus;
    private Empresa empresa;

    public Omnibus(Empresa empresa, String id_omnibus) {
        this.empresa = empresa;
        this.id_omnibus = id_omnibus;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    public String getIdOmnibus() {
        return id_omnibus;
    }
}
