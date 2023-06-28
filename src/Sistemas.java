import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.empresas.ListaDeViajes;
import data.empresas.estructura.omnibus.Omnibus;
import data.usuarios.Usuarios;
import data.usuarios.estructura.Tarjeta;
import data.usuarios.estructura.Usuario;
import modules.BuscarViajes;
import data.empresas.estructura.omnibus.Asiento;
import data.empresas.estructura.viaje.Viaje;
import modules.Login;

class Menu{
    public static final List<String> opcionesSinLogin = new ArrayList<>(List.of("1. Iniciar sesion", "2. Registrarse", "3. Buscar viajes", "4. Salir"));
    public static final List<String> opcionesConLogin = new ArrayList<>(List.of("1. Buscar viajes", "2. Cerrar Session", "3. Salir"));
    public static final List<String> opcionesConLoginAdmin = new ArrayList<>(List.of("1. Buscar viajes", "2. Gestionar Empresas" ,"3. Cerrar Session", "4. Salir"));

    public static final String menuSinLogin = "1. Iniciar sesion\n2. Registrarse\n3. Buscar viajes\n4. Salir\nIngrese una opcion: ";
    public static final String menuConLogin = "1. Buscar viajes\n2. Cerrar Sesion\n3. Salir\nIngrese una opcion: ";
    public static final String menuConLoginAdmin = "1. Buscar viajes\n2. Gestionar Empresas\n3. Cerrar Sesion\n4. Salir\nIngrese una opcion: ";
}

public class Sistemas {

    private final Usuarios usuarios = Usuarios.getInstance();
    private final Login login = new Login();
    private final BuscarViajes buscarViajes = new BuscarViajes();

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

    private void menuSinLogin(int opcion){
        switch (opcion) {
            case 1 -> {
                boolean exito = login.loguearse();
                while(!exito){
                    System.out.println("Algo salio mal, desea volver a intentarlo? (y/n)");
                    Scanner scanner = new Scanner(System.in);
                    String respuesta = scanner.nextLine();
                    scanner.close();
                    if(respuesta.equals("y")){
                        exito = login.loguearse();
                    }else
                        return;
                }
            }
            case 2 -> buscarViajes();
        }
    }
    private void menuConLogin(int opcion){
        switch (opcion) {
            case 1 ->
                buscarViajes();
            case 2 -> {
                login.closeLogin();
                System.out.println("Cerrado de sesion exitoso");
            }
        }
    }

    public void run() {
        List<String> menu = Menu.opcionesSinLogin;

        System.out.println(Menu.menuSinLogin);
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        scanner.close();

        while (opcion > 0 && opcion < menu.size()){
            if(login.isLogin()) {
                menuConLogin(opcion);
                System.out.println(Menu.menuConLogin);
            }else{
                menuSinLogin(opcion);
                System.out.println(Menu.menuSinLogin);
            }
        }
        System.out.println("Gracias por usar el sistema");

        Usuarios.getInstance().close();
        ListaDeViajes.getInstance().close();
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
            for (int i = 0; i < asientosSeleccionados.size(); i++) {
                System.out.println("Ingrese el DNI del pasajero que ocupara el asiento");
                String DNI = scanner.nextLine();
                if(usuarios.exists(DNI)){
                    asientosSeleccionados.get(i).setPasajero(usuarios.getUser(DNI));
                }
                else
                {
                    System.out.println("Ingrese nombre y apellido para el pasajero del asiento: " + asientosSeleccionados.get(i).getNroAsiento() + System.lineSeparator());
                    System.out.println("Nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Apellido:");
                    String apellido = scanner.nextLine();
                    Usuario u = new Usuario(nombre,"",apellido,DNI,null,"","");
                    asientosSeleccionados.get(i).setPasajero(u);
                }
            }
        }
        getResumenCompra(asientosSeleccionados, t, v);
        System.out.println("Asientos seleccionados :");
        asientosSeleccionados.forEach(asiento -> System.out.println(asiento.getNroAsiento())); // Imprimir los asientos seleccionados
        System.out.println("Desea confirmar la compra?");
        System.out.println("1= Sí; 0=No");
        int i = Integer.parseInt(scanner.nextLine());
        if (i == 1) {
            pasajero.pagar(v, asientosSeleccionados.size());
            for (Asiento a : asientosSeleccionados) {
                v.getOmnibus().ocuparAsiento(a.getNroAsiento());
                // Envio de correo de confirmacion al usuario, con informacion detallada de la compra.
            }
            return true;
        }
        else return false;

    }

    public String getResumenCompra(ArrayList<Asiento> asientosSeleccionados, Tarjeta t, Viaje v) {
        return "Resumen de compra: " + System.lineSeparator() + "\n" +
                "Precio total de los pasajes: " + getMontoAPagar(v,asientosSeleccionados.size()) + "\n" +
                "Nuevo saldo de la tarjeta" + (t.getSaldo() - getMontoAPagar(v,asientosSeleccionados.size()));
    }


}
