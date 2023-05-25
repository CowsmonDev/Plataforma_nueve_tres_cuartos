package data.empresas;

public class Empresa {

    private String id_empresa;
    private String nombre;

    public Empresa(String id_empresa, String nombre) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public String getIdEmpresa() { return id_empresa; }
}
