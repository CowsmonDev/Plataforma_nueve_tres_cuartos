import busqueda.Busqueda;
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
    public void listarCiudades(List<Viaje> viajes){
        List<String> origen = new ArrayList<>();
        List<String> destino = new ArrayList<>();
        Date fechaActual = new Date();

        for(int i=0; i<viajes.size(); i++){
            if(fechaActual.after(viajes.get(i).getFecha())){
                origen.add(viajes.get(i).getOrigen());
                destino.add(viajes.get(i).getDestino());
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