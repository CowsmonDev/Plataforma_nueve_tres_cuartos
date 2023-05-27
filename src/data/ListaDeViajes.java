package data;

import data.empresas.Empresa;
import data.empresas.Omnibus;
import data.empresas.Viaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaDeViajes {

    List<Viaje> viajes = new ArrayList<>();
    Map<String, Omnibus> omnibus = new HashMap<>();
    Map<String, Empresa> empresas = new HashMap<>();

    public void addViaje(Empresa e, Omnibus o, String origen, String destino, float precio){
        if(empresas.containsKey(e.getIdEmpresa()) && omnibus.containsKey(o.getIdOmnibus())){
            Viaje v = new Viaje(o, origen, destino, precio);
            o.getViajes().add(v);
            viajes.add(v);
        }
    }

    public void addOmnibus(String id_omnibus, Empresa e){
        if(empresas.containsKey(e.getIdEmpresa()) && !omnibus.containsKey(id_omnibus)){
            Omnibus o = new Omnibus(e, id_omnibus);
            omnibus.put(id_omnibus, o);
            e.getOmnibus().add(o);
        }
    }

    public void addEmpresas(String id_empresa, String nombre){
        empresas.put(id_empresa, new Empresa(id_empresa, nombre));
    }

}
