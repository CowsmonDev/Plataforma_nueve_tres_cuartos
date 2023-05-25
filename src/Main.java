import data.busqueda.Busqueda;
import data.empresas.Empresa;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Busqueda b = new Busqueda();
        b.buscar(new ArrayList<Empresa>());
    }
}