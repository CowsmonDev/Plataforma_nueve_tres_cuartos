package modules;

import data.usuarios.Usuarios;
import data.usuarios.estructura.Usuario;

import java.util.Scanner;

public class Login {

    private final Usuarios clientes = Usuarios.getInstance();
    private Usuario us = null;

    /**
     * Le solicita a el usuario que ingrese sus datos para poder loguearse
     * @return retorna un booleano que indica si el logueo fue exitoso o no
     */
    public boolean loguearse(){
        String dni;
        String password;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su Dni: " + System.lineSeparator());
        dni = scanner.nextLine();

        System.out.print("Ingrese su contraseña: " + System.lineSeparator());
        password = scanner.nextLine();

        scanner.close();

        Usuario us = clientes.getUser(dni);

        if(us != null){  //valido que exista ese usuario
            if(us.getClaveAcceso().equals(password)){  //valido que se haya ingresado la contraseña correcta
                this.us = us;
                return true;
            }
        }
        return false;
    }

    /**
     * Le solicita a el usuario que ingrese sus datos para poder registrarse
     * crea un nuevo usuario y lo agrega a la lista de usuarios
     * el usuario tiene la opcion de crear una tarjeta si lo desea
     * @return retorna un booleano que indica si el registro fue exitoso o no
     */
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

    /**
     * Valida que la clave de acceso cumpla con los requisitos
     * 8 caracteres, minuscula/s, mayuscula/s y al menos un numero
     * @param claveAcceso clave de acceso a validar si cumple con los requisitos o no
     * @return retorna un booleano que indica si la clave de acceso es valida o no
     */
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
