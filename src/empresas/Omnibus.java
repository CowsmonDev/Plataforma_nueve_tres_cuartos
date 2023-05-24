package empresas;

import java.util.ArrayList;
import java.util.List;

public class Omnibus {

    private String id_omnibus;
    private Empresa empresa;
    private List<Viaje> viajes = new ArrayList<>();

    public Omnibus(Empresa empresa, String id_omnibus) {
        this.empresa = empresa;
        this.id_omnibus = id_omnibus;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getId_omnibus() {
        return id_omnibus;
    }
}
