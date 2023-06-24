package data.usuarios.estructura;


import data.empresas.estructura.viaje.Viaje;


public class Tarjeta {

    private String nroTarjeta;
    private String banco;
    private String marcaTarjeta;

    private double saldo = 0;

    private static int cantNumTarjeta = 16; //en ARG.

    public Tarjeta(String nroTarjeta,String banco,String marcaTarjeta) {
        this.nroTarjeta = nroTarjeta;
        this.banco = banco;
        this.marcaTarjeta = marcaTarjeta;
    }

    public String getNroTarjeta() {
        return this.nroTarjeta;
    }

    public String getBanco()
    {
        return this.banco;
    }

    public String getMarcaTarjeta() {
        return  this.marcaTarjeta;
    }

    public void recargarTarjeta(double i)
    {
        this.saldo = this.saldo + i;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void actualizarSaldo(Viaje v, int i) {
        this.saldo = this.saldo - (v.getPrecio() * i);
    }

    public boolean verificarDatos(){
        // Verifico el formato de la tarjeta para que sea valida


        String restriccion = "^[a-zA-Z0-9 ]+$";// letras, mayus, num y espacios
        boolean esMarca = this.marcaTarjeta.matches(restriccion); // verifica que coincida con la expresion dada
        boolean esBanco = this.banco.matches(restriccion);

        return ((nroTarjeta.length() == this.cantNumTarjeta) && esMarca && esBanco);// devuelve si cumple
    }

}


