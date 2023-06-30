package modules;
import java.util.Scanner;

import data.empresas.ListaDeViajes;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.omnibus.Omnibus;
import data.empresas.estructura.viaje.Viaje;
import data.usuarios.estructura.Administrador;

import java.util.List;

public class GestionEmpresas{

    private final List<Administrador> admins;
    private final ListaDeViajes listaDeViajes = ListaDeViajes.getInstance();

    public GestionEmpresas(List<Administrador> admins) {
        this.admins = admins;
    }

    /**
     * Muestra un menu donde le da opciones para gestionar las empresas, los omnibus y los viajes
     */
    public void startMenu(){
        String menu = "1. Gestion de Empresas\n2. Gestion de Omnibus\n3. Gestion de Viajes\n4. Salir\nIngrese una opcion: ";
        int menuSize = 4;

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menuSize){
            switch (opcion){
                case 1:
                    gestionEmpresas();
                    break;
                case 2:
                    gestionOmnibus();
                    break;
                case 3:
                    gestionViajes();
                    break;
            }
            scanner = new Scanner(System.in);
            opcion = scanner.nextInt();
            scanner.close();
        }


    }

    private void gestionEmpresas(){
        String menu = "1. Agregar Empresa\n2. Eliminar Empresa\n3. Salir\nIngrese una opcion: ";
        int menuSize = 3;

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menuSize){
            switch (opcion) {
                case 1 -> this.add(Empresa.createEmpresa());
                case 2 -> {
                    System.out.println("Ingresa el id de la empresa a eliminar");
                    Scanner sc = new Scanner(System.in);
                    String idEmpresa = sc.nextLine();
                    sc.close();
                    this.deleteEmpresa(idEmpresa);
                }
            }
            scanner = new Scanner(System.in);
            opcion = scanner.nextInt();
            scanner.close();
        }
    }

    // repetir gestionEmpresa pero en un metodo aparte para gestionar omnibus con Omnibus.createOmnibus()
    private void gestionOmnibus(){
        String menu = "1. Agregar Omnibus\n2. Eliminar Omnibus\n3. Salir\nIngrese una opcion: ";
        int menuSize = 3;

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menuSize){
            switch (opcion) {
                case 1 -> this.add(Omnibus.createOmnibus());
                case 2 -> {
                    System.out.println("Ingresa el id del omnibus a eliminar");
                    Scanner sc = new Scanner(System.in);
                    String idOmnibus = sc.nextLine();
                    sc.close();
                    this.deleteOmnibus(idOmnibus);
                }
            }
            scanner = new Scanner(System.in);
            opcion = scanner.nextInt();
            scanner.close();
        }
    }

    private void gestionViajes(){
        String menu = "1. Agregar Viaje\n2. Eliminar Viaje\n3. Salir\nIngrese una opcion: ";
        int menuSize = 3;

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menuSize){
            switch (opcion) {
                case 1 -> this.add(Viaje.createViaje());
                case 2 -> this.deleteViaje(Viaje.createViaje());
            }
            scanner = new Scanner(System.in);
            opcion = scanner.nextInt();
            scanner.close();
        }
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
