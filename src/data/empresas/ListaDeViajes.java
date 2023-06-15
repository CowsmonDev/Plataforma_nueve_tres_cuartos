package data.empresas;

import data.db.cvs.CSVLector;

import java.util.*;

public class ListaDeViajes {

    List<Viaje> viajes = new ArrayList<>();
    List<Empresa> empresas = new ArrayList<>();

    Map<String, Omnibus> hashOmnibus = new HashMap<>();
    Map<String, Empresa> hashEmpresas = new HashMap<>();

    private final CSVLector<Viaje> ReaderViajes = new CSVLector<Viaje>("src/data/db/data/VIAJE.csv");
    private CSVLector<Omnibus> ReaderOmnibus = new CSVLector<Omnibus>("src/data/db/data/OMNIBUS.csv");
    private CSVLector<Empresa> ReaderEmpresas = new CSVLector<Empresa>("src/data/db/data/EMPRESA.csv");

    private static ListaDeViajes instance = null;

    public static ListaDeViajes getInstance(){
        return (instance == null)? instance = new ListaDeViajes() : instance;
    }

    private ListaDeViajes(){
        ReaderEmpresas.getData(new Empresa()).forEach(this::add);
        if(!this.empresas.isEmpty())
            ReaderOmnibus.getData(new Omnibus()).forEach(this::add);
        if(!this.hashOmnibus.isEmpty())
            ReaderViajes.getData(new Viaje()).forEach(this::add);
    }

    public void add(Viaje v){
        if(hashOmnibus.containsKey(v.getIdOmnibus())){
            Omnibus o = hashOmnibus.get(v.getIdOmnibus());
            v.setOmnibus(o);
            o.getViajes().add(v);
            viajes.add(v);
        }
    }

    public void add(Omnibus o){
        if(hashEmpresas.containsKey(o.getIdEmpresa()) && !hashOmnibus.containsKey(o.getIdOmnibus())){
            Empresa e = hashEmpresas.get(o.getIdEmpresa());
            o.setEmpresa(e);
            e.getOmnibus().add(o);
            hashOmnibus.put(o.getIdOmnibus(), o);
        }
    }

    public void add(Empresa e){
        if(!hashEmpresas.containsKey(e.getIdEmpresa())){
            empresas.add(e);
            hashEmpresas.put(e.getIdEmpresa(), e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Viajes: \n");
        for (Empresa e : this.empresas){
            sb.append("Empresa:: id: ").append(e.getIdEmpresa()).append(" nombre: ").append(e.getNombre()).append("\n");
            for (Omnibus o : e.getOmnibus()){
                sb.append(" Omnibus:: ");
                sb.append("id: ").append(o.getIdOmnibus()).append(" velMax: ").append(o.getVelMax()).append(" ocupados: ").append(o.getOcupados()).append("\n");
                sb.append("    Viajes: \n");
                for (Viaje v : o.getViajes()){
                    sb.append("      origen: ").append(v.getOrigen()).append(" destino: ").append(v.getDestino()).append(" precio: ").append(v.getPrecio()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void close(){
        ReaderEmpresas.setData("IdEmpresa;Nombre", this.empresas);
        ReaderOmnibus.setData("IdOmnibus;capacidad;vel_max;IdEmpresa", this.hashOmnibus.values().stream().toList());
        ReaderViajes.setData("Origen;Destino;Fecha;Precio;IdOmnibus", this.viajes);
    }


}
