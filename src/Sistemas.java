import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.busqueda.Busqueda;
import data.busqueda.Pair;
import data.busqueda.filtros.*;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

public class Sistemas {

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


    public Pair<List<Empresa>, List<Empresa>> elegirFechas(List<Empresa> empresas_totales) {
        Scanner scanner = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Elegir fecha ida (dd/MM/yyyy): ");
        String ida = scanner.nextLine();

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
        String resp = scanner.nextLine();

        List<Empresa> empresasVuelta = null;
        if (resp == "y") {
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
