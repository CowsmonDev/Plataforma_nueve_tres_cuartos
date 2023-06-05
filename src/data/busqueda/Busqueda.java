package data.busqueda;

import java.util.ArrayList;
import java.util.List;

import data.busqueda.filtros.Filtros;
import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

public class Busqueda {

    private Filtros<Empresa> filtrosEmpresas = null;
    private Filtros<Omnibus> filtrosOmnibus = null;
    private Filtros<Viaje> filtrosViajes = null;


    public Busqueda setFiltroEmpresa(Filtros<Empresa> filtro){
        this.filtrosEmpresas = filtro;
        return this;
    }

    public Busqueda setFiltroOmnibus(Filtros<Omnibus> filtro){
        this.filtrosOmnibus = filtro;
        return this;
    }

    public Busqueda setFiltroViajes(Filtros<Viaje> filtro){
        this.filtrosViajes = filtro;
        return this;
    }

    public List<Empresa> buscar(List<Empresa> empresas_a_filtrar){
            List<Empresa> empresas = new ArrayList<>();
            for (Empresa e : empresas_a_filtrar){
                if(this.filtrosEmpresas != null && !this.filtrosEmpresas.filtrar(e)){
                    continue;
                }else{
                    Empresa empresa = new Empresa(e.getIdEmpresa(), e.getNombre());
                    List<Omnibus> omnibus = this.buscarOmnibus(e.getOmnibus(), empresa);
                    if(!omnibus.isEmpty()){
                        empresa.setOmnibus(omnibus);
                        empresas.add(empresa);
                    }
                }
            }
            return empresas;
    }
    private List<Omnibus> buscarOmnibus(List<Omnibus> omnibus_a_filtrar, Empresa empresa){
        List<Omnibus> omnibus = new ArrayList<>();
        for (Omnibus o : omnibus_a_filtrar){
            if(this.filtrosOmnibus != null && !this.filtrosOmnibus.filtrar(o)){
                continue;
            }else{
                Omnibus omnibus_nuevo = new Omnibus(o.getIdOmnibus(), o.getVelMax(), o.getCapacidad(), empresa);
                List<Viaje> viajes = buscarViajes(o.getViajes(), omnibus_nuevo);
                if(!viajes.isEmpty()){
                    omnibus_nuevo.setViajes(viajes);
                    omnibus.add(omnibus_nuevo);
                }
            }
        }
        return omnibus;
    }
    private List<Viaje> buscarViajes(List<Viaje> viajes_a_filtrar, Omnibus omnibus){
        List<Viaje> viajes = new ArrayList<>();
        for(Viaje v : viajes_a_filtrar){
            Viaje viaje = new Viaje(v.getOrigen(), v.getDestino(), v.getPrecio(), omnibus);
            viaje.setFecha(v.getFecha());
            if(this.filtrosViajes == null){
                viajes.add(viaje);
            }else if(this.filtrosViajes.filtrar(v)){
                viajes.add(viaje);
            }
        }
        return viajes;
    }
}