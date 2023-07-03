package modules.busqueda;

import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import modules.busqueda.filtros.Filtros;

import java.util.ArrayList;
import java.util.List;

public class BusquedaConEmpresa extends Busqueda<List<Empresa>>{

    public List<Empresa> buscar(List<Empresa> empresas_a_filtrar){
            List<Empresa> empresas = new ArrayList<>();
            for (Empresa e : empresas_a_filtrar){
                if (this.getFiltrosEmpresas() == null || this.getFiltrosEmpresas().filtrar(e)) {
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
            if (this.getFiltrosOmnibus() == null || this.getFiltrosOmnibus().filtrar(o)) {
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
            if(this.getFiltrosViajes() == null || this.getFiltrosViajes().filtrar(v))
                viajes.add(viaje);
        }
        return viajes;
    }
}
