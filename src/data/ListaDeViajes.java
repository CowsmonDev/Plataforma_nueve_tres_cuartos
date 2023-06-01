package data;

import data.db.CSVReader;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ReaderViajes.FullData(this);
        ReaderOmnibus.FullData(this);
        ReaderEmpresas.FullData(this);
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
        empresas.add(e);
        hashEmpresas.put(e.getIdEmpresa(), e);
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
            Viaje v = new Viaje(
                    line[0],
                    line[1],
                    Float.parseFloat(line[3]),
                    null
            );
            viajes.addViaje(line[4], v);
        }
    };

    private CSVReader ReaderOmnibus = new CSVReader("src/data/db/data/OMNIBUS.csv") {
        @Override
        protected void addData(ListaDeViajes viajes, String[] line) {
            Omnibus o = new Omnibus(line[0],
                    Integer.parseInt(line[1]),
                    Integer.parseInt(line[2]),
                    "Nombre: Insertar nombre en la base de datos :)",
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
