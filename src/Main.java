import data.ListaDeViajes;
import data.busqueda.Pair;
import data.busqueda.filtros.FiltroMenorVelMax;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> e = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e

        Sistemas s = new Sistemas();
        //s.listarCiudades(e);
        //s.elegirCiudades();
        for (Empresa emp : e) {
            for (Omnibus o : emp.getOmnibus()) {
                //System.out.println(o.toString());
            }

        }
        List<Empresa> empresas = s.posiblesOmn(e, new FiltroMenorVelMax(30));
        //System.out.println(empresas.size());
        for (Empresa emp : empresas) {
            for (Omnibus o : emp.getOmnibus()) {
                System.out.println(o.toString());
            }
        }

        Pair<List<Empresa>, List<Empresa>> empresasFechas = s.elegirFechas(e);
        List<Empresa> empresasIda = empresasFechas.getFirst();
        List<Empresa> empresasVuelta = empresasFechas.getSecond();
        System.out.println("Viajes ida: ");

        for (Empresa emp : empresasIda) {
            System.out.println("1");
            for (Omnibus o : emp.getOmnibus()) {
                System.out.println("2");
                for (Viaje v : o.getViajes()) {
                    System.out.println("3");
                    System.out.println(v.toString());
                }
            }
        }
        if (empresasVuelta != null) {
            System.out.println("Viajes vuelta: ");
            for (Empresa emp : empresasVuelta) {
                for (Omnibus o : emp.getOmnibus()) {
                    for (Viaje v : o.getViajes()) {
                        System.out.println(v.toString());
                    }
                }
            }
        }

    }
}