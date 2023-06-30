import java.util.List;

import data.empresas.ListaDeViajes;
import data.empresas.estructura.empresa.Empresa;

public class Main {

    public static void main(String[] args) {
        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> empresas_totales = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e

        Sistemas s = new Sistemas();
        s.startMenu();

        // Ejemplo realizarCompra.
        // Se ejemplifica con un Usuario estatico el uso del metodo realizarCompra.
        // Se ejemplifica de esta manera debido a la falta de metodos de futuros sprints.

        //Tarjeta t = new Tarjeta("43568131","1234123412341234","Galicia","Visa");
        //Usuario cl = new Usuario("Conrado","pepito" , "Pino", "43568131", t,"12345","@");
        //s.AgregarCliente(cl);
        //cl.cargarSaldoTarjeta(1000000);

        //System.out.println(s.Login());

    }
}