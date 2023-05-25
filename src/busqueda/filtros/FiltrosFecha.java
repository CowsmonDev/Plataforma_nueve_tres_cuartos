package busqueda.filtros;

import empresas.Viaje;

import java.util.Date;

public class FiltrosFecha implements Filtros<Viaje>{

    private Date fecha;

    public FiltrosFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean filtrar(Viaje element) {
        return element.getFecha().before(fecha);
    }
}
