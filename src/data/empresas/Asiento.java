package data.empresas;
import data.usuarios.Usuario;

public class Asiento {
    private int nroAsiento;
    private Usuario pasajero;
    private boolean ocupacion;

    public Asiento(int nroAsiento)
    {
        this.nroAsiento = nroAsiento;
        this.ocupacion = false;
        this.pasajero = null;
    }

    public void setPasajero(Usuario pasajero) {
        this.pasajero = pasajero;
    }

    public void setOcupacion(boolean ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getNroAsiento() {
        return this.nroAsiento;
    }

    public boolean getOcupacion()
    {
        return this.ocupacion;
    }

}
