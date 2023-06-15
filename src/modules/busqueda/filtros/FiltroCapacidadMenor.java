package modules.busqueda.filtros;

import data.empresas.estructura.omnibus.Omnibus;

public class FiltroCapacidadMenor implements Filtros<Omnibus> {

    private int cap;

    public FiltroCapacidadMenor(int cap){
        this.cap = cap;
    }

    @Override
    public boolean filtrar(Omnibus element) {

        return element.getCapacidad() < this.cap;
    }
}