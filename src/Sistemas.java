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

import data.usuarios.Usuarios;
import data.usuarios.estructura.Tarjeta;
import data.usuarios.estructura.Usuario;
import modules.busqueda.BusquedaConEmpresa;
import modules.busqueda.Pair;
import modules.busqueda.filtros.FiltroLleno;
import modules.busqueda.filtros.FiltroNot;
import modules.busqueda.filtros.Filtros;
import modules.busqueda.filtros.FiltrosAND;
import modules.busqueda.filtros.FiltrosFechaEnAdelante;
import modules.busqueda.filtros.FiltrosFechaExacta;
import data.empresas.estructura.omnibus.Asiento;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;

public class Sistemas {

    private final Usuarios usuarios = Usuarios.getInstance();

    //Metodos de Seleccion de origen y destino
    public Set<Pair<String, String>> listarCiudades(List<Empresa> empresas_totales) {
        Set<Pair<String, String>> pares = new HashSet<>();

        BusquedaConEmpresa b = new BusquedaConEmpresa();
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
        BusquedaConEmpresa b = new BusquedaConEmpresa();
        f1 = (f1 != null)? new FiltrosAND<>(f1, new FiltroNot<>(new FiltroLleno()))
                : new FiltroNot<>(new FiltroLleno());

        b.setFiltroOmnibus(f1);

        return b.buscar(empresas_totales);
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
        int viajeElegido = 0;
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



    private double getMontoAPagar(Viaje v, int i)
    {
        return v.getPrecio() * i;
    }

    public boolean realizarCompra(Usuario pasajero,Viaje v, ArrayList<Asiento> asientosSeleccionados)
    {
        Scanner scanner = new Scanner(System.in);
        Tarjeta t;
        while(!pasajero.hasTarjeta()){
            pasajero.registrarTarjeta();
        }
        t = pasajero.getTarjeta();
        if (asientosSeleccionados.size() == 1)
            asientosSeleccionados.get(0).setPasajero(pasajero);
        else{
            for (int i = 0; i < asientosSeleccionados.size(); i++) {
                System.out.println("Ingrese el DNI del pasajero que ocupara el asiento");
                String DNI = scanner.nextLine();
                if(usuarios.exists(DNI)){
                    asientosSeleccionados.get(i).setPasajero(usuarios.getUser(DNI));
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
        String resumen = getResumenCompra(asientosSeleccionados, t, v); // Para que no se recalcule el saldo cuando se vuelve a usar el resumen en correo.
        System.out.println(resumen);
        System.out.println("Asientos seleccionados :");
        asientosSeleccionados.forEach(asiento -> System.out.println(asiento.getNroAsiento())); // Imprimir los asientos seleccionados
        System.out.println("Desea confirmar la compra?");
        System.out.println("1= Sí; 0=No");
        int i = Integer.parseInt(scanner.nextLine());
        if (i == 1) {
            pasajero.pagar(v, asientosSeleccionados.size());
            for (Asiento a : asientosSeleccionados) {
                v.getOmnibus().ocuparAsiento(a.getNroAsiento());
                enviarCorreo(resumen, pasajero);
            }
            return true;
        }
        else return false;

    }

    void enviarCorreo(String correo, Usuario pasajero) {
        pasajero.addCorreo(correo);
    }

    public String getResumenCompra(ArrayList<Asiento> asientosSeleccionados, Tarjeta t, Viaje v) {
        return "Resumen de compra: " + System.lineSeparator() + "\n" +
                "Precio total de los pasajes: " + getMontoAPagar(v,asientosSeleccionados.size()) + "\n" +
                "Nuevo saldo de la tarjeta" + (t.getSaldo() - getMontoAPagar(v,asientosSeleccionados.size()));
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

        BusquedaConEmpresa b = new BusquedaConEmpresa();

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
        return new Pair<>(empresasIda, empresasVuelta);
    }
}
