import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import data.Tarjeta;
import data.Usuario;
import data.busqueda.Busqueda;
import data.busqueda.Pair;
import data.busqueda.filtros.FiltroLleno;
import data.busqueda.filtros.FiltroNot;
import data.busqueda.filtros.Filtros;
import data.busqueda.filtros.FiltrosAND;
import data.busqueda.filtros.FiltrosFechaEnAdelante;
import data.busqueda.filtros.FiltrosFechaExacta;
import data.empresas.Asiento;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;
public class Sistemas {


    private ArrayList<Usuario> clientes = new ArrayList<>();

    public void AgregarCliente(Usuario cliente)
    {
        this.clientes.add(cliente);
    }

    public String Login(){
        String nombre;
        String password;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nick: ");
        nombre = scanner.nextLine();

        System.out.println("Ingrese su contraseña: ");
        password = scanner.nextLine();

        Usuario us = new Usuario("", nombre, password,0, null,"" ,"" );
        Usuario aux = us.validarLogueo(clientes);
        if(aux != null){  //valido que exista ese usuario 
            if(aux.getClaveAcceso() == password){  //valido que se haya ingresado la contraseña correcta
                return "Logueado";
            }
        }
        return "Algo salio mal, logueese nuevamente";
    }


    //Metodos de Seleccion de origen y destino
    public Set<Pair<String, String>> listarCiudades(List<Empresa> empresas_totales) {
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
        return pares;
    }

    public Pair<String, String> elegirCiudades(Set<Pair<String, String>> ciudades){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elegir ciudad origen: ");
        String origen = scanner.nextLine();
        System.out.println("Elegir ciudad destino");
        String destino = scanner.nextLine();

        Pair<String, String> ciudad = new Pair<>(origen, destino);
        scanner.close();
        return ciudad;
    }
    
    //funcion para filtrado de omnibuses
    public List<Empresa> posiblesOmn(List<Empresa> empresas_totales, Filtros<Omnibus> f1){

        //creo una busqueda y la filtro con el parametro f1
        Busqueda b = new Busqueda();
        f1 = (f1 != null)? new FiltrosAND<>(f1, new FiltroNot<>(new FiltroLleno()))
                : new FiltroNot<>(new FiltroLleno());

        b.setFiltroOmnibus(f1);
        List<Empresa> empresas = b.buscar(empresas_totales);

        return empresas;
    }

    public Viaje elegirViaje(List<Empresa> empresas_totales, Scanner sc){
        Map<Integer, Viaje> viaje = new HashMap<>();
        Integer i = 0;
        for (Empresa e : empresas_totales){
            for (Omnibus o : e.getOmnibus()){
                for (Viaje v : o.getViajes()){
                    System.out.println(i + " - " + v.toString());
                    viaje.put(i, v);
                    i++;
                }
            }
        }
        //int viajeElegido = sc.nextInt();
        Integer viajeElegido = 0;
        while (!viaje.containsKey(viajeElegido)){
            System.out.println("Viaje no valido, ingrese otro");
            viajeElegido = sc.nextInt();
        }
        return viaje.get(viajeElegido);
    }
    public ArrayList<Asiento> seleccionarAsientos(Omnibus o, Scanner scanner) {
        o.esquemaAsiento();
        ArrayList<Asiento> asientosSeleccionados = new ArrayList<>();
        while (true){
            System.out.println(System.lineSeparator() + "Ingrese el asiento que quiera seleccionar.");
            int i = Integer.parseInt(scanner.nextLine());
            asientosSeleccionados.add(new Asiento(i));
            System.out.println("Desea seleccionar otro asiento?" + System.lineSeparator() + "1 = SI" + System.lineSeparator() + "0 = NO" );
            i = Integer.parseInt(scanner.nextLine());
            if(i != 1){
                return asientosSeleccionados;
            }
        }
    }

    private Usuario clienteCoincidiente(int DNI)
    {
        for (Usuario cliente: clientes) {
            if (cliente.getDNI() == DNI)
                return cliente;
        }
        return null;
    }

    private void listarAsientos(ArrayList<Asiento> asientosSeleccionados)
    {
        for (Asiento a: asientosSeleccionados)
        {
            System.out.println(a.getNroAsiento());
        }
    }

    private double getMontoAPagar(Viaje v, int i)
    {
        return v.getPrecio() * i;
    }

    public boolean realizarCompra(Usuario pasajero,Viaje v, ArrayList<Asiento> asientosSeleccionados)
    {
        Scanner scanner = new Scanner(System.in);
        Tarjeta t;
        if (pasajero.getTarjeta() != null)
            t = pasajero.getTarjeta();

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

                    System.out.println("Ingrese nombre y apellido para el pasajero del asiento: " + asientosSeleccionados.get(i).getNroAsiento() + System.lineSeparator());

                    System.out.println("Nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Apellido:");
                    String apellido = scanner.nextLine();
                    Usuario u = new Usuario(nombre,"",apellido,DNI,null,"","");
                    asientosSeleccionados.get(i).setPasajero(u);
                }
            }
        }
        System.out.println("Resumen de compra: " + System.lineSeparator());
        System.out.println("Asientos seleccionados :");
        listarAsientos(asientosSeleccionados);
        System.out.println("Nuevo saldo de la tarjeta" + (t.getSaldo() - getMontoAPagar(v,asientosSeleccionados.size())));
        System.out.println("Desea confirmar la compra?");
        System.out.println("1= Sí; 0=No");
        int i = Integer.parseInt(scanner.nextLine());
        if (i == 1) {
            pasajero.pagar(v, asientosSeleccionados.size());
            // Metodo a implementar en siguientes sprints.
            return true;
        }
        else return false;

    }

    public Pair<List<Empresa>, List<Empresa>> elegirFechas(List<Empresa> empresas_totales, Scanner scanner) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Elegir fecha ida (dd/MM/yyyy): ");
        //String ida = scanner.nextLine();
        String ida = "26/07/2023";

        Date fechaIda = new Date();
        try {
            fechaIda = dateFormat.parse(ida);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Busqueda b = new Busqueda();

        Filtros<Viaje> f1 = new FiltrosFechaExacta(fechaIda);
        b.setFiltroViajes(f1);
        List<Empresa> empresasIda = b.buscar(empresas_totales);

        System.out.println("Desea elegir fecha de vuelta? (y/n): ");
        //String resp = scanner.nextLine();
        String resp = "n";

        List<Empresa> empresasVuelta = null;
        if (resp.equals("y")) {
            System.out.println("Elegir fecha vuelta (dd/MM/yyyy): ");
            String vuelta = scanner.nextLine();

            Date fechaVuelta = new Date();
            try {
                fechaVuelta = dateFormat.parse(vuelta);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Filtros<Viaje> f2 = new FiltrosFechaExacta(fechaVuelta);
            b.setFiltroViajes(f2);
            empresasVuelta = b.buscar(empresas_totales);
        }
        Pair<List<Empresa>, List<Empresa>> result = new Pair<>(empresasIda, empresasVuelta);
        return result;
    }
}
