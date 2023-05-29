import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import data.busqueda.Busqueda;
import data.busqueda.filtros.Filtros;
import data.empresas.Empresa;
import data.empresas.Omnibus;

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

    //funcion para filtrado de omnibuses
    public ArrayList<Omnibus> posiblesOmn(Filtros<Omnibus> f1){

        //o1 sera el retorno
        ArrayList<Omnibus> o1 = new ArrayList<>();

        //creo una busqueda y la filtro con el parametro f1
        Busqueda b = new Busqueda();
        b.setFiltroOmnibus(f1);
        ArrayList<Omnibus> aux = new ArrayList<>();
        b.buscarOmnibus(aux);

        if(!aux.isEmpty()){
            for(Omnibus o: aux ){
                if( o.getOcupados() == o.getCapacidad()){
                    continue;
                }else{
                    o1.add(o);
                }
            }
        }

        return o1;
    }


}