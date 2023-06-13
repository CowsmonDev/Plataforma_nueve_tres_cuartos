package data;

import data.db.cvs.CSVReader;
import data.db.cvs.CSVTransfrom;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.util.*;

public class ListaDeViajes {

    List<Viaje> viajes = new ArrayList<>();
    List<Empresa> empresas = new ArrayList<>();

    Map<String, Omnibus> hashOmnibus = new HashMap<>();
    Map<String, Empresa> hashEmpresas = new HashMap<>();

    private static ListaDeViajes instance = null;

    public static ListaDeViajes getInstance(){
        return (instance == null)? instance = new ListaDeViajes() : instance;
    }

    private ListaDeViajes(){
        ReaderEmpresas.FullData(this, new Empresa("", ""));
        ReaderOmnibus.FullData(this, new Omnibus("", 0, 0, ""));
        ReaderViajes.FullData(this, new Viaje());
    }

    public <T extends CSVTransfrom<T>> void add(T object){
        this.add(object);
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

    private final CSVReader<Viaje> ReaderViajes = new CSVReader<Viaje>("src/data/db/data/VIAJE.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, Viaje object, String[] line) {
            viajes.add(object.transformFromCSV(line));
        }
    };

    private CSVReader<Omnibus> ReaderOmnibus = new CSVReader<Omnibus>("src/data/db/data/OMNIBUS.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, Omnibus object, String[] line) {
            viajes.add(object.transformFromCSV(line));
        }
    };

    private CSVReader<Empresa> ReaderEmpresas = new CSVReader<Empresa>("src/data/db/data/EMPRESA.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, Empresa object, String[] line) {
            viajes.add(object.transformFromCSV(line));
        }
    };

}
