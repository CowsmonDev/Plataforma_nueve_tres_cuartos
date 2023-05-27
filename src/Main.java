import java.text.SimpleDateFormat;
import java.util.*;

import busqueda.Busqueda;
import busqueda.filtros.Filtros;
import busqueda.filtros.FiltrosFecha;
import empresas.Empresa;
import empresas.Omnibus;
import empresas.Viaje;

public class Main {
    public static void main(String[] args) {
        Busqueda b = new Busqueda();
        b.buscar(new ArrayList<Empresa>());

    }

    //Metodos de Seleccion de origen y destino
    public void listarCiudades(List<Empresa> empresas_totales){

        List<String> origen = new ArrayList<>();
        List<String> destino = new ArrayList<>();
        //Set<String> origen = new HashSet<>();
        //Set<String> destino = new HashSet<>();

        Busqueda b = new Busqueda();
        b.setFiltroViajes(new FiltrosFecha(new Date()));
        List<Empresa> empresas = b.buscar(empresas_totales);

        System.out.println("Ciudades posibles:");
        for(Empresa e : empresas){
            for(Omnibus o : e.getOmnibus()){
                for(Viaje v : o.getViajes()){
                    origen.add(v.getOrigen());
                    destino.add(v.getDestino());
                    System.out.println("Origen: " + v.getOrigen() + "Destino: " + v.getDestino());
                }
            }
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

    public void elegirFechas() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elegir fecha ida: ");
        String ida = scanner.nextLine();
        System.out.println("Elegir fecha vuelta: ");
        String vuelta = scanner.nextLine();
        // String a Date?
        // Usuario selecciona fecha sin ser necesario un listado previo. Luego se filtra.
    }


}