import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.empresas.ListaDeViajes;
import data.empresas.estructura.omnibus.Omnibus;
import data.usuarios.Usuarios;
import data.usuarios.estructura.Administrador;
import data.usuarios.estructura.Tarjeta;
import data.usuarios.estructura.Usuario;
import modules.BuscarViajes;
import data.empresas.estructura.omnibus.Asiento;
import data.empresas.estructura.viaje.Viaje;
import modules.GestionEmpresas;
import modules.Login;

public class Sistemas {

    private final Usuarios usuarios = Usuarios.getInstance();
    private final Login login = new Login();
    private final BuscarViajes buscarViajes = new BuscarViajes();
    private final GestionEmpresas gestionEmpresas = new GestionEmpresas(List.of(new Administrador()));

    public void startMenu() {
        String menuSinLogin = "1. Buscar viajes\n2. Iniciar sesion\n3. Registrarse\n4. Salir\nIngrese una opcion: ";
        String menuConLogin = "1. Buscar viajes\n2. Gestion Empresas\n3. Cerrar Sesion\n4. Salir\nIngrese una opcion: ";
        String menu = login.isLogin() ? menuConLogin : menuSinLogin;
        int menuSize = 4;

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menuSize){
            if(opcion == 1){
                buscarViajes();
            }else if(opcion == 2){
                if(login.isLogin()){ // Cerrar sesion
                    login.closeLogin();
                    menu = menuSinLogin;
                    System.out.println("Cerrado de sesion exitoso");
                }else{ // Iniciar sesion
                    boolean exito = login.loguearse();
                    while(!exito){
                        System.out.println("Algo salio mal, desea volver a intentarlo? (y/n)");
                        String respuesta = scanner.nextLine();
                        scanner.close();
                        if(respuesta.equals("y")){
                            exito = login.loguearse();
                        }else
                            break;
                    }
                    menu = menuConLogin;
                }
            }else {
                if(login.isLogin()){ // Gestion de Empresa
                    gestionEmpresas.startMenu();
                }else{ // Registrarse
                    boolean exito = login.registrarse();
                    while(!exito){
                        System.out.println("Algo salio mal, desea volver a intentarlo? (y/n)");
                        String respuesta = scanner.nextLine();
                        scanner.close();
                        if(respuesta.equals("y")){
                            exito = login.registrarse();
                        }else
                            break;
                    }
                }
            }
            System.out.println(menu);
            scanner = new Scanner(System.in);
            opcion = scanner.nextInt();
            scanner.close();
        }
        System.out.println("Gracias por usar el sistema");

        Usuarios.getInstance().close();
        ListaDeViajes.getInstance().close();
    }

    /**
     * Busca viajes y realiza la compra de los asientos seleccionados por el usuario logueado en el sistema.
     *
     */
    private void buscarViajes(){
        Omnibus omnibusIda = buscarViajes.run().getOmnibus();
        ArrayList<Asiento> listaAsientos = buscarViajes.seleccionarAsientos(omnibusIda);
        boolean seConfirmaCompra = realizarCompra(login.getUs(), omnibusIda.getViajes().get(0), listaAsientos);
        while (!login.isLogin()){
            System.out.println("Necesita estar logueado para terminar la compra\n");
            boolean exito = login.loguearse();
            if(!exito){
                System.out.println("Algo salio mal, desea volver a intentarlo? (y/n)");
                Scanner scanner = new Scanner(System.in);
                String respuesta = scanner.nextLine();
                if(respuesta.equals("n")){
                    scanner.close();
                    return;
                }
                scanner.close();
            }
        }
        if(seConfirmaCompra)
            for (Asiento a: listaAsientos)
                omnibusIda.ocuparAsiento(a.getNroAsiento());
        System.out.println("Asientos Ocupados: " + omnibusIda.getOcupados());
        System.out.println("Capacidad Maxima: " + omnibusIda.getCapacidad());
        omnibusIda.esquemaAsiento();
    }

    private double getMontoAPagar(Viaje v, int i)
    {
        return v.getPrecio() * i;
    }

    public boolean realizarCompra(Usuario pasajero,Viaje v, ArrayList<Asiento> asientosSeleccionados)
    {
        Scanner scanner = new Scanner(System.in);
        Tarjeta t;
        while(!pasajero.hasTarjeta()){
            pasajero.registrarTarjeta();
        }
        t = pasajero.getTarjeta();
        if (asientosSeleccionados.size() == 1)
            asientosSeleccionados.get(0).setPasajero(pasajero);
        else{
            for (Asiento asientosSeleccionado : asientosSeleccionados) {
                System.out.println("Ingrese el DNI del pasajero que ocupara el asiento");
                String DNI = scanner.nextLine();
                if (usuarios.exists(DNI)) {
                    asientosSeleccionado.setPasajero(usuarios.getUser(DNI));
                } else {
                    System.out.println("Ingrese nombre y apellido para el pasajero del asiento: " + asientosSeleccionado.getNroAsiento() + System.lineSeparator());
                    System.out.println("Nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Apellido:");
                    String apellido = scanner.nextLine();
                    Usuario u = new Usuario(nombre, "", apellido, DNI, null, "", "");
                    asientosSeleccionado.setPasajero(u);
                }
            }
        }
        System.out.println(getResumenCompra(asientosSeleccionados, t, v));
        System.out.println("Asientos seleccionados :");
        asientosSeleccionados.forEach(asiento -> System.out.println(asiento.getNroAsiento())); // Imprimir los asientos seleccionados
        System.out.println("Desea confirmar la compra?");
        System.out.println("1= Sí; 0=No");
        int i = Integer.parseInt(scanner.nextLine());
        if (i == 1) {
            pasajero.pagar(v, asientosSeleccionados.size());
            for (Asiento a : asientosSeleccionados) {
                v.getOmnibus().ocuparAsiento(a.getNroAsiento());
                pasajero.addCorreo(a, v);
            }
            return true;
        }
        else return false;

    }

    public String getResumenCompra(ArrayList<Asiento> asientosSeleccionados, Tarjeta t, Viaje v) {
        return ("Resumen de compra: " + System.lineSeparator() + "\n" +
                "Precio total de los pasajes: " + getMontoAPagar(v,asientosSeleccionados.size()) + "\n" +
                "Nuevo saldo de la tarjeta" + (t.getSaldo() - getMontoAPagar(v,asientosSeleccionados.size())));
    }


}
