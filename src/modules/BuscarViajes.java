package modules;


import data.empresas.ListaDeViajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Asiento;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import modules.busqueda.BusquedaConEmpresa;
import modules.busqueda.Pair;
import modules.busqueda.filtros.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BuscarViajes {

    //Metodos de Seleccion de origen y destino
    private final ListaDeViajes listaDeViajes = ListaDeViajes.getInstance();

    public Viaje run(){

        System.out.println("Por favor, ingrese la ciudad de origen y una ciudad de destino");
        Set<Pair<String, String>> ciudades = listarCiudades(this.listaDeViajes.getEmpresas());
        Pair<String, String> ciudad = elegirCiudades(ciudades);

        // Crea una busqueda y busca las empresas que ofrecen viajes en ese origen a ese destino
        BusquedaConEmpresa b = new BusquedaConEmpresa();
        b.setFiltroEmpresa(null).setFiltroOmnibus(null).setFiltroViajes(null);
        b.setFiltroViajes(new FiltrosAND<Viaje>(new FiltroCiudadOrigen(ciudad.getFirst()), new FiltroCiudadDestino(ciudad.getSecond())));

        List<Empresa> empresas_totales = this.listaDeViajes.getEmpresas();

        // Obtengo las empresas que ofrecen viajes en ese origen a ese destino en una determinada fecha
        Pair<List<Empresa>, List<Empresa>> viajes = this.elegirFechas(empresas_totales);

        List<Empresa> viajeIda = viajes.getFirst();
        List<Empresa> viajeVuelta = viajes.getSecond();

        // Seleccionar Omnibus y Viaje
        viajeIda = this.posiblesOmn(viajeIda, null);
        System.out.println("Selecciona el Viaje de Ida");

        return this.elegirViaje(viajeIda);
    }

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

    public Pair<List<Empresa>, List<Empresa>> elegirFechas(List<Empresa> empresas_totales) {

        Scanner scanner = new Scanner(System.in);

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
        scanner.close();
        return new Pair<>(empresasIda, empresasVuelta);
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

    public Viaje elegirViaje(List<Empresa> empresas_totales){
        Scanner sc = new Scanner(System.in);
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
        sc.close();
        return viaje.get(viajeElegido);
    }
    public ArrayList<Asiento> seleccionarAsientos(Omnibus o) {
        Scanner scanner = new Scanner(System.in);
        o.esquemaAsiento();
        ArrayList<Asiento> asientosSeleccionados = new ArrayList<>();
        int i = 1;
        do {
            System.out.println(System.lineSeparator() + "Ingrese el asiento que quiera seleccionar.");
            i = scanner.nextInt();
            asientosSeleccionados.add(new Asiento(i));
            System.out.println("Desea seleccionar otro asiento?" + System.lineSeparator() + "1 = SI" + System.lineSeparator() + "0 = NO" );
            i = scanner.nextInt();
        }while (i == 1);

        scanner.close();
        return asientosSeleccionados;
    }




}
