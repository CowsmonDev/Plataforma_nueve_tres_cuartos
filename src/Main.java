import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import data.ListaDeViajes;
import data.busqueda.Busqueda;
import data.busqueda.filtros.FiltroMenorVelMax;
import data.empresas.Empresa;
import data.empresas.Omnibus;

public class Main {
    public static void main(String[] args) {
        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> e = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e

        Sistemas s = new Sistemas();
        //s.listarCiudades(e);
        //s.elegirCiudades();
        for (Empresa emp : e) {
            for (Omnibus o : emp.getOmnibus()) {
                //System.out.println(o.toString());
            }
        }
        List<Empresa> empresas = s.posiblesOmn(e, new FiltroMenorVelMax(30));
        //System.out.println(empresas.size());
        for (Empresa emp : empresas) {
            for (Omnibus o : emp.getOmnibus()) {
                System.out.println(o.toString());
            }
        }
    }
}