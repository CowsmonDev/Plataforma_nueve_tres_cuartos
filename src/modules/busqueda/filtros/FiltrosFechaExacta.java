package modules.busqueda.filtros;

import data.empresas.Viaje;

import java.util.Date;

public class FiltrosFechaExacta implements Filtros<Viaje> {
    private Date fecha;

    public FiltrosFechaExacta(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean filtrar(Viaje viaje) {
        return viaje.getFecha().equals(fecha);
    }
}
