package data.usuarios.estructura;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import data.empresas.estructura.viaje.Viaje;

public class Usuario {
    private String nombre;
    private String nickname;
    private String apellido;
    private long DNI;
    private Tarjeta tarjeta;
    private String claveAcceso;
    private String mail;

    public Usuario(String nombre,String nickname,String apellido, long DNI, Tarjeta tarjeta,String contraseña,String mail)
    {
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.tarjeta = tarjeta;
        this.nickname = nickname;
        this.claveAcceso =contraseña;
        this.mail = mail;
    }
    public Usuario(String nombre,String nickname,String apellido, long DNI,String contraseña,String mail){
        this(nombre,nickname,apellido,DNI,null,contraseña,mail);
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

    public String getNickname(){
        return nickname;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTarjeta(Tarjeta t){this.tarjeta = t;}

    // Log In
    public void registrarse(ArrayList<Usuario> us) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        nombre = scanner.nextLine();

        System.out.println("Ingrese su nickname: ");
        nombre = scanner.nextLine();

        System.out.println("Ingrese su apellido: ");
        apellido = scanner.nextLine();

        System.out.println("Ingrese su DNI: ");
        DNI = scanner.nextLong();

        // Validar datos personales
        if (!validarDatosPersonales(us)) {
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
        setClaveAcceso(clave);

        System.out.println("Desea ingresar tarjeta? (y/n): ");
        String resp = scanner.nextLine().toLowerCase();
        if(resp.equals("y")){
            this.registrarTarjeta();
        }else{
            tarjeta = null;
        }

        System.out.println("Ingresar correo electronico: ");
        String email = scanner.nextLine().toLowerCase();

        if(!validarMail(us, email)){
            do {
                System.out.println("Email ya registrado en el sistema.");
                System.out.println("Ingrese otro correo electronico: ");
                email = scanner.nextLine().toLowerCase();
            } while (!validarMail(us, clave));
        }
        setMail(email);


        System.out.println("Registro completado exitosamente.");
        scanner.close();
    }

    public boolean validarDatosPersonales(ArrayList<Usuario> us){
        boolean igual = false;
        for(int i=0; i<us.size(); i++){
            if((us.get(i).getDNI() == this.getDNI())/*||(us.get(i).getNickname() == this.getNickname())*/){
                igual = true;
                break;
            }
        }
        return igual;
    }

    public Usuario validarLogueo(ArrayList<Usuario> us){
        for(Usuario usuario: us){
            if(((usuario.getNickname() == this.getNickname()))){
                return usuario;
            }
        }
        return null;
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

    public boolean validarMail(ArrayList<Usuario> us, String email){
        boolean igual = false;
        for(int i=0; i<us.size(); i++){
            if(us.get(i).getMail().equals(email)){
                igual = true;
                break;
            }
        }
        return igual;
    }

    public void cargarSaldoTarjeta(double i)
    {
        this.tarjeta.recargarTarjeta(i);
    }

    public void pagar(Viaje v, int i)
    {
        this.getTarjeta().actualizarSaldo(v,i);
    }

    public void registrarTarjeta(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Ingrese los siguientes campos de la tarjeta: numero de tarjeta, banco emisor, marca de tarjeta de credito");
            int nroTarjeta = Integer.parseInt(scanner.nextLine());
            String bncoEmisor = scanner.nextLine();
            String marcaTarjeta = scanner.nextLine();
            Tarjeta t = new Tarjeta(nroTarjeta, bncoEmisor, marcaTarjeta);
            if(t.verificarDatos()){
                // Confirmacion de La tarjeta Ingresada
                System.out.println("Desea confirmar el ingreso de la tarjeta:" + System.lineSeparator() + "Nro:" + t.getNroTarjeta() + System.lineSeparator() + "Marca:" + t.getMarcaTarjeta() + System.lineSeparator() + "Banco:" + t.getBanco());
                System.out.println("1= Sí; 0=No");
                int i = Integer.parseInt(scanner.nextLine());
                if (i == 1){
                    this.tarjeta = t;
                    return;
                }

                // Reitero del Bucle
                System.out.println("Desea registrar la tarjeta nuevamente?");
                System.out.println("1= Sí; 0=No");
                i = Integer.parseInt(scanner.nextLine());
                if (i == 0){
                    return;
                }
            }else{
                System.out.println("Los datos ingresados no son validos, Ingreselo de vuelta");
            }
        }
    }

    public boolean hasTarjeta(){
        return tarjeta != null;
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

