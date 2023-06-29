package modules;

import data.usuarios.Usuarios;
import data.usuarios.estructura.Usuario;

import java.util.Scanner;

public class Login {

    private final Usuarios clientes = Usuarios.getInstance();
    private Usuario us = null;

    public boolean loguearse(){
        String dni;
        String password;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su Dni: ");
        dni = scanner.nextLine();

        System.out.println("Ingrese su contraseña: ");
        password = scanner.nextLine();

        Usuario us = clientes.getUser(dni);

        if(us != null){  //valido que exista ese usuario
            if(us.getClaveAcceso().equals(password)){  //valido que se haya ingresado la contraseña correcta
                this.us = us;
                return true;
            }
        }
        return false;
    }



    public boolean registrarse() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su nickname: ");
        String nickName = scanner.nextLine();

        System.out.println("Ingrese su apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese su DNI: ");
        String DNI = scanner.nextLine();

        // Validar datos personales
        if (this.clientes.exists(DNI)) {
            System.out.println("ERROR. DNI existente en el sistema.");
            return false;
        }

        System.out.println("Ingrese clave de acceso. (debe contener al menos 8 caracteres, minuscula/s, mayuscula/s y al menos un numero)");
        String clave = scanner.nextLine();

        if(this.validarClaveAcceso(clave)){
            do {
                System.out.println("Clave de acceso NO valida.");
                System.out.println("Ingrese clave de acceso. (debe contener al menos 8 caracteres, minuscula/s, mayuscula/s y al menos un numero)");
                clave = scanner.nextLine();
            } while (!this.validarClaveAcceso(clave));
        }



        System.out.println("Ingresar correo electronico: ");
        String email = scanner.nextLine().toLowerCase();

        if(clientes.existsMail(email)){
            do {
                System.out.println("Email ya registrado en el sistema.");
                System.out.println("Ingrese otro correo electronico: ");
                email = scanner.nextLine().toLowerCase();
            } while (clientes.existsMail(email));
        }

        Usuario us = new Usuario(nombre,nickName, apellido, DNI, clave, email);

        System.out.println("Desea ingresar tarjeta? (y/n): ");
        String resp = scanner.nextLine().toLowerCase();
        if(resp.equals("y")){
            us.registrarTarjeta();
        }

        clientes.addUser(us);

        System.out.println("Registro completado exitosamente.");
        scanner.close();
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
            if (contieneMinuscula && contieneMayuscula && contieneNumero)
                return true;
        }

        return false;
    }

    public Usuario getUs() {
        return us;
    }

    public boolean isLogin(){
        return us != null;
    }

    public void closeLogin(){
        if(us != null){
            us = null;
        }
    }

}
