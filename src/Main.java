import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.empresas.ListaDeViajes;
import data.usuarios.estructura.Tarjeta;
import data.usuarios.estructura.Usuario;
import modules.busqueda.BusquedaConEmpresa;
import modules.busqueda.Pair;
import modules.busqueda.filtros.FiltroCiudadDestino;
import modules.busqueda.filtros.FiltroCiudadOrigen;
import modules.busqueda.filtros.FiltrosAND;
import data.empresas.estructura.omnibus.Asiento;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;

public class Main {
    public static void main(String[] args) {
        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> empresas_totales = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e
        BusquedaConEmpresa b = new BusquedaConEmpresa(); //Crea una nueva busqueda;

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

        Tarjeta t = new Tarjeta("1234123412341234","Galicia","Visa");
        Usuario cl = new Usuario("Conrado","pepito" , "Pino", 43568131, t,"12345","@");
        s.AgregarCliente(cl);
        cl.cargarSaldoTarjeta(1000000);

        System.out.println(s.Login());


        boolean seConfirmaCompra = s.realizarCompra(cl, omnibusIda.getViajes().get(0), listaAsientos);
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