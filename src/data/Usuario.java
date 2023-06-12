package data;


import data.empresas.Asiento;
import data.empresas.Viaje;

import java.util.ArrayList;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Usuario {
    private String nombre;
    private String apellido;
    private long DNI;
    private Tarjeta tarjeta;
    private String claveAcceso;

    public Usuario(String nombre,String apellido, long DNI, Tarjeta tarjeta)
    {
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.tarjeta = tarjeta;
    }

    public String getApellido() {
        return apellido;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public long getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    // Log In
    public void registrarse() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        nombre = scanner.nextLine();

        System.out.println("Ingrese su apellido: ");
        apellido = scanner.nextLine();

        System.out.println("Ingrese su DNI: ");
        DNI = scanner.nextLong();

        // Validar datos personales
        if (!validarDatosPersonales()) {
            System.out.println("ERROR. DNI existente en el sistema.");
            return;
        }

        System.out.println("Ingrese clave de acceso. (debe contener al menos 8 caracteres, minuscula/s, mayuscula/s y al menos un numero)");
        String clave = scanner.nextLine();

        if(!validarClaveAcceso(clave)){
            do {
                System.out.println("Clave de acceso NO valida.");
                System.out.println("Ingrese clave de acceso. (debe contener al menos 8 caracteres, minuscula/s, mayuscula/s y al menos un numero)");
                clave = scanner.nextLine();
            } while (!validarClaveAcceso(clave));
        }

        System.out.println("Desea ingresar tarjeta? (y/n): ");
        String resp = scanner.nextLine().toLowerCase();
        if(resp.equals("y")){
            // tarjeta. Metodo para cargar tarjeta
        }else{
            tarjeta = null;
        }


        System.out.println("Registro completado exitosamente.");
        // GUARDAR EN DB
        scanner.close();
    }

    public boolean validarDatosPersonales(){
        // Chequear si ya existe el DNI en la db
        return true;
    }

    public boolean validarClaveAcceso(String claveAcceso) {
        if (claveAcceso.length() < 8) {
            return false;
        }

        boolean contieneMinuscula = false;
        boolean contieneMayuscula = false;
        boolean contieneNumero = false;

        for (char c : claveAcceso.toCharArray()) {
            if (Character.isLowerCase(c)) {
                contieneMinuscula = true;
            } else if (Character.isUpperCase(c)) {
                contieneMayuscula = true;
            } else if (Character.isDigit(c)) {
                contieneNumero = true;
            }

            // Si ya se cumple con los requisitos, se puede salir del bucle
            if (contieneMinuscula && contieneMayuscula && contieneNumero) {
                break;
            }
        }

        return contieneMinuscula && contieneMayuscula && contieneNumero;
    }



    public void cargarSaldoTarjeta(double i)
    {
        this.tarjeta.recargarTarjeta(i);
    }

    public void pagar(Viaje v, int i)
    {
        this.getTarjeta().calcularNuevoSaldo(v,i);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return DNI == usuario.DNI && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(tarjeta, usuario.tarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, DNI, tarjeta);
    }




}
