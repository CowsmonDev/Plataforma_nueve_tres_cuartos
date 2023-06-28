package data.usuarios.estructura;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import data.db.cvs.CSVTransfrom;
import data.empresas.estructura.viaje.Viaje;

public class Usuario implements CSVTransfrom<Usuario> {
    private final String nombre;
    private final String nickname;
    private final String apellido;
    private final String DNI;
    private Tarjeta tarjeta;
    private String claveAcceso;
    private String mail;
    private ArrayList<String> correos;

    public Usuario(String nombre,String nickname,String apellido, String DNI, Tarjeta tarjeta,String contraseña,String mail)
    {
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.tarjeta = tarjeta;
        this.nickname = nickname;
        this.claveAcceso =contraseña;
        this.mail = mail;
        ArrayList<String> correos = new ArrayList<>();
    }
    public Usuario(String nombre,String nickname,String apellido, String DNI,String contraseña,String mail){
        this(nombre,nickname,apellido,DNI,null,contraseña,mail);
    }

    public Usuario() {
        this("","","","",null,"","");
    }

    public String getApellido() {
        return apellido;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public String getDNI() {
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

    public void addCorreo(String correo) {
        String confirm = "Compra de pasaje/s confirmada." + "\n" + correo;
        this.correos.add(confirm);
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
            String nroTarjeta = scanner.nextLine();
            String bncoEmisor = scanner.nextLine();
            String marcaTarjeta = scanner.nextLine();
            Tarjeta t = new Tarjeta(this.DNI, nroTarjeta, bncoEmisor, marcaTarjeta);
            if(Tarjeta.verificarDatos(t)){
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


    @Override
    public Usuario transformFromCSV(String[] data) {
        if(data.length == 6)
            return new Usuario(data[0], data[1], data[2], data[3], null, data[4], data[5]);
        return new Usuario();
    }

    @Override
    public String transformToCSV() {
        return getDNI() + ";" + getNombre() + ";" + getApellido() + ";" + getMail() + ";" + getNickname() + ";" + getClaveAcceso();
    }
}

