package modules.busqueda.filtros;

public class FiltroNot<T> implements Filtros<T> {
    private final Filtros<T> filtros;

    public FiltroNot(Filtros<T> filtros){
        this.filtros = filtros;
    }

    @Override
    public boolean filtrar(T element) {
        return !filtros.filtrar(element);
    }
}