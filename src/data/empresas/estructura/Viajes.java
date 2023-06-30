package data.empresas.estructura;

import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import modules.busqueda.Busqueda;
import modules.busqueda.BusquedaConEmpresa;
import modules.busqueda.filtros.ViajeIgual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Viajes {
    protected List<Viaje> viajes = new ArrayList<>();
    //protected List<Empresa> empresas = new ArrayList<>();

    protected Map<String, Omnibus> hashOmnibus = new HashMap<>();
    protected Map<String, Empresa> hashEmpresas = new HashMap<>();


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
            //empresas.add(e);
            hashEmpresas.put(e.getIdEmpresa(), e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Viajes: \n");
        for (Empresa e : this.hashEmpresas.values()){
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

    // Getters de Empresas
    public List<Empresa> getEmpresas() {
        return hashEmpresas.values().stream().toList();
    }

    public Empresa getEmpresa(String id_empresa){
        return this.hashEmpresas.get(id_empresa);
    }

    // Consultas de Empresas
    public boolean isEmptyEmpresa(){
        return this.hashEmpresas.isEmpty();
    }

    // Getters de Omnibus
    public List<Omnibus> getOmnibuses(){
        return this.hashOmnibus.values().stream().toList();
    }

    public Omnibus getOmnibus(String id_omnibus){
        return this.hashOmnibus.get(id_omnibus);
    }

    // Consultas de Omnibus
    public boolean isEmptyOmnibus(){
        return this.hashOmnibus.isEmpty();
    }

    // Getters de Viajes
    public List<Viaje> getViajes() {
        return viajes;
    }

    public Viaje getViaje(Viaje viaje){
        return this.viajes.stream().filter(v -> v.equals(viaje)).findFirst().orElse(null);
    }

    public boolean containsViaje(Viaje viaje){
        return this.getViaje(viaje) != null;
    }

}
