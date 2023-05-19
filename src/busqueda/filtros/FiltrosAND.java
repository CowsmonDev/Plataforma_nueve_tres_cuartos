package busqueda.filtros;

public class FiltrosAND<T> implements Filtros<T>{

    private final Filtros<T>[] filtros;

    @SafeVarargs
    public FiltrosAND(Filtros<T>... filtros){
        this.filtros = filtros;
    }

    @Override
    public boolean filtrar(T element) {
        for (Filtros<T> f : filtros)
            if(!f.filtrar(element))
                return false;
        return true;
    }
}
