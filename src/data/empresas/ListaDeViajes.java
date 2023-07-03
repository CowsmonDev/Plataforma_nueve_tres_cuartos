package data.empresas;

import data.db.cvs.CSVLector;
import data.empresas.estructura.Viajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import data.usuarios.estructura.Administrador;

public class ListaDeViajes extends Viajes {

    private final CSVLector<Viaje> ReaderViajes = new CSVLector<Viaje>("src/data/db/data/VIAJE.csv");
    private CSVLector<Omnibus> ReaderOmnibus = new CSVLector<Omnibus>("src/data/db/data/OMNIBUS.csv");
    private CSVLector<Empresa> ReaderEmpresas = new CSVLector<Empresa>("src/data/db/data/EMPRESA.csv");

    private static ListaDeViajes instance = null;
    private boolean escritura;

    public static ListaDeViajes getInstance(){
        return (instance == null)? instance = new ListaDeViajes() : instance;
    }

    private ListaDeViajes(){
        this.escritura = true;
        ReaderEmpresas.getData(new Empresa()).forEach(this::add);
        if(!this.isEmptyEmpresa())
            ReaderOmnibus.getData(new Omnibus()).forEach(this::add);
        if(!this.isEmptyOmnibus())
            ReaderViajes.getData(new Viaje()).forEach(this::add);
        this.escritura = false;
    }

    @Override
    public void add(Viaje v) {
        if(escritura){
            super.add(v);
        }
    }

    @Override
    public void add(Omnibus o) {
        if(escritura) {
            super.add(o);
        }
    }

    @Override
    public void add(Empresa e) {
        if(escritura) {
            super.add(e);
        }
    }

    public void deleteEmpresa(String idEmpresa){
        if(escritura) {
            hashEmpresas.remove(idEmpresa);
        }
    }

    public void deleteOmnibus(String idOmnibus){
        if (escritura) {
            if (hashOmnibus.containsKey(idOmnibus)) {
                Omnibus omnibus = hashOmnibus.get(idOmnibus);
                omnibus.getEmpresa().getOmnibus().remove(omnibus);
                hashOmnibus.remove(idOmnibus);
            }
        }
    }

    public void deleteViaje(Viaje viaje){
        if (escritura) {
            for (Viaje v : viajes) {
                if (v.equals(viaje)) {
                    v.getOmnibus().getViajes().remove(v);
                    viajes.remove(v);
                    return;
                }
            }
        }
    }

    public void setEscritura(Administrador admin, boolean escritura){
        this.escritura = escritura;
    }

    public void close(){
        ReaderEmpresas.setData("IdEmpresa;Nombre", this.getEmpresas());
        ReaderOmnibus.setData("IdOmnibus;capacidad;vel_max;IdEmpresa", this.getOmnibuses());
        ReaderViajes.setData("Origen;Destino;Fecha;Precio;IdOmnibus", this.getViajes());
    }


}
