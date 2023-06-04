import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.Tarjeta;
import data.Usuario;
import data.busqueda.Busqueda;
import data.busqueda.Pair;
import data.busqueda.filtros.*;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;
import data.empresas.Asiento;
public class Sistemas {

    private ArrayList<Usuario> clientes;

    //Metodos de Seleccion de origen y destino
    public void listarCiudades(List<Empresa> empresas_totales) {
        Set<Pair<String, String>> pares = new HashSet<>();

        Busqueda b = new Busqueda();
        b.setFiltroViajes(new FiltrosFechaEnAdelante(new Date()));
        List<Empresa> empresas = b.buscar(empresas_totales);

        System.out.println("Ciudades posibles:");
        for (Empresa e : empresas) {
            for (Omnibus o : e.getOmnibus()) {
                for (Viaje v : o.getViajes()) {
                    Pair<String, String> par = new Pair<>(v.getOrigen(), v.getDestino());
                    pares.add(par);
                }
            }
        }

        // Imprimir la lista de pares
        for (Pair<String, String> par : pares) {
            System.out.println("Origen: " + par.getFirst() + ", Destino: " + par.getSecond());
        }

    }

    public void elegirCiudades(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elegir ciudad origen: ");
        String origen = scanner.nextLine();
        System.out.println("Elegir ciudad destino");
        String destino = scanner.nextLine();

        //Alguna forma de almancenar origen y destino tiene que haber
        //this.origen = origen;
        //this.destino = destino;

        scanner.close();
    }

    //funcion para filtrado de omnibuses
    public List<Empresa> posiblesOmn(List<Empresa> empresas_totales, Filtros<Omnibus> f1){

        //creo una busqueda y la filtro con el parametro f1
        Busqueda b = new Busqueda();
        b.setFiltroOmnibus(new FiltrosAND<>(f1, new FiltroNot<>(new FiltroLleno())));
        List<Empresa> empresas = b.buscar(empresas_totales);

        return empresas;
    }

    public ArrayList<Integer> seleccionarAsientos(Omnibus o)
    {
        Scanner scanner = new Scanner(System.in);
        o.esquemaAsiento();
        ArrayList<Integer> asientosSeleccionados = null;
        System.out.println("Ingrese los asientos que quiera seleccionar. Cuando quiera terminar con la seleccion precione X");
        while (!scanner.nextLine().equals("X"))
        {
            int i = Integer.parseInt(scanner.nextLine());
            o.ocuparAsiento(i);
            asientosSeleccionados.add(i);
        }
        return asientosSeleccionados;
    }

    private boolean estaTarjetaRegistrada(Tarjeta t)
    {
        for (Usuario cliente: clientes) {
            if (cliente.getTarjeta().equals(t))
                return true;
        }
        return false;
    }

    private Usuario clienteCoincidiente(int DNI)
    {
        for (Usuario cliente: clientes) {
            if (cliente.getDNI() == DNI)
                return cliente;
        }
        return null;
    }

    public void realizarCompra(Usuario pasajero,Viaje v, ArrayList<Asiento> asientosSeleccionados)
    {
        Scanner scanner = new Scanner(System.in);
        Tarjeta t = null;
        if (pasajero.getTarjeta() != null)
        {
            t = pasajero.getTarjeta();
        }
        else
        {
            System.out.println("Ingrese los siguientes campos de la tarjeta: numero de tarjeta, banco emisor, marca de tarjeta de credito");
            int nroTarjeta = Integer.parseInt(scanner.nextLine());
            String bncoEmisor = scanner.nextLine();
            String marcaTarjeta = scanner.nextLine();
            t = new Tarjeta(nroTarjeta,bncoEmisor,marcaTarjeta);
        }
        if (asientosSeleccionados.size() == 1)
            asientosSeleccionados.get(0).setPasajero(pasajero);
        else{
            for (int i = 0; i < asientosSeleccionados.size(); i++) {
                System.out.println("Ingrese el DNI del pasajero que ocupara el asiento");
                int DNI = Integer.parseInt(scanner.nextLine());
                if (clienteCoincidiente(DNI) != null)
                {
                    asientosSeleccionados.get(i).setPasajero(clienteCoincidiente(DNI));
                }
                else
                {
                    System.out.println("Ingrese nombre y apellido para el pasajero del asiento" + i);
                    System.out.println("Nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Apellido:");
                    String apellido = scanner.nextLine();
                    Usuario u = new Usuario(nombre,apellido,DNI,null);
                }
            }
        }
        //System.out.println("El nuevo saldo de la tarjeta es" +
        // El metodo calcularNuevoSaldo se implementara en los proximos sprints.
        // /*pasajero.getTarjeta().calcularNuevoSaldo(Viaje v,ArrayList<Asiento> asientosSeleccionados)*/);
        System.out.println("Desea confirmar la compra?");
        System.out.println("S= Sí; N=No");
        int i = Integer.parseInt(scanner.nextLine());
    }


    public void elegirFechas(List<Empresa> empresas_totales) {
        Scanner scanner = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Elegir fecha ida (dd/MM/yyyy): ");
        String ida = scanner.nextLine();

        System.out.println("Elegir fecha vuelta (dd/MM/yyyy): ");
        String vuelta = scanner.nextLine();

        Date fechaIda = new Date();
        Date fechaVuelta = new Date();
        try {
            fechaIda = dateFormat.parse(ida);
            fechaVuelta = dateFormat.parse(ida);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Busqueda b = new Busqueda();

        Filtros<Viaje> f1 = new FiltrosFechaExacta(fechaIda);
        b.setFiltroViajes(f1);
        List<Empresa> empresasIda = b.buscar(empresas_totales);

        if (!vuelta.equals("")) {
            Filtros<Viaje> f2 = new FiltrosFechaExacta(fechaVuelta);
            b.setFiltroViajes(f2);
            List<Empresa> empresasVuelta = b.buscar(empresas_totales);
        }
    }
}
