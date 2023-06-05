import java.util.ArrayList;
import java.util.List;

import data.ListaDeViajes;
import data.Tarjeta;
import data.Usuario;
import data.busqueda.filtros.FiltroCapacidadMenor;
import data.empresas.Asiento;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

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
        /*for(int i=0; i<omnAux.getCapacidad(); i++){
            omnAux.ocuparAsiento(i);
        }*/

        Tarjeta t = new Tarjeta(122,"Galicia","Visa");
        Usuario yo = new Usuario("Conrado", "Pino", 43568131, t);
        s.AgregarCliente(yo);
        yo.cargarSaldoTarjeta(1000000);
        ArrayList<Asiento> listaAsientos = s.seleccionarAsientos(omnAux);
        boolean b = s.realizarCompra(yo, omnAux.getViajes().get(0), listaAsientos);
        if (b)
        {
            for (Asiento a: listaAsientos)
            {
                omnAux.ocuparAsiento(a.getNroAsiento());
            }
        }
        System.out.println("Asientos Ocupados: " + omnAux.getOcupados());
        System.out.println("Capacidad Maxima: " + omnAux.getCapacidad());
        omnAux.esquemaAsiento();

        /*Pair<List<Empresa>, List<Empresa>> empresasFechas = s.elegirFechas(e);

        List<Empresa> empresasIda = empresasFechas.getFirst();
        List<Empresa> empresasVuelta = empresasFechas.getSecond();
        System.out.println("Viajes ida: ");

        for (Empresa emp : empresasIda) {
            for (Omnibus o : emp.getOmnibus()) {
                for (Viaje v : o.getViajes()) {
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