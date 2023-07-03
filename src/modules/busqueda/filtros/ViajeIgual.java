package modules.busqueda.filtros;

import data.empresas.estructura.viaje.Viaje;


public class ViajeIgual implements Filtros<Viaje>{

    private final Viaje viaje;

    public ViajeIgual(Viaje viaje){
        this.viaje = viaje;
    }

    @Override
    public boolean filtrar(Viaje v) {
        return this.viaje.equals(v);
    }
}
