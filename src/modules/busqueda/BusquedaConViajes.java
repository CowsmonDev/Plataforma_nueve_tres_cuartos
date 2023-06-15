package modules.busqueda;

import data.empresas.estructura.Viajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;

import java.util.List;

public class BusquedaConViajes extends Busqueda<Viajes>{

    public Viajes buscar(Viajes listaDeViajesTotales){
        Viajes listaDeViajes = new Viajes();
        List<Viaje> viajes_a_filtrar = listaDeViajesTotales.getViajes();
        for(Viaje v : viajes_a_filtrar){
            Omnibus o = v.getOmnibus();
            Empresa e = o.getEmpresa();
            if(this.getFiltrosViajes() == null || this.getFiltrosViajes().filtrar(v)){
                if(this.getFiltrosOmnibus() == null || this.getFiltrosOmnibus().filtrar(o)){
                    if(this.getFiltrosEmpresas() == null || this.getFiltrosEmpresas().filtrar(e)){
                        listaDeViajes.add(e);
                        listaDeViajes.add(o);
                        listaDeViajes.add(v);
                    }
                }
            }
        }
        return listaDeViajes;
    }
}
