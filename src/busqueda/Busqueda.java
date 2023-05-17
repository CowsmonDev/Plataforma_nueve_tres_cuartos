package busqueda;

import busqueda.filtros.FiltrosEmpresas;
import busqueda.filtros.FiltrosOmnibus;
import busqueda.filtros.FiltrosViajes;
import empresas.Empresa;
import empresas.Omnibus;
import empresas.Viaje;

import java.util.ArrayList;
import java.util.List;

public class Busqueda {

    private FiltrosEmpresas filtrosEmpresas = null;
    private FiltrosOmnibus filtrosOmnibus = null;
    private FiltrosViajes filtrosViajes = null;


    public Busqueda setFiltroEmpresa(FiltrosEmpresas filtro){
        this.filtrosEmpresas = filtro;
        return this;
    }

    public Busqueda setFiltroOmnibus(FiltrosOmnibus filtro){
        this.filtrosOmnibus = filtro;
        return this;
    }

    public Busqueda setFiltroViajes(FiltrosViajes filtro){
        this.filtrosViajes = filtro;
        return this;
    }

    public List<Empresa> buscar(List<Empresa> empresas_a_filtrar){
            List<Empresa> empresas = new ArrayList<>();
            for (Empresa e : empresas_a_filtrar){
                if(this.filtrosEmpresas != null && !this.filtrosEmpresas.filtrar(e)){
                    continue;
                }else{
                    List<Omnibus> omnibus = this.buscarOmnibus(e.getOmnibus());
                    if(!omnibus.isEmpty()){
                        empresas.add(e);
                    }
                }
            }
            return empresas;
    }
    private List<Omnibus> buscarOmnibus(List<Omnibus> omnibus_a_filtrar){
        List<Omnibus> omnibus = new ArrayList<>();
        for (Omnibus o : omnibus_a_filtrar){
            if(this.filtrosOmnibus != null && !this.filtrosOmnibus.filtrar(o)){
                continue;
            }else{
                List<Viaje> viajes = buscarViajes(o.getViajes());
                if(!viajes.isEmpty()){
                    omnibus.add(o);
                }
            }
        }
        return omnibus;
    }
    private List<Viaje> buscarViajes(List<Viaje> viajes_a_filtrar){
        List<Viaje> viajes = new ArrayList<>();
        if (this.filtrosViajes == null)
            return viajes_a_filtrar;
        for(Viaje v : viajes_a_filtrar){
            if(this.filtrosViajes.filtrar(v)){
                viajes.add(v);
            }
        }

        return viajes;
    }
}