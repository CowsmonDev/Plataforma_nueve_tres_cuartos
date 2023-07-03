package data.usuarios.estructura;


import data.empresas.estructura.viaje.Viaje;


public class Tarjeta implements data.db.cvs.CSVTransfrom<Tarjeta>{

    private String dniCliente;
    private String nroTarjeta;
    private String banco;
    private String marcaTarjeta;

    private double saldo = 0;

    private static int cantNumTarjeta = 16; //en ARG.

    public Tarjeta(String dniCliente, String nroTarjeta,String banco,String marcaTarjeta) {
        this.dniCliente = dniCliente;
        this.nroTarjeta = nroTarjeta;
        this.banco = banco;
        this.marcaTarjeta = marcaTarjeta;
    }

    public Tarjeta(){
        this("","","","");
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

    public static boolean verificarDatos(Tarjeta t){
        // Verifico el formato de la tarjeta para que sea valida

        String restriccion2 = "^[0-9]+$"; // solo numeros
        String restriccion = "^[a-zA-Z0-9 ]+$";// letras, mayus, num y espacios
        boolean esMarca = t.marcaTarjeta.matches(restriccion); // verifica que coincida con la expresion dada
        boolean esBanco = t.banco.matches(restriccion);
        boolean esNroTarjeta = t.nroTarjeta.matches(restriccion2);

        return (esNroTarjeta && (t.nroTarjeta.length() == Tarjeta.cantNumTarjeta) && esMarca && esBanco);// devuelve si cumple
    }

    public boolean verificarDatos(){
        return Tarjeta.verificarDatos(this);
    }

    public String getDniCliente() {
        return dniCliente;
    }

    @Override
    public Tarjeta transformFromCSV(String[] data) {
        if(data.length == 4)
            return new Tarjeta(data[0], data[1], data[2], data[3]);
        return null;
    }

    @Override
    public String transformToCSV() {
        return this.dniCliente + "," + this.nroTarjeta + "," + this.banco + "," + this.marcaTarjeta;
    }
}


