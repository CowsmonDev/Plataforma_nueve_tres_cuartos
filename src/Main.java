import java.util.ArrayList;
import java.util.List;
import java.util.*;

import data.ListaDeViajes;
import data.Tarjeta;
import data.Usuario;
import data.busqueda.filtros.FiltroCapacidadMenor;
import data.empresas.Asiento;

import java.util.List;

import data.ListaDeViajes;
import data.busqueda.filtros.FiltroCapacidadMenor;

import data.busqueda.Busqueda;
import data.busqueda.Pair;
import data.busqueda.filtros.FiltroCiudadDestino;
import data.busqueda.filtros.FiltroCiudadOrigen;
import data.busqueda.filtros.FiltrosAND;
import data.busqueda.filtros.FiltrosFechaEnAdelante;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

public class Main {
    public static void main(String[] args) {

        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> empresas_totales = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e
        Busqueda b = new Busqueda(); //Crea una nueva busqueda;
        b.setFiltroViajes(new FiltrosFechaEnAdelante(new Date()));
        empresas_totales = b.buscar(empresas_totales);

        Sistemas s = new Sistemas();

        System.out.println("Bienvenido al sistema de compra de pasajes");
        System.out.println("Por favor, elija una ciudad de origen y una de destino");
        Set<Pair<String, String>> ciudades = s.listarCiudades(empresas_totales);
        //Pair<String, String> ciudad = s.elegirCiudades(ciudades);
        Pair<String, String> ciudad = new Pair<>("Tandil", "Necochea");

        b.setFiltroEmpresa(null).setFiltroOmnibus(null).setFiltroViajes(null);
        b.setFiltroViajes(new FiltrosAND<Viaje>(new FiltroCiudadOrigen(ciudad.getFirst()), new FiltroCiudadDestino(ciudad.getSecond())));
        empresas_totales = b.buscar(empresas_totales);
        System.out.println(empresas_totales.size());

        Scanner sc = new Scanner(System.in);
        // Seleccio fecha de viaje
        Pair<List<Empresa>, List<Empresa>> viajes = s.elegirFechas(empresas_totales, sc);
        System.out.println(viajes.getFirst().size());
        List<Empresa> viajeIda = viajes.getFirst();
        List<Empresa> viajeVuelta = viajes.getSecond();

        //Seleccion de Omnibus
        viajeIda = s.posiblesOmn(viajeIda, null);
        System.out.println("Selecciona el Viaje de Ida");
        Viaje viajeFinal = s.elegirViaje(viajeIda, sc);

        Omnibus omnibusIda = viajeFinal.getOmnibus();
        ArrayList<Asiento> listaAsientos = s.seleccionarAsientos(omnibusIda, sc);



        // Ejemplo realizarCompra.
        // Se ejemplifica con un Usuario estatico el uso del metodo realizarCompra.
        // Se ejemplifica de esta manera debido a la falta de metodos de futuros sprints.

        Tarjeta t = new Tarjeta(122,"Galicia","Visa");
        Usuario yo = new Usuario("Conrado", "Pino", 43568131, t);
        s.AgregarCliente(yo);
        yo.cargarSaldoTarjeta(1000000);

        boolean seConfirmaCompra = s.realizarCompra(yo, omnibusIda.getViajes().get(0), listaAsientos);
        if (seConfirmaCompra)
        {
            for (Asiento a: listaAsientos)
            {
                omnibusIda.ocuparAsiento(a.getNroAsiento());
            }
        }
        System.out.println("Asientos Ocupados: " + omnibusIda.getOcupados());
        System.out.println("Capacidad Maxima: " + omnibusIda.getCapacidad());
        omnibusIda.esquemaAsiento();
    }
}