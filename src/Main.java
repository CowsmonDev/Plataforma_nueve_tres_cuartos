import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.ListaDeViajes;
import data.busqueda.Busqueda;
import data.busqueda.Pair;
import data.busqueda.filtros.FiltroMenorVelMax;
import data.busqueda.filtros.Filtros;
import data.busqueda.filtros.FiltrosFechaEnAdelante;
import data.busqueda.filtros.FiltrosFechaExacta;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

public class Main {
    public static void main(String[] args) {
        Busqueda b = new Busqueda();
        b.buscar(new ArrayList<Empresa>());
        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        System.out.println(l.toString()); //Muestra la estructura completa;
        List<Empresa> e = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e

        //TESTEO FILTRADO DE OMNIBUS
        FiltroMenorVelMax f1 = new FiltroMenorVelMax(30);
        Main m1 = new Main();
        ArrayList<Omnibus> omns = m1.posiblesOmn(e,f1);

        for(Omnibus o: omns){
            System.out.println(o.toString());
        }


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
    public ArrayList<Omnibus> posiblesOmn(List<Empresa> empresas_totales,Filtros<Omnibus> f1){

        //o1 sera el retorno
        ArrayList<Omnibus> o1 = new ArrayList<>();

        //creo una busqueda y la filtro con el parametro f1
        Busqueda b = new Busqueda();
        b.setFiltroOmnibus(f1);
        List<Empresa> empresas = b.buscar(empresas_totales);    

        for(Empresa e: empresas){
            for(Omnibus o: e.getOmnibus() ){
                if( o.getOcupados() == o.getCapacidad()){
                    continue;
                }else{
                    o1.add(o);
                }
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