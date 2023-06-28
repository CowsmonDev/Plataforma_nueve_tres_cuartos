package data.usuarios;

import data.db.cvs.CSVLector;
import data.usuarios.estructura.Tarjeta;
import data.usuarios.estructura.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuarios {

    private final CSVLector<Usuario> ReaderUsuarios = new CSVLector<Usuario>("src/data/db/data/USUARIO.csv");
    private final CSVLector<Tarjeta> ReaderTarjetas = new CSVLector<Tarjeta>("src/data/db/data/TARJETA.csv");

    Map<String, Usuario> usuarios = new HashMap<>();
    Map<String, Usuario> usuariosMail = new HashMap<>();

    // singleton
    private static Usuarios instance = null;

    private Usuarios() {
        ReaderUsuarios.getData(new Usuario()).forEach(this::addUser);
        ReaderTarjetas.getData(new Tarjeta()).forEach(this::addTarjeta);
    }

    public static Usuarios getInstance() {
        return instance == null ? instance = new Usuarios() : instance;
    }

    public void addUser(Usuario us) {
        if(!exists(us.getDNI())){
            usuarios.put(us.getDNI(),us);
            usuariosMail.put(us.getMail(),us);
        }
    }

    private void addTarjeta(Tarjeta tarjeta){
        if(tarjeta != null){
            usuarios.get(tarjeta.getDniCliente()).setTarjeta(tarjeta);
        }
    }

    public Usuario getUser(String dni){
        if(exists(dni))
            return usuarios.get(dni);
        return null;
    }

    public Usuario getUserMail(String mail){
        if(existsMail(mail))
            return usuariosMail.get(mail);
        return null;
    }

    public boolean exists(String dniUsuario){
        return usuarios.containsKey(dniUsuario);
    }

    public boolean existsMail(String mail){
        return usuariosMail.containsKey(mail);
    }

    public List<Usuario> getUsuarios(){
        return usuarios.values().stream().toList();
    }

    public void close(){
        ReaderUsuarios.setData("dni;nombre;apellido;mail;nickname;claveAcceso", this.getUsuarios());
        // Revisar como poder guardar las tarjetas
    }
}
