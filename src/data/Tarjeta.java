package data;

public class Tarjeta {

    private int nroTarjeta;
    private String banco;
    private String marcaTarjeta;

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
}
