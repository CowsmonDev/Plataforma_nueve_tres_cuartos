package data.busqueda.filtros;

import data.empresas.Omnibus;

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