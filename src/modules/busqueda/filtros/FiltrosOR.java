package modules.busqueda.filtros;

public class FiltrosOR<T> implements Filtros<T>{
    private final Filtros<T>[] filtros;

    @SafeVarargs
    public FiltrosOR(Filtros<T>... filtros){
        this.filtros = filtros;
    }

    @Override
    public boolean filtrar(T element) {
        for (Filtros<T> f : filtros)
            if(f.filtrar(element))
                return true;
        return true;
    }
}
