package empresas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataViajes {

    List<Viaje> viajes = new ArrayList<>();
    Map<String, Omnibus> omnibus = new HashMap<>();
    Map<String, Empresa> empresas = new HashMap<>();

    public void addData(String id_omnibus, String id_empresa, String origen, String destino, float precio){
        Omnibus o;
        Empresa e;
        if(!omnibus.containsKey(id_omnibus)){
            if(!empresas.containsKey(id_empresa)){
                e = new Empresa("Empresa " + id_empresa);
                o = new Omnibus(e, id_omnibus);
                empresas.put(id_empresa, e);
            }
            else{
                e = empresas.get(id_empresa);
                o = new Omnibus(e, id_omnibus);
            }
            e.addOmnibus(o);
            omnibus.put(id_omnibus, o);

        }else {
            o = omnibus.get(id_omnibus);
            e = empresas.get(id_empresa);
        }

        viajes.add(new Viaje(o, origen, destino, precio));
    }

}
