package data;

import data.db.CSVReader;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        ReaderEmpresas.FullData(this);
        ReaderOmnibus.FullData(this);
        ReaderViajes.FullData(this);
    }

    public void addViaje(String id_omnibus, Viaje v){
        if(hashOmnibus.containsKey(id_omnibus)){
            Omnibus o = hashOmnibus.get(id_omnibus);
            o.getViajes().add(v);
            v.setOmnibus(o);
            viajes.add(v);
        }
    }

    public void addOmnibus(Omnibus o, String id_empresa){
        if(hashEmpresas.containsKey(id_empresa) && !hashOmnibus.containsKey(o.getIdOmnibus())){
            hashOmnibus.put(o.getIdOmnibus(), o);
            hashEmpresas.get(id_empresa).getOmnibus().add(o);
        }
    }

    public void addEmpresas(Empresa e){
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

    private CSVReader ReaderViajes = new CSVReader("src/data/db/data/VIAJE.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, String[] line) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            try {
                data = dateFormat.parse(line[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Viaje v = new Viaje(
                    line[0],
                    line[1],
                    Float.parseFloat(line[3]),
                    null
            );
            v.setFecha(data);
            viajes.addViaje(line[4], v);
        }
    };

    private CSVReader ReaderOmnibus = new CSVReader("src/data/db/data/OMNIBUS.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, String[] line) {
            Omnibus o = new Omnibus(line[0],
                    Integer.parseInt(line[1]),
                    Integer.parseInt(line[2]),
                    null);
            viajes.addOmnibus(o, line[3]);
        }
    };

    private CSVReader ReaderEmpresas = new CSVReader("src/data/db/data/EMPRESA.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, String[] line) {
            viajes.addEmpresas(new Empresa(line[0], line[1]));
        }
    };

}
