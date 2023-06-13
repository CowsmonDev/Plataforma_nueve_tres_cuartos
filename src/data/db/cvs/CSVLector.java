package data.db.cvs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVLector<T extends CSVTransfrom<T>> {
    private String line = "";
    private final String delimiter = ";";
    private String path = "";

    public CSVLector(String path){
        this.path = path;
    }

    public List<T> getData(T object){
        List<T> elements = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            if((line = br.readLine()) != null)
                return br.lines().map(l -> object.transformFromCSV(l.split(delimiter))).toList();
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    // ! SIN FUNCIONAR, NO USAR
    public void setData(List<T> object){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.path));
            String header = "idEmpresa; Nombre";
            bw.write(header);
            for (T o : object) {
                System.out.println(o.transformToCSV());
                bw.write(o.transformToCSV());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
