package data.db.cvs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            if((line = br.readLine()) != null){
                while((line = br.readLine()) != null)
                    elements.add(object.transformFromCSV(line.split(delimiter)));
            }
            return elements;
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

}
