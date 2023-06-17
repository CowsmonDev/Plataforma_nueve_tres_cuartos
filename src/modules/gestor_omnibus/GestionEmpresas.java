package modules.gestor_omnibus;

import data.empresas.ListaDeViajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import data.usuarios.Administrador;

import java.util.List;

public class GestionEmpresas{

    private final List<Administrador> admins;
    private final ListaDeViajes listaDeViajes = ListaDeViajes.getInstance();

    public GestionEmpresas(List<Administrador> admins) {
        this.admins = admins;
    }

    public void add(Empresa empresa) {
        this.open();
        this.listaDeViajes.add(empresa);
        this.close();
    }

    public void deleteEmpresa(String idEmpresa) {
        this.open();
        this.listaDeViajes.deleteEmpresa(idEmpresa);
        this.close();
    }

    public void add(Omnibus omnibus) {
        this.open();
        this.listaDeViajes.add(omnibus);
        this.close();
    }

    public void deleteOmnibus(String idOmnibus) {
        this.open();
        this.listaDeViajes.deleteOmnibus(idOmnibus);
        this.close();
    }

    public void add(Viaje viaje) {
        this.open();
        this.listaDeViajes.add(viaje);
        this.close();
    }

    public void deleteViaje(Viaje viaje) {
        this.open();
        this.listaDeViajes.deleteViaje(viaje);
        this.close();
    }

    public List<Administrador> getAdmins() {
        return admins;
    }

    public Administrador getAdmin(int admin){
        if(admin < admins.size())
            return admins.get(admin);
        return null;
    }

    private void open(){
        if(!this.getAdmins().isEmpty()){
            this.listaDeViajes.setEscritura(this.getAdmins().get(0), true);
        }
    }

    private void close(){
        if(!this.getAdmins().isEmpty()){
            this.listaDeViajes.setEscritura(this.getAdmins().get(0), false);
        }
    }
}
