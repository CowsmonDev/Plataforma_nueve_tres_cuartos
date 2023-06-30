package data.usuarios.estructura;

public class Administrador extends Usuario{
    public Administrador(String nombre, String nickname, String apellido, String DNI, Tarjeta tarjeta, String contraseña, String mail) {
        super(nombre, nickname, apellido, DNI, tarjeta, contraseña, mail);
    }

    public Administrador(){
        super();
    }
}
