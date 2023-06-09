package modules.busqueda.filtros;


import data.empresas.estructura.viaje.Viaje;

import java.util.Date;

public class FiltrosFechaEnAdelante implements Filtros<Viaje>{

    private Date fecha;

    public FiltrosFechaEnAdelante(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean filtrar(Viaje element) {
        return element.getFecha().after(fecha);
    }
}
