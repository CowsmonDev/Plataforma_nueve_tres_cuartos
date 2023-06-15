package modules.busqueda;

import java.util.ArrayList;
import java.util.List;

import modules.busqueda.filtros.Filtros;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;

public abstract class Busqueda<T> {

    private Filtros<Empresa> filtrosEmpresas = null;
    private Filtros<Omnibus> filtrosOmnibus = null;
    private Filtros<Viaje> filtrosViajes = null;


    public Busqueda<T> setFiltroEmpresa(Filtros<Empresa> filtro){
        this.filtrosEmpresas = filtro;
        return this;
    }

    public Busqueda<T> setFiltroOmnibus(Filtros<Omnibus> filtro){
        this.filtrosOmnibus = filtro;
        return this;
    }

    public Busqueda<T> setFiltroViajes(Filtros<Viaje> filtro){
        this.filtrosViajes = filtro;
        return this;
    }

    public Filtros<Empresa> getFiltrosEmpresas() {
        return filtrosEmpresas;
    }

    public Filtros<Omnibus> getFiltrosOmnibus() {
        return filtrosOmnibus;
    }

    public Filtros<Viaje> getFiltrosViajes() {
        return filtrosViajes;
    }

    public abstract T buscar(T empresas_a_filtrar);
}