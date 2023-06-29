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

        // Ejemplo realizarCompra.
        // Se ejemplifica con un Usuario estatico el uso del metodo realizarCompra.
        // Se ejemplifica de esta manera debido a la falta de metodos de futuros sprints.

        Tarjeta t = new Tarjeta("43568131","1234123412341234","Galicia","Visa");
        Usuario cl = new Usuario("Conrado","pepito" , "Pino", "43568131", t,"12345","@");
        //s.AgregarCliente(cl);
        cl.cargarSaldoTarjeta(1000000);

        //System.out.println(s.Login());

    }
}