package data.empresas.estructura.omnibus;

import data.db.cvs.CSVTransfrom;
import data.empresas.estructura.empresa.Empresa;
import data.empresas.estructura.viaje.Viaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Omnibus implements CSVTransfrom<Omnibus> {

    private String id_omnibus;
    private int velMax;
    private int ocupados;

    private ArrayList<Asiento> asientos;
    private List<Viaje> viajes;
    private Empresa empresa;
    private String idEmpresa;

    private Omnibus(String id_omnibus, int velMax, int asientos) {
        this.id_omnibus = id_omnibus;
        this.velMax = velMax;
        this.ocupados = 0;
        this.asientos = new ArrayList<>(asientos);
        for (int i = 0; i < asientos; i++)
            this.asientos.add(new Asiento(i + 1));
        this.viajes = new ArrayList<>();
    }

    public Omnibus() {
        this("", 0, 0);
        this.idEmpresa = "";
        this.empresa = null;
    }

    public Omnibus(String id_omnibus, int velMax, int asientos, String idEmpresa) {
        this(id_omnibus, velMax, asientos);
        this.idEmpresa = idEmpresa;
        this.empresa = null;
    }

    public Omnibus(String id_omnibus, int velMax, int asientos, Empresa empresa) {
        this(id_omnibus, velMax, asientos);
        this.empresa = empresa;
        this.idEmpresa = empresa.getIdEmpresa();
    }

    public boolean isOcupado(int a1){
        return this.asientos.get(a1).getOcupacion();
    }

    public String ocuparAsiento(int a1){
        if(!isOcupado(a1-1)){   // true asiento ocupado / false asiento desocupado
            this.ocupados++;
            this.asientos.get(a1-1).setOcupacion(true);
            return "Fue seleccionado con exito";
        }
        return "Este asiento estaba ocupado";
    }

    public void desocuparAsiento(int a1) {
            this.ocupados--;
            this.asientos.get(a1 - 1).setOcupacion(false);
    }

    public String toString(){
        String s1 = " IdOmnibus: " + this.getIdOmnibus() + 
                    " Velocidad Maxima: " + this.getVelMax() + " Capacidad: " + this.getCapacidad();
        return s1;
    }

    public void esquemaAsiento(){
        System.out.println("Esquema de asientos del ómnibus:");
        int i = 1;
        for(Asiento a : asientos){
            System.out.printf("%3d", i); // Número de asiento
            System.out.print(
                    (a.getOcupacion())?
                            "[X]"   // Asiento Ocupado
                            : "[ ]" // Asiento Desocupado
            );

            if(i % 4 == 0){
                System.out.println(); // Salto de línea cada 4 asientos
            }else if(i % 2 == 0){ // si no es un salto, comprobamos si es un pasillo
                System.out.print(" | "); // Pasillo
            }
            i++;
        }
    }

    // Implements interface CSVTransfrom
    @Override
    public Omnibus transformFromCSV(String[] data) {
        //viajes.addOmnibus(o, line[3]);
        return new Omnibus(data[0],
                Integer.parseInt(data[2]),
                Integer.parseInt(data[1]),
                data[3]);
    }

    @Override
    public String transformToCSV() {
        return  this.id_omnibus + ";" +
                this.asientos.size() + ";" +
                this.velMax + ";" +
                this.idEmpresa;
    }

    // Getters
    public int getOcupados() {
        return ocupados;
    }
    public int getCapacidad() {
        return asientos.size();
    }
    public int getVelMax() { return velMax; }
    public String getIdOmnibus() {
        return id_omnibus;
    }
    public String getNombre() { return id_omnibus; }
    public List<Viaje> getViajes() { return viajes; }
    public Empresa getEmpresa() { return empresa; }
    public String getIdEmpresa() { return idEmpresa; }

    // Setters
    public void setVelMax(int velMax) {
        this.velMax = velMax;
    }
    public void setNombre(String id_omnibus) {
        this.id_omnibus = id_omnibus;
    }
    public void setViajes(List<Viaje> viajes) { this.viajes = viajes; }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        this.idEmpresa = empresa.getIdEmpresa();
    }


    /**
     * Crea un omnibus con los datos ingresados por el usuario y lo retorna
     * @return Omnibus creado con los datos ingresados por el usuario
     */
    public static Omnibus createOmnibus(){
        // repetir lo mismo que createEmpresa en el archivo empresa pero con los datos de omnibus
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id del omnibus");
        String idOmnibus = scanner.nextLine();
        System.out.println("Ingrese la velocidad maxima del omnibus");
        int velMax = scanner.nextInt();
        System.out.println("Ingrese la cantidad de asientos del omnibus");
        int asientos = scanner.nextInt();
        System.out.println("Ingrese el id de la empresa a la que pertenece el omnibus");
        String idEmpresa = scanner.nextLine();
        return new Omnibus(idOmnibus, velMax, asientos, idEmpresa);
    }

}
