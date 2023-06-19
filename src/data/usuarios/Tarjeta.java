package data.usuarios;


import data.empresas.estructura.viaje.Viaje;


public class Tarjeta {

    private int nroTarjeta;
    private String banco;
    private String marcaTarjeta;

    private double saldo;

    public Tarjeta(int nroTarjeta,String banco,String marcaTarjeta) {
        this.nroTarjeta = nroTarjeta;
        this.banco = banco;
        this.marcaTarjeta = marcaTarjeta;
    }

    public int getNroTarjeta() {
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


    public boolean VerificarDatos (int nroTarjeta,String banco,String marcaTarjeta){
        // verifico comprobando que sean los datos de su tarjeta, para comparacion de datos
        return ((this.nroTarjeta == nroTarjeta) && (this.banco == banco )&& (this.marcaTarjeta == marcaTarjeta));
    }


}


