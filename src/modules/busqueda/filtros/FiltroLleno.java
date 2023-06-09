package modules.busqueda.filtros;

import data.empresas.estructura.omnibus.Omnibus;

public class FiltroLleno implements Filtros<Omnibus>{

    @Override
    public boolean filtrar(Omnibus element) {
        return element.getCapacidad() == element.getOcupados();
    }
}
