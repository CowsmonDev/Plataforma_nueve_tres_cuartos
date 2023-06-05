package data;

import data.empresas.Asiento;
import data.empresas.Viaje;

import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
    private String nombre;
    private String apellido;
    private int DNI;
    private Tarjeta tarjeta;

    public Usuario(String nombre,String apellido, int DNI, Tarjeta tarjeta)
    {
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.tarjeta = tarjeta;
    }

    public String getApellido() {
        return apellido;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public int getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void cargarSaldoTarjeta(double i)
    {
        this.tarjeta.recargarTarjeta(i);
    }

    public void pagar(Viaje v, int i)
    {
        this.getTarjeta().calcularNuevoSaldo(v,i);
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
}
