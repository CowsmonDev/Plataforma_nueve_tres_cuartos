import java.util.List;

import data.ListaDeViajes;
import data.busqueda.filtros.FiltroCapacidadMenor;
import data.empresas.Empresa;
import data.empresas.Omnibus;

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

        //Falta arreglar filtro por string
        List<Empresa> empresas = s.posiblesOmn(e, new FiltroCapacidadMenor(100));
        /*
        for (Empresa emp : empresas) {
            for (Omnibus o : emp.getOmnibus()) {
                System.out.println(o.toString());
            }
        }*/

        Omnibus omnAux = empresas.get(0).getOmnibus().get(0);
        System.out.println("Asientos Ocupados: " + omnAux.getOcupados());
        System.out.println("Capacidad Maxima: " + omnAux.getCapacidad());
        for(int i=0; i<omnAux.getCapacidad(); i++){
            omnAux.ocuparAsiento(i);
        }
        System.out.println("Asientos Ocupados: " + omnAux.getOcupados());
        System.out.println("Capacidad Maxima: " + omnAux.getCapacidad());


        /*Pair<List<Empresa>, List<Empresa>> empresasFechas = s.elegirFechas(e);
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
        }*/

    }
}