import busqueda.Busqueda;
import busqueda.filtros.FiltrosFecha;
import empresas.Empresa;
import empresas.Viaje;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Busqueda b = new Busqueda();
        b.buscar(new ArrayList<Empresa>());

    }

    //Metodos de Seleccion de origen y destino
    public void listarCiudades(List<Empresa> empresas){

        List<String> origen = new ArrayList<>();
        List<String> destino = new ArrayList<>();

        Date fechaActual = new Date();
        Busqueda b = new Busqueda();
        FiltrosFecha f = new FiltrosFecha(fechaActual);
        b.setFiltroViajes(f);
        List<Empresa> e = b.buscar(empresas);

        for(int i=0; i<e.size(); i++){
            for(int j=0; j<e.get(i).getOmnibus().size(); j++){
                for (int k=0; j<e.get(i).getOmnibus().get(j).getViajes().size(); k++){
                    origen.add(e.get(i).getOmnibus().get(j).getViajes().get(k).getOrigen());
                    destino.add(e.get(i).getOmnibus().get(j).getViajes().get(k).getDestino());
                }
            }
        }

        System.out.println("Ciudades posibles:");
        for(int i=0; i<origen.size(); i++){
            if (i != origen.size()-1){
                System.out.print("Origen: " + origen.get(i) + "Destino: " + destino.get(i) +  ", ");
            }else{
                System.out.println("Origen: " + origen.get(i) + "Destino: " + destino.get(i));
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


}