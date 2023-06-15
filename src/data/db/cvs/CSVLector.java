package data.db.cvs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVLector<T extends CSVTransfrom<T>> {
    private final String delimiter = ";";
    private String path = "";

    public CSVLector(String path){
        this.path = path;
    }

    public List<T> getData(T object){
        List<T> elements = new ArrayList<>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            if((line = br.readLine()) != null)
                elements = br.lines().map(l -> object.transformFromCSV(l.split(delimiter))).toList();
            br.close();
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    public void setData(String header, List<T> object){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.path));
            bw.write(header);
            for (T o : object) {
                bw.newLine();
                bw.write(o.transformToCSV());
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
