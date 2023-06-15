package data.empresas;

import data.db.cvs.CSVLector;
import data.empresas.estructura.Viajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;

import java.util.*;

public class ListaDeViajes extends Viajes {

    private final CSVLector<Viaje> ReaderViajes = new CSVLector<Viaje>("src/data/db/data/VIAJE.csv");
    private CSVLector<Omnibus> ReaderOmnibus = new CSVLector<Omnibus>("src/data/db/data/OMNIBUS.csv");
    private CSVLector<Empresa> ReaderEmpresas = new CSVLector<Empresa>("src/data/db/data/EMPRESA.csv");

    private static ListaDeViajes instance = null;

    public static ListaDeViajes getInstance(){
        return (instance == null)? instance = new ListaDeViajes() : instance;
    }

    private ListaDeViajes(){
        ReaderEmpresas.getData(new Empresa()).forEach(this::add);
        if(!this.isEmptyEmpresa())
            ReaderOmnibus.getData(new Omnibus()).forEach(this::add);
        if(!this.isEmptyOmnibus())
            ReaderViajes.getData(new Viaje()).forEach(this::add);
    }

    public void close(){
        ReaderEmpresas.setData("IdEmpresa;Nombre", this.getEmpresas());
        ReaderOmnibus.setData("IdOmnibus;capacidad;vel_max;IdEmpresa", this.getOmnibuses());
        ReaderViajes.setData("Origen;Destino;Fecha;Precio;IdOmnibus", this.getViajes());
    }


}
