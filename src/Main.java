import data.busqueda.Pair;
import data.busqueda.filtros.Filtros;
import data.busqueda.filtros.FiltrosFechaEnAdelante;
import data.busqueda.Busqueda;
import data.busqueda.filtros.FiltrosFechaExacta;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        Busqueda b = new Busqueda();
        b.buscar(new ArrayList<Empresa>());

    }

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

        elegirCiudades();
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
    public ArrayList<Omnibus> posiblesOmn(List<Empresa> empresas_totales, Filtros<Omnibus> f){
        ArrayList<Omnibus> o1 = new ArrayList<>();


        Busqueda b = new Busqueda();
        b.setFiltroOmnibus(f);

        List<Empresa> empresas = b.buscar(empresas_totales);
        for(Empresa e : empresas){
            for(Omnibus o : e.getOmnibus()){
                o1.add(o);
            }
        }

        return o1;
    }

    public void elegirFechas(List<Empresa> empresas_totales) {
        Scanner scanner = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Elegir fecha ida (dd/MM/yyyy): ");
        String ida = scanner.nextLine();

        System.out.println("Elegir fecha vuelta (dd/MM/yyyy): ");
        String vuelta = scanner.nextLine();

        Date fechaIda = dateFormat.parse(ida);
        Date fechaVuelta = dateFormat.parse(vuelta);

        // Forma correcta de convertir String a Date?

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