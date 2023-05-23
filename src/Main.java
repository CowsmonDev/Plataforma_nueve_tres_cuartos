import busqueda.Busqueda;
import empresas.Empresa;
import empresas.Viaje;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Busqueda b = new Busqueda();
        //b.buscar(new ArrayList<Empresa>());

        Main m = new Main();
        m.obtenerCiudades();



    }

    public void obtenerCiudades(List<Viaje> viajes){
        List<String> origen = new ArrayList<>();
        List<String> destino = new ArrayList<>();
        Date fechaActual = new Date();

        for(int i=0; i<viajes.size(); i++){
            if(fechaActual.after(viajes.get(i).getFecha())){

            }
        }

    }

}