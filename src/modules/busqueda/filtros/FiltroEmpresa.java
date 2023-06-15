package modules.busqueda.filtros;

import data.empresas.estructura.omnibus.Omnibus;

public class FiltroEmpresa implements Filtros<Omnibus> {
    
    private String nom;

    public FiltroEmpresa(String nom){
        this.nom = nom;
    }

    @Override
    public boolean filtrar(Omnibus element) {
        String s1 = element.getEmpresa().getNombre();

        return s1.equalsIgnoreCase(this.nom);
    }
}
