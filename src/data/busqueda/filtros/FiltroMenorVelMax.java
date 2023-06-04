package data.busqueda.filtros;

import data.empresas.Omnibus;

public class FiltroMenorVelMax implements Filtros<Omnibus> {

    private int vel;

    public FiltroMenorVelMax(int vel){
        this.vel = vel;
    }

    @Override
    public boolean filtrar(Omnibus element) {
        return element.getVelMax() < this.vel;
    }
}