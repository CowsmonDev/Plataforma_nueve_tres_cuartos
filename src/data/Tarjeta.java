package data;

import data.empresas.Viaje;

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

    public void calcularNuevoSaldo(Viaje v, int i)
    {
        //Calcula y devuelve el nuevo saldo de la tarjeta en caso de que se realizara la compra.
    }
}
